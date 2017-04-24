package com.codo.amber_sleepeanuty.module_login.contract;

import com.codo.amber_sleepeanuty.library.bean.LoginBean;
import com.codo.amber_sleepeanuty.library.bean.RegisterBean;

/**
 * Created by amber_sleepeanuty on 2017/4/7.
 */

public class Contract {
    public interface ILoginView{
        String getID();
        String getPassword();
        boolean getLoginState();
        void showProgress();
        void dismissProgress();
        void Toast(String state);
    }

    public interface ILoginModel{
        String fetchIdentifyCode();
        boolean query(LoginBean bean);
    }

    public interface ISignUpView{
        void getID();
        void getPassword();
        void getPhoneNumber();

        void showProgress();
        void dismissProgress();
        void Toast(String state);
    }

    public interface ISignUpModel{
        String fetchIdentifyCode();
        boolean sumbit(RegisterBean bean);
    }
}
