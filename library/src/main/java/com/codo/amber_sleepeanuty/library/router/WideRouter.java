package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ILocalRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.service.LocalConnectService;
import com.codo.amber_sleepeanuty.library.service.WideConnectService;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;
import com.codo.amber_sleepeanuty.library.decoretor.LocalServiceWrapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/12.
 */

public class WideRouter {

    private CodoApplication context;
    public static final String processName = "com.codo.amber_sleepeanuty.widerouter";
    private static WideRouter tempInstance = null;
    private static HashMap<String,LocalServiceWrapper> serviceWrapperMap;
    //为了移除服务时方便
    private HashMap<String,ILocalRouterAIDL> LocalRouterAIDLMap;
    private HashMap<String,ServiceConnection> LocalConnectServiceMap;
    public boolean isStop = false;

    private WideRouter(CodoApplication context){
        this.context = context;
        String requestProcessName = ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId());
        if(!processName.equals(requestProcessName)){
            throw new RuntimeException("you can't intial widerRouter in your current process");
        }
        serviceWrapperMap = new HashMap<>();
        LocalRouterAIDLMap = new HashMap<>();
        LocalConnectServiceMap = new HashMap<>();

    }

    public static synchronized WideRouter getInstance(@NonNull CodoApplication context){
        if(null == tempInstance){
            tempInstance = new WideRouter(context);
        }
        return tempInstance;
    }

    public boolean checkLocalRouterHasRegistered(final String domain) {
        LogUtil.d("CHECKing size¡!"+serviceWrapperMap.size());
        LocalServiceWrapper connectServiceWrapper = serviceWrapperMap.get(domain);
        if (null == connectServiceWrapper) {
            return false;
        }
        Class<? extends LocalConnectService> clazz = connectServiceWrapper.targetService;
        if (null == clazz) {
            return false;
        } else {
            return true;
        }
    }


        public static void registerLocalConnectService(String domain,Class<? extends LocalConnectService> register ){
            LogUtil.d("Register From!!!!!!!!"+domain);
        if(null==serviceWrapperMap) {
            serviceWrapperMap = new HashMap<>();
            LogUtil.d("Register new a HashMap!");
        }

            LocalServiceWrapper serviceWrapper = new LocalServiceWrapper(register);
            serviceWrapperMap.put(domain, serviceWrapper);
            LogUtil.d("Register!!!!!!!is puuuuuting domain: "+domain);

    }

/*    public Class<? extends LocalConnectService> fetchLocalConnectService(String domain){
        if(null!=serviceWrapperMap){
            LocalServiceWrapper wrapper = serviceWrapperMap.get(domain);
            if(null!=wrapper.targetService){
                return wrapper.targetService;
            }
        }
    }*/

    public  boolean connectLocalRouter(final String domain){
        LogUtil.d("connectLocalRouter domain:"+domain);
        boolean result = false;

        LocalServiceWrapper wrapper = serviceWrapperMap.get(domain);
        if(null == wrapper){
            return result;
        }

        Class<? extends LocalConnectService> targetSerive = wrapper.targetService;
        if(null == targetSerive){
            return result;
        }

        Intent bindIntent = new Intent(context,targetSerive);
        Bundle bundle= new Bundle();
        bindIntent.putExtras(bundle);

        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ILocalRouterAIDL mLocalRouter = ILocalRouterAIDL.Stub.asInterface(service);
                ILocalRouterAIDL temp = LocalRouterAIDLMap.get(domain);
                if(null==temp){
                    LocalRouterAIDLMap.put(domain,mLocalRouter);
                    LocalConnectServiceMap.put(domain,this);
                }
                try {
                    mLocalRouter.connectWideRouter();
                    LogUtil.d("connectWiderRouter!!!!!!!!");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LocalConnectServiceMap.remove(domain);
                LocalRouterAIDLMap.remove(domain);
            }
        };
        boolean s = context.bindService(bindIntent,connection,Context.BIND_AUTO_CREATE);
        LogUtil.d("From!!!!!!!!"+domain+"W 2 L binding:"+s);
        return true;
    }

    public boolean disconnectLocalRouter(String domain){
        if(TextUtils.isEmpty(domain)){
            return false;
        }else if(processName.equals(domain)){
            stopSelf();
            return true;
        }else if(null == LocalConnectServiceMap.get(domain)){
            return false;
        }else {
            ILocalRouterAIDL aidl = LocalRouterAIDLMap.get(domain);
            if(null != aidl){
                try {
                    aidl.stopWideRouter();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }else{
                context.unbindService(LocalConnectServiceMap.get(domain));
            }
            LocalRouterAIDLMap.remove(domain);
            LocalConnectServiceMap.remove(domain);
            return true;
        }
    }

    public void stopSelf() {
        isStop = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(null != LocalRouterAIDLMap){
                    ArrayList<String> temp = new ArrayList<>();
                    temp.addAll(LocalConnectServiceMap.keySet());
                    for (String domain:temp
                            ) {
                        ILocalRouterAIDL aidl = LocalRouterAIDLMap.get(domain);
                        try {
                            aidl.stopWideRouter();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        context.unbindService(LocalConnectServiceMap.get(domain));
                        LocalRouterAIDLMap.remove(domain);
                        LocalConnectServiceMap.remove(domain);
                    }
                    try {
                        Thread.sleep(1000);
                        context.stopService(new Intent(context, WideConnectService.class));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }else{
                    throw new NullPointerException("your AIDL map is null");
                }
            }
        }).start();
    }

    public ActionResult route(String domain,RouterRequest requst){
        if(isStop){
            ActionResult result =  new ActionResult(context,ActionResult.WIDEROUTER_NOT_WORKING,
                    "check your widerouter state , right now it's shut down",false);
            requst.isIdle.set(true);
            return result;
        }
        if(processName.equals(requst.getDomain())){
            ActionResult result = new ActionResult(context,ActionResult.CODE_ERROR,
                    "you cann't do this in the widerrouter process",false);
        }
        ILocalRouterAIDL target = LocalRouterAIDLMap.get(domain);
        if(null == target){
            if(!connectLocalRouter(domain)){
                ActionResult result = new ActionResult(context,ActionResult.LOCALROUTER_FAILED,
                        "Wrong with connecting localrouter!",false);
                requst.isIdle.set(true);
                return result;
            }else {
                int time = 0;
                while(true){
                    target = LocalRouterAIDLMap.get(domain);
                    if (null == target) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        time++;
                    } else {
                        break;
                    }
                    if (time >= 600) {
                        ActionResult result = new ActionResult(context,
                                ActionResult.CODE_CANNOT_BIND_LOCAL,"can't bind local",false);
                        requst.isIdle.set(true);
                        return result;
                    }
                }

            }

        }
        try {
            ActionResult result = target.route(requst);
            requst.isIdle.set(true);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            ActionResult result = new ActionResult(context,ActionResult.REMOTE_EXCEPTION,e.toString(),false);
            requst.isIdle.set(true);
            return  result;
        }
    }

    public boolean answerLocalRouterActionAsync(String domain,RouterRequest request){
        ILocalRouterAIDL target = LocalRouterAIDLMap.get(domain);
        if(target==null){
            LocalServiceWrapper wrapper = serviceWrapperMap.get(domain);
            Class<? extends LocalConnectService> clazz = wrapper.targetService;
            if(clazz==null){
                return false;
            }else{
                return true;
            }
        }else{
            try {
                return target.respondAsync(request);
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }
    }
}
