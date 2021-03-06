package com.codo.amber_sleepeanuty.module_chat.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.base.BasePresenter;
import com.codo.amber_sleepeanuty.library.bean.MsgContainStatesBean;
import com.codo.amber_sleepeanuty.library.event.MsgEvent;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.library.widget.MediaManager;
import com.codo.amber_sleepeanuty.module_chat.ChatRoomActivity;
import com.codo.amber_sleepeanuty.module_chat.Contract;
import com.codo.amber_sleepeanuty.module_chat.model.ChatModel;
import com.codo.amber_sleepeanuty.module_chat.ui.BigImageActivity;
import com.codo.amber_sleepeanuty.module_chat.ui.VideoChatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ChatPresenter extends BasePresenter<Contract.IChatView> {


    private Contract.IChatModel m;
    private final static int RECEIVED = 0x997;
    private final static int SENDED = 0x996;
    private final static int SEND_ERROR = 0x995;
    private final static int RECEIVE_ERROR = 0x994;

    private static final int CONNECT_TIME_OUT = 0X111;


    private EMMessage msg;

    private Thread voiceWait;


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

                case CONNECT_TIME_OUT:{
                    voiceWait.interrupt();
                    view.notifyMsgsendError("下载失败");
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
        ;
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

    public void handleVoiceClickEvent(final EMVoiceMessageBody body) {
        // TODO: 2017/6/26 微信小程序语音仍未处理 
        //本地已经下载完毕语音
        if (body.downloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
            if (!MediaManager.isPlaying()) {
                MediaManager.playVioce(body.getLocalUrl(), null);
            } else {
                MediaManager.release();
                MediaManager.playVioce(body.getLocalUrl(), null);
            }
            return;
        }
        //自带异步下载  开启线程通知
        else {
            voiceWait = new Thread(new Runnable() {
                @Override
                public void run() {
                    msgStatusHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            msgStatusHandler.sendEmptyMessage(CONNECT_TIME_OUT);
                            return;
                        }
                    }, 3000);
                    while (true) {
                        if (body.downloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
                            MediaManager.playVioce(body.getLocalUrl(), null);
                            return;
                        }
                    }
                }
            });
            voiceWait.start();
        }

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

    public ArrayList<MsgContainStatesBean> fetchReadedMesssage(String toUser,String msgID , int count) {
        ArrayList<MsgContainStatesBean> result = fixState2Msg(0,EMClient.getInstance().chatManager()
                .getConversation(toUser).loadMoreMsgFromDB(msgID,count));

        if(result == null){
            throw new NullPointerException("加载失败，检查加载id或其他地方");
        }
        return result;
    }

    public ArrayList<MsgContainStatesBean> fixState2Msg(int unreadMsgCount, List<EMMessage> allMessages) {
        ArrayList<MsgContainStatesBean> result = new ArrayList<>();
        if(allMessages == null){
            return null;
        }
        MsgContainStatesBean bean = new MsgContainStatesBean();
        for(int i = 0; i<allMessages.size(); i++){
            if (i < unreadMsgCount) {
                bean.setState(MsgEvent.MsgStates.Unread);
                bean.setMsg(allMessages.get(i));
            }else {
                bean.setState(MsgEvent.MsgStates.Read);
                bean.setMsg(allMessages.get(i));
            }
            result.add(bean);
        }
        return result;
    }
}
