package com.codo.amber_sleepeanuty.module_login.presenter;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.model.SignUpModel;

/**
 * Created by amber_sleepeanuty on 2017/4/12.
 */

public class SignUpPresenter extends BasePresenter<Contract.ISignUpView>{
    Contract.ISignUpModel m;
    String mPhone;
    String mCode;
    String mRCode;
    String mVerification;


    public SignUpPresenter() {
        m = new SignUpModel();
    }

    public boolean register(Context context){

        if(!view.isVerified()){
            return false;
        }
        mPhone = view.getPhoneNumber();
        mRCode = view.getPassWordRecheck();
        mCode =  view.getPassWord();
        mVerification = view.getVerification();
        return false;
    }
    public String obtainVerification() {
        return m.obtainVerification();
    }
}
