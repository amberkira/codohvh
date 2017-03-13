package com.codo.amber_sleepeanuty.library.router;

import com.codo.amber_sleepeanuty.library.base.BaseLocalRouter;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/12.
 */

public class WideRouter {
    //存已注册的需多进程module的localrouter
    private HashMap<String,Class<? extends BaseLocalRouter>> mRouterMap;


}
