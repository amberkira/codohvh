package com.codo.amber_sleepeanuty.library;

import android.app.Application;

import com.codo.amber_sleepeanuty.library.base.LogicWrapper;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class CodoApplication extends Application {
    private HashMap<String,ArrayList<LogicWrapper>> logicMap;
    private ArrayList<LogicWrapper> logicList;
    public LocalRouter mLocalRouter;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        connectWideRouter();
        initialLogicWithinSameProcess();
        dispatchLogicWithinSameProcess();
        instantiateLogicWithinSameProcess();
    }



    private void initialLogicWithinSameProcess() {
    }

    private void dispatchLogicWithinSameProcess(){

    }

    private void instantiateLogicWithinSameProcess(){

    }

    private void connectWideRouter() {
    }

    private void init() {
        mLocalRouter = LocalRouter.getInstance(this);
        logicList = new ArrayList<>();
        logicMap = new HashMap<>();
    }
}
