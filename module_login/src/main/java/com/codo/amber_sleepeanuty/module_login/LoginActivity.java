package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.ui.CodoEditText;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.presenter.LoginPresenter;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action3;
import rx.functions.Action5;
import rx.functions.Func1;
import rx.functions.Func8;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class LoginActivity extends Activity implements Contract.ILoginView{

    public LoginPresenter mLoginPresenter;
    protected Button mBtn_Login;
    protected EditText mTx_ID;
    protected CodoEditText mTx_Pass;
    protected ImageView img;
    protected RelativeLayout forgetLayout;
    protected RelativeLayout registerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initPresenter();
        init();
        //Glide.with(this).load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg" +
         //       "").into(img);
    }

    private void init(){
        mBtn_Login = (Button) findViewById(R.id.btn_signin);
        mBtn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.Login(LoginActivity.this);
            }
        });
        registerLayout = (RelativeLayout) findViewById(R.id.layout_register);
        registerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.goToSignUp(LoginActivity.this);
            }
        });
        mTx_ID = (CodoEditText) findViewById(R.id.login_id);
        mTx_Pass = (CodoEditText) findViewById(R.id.login_password);
        img = (ImageView) findViewById(R.id.login_header_img);
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
        return false;
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
}
