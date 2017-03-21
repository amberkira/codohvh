package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ErrorAction;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.base.BaseProvider;
import com.codo.amber_sleepeanuty.library.service.WideConnectService;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class LocalRouter{
    private CodoApplication mApplication;
    private String processName;
    public static LocalRouter mLocalRouter = null;
    private HashMap<String,BaseProvider> providerHashMap;
    public IWideRouterAIDL mWideRouterAIDL;
    private static ExecutorService threadPool = null;
    public ServiceConnection wideServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mWideRouterAIDL = IWideRouterAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWideRouterAIDL = null;
        }
    };

    private LocalRouter(CodoApplication context) {
        processName = ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId());
        mApplication = context;
        providerHashMap = new HashMap<>();
    }

    public synchronized static LocalRouter getInstance(CodoApplication context){
        if(null!=context){
            mLocalRouter = new LocalRouter(context);
        }
        return mLocalRouter;
    }

    public void connectWideRouterService(){
        Intent it = new Intent(mApplication, WideConnectService.class);
        it.putExtra("domain",processName);
        mApplication.bindService(it,wideServiceConnection,Context.BIND_AUTO_CREATE);
    }

    public void stopWideRouterService(){
        if (null==mWideRouterAIDL){
            return;
        }
        mApplication.unbindService(wideServiceConnection);
        mWideRouterAIDL = null;
    }

    public boolean checkWideRouterService(){
        boolean result = false;
        if(null!=mWideRouterAIDL){
            result = true;
        }
        return result;
    }

    public void registerProvider(Context context,String providerName, BaseProvider provider){
        if (ProcessNameUtil.getProcessName(context.getApplicationContext(),ProcessNameUtil.getMyProcessId())==processName&&null!=processName)
            providerHashMap.put(providerName,provider);
    }


    private static synchronized ExecutorService getThreadPool() {
        if (null == threadPool) {
            threadPool = Executors.newCachedThreadPool();
        }
        return threadPool;
    }


    public ActionResult route(Context context, @NonNull RouterRequest request) throws Exception {
        //RouterRespond respond = new RouterRespond();
        //local
        ActionResult result = new ActionResult();
        if(request.getDomain().equals(processName)){
            BaseAction targetAction= findAction(request);
            request.isIdle.set(true);
            //action finding mission has completed;
            boolean ismAsync = targetAction.isAsync();
            result.isActionAsync = ismAsync;
            //sync
            if(!ismAsync){
                result = targetAction.invoke(context,request);
            }else{
                //async
                LocalTask task = new LocalTask(request,targetAction);
                result.Holder =getThreadPool().submit(task);
            }
        }else if(!mApplication.isNeedMultipleProcess()){
            throw new Exception("请确认您应用是否需要多进程！如果是，请在application中修改isNeedMultipleProcess值");
        }
        //ipc
        else{
            boolean isConnectWide = checkWideRouterService();
            if(!isConnectWide){
                connectWideRouterService();
            }
        }

        return result;

    }

    public BaseAction findAction(RouterRequest request){
        BaseProvider target = providerHashMap.get(request.getProvider());
        ErrorAction error = new ErrorAction();
        if(null==target){
            error= new ErrorAction(false,ActionResult.PROVIDER_NOT_FOUND,"NO SUCH PROVIDERS!!!");
            return error;
        }else {
            BaseAction action = target.findAction(request.getAction());
            if(null==action){
                error = new ErrorAction(false,ActionResult.ACTION_NOT_FOUND,"NO SUCH ACTIONS");
            }
            return action;
        }
    }

    private class LocalTask implements Callable<ActionResult>{
        private RouterRequest request;
        private BaseAction action;
        private ActionResult result;
        private Context context;

        public LocalTask(RouterRequest request,BaseAction action) {
            this.request = request;
            this.action = action;
        }

        @Override
        public ActionResult call() throws Exception {
            result = action.invoke(context,request);
            return result;
        }
    }

    private class ConnectWideRouterServiceTask implements Callable<ActionResult>{
        private Context context;
        private RouterRequest request;
        //设置连接超时时间为600ms
        private int Timeout = 600;

        public ConnectWideRouterServiceTask(Context context,RouterRequest request) {

        }

        @Override
        public ActionResult call() throws Exception {
            return null;
        }
    }






}
