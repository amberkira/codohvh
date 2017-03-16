package com.codo.amber_sleepeanuty.library.base;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class LogicWrapper implements Comparable<LogicWrapper>{
    public int priority = 0;
    public Class<? extends BaseAppLogic> target;
    public BaseAppLogic mAppLogic;

    public LogicWrapper(Class<? extends BaseAppLogic> targetClass,int priority) {
        this.target = targetClass;
        this.priority = priority;
    }

    @Override
    public int compareTo(LogicWrapper o) {
        return o.priority-this.priority;
    }
}
