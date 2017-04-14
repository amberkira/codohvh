package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.presenter.LoginPresenter;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action3;
import rx.functions.Action5;
import rx.functions.Func8;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class LoginActivity extends Activity implements Contract.ILoginView{

    public LoginPresenter mLoginPresenter;
    protected Button mBtn_Signup;
    protected Button mBtn_Login;
    protected EditText mTx_ID;
    protected EditText mTx_Pass;
    protected CheckBox mCheckBox_LoginState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        initPresenter();


        Observable<String> ob = Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("fuck");
            }
        }).observeOn(AndroidSchedulers.mainThread());



        Subscriber<String> sb = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
    }

    private void init(){
        mBtn_Login = (Button) findViewById(R.id.btn_signin);
        mBtn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("Login!!!!!!!");
            }
        });
        mBtn_Signup = (Button) findViewById(R.id.btn_signup);
        mBtn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("sign up!!!!!!!");
                mLoginPresenter.goToSignUp(LoginActivity.this);
            }
        });
        mTx_ID = (EditText) findViewById(R.id.login_id);
        mTx_Pass = (EditText) findViewById(R.id.login_password);
        mCheckBox_LoginState = (CheckBox) findViewById(R.id.cb_loginstate);
    }

    private void initPresenter(){
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.detachView();
        super.onDestroy();

    }

    @Override
    public String getID() {
        return mTx_ID.getText().toString();
    }

    @Override
    public String getPassword() {
        return mTx_Pass.getText().toString();
    }

    @Override
    public boolean getLoginState() {
        return mCheckBox_LoginState.isChecked();
    }

    public  void showProgress(){
        ProgressBar bar = new ProgressBar(this);
    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void Toast(String state) {

    }


    public void onClick(View v) {
        LogUtil.d(String.valueOf(v.getId()));
        /**if(v.getId()==R.id.btn_signin){
            mLoginPresenter.Login(LoginActivity.this);
        }else if(v.getId()==R.id.btn_signup){
            mLoginPresenter.goToSignUp(LoginActivity.this);
        }**/
    }
}
