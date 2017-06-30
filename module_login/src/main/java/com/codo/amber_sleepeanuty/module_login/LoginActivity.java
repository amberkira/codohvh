package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.codo.amber_sleepeanuty.library.bean.FriendListBean;
import com.codo.amber_sleepeanuty.library.dao.DBprovider;
import com.codo.amber_sleepeanuty.library.dao.DatabaseHelper;
import com.codo.amber_sleepeanuty.library.event.InitEvent;
import com.codo.amber_sleepeanuty.library.event.LoginEvent;
import com.codo.amber_sleepeanuty.library.ui.CodoEditText;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class LoginActivity extends Activity implements Contract.ILoginView{

    public LoginPresenter mLoginPresenter;
    protected Button mBtn_Login;
    protected EditText mTx_ID;
    protected CodoEditText mTx_Pass;
    protected ImageView img;
    protected RelativeLayout registerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        EventBus.getDefault().post(new InitEvent(null));
        initPresenter();
        init();
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

    boolean isfirst = true;
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onUpdate(LoginEvent event){
        LogUtil.e("LOGIN EVENT ");
        DBprovider dBprovider = DBprovider.getInstance(this);
        List<FriendListBean.Info> list = event.getEvent().getServer().getInfo();
        for(int i = 0; i<list.size(); i++){
            int count = list.get(i).getInfolist().size();
            for (int j = 0;j<count; j++){
                FriendListBean.Infolist temp = list.get(i).getInfolist().get(j);
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.USER_NICKNAME,temp.getNickname());
                values.put(DatabaseHelper.USER_AVATAR,temp.getPortrait());
                values.put(DatabaseHelper.USER_EASEMOBID,temp.getName());
                values.put(DatabaseHelper.Mobile,temp.getMobile());
                if(isfirst){
                    values.put(DatabaseHelper.USER_NAME,temp.getName());
                    dBprovider.Insert(DatabaseHelper.TABLE_USER_INFORMATION,values);
                }else {
                    dBprovider.Update(DatabaseHelper.TABLE_USER_INFORMATION,values,DatabaseHelper.USER_NAME+"=?",new String[]{temp.getName()});
                }
            }
        }
        isfirst = false;
        dBprovider.Query(DatabaseHelper.TABLE_USER_INFORMATION);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
}
