package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ILocalRouterAIDL;
import com.codo.amber_sleepeanuty.library.service.LocalConnectService;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;
import com.codo.amber_sleepeanuty.library.wrapper.LocalServiceWrapper;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/12.
 */

public class WideRouter {

    private CodoApplication context;
    private String processName = "com.codo.amber_sleepeanuty.widerouter";
    private WideRouter tempInstance;
    private HashMap<String,LocalServiceWrapper> serviceWrapperMap;
    private HashMap<String,ILocalRouterAIDL> LocalRouterAIDLMap;
    private HashMap<String,ServiceConnection> LocalConnectServiceMap;

    private WideRouter(CodoApplication context){
        this.context = context;
        String requestProcessName = ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId());
        if(!processName.equals(requestProcessName)){
            throw new RuntimeException("you can't intial widerRouter in your current process");
        }
        serviceWrapperMap = new HashMap<>();

    }

    public WideRouter getInstence(CodoApplication context){
        if(null!=context){
            tempInstance = new WideRouter(context);
        }
        return tempInstance;
    }


    public void registerLocalConnectService(String domain,Class<? extends LocalConnectService> register ){
        if(null==serviceWrapperMap) {
            serviceWrapperMap = new HashMap<>();
        }
        LocalServiceWrapper serviceWrapper = new LocalServiceWrapper(register);
        serviceWrapperMap.put(domain, serviceWrapper);
    }

/*    public Class<? extends LocalConnectService> fetchLocalConnectService(String domain){
        if(null!=serviceWrapperMap){
            LocalServiceWrapper wrapper = serviceWrapperMap.get(domain);
            if(null!=wrapper.targetService){
                return wrapper.targetService;
            }
        }
    }*/

    public boolean connectLocalRouter(final String domain){
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
                mLocalRouter.connectWideRouter();


            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(bindIntent,connection,Context.BIND_AUTO_CREATE);
        return true;
    }
}
