package com.codo.amber_sleepeanuty.library;

import android.icu.lang.UScript;

import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.WideRouter;

/**
 * Created by amber_sleepeanuty on 2017/3/27.
 */

public class WideRouterApplicationLogic extends BaseAppLogic {

    public WideRouterApplicationLogic() {
    }

    public void onCreate(){
        super.onCreate();
        initWideRouter();
    }

    private void initWideRouter(){
        WideRouter.getInstence(mApplication);
        mApplication.initAllProcessesRouter();
    }
}
