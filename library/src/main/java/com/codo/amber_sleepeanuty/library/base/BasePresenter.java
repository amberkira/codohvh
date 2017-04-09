package com.codo.amber_sleepeanuty.library.base;


/**
 * Created by amber_sleepeanuty on 2017/4/7.
 */

public abstract class BasePresenter<T> {
    public T view;

    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }

}
