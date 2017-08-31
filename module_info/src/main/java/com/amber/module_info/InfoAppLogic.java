package com.amber.module_info;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class InfoAppLogic extends BaseAppLogic {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalRouter.getInstance(CodoApplication.getCodoApplication()).registerProvider("Info",new InfoProvider());
    }
}
