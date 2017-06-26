package com.codo.amber_sleepeanuty.module_chat.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.module_chat.ChatRoomActivity;
import com.codo.amber_sleepeanuty.module_chat.Contract;
import com.codo.amber_sleepeanuty.module_chat.model.ChatModel;
import com.codo.amber_sleepeanuty.module_chat.ui.BigImageActivity;
import com.codo.amber_sleepeanuty.module_chat.ui.VideoChatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.util.ArrayList;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ChatPresenter extends BasePresenter<Contract.IChatView> {


    private Contract.IChatModel m;
    private final static int RECEIVED = 0x997;
    private final static int SENDED = 0x996;
    private final static int SEND_ERROR = 0x995;
    private final static int RECEIVE_ERROR = 0x994;

    private EMMessage msg;

    private Handler msgStatusHandler = new Handler(){
        @Override
        public void handleMessage(Message m) {
            switch (m.what){
                case SENDED:{
                    view.notifyMsgsended(msg);
                    break;
                }
                case SEND_ERROR:{
                    view.notifyMsgsendError((String)m.obj);
                }
            }
        }
    };

    private EMCallBack mSendingMsgStatusCallBack = new EMCallBack() {
        @Override
        public void onSuccess() {
            msgStatusHandler.sendEmptyMessage(SENDED);
        }

        @Override
        public void onError(int code, String error) {
            Message err = new Message();
            err.obj = error;
            err.what = SEND_ERROR;
            msgStatusHandler.sendMessage(err);
        }

        @Override
        public void onProgress(int progress, String status) {

        }
    };






    public ChatPresenter(Contract.IChatView view) {
        this.view = view;
        m = new ChatModel();
    }

    public void sendImg(String path,boolean origin,String toUser){
        msg = EMMessage.createImageSendMessage(path,origin,toUser);
        EMClient.getInstance().chatManager().sendMessage(msg);
        view.notifyMsgsended(msg);
    }

    public void sendTxtMessage(String txt,String toUser) {
        msg = EMMessage.createTxtSendMessage(txt, toUser);
        EMClient.getInstance().chatManager().sendMessage(msg);
        msg.setMessageStatusCallback(mSendingMsgStatusCallBack);
        view.notifyMsgsended(msg);
    }

    public void sendVoice(float sec, String path,String toUser) {
        msg = EMMessage.createVoiceSendMessage(path,(int)(sec+0.5f),toUser);
        EMClient.getInstance().chatManager().sendMessage(msg);
        view.notifyMsgsended(msg);
    }

    public void videoCall(String nickname,String toUser) {

        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_NICKNAME,CheckNotNull.check(nickname));
        bundle.putString(Constant.EASEMOB_ID,CheckNotNull.check(toUser));
        bundle.putInt(Constant.VIDEO_CALL_TYPE,Constant.CALL_DAILING);

        Intent it = new Intent((ChatRoomActivity)view, VideoChatActivity.class);
        it.putExtras(bundle);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((ChatRoomActivity)view).startActivity(it);
    }

    public void handleVoiceClickEvent(EMVoiceMessageBody body) {
    }

    public void handleImgClickEvent(EMImageMessageBody body) {
        String remoteUrl = body.getRemoteUrl();
        String thumbnailUrl = body.getThumbnailUrl();
        Intent it = new Intent((ChatRoomActivity)view, BigImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("remote", remoteUrl);
        bundle.putString("thumbnail", thumbnailUrl);
        it.putExtras(bundle);
        ((ChatRoomActivity)view).startActivity(it);
    }

    public ArrayList<EMMessage> fetchReadedMesssage(String toUser,String msgID , int count) {
        ArrayList<EMMessage> result = (ArrayList<EMMessage>) EMClient.getInstance().chatManager()
                .getConversation(toUser).loadMoreMsgFromDB(msgID,count);
        if(result == null){
            throw new NullPointerException("加载失败，检查加载id或其他地方");
        }
        return result;
    }
}
