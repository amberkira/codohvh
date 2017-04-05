package com.codo.amber_sleepeanuty.codohvh;

import android.util.Log;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.router.WideRouter;
import com.codo.amber_sleepeanuty.module_login.LoginAppLogic;
import com.codo.amber_sleepeanuty.module_login.LoginConnectService;

/**
 * Created by amber_sleepeanuty on 2017/3/28.
 */

public class MainApplication extends CodoApplication {
    @Override
    public void initialLogicWithinSameProcess() {
        Log.d("app","yaahsjdfejfenfenf");
        registerApplicationLogic("Login",1000,LoginAppLogic.class);
    }

    @Override
    public void initAllProcessesRouter() {
        WideRouter.registerLocalConnectService("Login",LoginConnectService.class);
    }
}
