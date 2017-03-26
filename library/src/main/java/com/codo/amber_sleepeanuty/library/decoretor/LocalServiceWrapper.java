package com.codo.amber_sleepeanuty.library.decoretor;

import com.codo.amber_sleepeanuty.library.service.LocalConnectService;

/**
 * Created by amber_sleepeanuty on 2017/3/23.
 */

public class LocalServiceWrapper {
    public Class<? extends LocalConnectService> targetService = null;

    public LocalServiceWrapper(Class<? extends LocalConnectService> localConnectService){
        this.targetService = localConnectService;
    }

    public void test(){

    }

}
