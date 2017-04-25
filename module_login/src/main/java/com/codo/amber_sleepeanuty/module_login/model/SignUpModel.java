package com.codo.amber_sleepeanuty.module_login.model;

import com.codo.amber_sleepeanuty.library.bean.RegisterBean;
import com.codo.amber_sleepeanuty.module_login.VerificationProvider;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;

/**
 * Created by amber_sleepeanuty on 2017/4/24.
 */

public class SignUpModel implements Contract.ISignUpModel {
    @Override
    public String fetchIdentifyCode() {
        return null;
    }

    @Override
    public boolean sumbit(RegisterBean bean) {
        return false;
    }

    @Override
    public boolean isPhoneValid(String num) {
        return false;
    }

    @Override
    public String obtainVerification() {
        return VerificationProvider.getInstance().Create(6);
    }

    @Override
    public boolean isVerificationCorrect(String ver) {
        return false;
    }

    @Override
    public boolean isCodeValid(String start, String end) {
        return false;
    }
}
