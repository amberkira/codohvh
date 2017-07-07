package com.codo.amber_sleepeanuty.module_chat.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.bean.MsgContainStatesBean;
import com.codo.amber_sleepeanuty.library.util.ImageLoader;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.UnitExchange;
import com.codo.amber_sleepeanuty.module_chat.R;
import com.codo.amber_sleepeanuty.module_chat.widget.ViewHolderHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/5/26.
 */

public class ImMessageAdapter extends RecyclerView.Adapter{

    //状态码区分消息来源；
    public static final int HEADER = -1;
    public static final int TXT_SEND = 0;
    public static final int TXT_RECEIVE = 1;
    public static final int IMG_SEND = 2;
    public static final int IMG_RECEIVE = 3;
    public static final int VOICE_SEND = 4;
    public static final int VOICE_RECEIVE = 5;


    private static final int PRELOAD_MESSAGES_LIMIT = 5;

    public Context context;
    public List<MsgContainStatesBean> list;
    public View header;
    public ViewHolderHelper helper;
    public ProgressBarListener listener;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                listener.show();
            }else {
                listener.dismiss();
            }
            //super.handleMessage(msg);
        }
    };

    public ImMessageAdapter(Context mContext , List<MsgContainStatesBean> mList ) {
        context = mContext;
        list = mList;
    }

    public void setViewHolderHelper(ViewHolderHelper helper){
        this.helper = helper;
    }

    private void setProgressbarListener(ProgressBarListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER){
            View v = LayoutInflater.from(context).inflate(R.layout.header_message_item,parent,false);
            return new HeaderHolder(v);
        }
        if(viewType == TXT_SEND){
            View v = LayoutInflater.from(context).inflate(R.layout.text_message_userself_item,parent,false);
            return new TextSelfHolder(v);
        }
        if(viewType == TXT_RECEIVE){
            View v = LayoutInflater.from(context).inflate(R.layout.text_message_userfriend_item,parent,false);
            return new TextFriendHolder(v);
        }
        if(viewType == VOICE_SEND){
            View v = LayoutInflater.from(context).inflate(R.layout.voice_message_userself_item,parent,false);
            return new VoiceSelfHolder(v);
        }
        if(viewType == VOICE_RECEIVE){
            View v = LayoutInflater.from(context).inflate(R.layout.voice_message_userfriend_item,parent,false);
            return new VoiceFriendHolder(v);
        }
        if(viewType == IMG_SEND){
            View v = LayoutInflater.from(context).inflate(R.layout.img_message_userself_item,parent,false);
            return new ImgSelfHolder(v);
        }
        if(viewType == IMG_RECEIVE){
            View v = LayoutInflater.from(context).inflate(R.layout.img_message_userfriend_item,parent,false);
            return new ImgFriendHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        setProgressbarListener((BaseHolder)holder);
        int index = position-getHeader();
        final MsgContainStatesBean message = list.get(index);
        String time = UnitExchange.Timestamp2DataString(message.getMsg().getMsgTime());

        if(holder instanceof TextSelfHolder){
            ((TextSelfHolder) holder).time.setText(time);
            EMTextMessageBody body = (EMTextMessageBody) list.get(index).getMsg().getBody();
            ((TextSelfHolder) holder).selfText.setText(body.getMessage());
            message.getMsg().setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                }
                @Override
                public void onError(int i, String s) {
                }
                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);

                }
            });
            // TODO: 2017/6/8 设置本地头像地址
        }

        if(holder instanceof TextFriendHolder){
            ((TextFriendHolder) holder).time.setText(time);
            EMTextMessageBody body = (EMTextMessageBody) list.get(index).getMsg().getBody();
            ((TextFriendHolder) holder).friendText.setText(body.getMessage());

            // TODO: 2017/6/8 设置对象头像地址
            message.getMsg().setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                }
                @Override
                public void onError(int i, String s) {
                    Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);
                }
            });
        }

        if (holder instanceof VoiceSelfHolder){
            ((VoiceSelfHolder) holder).time.setText(time);
            final EMVoiceMessageBody body = (EMVoiceMessageBody) list.get(index).getMsg().getBody();
            ((VoiceSelfHolder) holder).selfVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.HandleVoiceClickEvent(body);
                }
            });
            ((VoiceSelfHolder) holder).selflength.setText(body.getLength()+"s");


           /** message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                    ((VoiceSelfHolder) holder).selfVoice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helper.HandleVoiceClickEvent(body);
                        }
                    });
                }
                @Override
                public void onError(int i, String s) {
                    Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);
                }
            });**/
        }

        if (holder instanceof VoiceFriendHolder){

            ((VoiceFriendHolder) holder).time.setText(time);
            final EMVoiceMessageBody body = (EMVoiceMessageBody) list.get(index).getMsg().getBody();
            /**message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);
                }
            });**/
            ((VoiceFriendHolder) holder).friendVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.HandleVoiceClickEvent(body);
                }
            });
            ((VoiceFriendHolder) holder).friendlength.setText(body.getLength()+"s");
        }

        if(holder instanceof ImgSelfHolder){
            ((ImgSelfHolder) holder).time.setText(time);
            final EMImageMessageBody body = (EMImageMessageBody) list.get(index).getMsg().getBody();
            ;
            if(body.getLocalUrl().contains("storage")){
                Bitmap bitmap = ImageLoader.loadBitmapFromPath(body.getLocalUrl());
                int w = bitmap.getWidth();
                ViewGroup.LayoutParams params = ((ImgSelfHolder) holder).selfImg.getLayoutParams();
                params.width = bitmap.getWidth();
                params.height = bitmap.getHeight();
                ((ImgSelfHolder) holder).selfImg.setLayoutParams(params);
                ((ImgSelfHolder) holder).selfImg.setImageBitmap(bitmap);
            }else{
                Uri uri = Uri.parse(body.getLocalUrl());
                Bitmap bitmap = ImageLoader.loadBitmapFromUri(uri);
                ViewGroup.LayoutParams params = ((ImgSelfHolder) holder).selfImg.getLayoutParams();
                params.width = bitmap.getWidth();
                params.height = bitmap.getHeight();
                ((ImgSelfHolder) holder).selfImg.setLayoutParams(params);
                ((ImgSelfHolder) holder).selfImg.setImageBitmap(bitmap);
            }
            ((ImgSelfHolder) holder).selfImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.HandleImgClickEvent(body);
                }
            });
           /** message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);
                }
            });**/
        }

        if (holder instanceof ImgFriendHolder){
            ((ImgFriendHolder) holder).time.setText(time);
            final EMImageMessageBody body = (EMImageMessageBody) list.get(index).getMsg().getBody();
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
            //判断本地是否存在缩略图
            if(body.thumbnailLocalPath()!=null){
                bitmap = ImageLoader.loadBitmapFromPath(body.thumbnailLocalPath());
                if (bitmap!=null){
                    ViewGroup.LayoutParams params = ((ImgFriendHolder)holder).friendImg.getLayoutParams();
                    params.width = bitmap.getWidth();
                    params.height = bitmap.getHeight();
                    ((ImgFriendHolder) holder).friendImg.setLayoutParams(params);
                    ((ImgFriendHolder) holder).friendImg.setImageBitmap(bitmap);
                }
            }

            ((ImgFriendHolder) holder).friendImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.HandleImgClickEvent(body);
                }
            });
            /**message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    handler.sendEmptyMessage(2);
                    ((ImgFriendHolder) holder).friendImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helper.HandleImgClickEvent(body);
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {
                    handler.sendEmptyMessage(1);
                }
            });**/

        }
    }

    @Override
    public int getItemCount() {
        return list.size()+getHeader();
    }

    public int getHeader(){
        return header==null?0:1;
    }


    @Override
    public int getItemViewType(int position) {
        if(list.get(position-getHeader()).getMsg().getType()== EMMessage.Type.TXT){
            return list.get(position-getHeader()).getMsg().direct()==EMMessage.Direct.RECEIVE?TXT_RECEIVE:TXT_SEND;
        }
        if(list.get(position-getHeader()).getMsg().getType()== EMMessage.Type.IMAGE){
            return list.get(position-getHeader()).getMsg().direct()==EMMessage.Direct.RECEIVE?IMG_RECEIVE:IMG_SEND;
        }
        if(list.get(position-getHeader()).getMsg().getType()== EMMessage.Type.VOICE) {
            return list.get(position - getHeader()).getMsg().direct() == EMMessage.Direct.RECEIVE ? VOICE_RECEIVE : VOICE_SEND;
        }
        return -2;
    }

    public void clear(){
        list.clear();
        removeHeader();
    }

    public void removeHeader(){
        header = null;
        notifyDataSetChanged();
    }

    public void addHeader(View mHeader){
        header = mHeader;
        notifyDataSetChanged();
    }

    public void removeSingleMessage(int position){
        list.remove(position-getHeader());
        notifyDataSetChanged();
    }

    public void addSingleMessage(MsgContainStatesBean bean){
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 我们默认去除多条聊天记录是指代的删除与某人的全部聊天记录
     */
    @Deprecated
    public void removeMutipleMessage(){
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加的多条记录若指代回溯聊天记录,所以添加至list的时候是从0开始
     * @param beans
     */
    public void addMutipleMessages(int index,List<MsgContainStatesBean> beans){
        list.addAll(index,beans);
        notifyDataSetChanged();
    }

    /**
     * 初始化添加聊天记录 若有未读则需要全部加载，如若没有也需要加载前5条记录
     * 这里肯定能保证的是unread一定是小于等于list的长度的
     */
    public void preloadMessages(int unread,List<MsgContainStatesBean> list){
        int length = list.size();
        LogUtil.e("adapter list origin size: "+this.list.size());

        if(unread>PRELOAD_MESSAGES_LIMIT){
            this.list.addAll(0,list.subList(length-unread,length));
        }

        if(length<=PRELOAD_MESSAGES_LIMIT){
            this.list.addAll(0,list);
        }

        if(length>PRELOAD_MESSAGES_LIMIT&&unread<PRELOAD_MESSAGES_LIMIT){
            this.list.addAll(0,list.subList(length-PRELOAD_MESSAGES_LIMIT,length));
        }
        notifyDataSetChanged();
    }

    public String getPreloadStartID(){
        return this.list.get(0).getMsg().getMsgId();
    }

    public void refreshList() {
        notifyDataSetChanged();
    }

    interface ProgressBarListener{
        void show();
        void dismiss();
    }

    public class BaseHolder extends RecyclerView.ViewHolder implements ProgressBarListener{
        public ProgressBar progressBar;
        public BaseHolder(View itemView) {
            super(itemView);
        }

        public void show(){
            progressBar.setVisibility(View.VISIBLE);
        }

        public void dismiss(){
            progressBar.setVisibility(View.GONE);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private class TextSelfHolder extends BaseHolder{
        public TextView time;
        public ImageView selfIcon;
        public TextView selfText;
        public TextSelfHolder(View itemView) {
            super(itemView);
            selfIcon = (ImageView) itemView.findViewById(R.id.text_user_self_icon);
            selfText = (TextView) itemView.findViewById(R.id.text_user_self);
            progressBar = (ProgressBar) itemView.findViewById(R.id.text_user_self_pb);
            progressBar.setVisibility(View.GONE);
            time = (TextView) itemView.findViewById(R.id.text_time_self).findViewById(R.id.time_remainder);
        }
    }

    private class TextFriendHolder extends  BaseHolder{
        //public ProgressBar progressBar;
        public TextView time;
        public ImageView friendIcon;
        public TextView friendText;
        public TextFriendHolder(View itemView) {
            super(itemView);
            friendIcon = (ImageView) itemView.findViewById(R.id.text_user_friend_icon);
            friendText = (TextView) itemView.findViewById(R.id.text_user_friend);
            progressBar = (ProgressBar) itemView.findViewById(R.id.text_user_friend_pb);
            progressBar.setVisibility(View.GONE);
            time = (TextView) itemView.findViewById(R.id.text_time_friend).findViewById(R.id.time_remainder);
        }
    }

    private class VoiceSelfHolder extends  BaseHolder{
        //public ProgressBar progressBar;
        public TextView time;
        public ImageView selfIcon;
        public ImageView selfVoice;
        public TextView selflength;
        public VoiceSelfHolder(View itemView) {
            super(itemView);
            selfIcon = (ImageView) itemView.findViewById(R.id.voice_user_self_icon);
            selfVoice = (ImageView) itemView.findViewById(R.id.voice_user_self);
            selflength = (TextView) itemView.findViewById(R.id.voice_user_self_time_length);
            progressBar = (ProgressBar) itemView.findViewById(R.id.voice_user_self_pb);
            progressBar.setVisibility(View.GONE);
            time = (TextView) itemView.findViewById(R.id.voice_time_self).findViewById(R.id.time_remainder);
        }
    }

    private class VoiceFriendHolder extends BaseHolder{
        //public ProgressBar progressBar;
        public TextView time;
        public ImageView friendIcon;
        public ImageView friendVoice;
        public TextView friendlength;
        public VoiceFriendHolder(View itemView) {
            super(itemView);
            friendIcon = (ImageView) itemView.findViewById(R.id.voice_user_friend_icon);
            friendVoice = (ImageView) itemView.findViewById(R.id.voice_user_friend);
            friendlength = (TextView) itemView.findViewById(R.id.voice_user_friend_time_length_1);
            progressBar = (ProgressBar) itemView.findViewById(R.id.voice_user_friend_pb);
            progressBar.setVisibility(View.GONE);
            time = (TextView) itemView.findViewById(R.id.voice_time_friend).findViewById(R.id.time_remainder);
        }
    }

    private class ImgSelfHolder extends  BaseHolder{
        //public ProgressBar progressBar;
        public TextView time;
        public ImageView selfIcon;
        public ImageView selfImg;
        public ImgSelfHolder(View itemView) {
            super(itemView);
            selfIcon = (ImageView) itemView.findViewById(R.id.img_user_self_icon);
            selfImg = (ImageView) itemView.findViewById(R.id.img_user_self_content);
            progressBar = (ProgressBar) itemView.findViewById(R.id.img_user_self_pb);
            progressBar.setVisibility(View.GONE);

            time = (TextView) itemView.findViewById(R.id.img_time_self).findViewById(R.id.time_remainder);
        }
    }

    private class ImgFriendHolder extends  BaseHolder{
        //public ProgressBar progressBar;
        public TextView time;
        public ImageView friendIcon;
        public ImageView friendImg;
        public ImgFriendHolder(View itemView) {
            super(itemView);
            friendIcon = (ImageView) itemView.findViewById(R.id.img_user_friend_icon);
            friendImg = (ImageView) itemView.findViewById(R.id.img_user_friend_content);
            progressBar = (ProgressBar) itemView.findViewById(R.id.img_user_friend_pb);
            progressBar.setVisibility(View.GONE);

            time = (TextView) itemView.findViewById(R.id.img_time_friend).findViewById(R.id.time_remainder);
        }
    }

    /**public void showProgress(){
        mRedPoint.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    public void dismissProgress(int flag){
        if(flag == 0){//消息从自己发出
            mRedPoint.setVisibility(View.GONE);
            mProgress.setVisibility(View.GONE);
        }else {//收到消息
            mRedPoint.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        }

    }**/

}
