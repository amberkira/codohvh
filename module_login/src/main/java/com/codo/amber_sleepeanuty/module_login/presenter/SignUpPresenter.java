package com.codo.amber_sleepeanuty.module_login.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.library.bean.RegisterBean;
import com.codo.amber_sleepeanuty.library.bean.SMSBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.module_login.SignupActivity;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.model.SignUpModel;

import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/4/12.
 */

public class SignUpPresenter extends BasePresenter<Contract.ISignUpView>{
    Contract.ISignUpModel m;
    String mPhone;
    String mCode;
    String mRCode;
    String mVerification;
    String mInputVerification;


    public SignUpPresenter() {
        m = new SignUpModel();
    }

    public boolean register(Context context){

        if(!view.isVerified()){
            return false;
        }
        mInputVerification = view.getVerification();
        mPhone = view.getPhoneNumber();
        mRCode = view.getPassWordRecheck();
        mCode =  view.getPassWord();

        if(!mVerification.equals(mInputVerification))
            return false;

        if(!mRCode.equals(mCode))
            return false;


        APIService.Factory.createService((Activity)view)
                .register(mPhone,mCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                    }
                });

        return true;
    }

    public String obtainVerification() {
        mVerification = m.obtainVerification();
        mPhone = view.getPhoneNumber();
        APIService.Factory.createService((Activity)view)
                .sendSMS(mPhone,mVerification)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SMSBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SMSBean smsBean) {
                        if(smsBean.getSms().getMt().getStatus().equals("101")){
                            Toast.makeText((Activity)view,"短信已发送请稍等",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // TODO: 2017/4/24 插入发送验证请求短信代码
        return mVerification;
    }
}
