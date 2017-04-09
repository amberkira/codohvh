package com.codo.amber_sleepeanuty.library.util;

/**
 * Created by amber_sleepeanuty on 2017/4/9.
 */

public class CheckNotNull<T> {
    public static<T> T check(T param){
        if(null==param){
            throw new NullPointerException();
        }
        return param;
    }

    public static<T> T check(T param,String errorMsg){
        if(null==param){
            throw new NullPointerException(errorMsg);
        }
        return param;
    }
}
