package com.codo.amber_sleepeanuty.module_login.presenter;



import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.library.bean.FriendListBean;
import com.codo.amber_sleepeanuty.library.bean.LoginBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_login.Constant;
import com.codo.amber_sleepeanuty.module_login.SignupActivity;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;
import com.codo.amber_sleepeanuty.module_login.model.LoginModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;


import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


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
        mID = CheckNotNull.check(view.getID(), "LoginID is null");
        mPassword = CheckNotNull.check(view.getPassword(), "LoginPassword is null");
        mLoginState = view.getLoginState();

        EMClient.getInstance().login("13683514101", "1234", new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().chatManager().loadAllConversations();
                SpUtil.saveString(com.codo.amber_sleepeanuty.library.Constant.CURRENT_LOGIN_ID,"13683514101");
            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });



        APIService.Factory.createService(context)
                .login(mID,
                        mPassword,
                        SpUtil.getString(com.codo.amber_sleepeanuty.library.Constant.LONGITUDE,""),
                        SpUtil.getString(com.codo.amber_sleepeanuty.library.Constant.LATITUDE,""),
                        SpUtil.getString(com.codo.amber_sleepeanuty.library.Constant.DEVICE_ID,""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        int state = loginBean.getServer().getErrno();
                        if (state==0) {
                            LogUtil.e(loginBean.getServer().getInfo().getSessionid());
                            LogUtil.e(loginBean.getServer().getInfo().getNickname());
                            SpUtil.saveString(com.codo.amber_sleepeanuty.library.Constant.SESSION_ID,
                                    loginBean.getServer().getInfo().getSessionid());
                            Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                            if(mLoginState){
                                SpUtil.saveString(Constant.USER_NAME_KEY,mID);
                                SpUtil.saveString(Constant.USER_PWD_KEY,mPassword);
                            }
                            RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(context,5);
                            routerRequest.provider("Index")
                                    .action("Index");
                            try {
                                LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(context,routerRequest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (state==1001) {
                            Toast.makeText(context, "登陆失败", Toast.LENGTH_LONG).show();
                            // TODO: 2017/4/24 错误验证
                            String s = "";
                        }
                    }
                });
    }



    //跳转页面至注册页面
    public void goToSignUp(Context context) {
        Intent it = new Intent(context, SignupActivity.class);
        context.startActivity(it);
    }
}
