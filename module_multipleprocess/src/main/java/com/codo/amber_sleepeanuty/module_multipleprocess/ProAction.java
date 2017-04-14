package com.codo.amber_sleepeanuty.module_multipleprocess;

import android.content.Context;
import android.content.Intent;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/4/13.
 */

public class ProAction extends BaseAction {
    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public ActionResult invoke(Context context, RouterRequest requestData) {
        Intent it = new Intent(context,ProActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"process");

    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
