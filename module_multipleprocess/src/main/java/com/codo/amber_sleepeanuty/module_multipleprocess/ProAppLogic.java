package com.codo.amber_sleepeanuty.module_multipleprocess;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/4/13.
 */

public class ProAppLogic extends BaseAppLogic {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalRouter.getInstance(CodoApplication.getCodoApplication()).registerProvider("Pro", new ProProvider());
    }
}
