package com.codo.amber_sleepeanuty.library.base;

/**
 * Created by amber_sleepeanuty on 2017/6/30.
 */

public class BaseEvent<T> {
    private T t;

    public BaseEvent(T t) {
        this.t = t;
    }

    public T getEvent(){
        return t;
    }

    public void setEvent(T t){
        this.t = t;
    }
}
