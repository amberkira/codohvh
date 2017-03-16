package com.codo.amber_sleepeanuty.library.router;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.CodoApplication;
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




}
