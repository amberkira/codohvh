package com.amber.module_info;

import com.codo.amber_sleepeanuty.library.base.BaseProvider;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class InfoProvider extends BaseProvider {
    @Override
    public void registerActions() {
        registerAction("Info",new InfoAction());
    }
}
