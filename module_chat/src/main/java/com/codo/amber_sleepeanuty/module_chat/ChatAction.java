package com.codo.amber_sleepeanuty.module_chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.base.BaseAction;
import com.codo.amber_sleepeanuty.library.util.LogUtil;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ChatAction extends BaseAction {
    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public ActionResult invoke(Context context, RouterRequest requestData) {
        // 需要补充聊天类型／title名目／环信id - 名目为USER_NICKNAME 环信id为DEFAULT_NAME
        Bundle bundle = transformRequest2Bundle(requestData);
        Intent it = new Intent(context,ChatRoomActivity.class);
        it.putExtras(bundle);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        return new ActionResult(context,ActionResult.ACTION_SUCCESSED,"Chat");
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    private Bundle transformRequest2Bundle(RouterRequest request){

        Bundle bundle = new Bundle();
        int type;
        String title = null;
        String esmobID = null;

        int mChatType = Integer.valueOf(request.getData().get(Constant.CHAT_TYPE));
        //个人用户
        if(mChatType == Constant.CHAT_TYPE_SINGLE){
            esmobID = request.getData().get(Constant.EASEMOB_ID);
            title = request.getData().get(Constant.USER_NICKNAME);
        }
        //群聊初始信息
        if(mChatType == Constant.CHAT_TYPE_GROUP){
            esmobID = request.getData().get(Constant.GROUP_ID);
            title = request.getData().get(Constant.GROUP_NAME);
        }
        //类型
        bundle.putInt(Constant.CHAT_TYPE,mChatType);
        //title 显示
        bundle.putString(Constant.CHAT_ROOM_NAME,title);
        //环信id
        bundle.putString(Constant.CHAT_ROOM_ID,esmobID);

        return bundle;
    }
}
