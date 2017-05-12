package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.ui.CodoEditText;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.ThrottleUtil;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.presenter.SignUpPresenter;

/**
 * Created by amber_sleepeanuty on 2017/4/6.
 */

public class SignupActivity extends Activity implements Contract.ISignUpView {
    public SignUpPresenter mPresenter;
    public Button mBtnSubmit;
    public Button mBtnVerification;
    public CodoEditText mPhone;
    public CodoEditText mCode;
    public CodoEditText mCodeRecheck;
    public EditText mVerification;
    public boolean isVerified;
    public String VerificationNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        initPresenter();
        init();
        eventInit();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void initPresenter() {
        mPresenter = new SignUpPresenter();
        mPresenter.attachView(this);
    }

    private void eventInit() {

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register(SignupActivity.this);
                Toast.makeText(SignupActivity.this, "134", Toast.LENGTH_LONG).show();
            }
        });
        mBtnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ThrottleUtil.isRapid()){
                    VerificationNumber = mPresenter.obtainVerification();
                    isVerified = true;
                    // TODO: 2017/4/24 插入发送验证请求短信代码
                }
            }
        });
    }

    private void init() {
        isVerified = false;
        mBtnSubmit = (Button) findViewById(R.id.btn_signup_submit);
        mBtnVerification = (Button) findViewById(R.id.btn_verification_code);
        mPhone = (CodoEditText) findViewById(R.id.signup_phone);
        mCode = (CodoEditText) findViewById(R.id.signup_password);
        mCodeRecheck = (CodoEditText) findViewById(R.id.signup_password_recheck);
        mVerification = (EditText) findViewById(R.id.signup_verification);
        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!mPhone.isValid(0)){
                        Toast.makeText(SignupActivity.this,"请输入合法手机号",Toast.LENGTH_LONG).show();
                        LogUtil.e("phone_valid!");
                    }
                }
            }
        });

        mCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!mCode.isValid(1)){
                        Toast.makeText(SignupActivity.this,"请输入8-16位包含字母数字的合法密码",Toast.LENGTH_LONG).show();
                        LogUtil.e("code_valid!");
                    }
                }
            }
        });

        mCodeRecheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!mCodeRecheck.isValid(1)){
                        Toast.makeText(SignupActivity.this,"请输入8-16位包含字母数字的合法密码",Toast.LENGTH_LONG).show();
                        LogUtil.e("code_valid!");
                    }else if(check(mCode.getText().toString(),mCodeRecheck.getText().toString())){

                    }
                }
            }
        });
    }

    public  boolean check(String origin,String refill){
        boolean b = false;
        if(origin.equals(refill)){
            b = true;
        }
        return b;
    }
    @Override
    public String getPassWord() {
        return mCode.getText().toString();
    }

    @Override
    public String getPhoneNumber() {
        return mPhone.getText().toString();
    }

    @Override
    public String getPassWordRecheck() {
        return mCodeRecheck.getText().toString();
    }

    @Override
    public String getVerification() {
        return mVerification.getText().toString();
    }

    @Override
    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void Toast(String state) {

    }

}
