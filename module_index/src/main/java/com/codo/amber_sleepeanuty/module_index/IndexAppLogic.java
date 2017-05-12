package com.codo.amber_sleepeanuty.module_index;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/5/9.
 */

public class IndexAppLogic extends BaseAppLogic {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalRouter.getInstance(CodoApplication.getCodoApplication()).registerProvider("Index",new IndexProvider());
    }
}
