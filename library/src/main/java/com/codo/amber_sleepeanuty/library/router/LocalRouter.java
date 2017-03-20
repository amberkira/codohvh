package com.codo.amber_sleepeanuty.library.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRespond;
import com.codo.amber_sleepeanuty.library.base.BaseProvider;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.HashMap;

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

    public static RouterRespond route(){
        RouterRespond respond = new RouterRespond();
        return respond;

    }






}
