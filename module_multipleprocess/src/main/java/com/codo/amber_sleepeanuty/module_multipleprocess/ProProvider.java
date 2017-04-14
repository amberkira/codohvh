package com.codo.amber_sleepeanuty.module_multipleprocess;

import com.codo.amber_sleepeanuty.library.base.BaseProvider;

/**
 * Created by amber_sleepeanuty on 2017/4/13.
 */

public class ProProvider extends BaseProvider {
    @Override
    public void registerActions() {
        registerAction("Pro",new ProAction());
    }
}
