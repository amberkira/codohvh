package com.codo.amber_sleepeanuty.codohvh;

import android.util.Log;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.router.WideRouter;
import com.codo.amber_sleepeanuty.module_login.LoginAppLogic;
import com.codo.amber_sleepeanuty.module_login.LoginConnectService;
import com.codo.amber_sleepeanuty.module_multipleprocess.ProAppLogic;
import com.codo.amber_sleepeanuty.module_multipleprocess.ProConnectService;

/**
 * Created by amber_sleepeanuty on 2017/3/28.
 */

public class MainApplication extends CodoApplication {
    @Override
    public void initialLogicWithinSameProcess() {
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh",1000,LoginAppLogic.class);
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh:Pro",1000,ProAppLogic.class);
    }

    @Override
    public void initAllProcessesRouter() {
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh",MainRouterConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Login",LoginConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Pro",ProConnectService.class);
    }
}
