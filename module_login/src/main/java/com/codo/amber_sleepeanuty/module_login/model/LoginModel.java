package com.codo.amber_sleepeanuty.module_login.model;

import com.codo.amber_sleepeanuty.library.bean.CustomerLoginBean;
import com.codo.amber_sleepeanuty.module_login.contract.Contract;

/**
 * Created by amber_sleepeanuty on 2017/4/7.
 */

public class LoginModel implements Contract.ILoginModel {
    @Override
    public String fetchIdentifyCode() {
        return null;
    }

    @Override
    public boolean query(CustomerLoginBean bean) {
        return false;
    }
}
