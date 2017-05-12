package com.codo.amber_sleepeanuty.module_index;

import com.codo.amber_sleepeanuty.library.base.BaseProvider;

/**
 * Created by amber_sleepeanuty on 2017/5/9.
 */

public class IndexProvider extends BaseProvider {
    @Override
    public void registerActions() {
        registerAction("Index",new IndexAction());
    }
}
