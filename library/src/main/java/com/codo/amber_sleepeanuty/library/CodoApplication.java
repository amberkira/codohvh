package com.codo.amber_sleepeanuty.library;

import android.app.Application;
import android.content.Intent;

import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.base.LogicWrapper;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.router.WideRouter;
import com.codo.amber_sleepeanuty.library.service.WideConnectService;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class CodoApplication extends Application {
    private static final String TAG = "CodoApplication";
    private HashMap<String,ArrayList<LogicWrapper>> logiclistMap;
    private ArrayList<LogicWrapper> logicList;
    private boolean isMultipleProcess;

    public LocalRouter mLocalRouter;

    private static CodoApplication instence;

    @Override
    public void onCreate() {
        super.onCreate();
        instence = this;
        init();
        connectWideRouter();
        initialLogicWithinSameProcess();
        dispatchLogicWithinSameProcess();
        instantiateLogicWithinSameProcess();
    }



    private void initialLogicWithinSameProcess() {
    }

    private void dispatchLogicWithinSameProcess(){
        if(null!=logiclistMap){
            logicList = logiclistMap.get(ProcessNameUtil.getProcessName(this,ProcessNameUtil.getMyProcessId()));
        }

    }

    private void instantiateLogicWithinSameProcess(){
        if(null!=logicList&&logicList.size()>0){
            Collections.sort(logicList);
            for (LogicWrapper wrapper:logicList
                 ) {
                if(null!=wrapper){
                    try {
                        wrapper.mAppLogic = wrapper.target.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if(wrapper.mAppLogic!=null){
                    wrapper.mAppLogic.setApplication(this);
                }
            }
        }

    }

    private void connectWideRouter() {
        if(isNeedMultipleProcess()){
            registerApplicationLogic(WideRouter.processName, 1000, WideRouterApplicationLogic.class);
            Intent it = new Intent(this, WideConnectService.class);
            startService(it);
        }

    }

    private void init() {
        mLocalRouter = LocalRouter.getInstance(this);
        logicList = new ArrayList<>();
        logiclistMap = new HashMap<>();
        isMultipleProcess = false;
    }

    public boolean registerApplicationLogic(String domain,int priority,Class<? extends BaseAppLogic> logic){
        boolean result = false;
        if(isNeedMultipleProcess()){
            if(null!=logiclistMap){
                ArrayList<LogicWrapper> logiclist = logiclistMap.get(domain);
                if(null==logiclist){
                    logiclist = new ArrayList<>();
                    logiclistMap.put(domain,logiclist);
                }
                if(logiclist.size()>0){
                    for(LogicWrapper wrapper:logiclist){
                        if(wrapper.getClass().equals(logic.getClasses())){
                            throw new RuntimeException(logic.getClasses()+"has registered");
                        }
                    }
                }
                LogicWrapper wrapper = new LogicWrapper(logic,priority);
                logiclist.add(wrapper);
                result = true;
            }
        }
        return result;
    }

    public void initAllProcessesRouter(){

    }


    public static CodoApplication getCodoApplication() {
        return instence;
    }

    public boolean isNeedMultipleProcess(){
        return isMultipleProcess;
    }
}
