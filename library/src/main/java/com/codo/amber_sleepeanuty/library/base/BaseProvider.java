package com.codo.amber_sleepeanuty.library.base;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public abstract class BaseProvider {
    private HashMap<String,BaseAction> mActionMap;
    private BaseAction defaultAction = null;
    public BaseProvider(){
        mActionMap = new HashMap<String, BaseAction>();
        registerActions();
    }
    public void registerAction(String name,BaseAction action){
        mActionMap.put(name,action);
    }
    public abstract void registerActions();
    public BaseAction findAction(String actionName){
        defaultAction = mActionMap.get(actionName);
        return defaultAction;
    }
}
