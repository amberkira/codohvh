package com.codo.amber_sleepeanuty.library.rx;

import com.codo.amber_sleepeanuty.library.base.IBaseView;
import com.codo.amber_sleepeanuty.library.network.RetrofitUtil;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by zhangstar on 2016/11/16.
 */

public class SubscribeCall<T, V extends IBaseView> extends Subscriber<T> {
    private boolean isSimpleSubscribe = false;
    private V baseView;
    private SimpleSubscribe simpleSusbscribe;
    private Action0 onComplete;
    private Action0 onStart;
    private Action1<? super T> onNext;
    private Action1<Throwable> onError;

    public SubscribeCall(V baseView) {
        this.baseView = baseView;
    }


    public SubscribeCall(V baseView, SimpleSubscribe<T> simpleSusbscribe) {
        this(baseView);
        this.simpleSusbscribe = simpleSusbscribe;
        isSimpleSubscribe = true;
    }

    public SubscribeCall(V baseView, Action1<? super T> onNext) {
        this(baseView);
        if (onNext == null)
            throw new NullPointerException("onNext 不能为空！");
        this.onNext = onNext;
    }

    public SubscribeCall(V baseView, Action1<? super T> onNext, Action1<Throwable> onError) {
        this(baseView, onNext);
        if (onError == null)
            throw new NullPointerException("onError 不能为空！");
        this.onError = onError;
    }

    public SubscribeCall(V baseView, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onComplete) {
        this(baseView, onNext, onError);
        if (onComplete == null)
            throw new NullPointerException("onComplete 不能为空！");
        this.onComplete = onComplete;
    }

    public SubscribeCall(V baseView, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onComplete, Action0 onStart) {
        this(baseView, onNext, onError, onComplete);
        if (onStart == null) {
            throw new NullPointerException("onStart 不能为空！");
        }
        this.onStart = onStart;
    }


    @Override
    public void onStart() {
        if (isSimpleSubscribe)
            simpleSusbscribe.onStart();
        else {
            if (onStart != null)
                onStart.call();
        }
    }


    @Override
    public void onCompleted() {
        if (isSimpleSubscribe)
            simpleSusbscribe.onComleted();
        else {
            if (onComplete != null)
                onComplete.call();
        }
    }

    @Override
    public void onError(Throwable e) {

        RetrofitUtil.resolveError(e);
        if (isSimpleSubscribe) {
            simpleSusbscribe.onError(e);
        } else {
            baseView.onError();
            if (onError != null) {
                onError.call(e);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (isSimpleSubscribe) {
            simpleSusbscribe.onNext(t);
        } else {
            baseView.onSuccess(t);
            if (onNext != null)
                onNext.call(t);
        }
    }

    interface SimpleSubscripbe<T> {
        void onNext(T data);

        void onError(Throwable throwable);

        void onComleted();

        void onStart();
    }

    public static class SimpleSubscribe<T> implements SimpleSubscripbe<T> {

        @Override
        public void onNext(T data) {

        }

        @Override
        public void onError(Throwable throwable) {
        }

        @Override
        public void onComleted() {
        }

        @Override
        public void onStart() {
        }
    }
}
