package com.codo.amber_sleepeanuty.module_login;

import android.content.Context;
import android.content.Intent;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.util.SpUtil;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/4/5.
 */

public class LoginAction extends BaseAction {
    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public ActionResult invoke(Context context, RouterRequest requestData) {
        if(SpUtil.getString(Constant.USER_NAME_KEY,null)==null&&SpUtil.getString(Constant.USER_PWD_KEY,null)==null){
            Intent it = new Intent(context,LoginActivity.class);
            context.startActivity(it);
            return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"Login");
        }
        // TODO: 2017/4/26 持久化保存密码用户的话就前往主页 下面代码以后改为前去主页
        Intent it = new Intent(context,LoginActivity.class);
        context.startActivity(it);
        return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"Login");
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
