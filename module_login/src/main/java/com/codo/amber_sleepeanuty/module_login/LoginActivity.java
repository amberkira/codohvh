package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.presenter.LoginPresenter;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class LoginActivity extends Activity implements View.OnClickListener,Contract.ILoginView{

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
    }

    private void init(){
        mBtn_Login = (Button) findViewById(R.id.btn_signin);
        mBtn_Signup = (Button) findViewById(R.id.btn_signup);
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


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_signin){
            mLoginPresenter.Login();
        }else if(v.getId()==R.id.btn_signup){
            mLoginPresenter.goToSignUp(LoginActivity.this);
        }
    }
}
