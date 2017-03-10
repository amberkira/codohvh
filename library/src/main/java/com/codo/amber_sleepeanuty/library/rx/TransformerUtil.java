package com.codo.amber_sleepeanuty.library.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangstar on 2016/12/7.
 */

public class TransformerUtil {
    static final Observable.Transformer mTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    //Rxjava操作符封装
    public static <T> Observable.Transformer<T, T> SchedulersCompose() {
        return (Observable.Transformer<T, T>) mTransformer;
    }
}
