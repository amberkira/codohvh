package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    public CodoEditText mVerification;
    public boolean isVerified;


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
                String code ="";
                if (!ThrottleUtil.isRapid()){
                    code = mPresenter.obtainVerification();
                    LogUtil.d("验证码",code);
                }else {
                    LogUtil.d("验证码","太快了");
                }

                // TODO: 2017/4/24 插入发送验证请求短信代码
                // TODO: 2017/4/24 改写view中btn实现抖动监听排除
                if (TextUtils.isEmpty(code) && code != null) {

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
        mVerification = (CodoEditText) findViewById(R.id.signup_verification);
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
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void Toast(String state) {

    }

}
