package com.codo.amber_sleepeanuty.codohvh;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/3/28.
 */

public class MainAppLogic extends BaseAppLogic {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalRouter.getInstance(CodoApplication.getCodoApplication()).registerProvider("Main",new MainProvider());
    }
}
