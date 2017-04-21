package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ErrorAction;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.base.BaseProvider;
import com.codo.amber_sleepeanuty.library.service.WideConnectService;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class LocalRouter {
    private CodoApplication mApplication;
    private String processName = "unknown_process_name";
    public static LocalRouter mLocalRouter = null;
    private HashMap<String, BaseProvider> providerHashMap = null;
    public IWideRouterAIDL mWideRouterAIDL;
    private static ExecutorService threadPool = null;
    public ServiceConnection wideServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mWideRouterAIDL = IWideRouterAIDL.Stub.asInterface(service);
            LogUtil.d("service: "+service.toString()+"-----     wideRouterAidl is created");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWideRouterAIDL = null;
        }
    };

    private LocalRouter(CodoApplication context) {
        processName = ProcessNameUtil.getProcessName(context, ProcessNameUtil.getMyProcessId());
        mApplication = context;
        providerHashMap = new HashMap<>();
        if (mApplication.isNeedMultipleProcess() && !WideRouter.processName.equals(processName)) {
            connectWideRouterService();
        }
    }

    public synchronized static LocalRouter getInstance(CodoApplication context) {
        if (null == mLocalRouter) {
            mLocalRouter = new LocalRouter(context);
        }
        return mLocalRouter;
    }

    public void connectWideRouterService() {
        Intent it = new Intent(mApplication, WideConnectService.class);
        it.putExtra("domain", processName);
        boolean s = mApplication.bindService(it, wideServiceConnection, Context.BIND_AUTO_CREATE);
        LogUtil.d(processName+"        T 2 W bingding !!!!!!! is  "+s);
    }

    public void stopWideRouterService() {
        if (null == mWideRouterAIDL) {
            return;
        }
        mApplication.unbindService(wideServiceConnection);
        mWideRouterAIDL = null;
    }

    public boolean checkWideRouterService() {
        boolean result = false;
        if (null != mWideRouterAIDL) {
            result = true;
        }
        return result;
    }

    public boolean answerWideRouterAsync(RouterRequest request){
        if(processName==request.getDomain()&&checkWideRouterService()){
            return mLocalRouter.findAction(request).isAsync();
        }else{
            return true;
        }

    }

    public void registerProvider(String providerName, BaseProvider provider) {
            providerHashMap.put(providerName, provider);
    }


    private static synchronized ExecutorService getThreadPool() {
        if (null == threadPool) {
            threadPool = Executors.newCachedThreadPool();
        }
        return threadPool;
    }


    public ActionResult route(Context context, RouterRequest request) throws Exception {
        ActionResult result = new ActionResult();
        if (request.getDomain().equals(processName)) {
            BaseAction targetAction = findAction(request);
            //action finding mission has completed;
            boolean ismAsync = targetAction.isAsync();
            result.isActionAsync = ismAsync;
            //sync
            if (!ismAsync) {
                result = targetAction.invoke(context, request);
            } else {
                //async
                LocalTask task = new LocalTask(request, targetAction);
                result.Holder = getThreadPool().submit(task);
            }
            //after we has completed the total action
            request.isIdle.set(true);
        } else if (!mApplication.isNeedMultipleProcess()) {
            throw new Exception("请确认您应用是否需要多进程！如果是，请在application中修改isNeedMultipleProcess值");
        }
        //ipc
        else {
            //check widerouter has been pepared.
            String targetDomain = request.getDomain();
            boolean isConnectWide = checkWideRouterService();
            if (isConnectWide) {
                result.isActionAsync = mWideRouterAIDL.respondAsync(request);
                LogUtil.d("我们已经链接上了！进来确认下是否异步！！ 异步："+result.isActionAsync
                +"  request的请求是:"+request.getDomain()+"   动作是："+request.getAction()
                );
            } else {
                //widerouter connection is quite a mission costing time which is needed another thread to approach our demand.
                ConnectWideRouterServiceTask task = new ConnectWideRouterServiceTask(request);
                result.Holder = getThreadPool().submit(task);
                result.isActionAsync = true;
                return result;
            }
            //async
            if (result.isActionAsync) {
                WideTask task = new WideTask(request);
                result.Holder = getThreadPool().submit(task);
                LogUtil.d("进入wideTask");
            }//sync
            else {
                result = mWideRouterAIDL.route(request);
            }
        }

        return result;

    }


    public BaseAction findAction(RouterRequest request) {
        BaseProvider target = providerHashMap.get(request.getProvider());
        if (null == target) {
            ErrorAction error = new ErrorAction(false, ActionResult.PROVIDER_NOT_FOUND, "NO SUCH PROVIDERS!!!");
            return error;
        } else {
            BaseAction action = target.findAction(request.getAction());
            if (null == action) {
                ErrorAction error = new ErrorAction(false, ActionResult.ACTION_NOT_FOUND, "NO SUCH ACTIONS");
                return error;
            }
            return action;
        }
    }

    private class LocalTask implements Callable<ActionResult> {
        private RouterRequest request;
        private BaseAction action;
        private ActionResult result;
        private Context context;

        public LocalTask(RouterRequest request, BaseAction action) {
            this.request = request;
            this.action = action;
        }

        @Override
        public ActionResult call() throws Exception {
            result = action.invoke(context, request);
            return result;
        }
    }

    private class WideTask implements Callable<ActionResult> {
        private RouterRequest request;
        private String domain;

        public WideTask(RouterRequest request) {
            this.request = request;
        }

        @Override
        public ActionResult call() throws Exception {
            domain = request.getDomain();
            return mWideRouterAIDL.route(request);
        }
    }

    /**
     * 该task作用是连接widerouter并且执行寻址工作
     * 因为已经另起线程了，所以我们这里也不需要再关心action执行是否为异步了
     */
    private class ConnectWideRouterServiceTask implements Callable<ActionResult> {
        private RouterRequest request;
        //设置连接超时时间为600ms
        private int Timeout = 600;

        public ConnectWideRouterServiceTask(RouterRequest request) {
            this.request = request;
        }

        @Override
        public ActionResult call() throws Exception {
            ActionResult result = new ActionResult();
            connectWideRouterService();
            while (true) {
                int time = 0;
                if (null == mWideRouterAIDL) {
                    LogUtil.d("失败！！！！！！！");
                    Thread.sleep(50);
                    ++time;
                } else {
                    break;
                }
                if (time > Timeout) {
                    LogUtil.d(" 没脸上啊！！！");
                    result.isActionAsync = true;
                    result.setCode(ActionResult.WIDEROUTER_NOT_CONNECTED);
                    result.setMsg("ops,we cann't reach the widerouterservice");
                    return result;
                }
            }
            LogUtil.d("连上了啦！！！！！！！");
            result = mWideRouterAIDL.route(request);
            LogUtil.d("msg: "+result.getMsg()+"   code"+result.getCode());
            return result;
        }
    }
}