package com.codo.amber_sleepeanuty.module_chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.module_chat.ui.VideoChatActivity;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class VideoAction extends BaseAction {
    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public ActionResult invoke(Context context, RouterRequest requestData) {
        HashMap<String,String> data = requestData.getData();
        String nickname = CheckNotNull.check(data.get(Constant.USER_NICKNAME));
        String id = CheckNotNull.check(data.get(Constant.EASEMOB_ID));
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_NICKNAME,nickname);
        bundle.putString(Constant.EASEMOB_ID,id);
        bundle.putInt(Constant.VIDEO_CALL_TYPE,Constant.CALL_INCOMING);
        Intent it = new Intent(context, VideoChatActivity.class);
        it.putExtras(bundle);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"video");
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
