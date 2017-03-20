package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ErrorAction;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRespond;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.base.BaseErrorAction;
import com.codo.amber_sleepeanuty.library.base.BaseProvider;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.security.Provider;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class LocalRouter{
    private CodoApplication mApplication;
    private String processName;
    public static LocalRouter mLocalRouter = null;
    private HashMap<String,BaseProvider> providerHashMap;
    public IWideRouterAIDL mWideRouterAIDL;
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

    public void registerProvider(Context context,String providerName, BaseProvider provider){
        if (ProcessNameUtil.getProcessName(context.getApplicationContext(),ProcessNameUtil.getMyProcessId())==processName&&null!=processName)
            providerHashMap.put(providerName,provider);
    }

    public  RouterRespond route(Context context, @NonNull RouterRequest request){
        RouterRespond respond = new RouterRespond();
        //local
        if(request.getDomain().equals(processName)){
            //sync
            if(!respond.isAsycn()){
                findAction(request);
                //TO DO respond
            }else{
                LocalTask task = new LocalTask(request);
                //TO DO respond
            }
        }else if(mApplication.isNeedMultipleProcess()){
            //throw exception;
        }
        //ipc
        else{
            mWideRouterAIDL
        }

        return respond;

    }

    public BaseAction findAction(RouterRequest request){
        BaseProvider target = providerHashMap.get(request.getProvider());
        ErrorAction error = new ErrorAction();
        if(null==target){
             error= new ErrorAction();
            return error;
        }else {

        }
        return
    }






}
