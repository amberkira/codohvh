package com.codo.amber_sleepeanuty.library.base;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.ActionResult;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public abstract class BaseAction {
    public abstract ActionResult invoke(Context context, HashMap<String,String> requestData);
    public abstract boolean isAsyn();
}
