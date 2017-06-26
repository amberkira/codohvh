package com.codo.amber_sleepeanuty.module_chat.widget;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.util.List;

import static com.codo.amber_sleepeanuty.module_chat.widget.MessageHandler.LIST_REFRESH;


/**
 * Created by amber_sleepeanuty on 2017/6/14.
 */

public class MessageHelper {


    public int getUnreadMsgCount(String user){
        return EMClient.getInstance().chatManager().getConversation(user).getUnreadMsgCount();
    }

    public static void loadChatMessageList(@NonNull List<EMMessage> messages, final Handler handler){
        for(int i = 0;i<messages.size();i++) {
            final EMMessageBody b = messages.get(i).getBody();
            final Message index = new Message();
            index.obj = messages.get(i);
            index.what = LIST_REFRESH;
            if(b instanceof EMTextMessageBody){
                handler.sendMessage(index);
            }
            if (b instanceof EMImageMessageBody){
                // 图片消息 监听
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (((EMImageMessageBody) b).thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
                                handler.sendMessage(index);
                                return;
                            }
                        }
                    }
                }).start();
            }
            if (b instanceof EMVoiceMessageBody) {
                //Log.e("file", ((EMVoiceMessageBody) b).getFileName());
                //Log.e("length", ((EMVoiceMessageBody) b).getLength() + "");
                //Log.e("remoter", ((EMVoiceMessageBody) b).getRemoteUrl());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (((EMVoiceMessageBody) b).downloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
                                handler.sendMessage(index);
                                return;
                            }
                        }
                    }
                }).start();
            }
        }
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }


}
