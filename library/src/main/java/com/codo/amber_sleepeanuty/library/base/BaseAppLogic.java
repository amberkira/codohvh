package com.codo.amber_sleepeanuty.library.base;

import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.codo.amber_sleepeanuty.library.CodoApplication;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public abstract class BaseAppLogic {
    public CodoApplication mApplication;
    public BaseAppLogic() {
    }

    public void setApplication(@NonNull CodoApplication application) {
        mApplication = application;
    }

    public void onCreate() {
    }

    public void onTerminate() {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

}
