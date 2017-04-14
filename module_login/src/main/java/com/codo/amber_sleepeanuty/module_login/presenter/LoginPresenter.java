package com.codo.amber_sleepeanuty.module_login.presenter;



import android.content.Context;
import android.content.Intent;

import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.library.bean.CustomerLoginBean;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.library.util.MD5Util;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_login.SignupActivity;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.model.LoginModel;


/**
 * Created by amber_sleepeanuty on 2017/4/7.
 */

public class LoginPresenter extends BasePresenter<Contract.ILoginView>{
    private Contract.ILoginModel m;
    public static final  String appID = "9e83c7766f6f889ea8c5ef0eb6b8ab5d";
    private String mID;
    private String mPassword;
    private boolean mLoginState;

    public LoginPresenter() {
        m = new LoginModel();
        mLoginState = false;
    }

    //获取信息验证登陆
    public void Login(final Context context) {
        mID = CheckNotNull.check(view.getID(),"LoginID is null");
        mPassword = CheckNotNull.check(view.getPassword(),"LoginPassword is null");
        mLoginState = view.getLoginState();
        CustomerLoginBean bean = new CustomerLoginBean();
        bean.setId(mID);
        bean.setPass(mPassword);
        //登陆验证
        if(m.query(bean)){
            if(mLoginState){
                SpUtil.saveString("ID",MD5Util.MD5(mID));
                SpUtil.saveString("Password",MD5Util.MD5(mPassword));
                SpUtil.saveLong("LastLoginTime",System.currentTimeMillis()/1000);
            }
            //TODO 负责跳转至下一module
            /**
             RouterRequest r = RouterRequestPool.getAvailableRequest(context,5);
             r.provider("Next").action("Next");
             try {
             LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(context,r);
             } catch (Exception e) {
             e.printStackTrace();
             }**/
        }
    }

    //跳转页面至注册页面
    public void goToSignUp(Context context) {

        Intent it = new Intent(context, SignupActivity.class);
        context.startActivity(it);
    }
}
