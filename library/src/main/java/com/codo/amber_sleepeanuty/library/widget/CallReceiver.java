package com.codo.amber_sleepeanuty.library.widget;

/**
 * Created by amber_sleepeanuty on 2017/6/13.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;


public class CallReceiver extends BroadcastReceiver {

    public CallReceiver() {
    }

    @Override public void onReceive(Context context, Intent intent) {
        // 判断环信是否登录成功
        if (!EMClient.getInstance().isLoggedInBefore()) {
            return;
        }

        // 呼叫方的usernmae
        String callFrom = intent.getStringExtra("from");
        // 呼叫类型，有语音和视频两种
        String callType = intent.getStringExtra("type");
        // 呼叫接收方
        String callTo = intent.getStringExtra("to");

        // 判断下当前被呼叫的为自己的时候才启动通话界面 TODO 这个当不同appkey下相同的username时就无效了
        if (callTo.equals(EMClient.getInstance().getCurrentUser())) {
            Intent callIntent = new Intent();
            // 根据通话类型跳转到语音通话或视频通话界面
            if (callType.equals("video")) {
                // 设置当前通话类型为视频通话
                try {
                    HashMap<String,String> data =new HashMap<>();
                    data.put(Constant.EASEMOB_ID,callFrom);
                    // TODO: 2017/6/22 后期通过emessage ext获取
                    data.put(Constant.USER_NICKNAME,"测试");
                    RouterRequest request = RouterRequestPool.getAvailableRequest(context,5);
                    request.provider("Chat")
                            .action("video").data(data);
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(context,request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (callType.equals("voice")) {
                // 设置当前通话类型为语音通话
                //callIntent.setClass(context, VideoChatActivity.class);
            }
        }
    }
}
