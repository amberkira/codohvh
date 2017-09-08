package com.codo.amber_sleepeanuty.library.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.DailyBean;
import com.codo.amber_sleepeanuty.library.bean.FeedbackCheckBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.util.SpUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/9/7.
 */

public class CheckService extends IntentService {

    private static final String THREAD_NAME = "checkthread";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param  Used to name the worker thread, important only for debugging.
     */
    public CheckService() {
        super(THREAD_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intesnt) {
        APIService.Factory.createService(CodoApplication.getCodoApplication())
                .dailyAttendence(SpUtil.getString(Constant.USER_NAME,null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedbackCheckBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FeedbackCheckBean feedbackCheckBean) {
                        if (feedbackCheckBean.getSignin().equals("ok")){
                            Toast.makeText(CodoApplication.getCodoApplication(),"签到成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CodoApplication.getCodoApplication(),"签到失败",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}

