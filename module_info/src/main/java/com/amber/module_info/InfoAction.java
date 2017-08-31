package com.amber.module_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class InfoAction extends BaseAction {
    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public ActionResult invoke(Context context, RouterRequest requestData) {
        Intent it = new Intent(context,InfoActivity.class);
        Bundle b = new Bundle();
        b.putInt(Constant.INFO_TYPE,Integer.valueOf(requestData.getData().get(Constant.INFO_TYPE)));
        it.putExtras(b);
        context.startActivity(it);
        return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"info");
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
