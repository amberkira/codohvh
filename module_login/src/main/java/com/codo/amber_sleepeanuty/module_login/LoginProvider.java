package com.codo.amber_sleepeanuty.module_login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.base.BaseProvider;

/**
 * Created by amber_sleepeanuty on 2017/4/5.
 */

public class LoginProvider extends BaseProvider {

    @Override
    public void registerActions() {
        registerAction("login",new LoginAction());
    }
}
