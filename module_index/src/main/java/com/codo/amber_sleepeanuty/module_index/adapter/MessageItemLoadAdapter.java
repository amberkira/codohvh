package com.codo.amber_sleepeanuty.module_index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.SimpleResource;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.bean.IMMsgBean;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.QuickSort;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_index.IndexActivity;
import com.codo.amber_sleepeanuty.module_index.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amber_sleepeanuty on 2017/6/19.
 */

public class MessageItemLoadAdapter extends RecyclerView.Adapter {
    //private HashMap<String,EMMessage> mMessageMap;
    private HashMap<String,IMMsgBean> mMessageMap;
    private ArrayList<IMMsgBean> mMessageList;
    public  OnNewMsgListener listener;
    private Context mContext;

    public MessageItemLoadAdapter(Context context,HashMap<String, IMMsgBean> mMessageMap) {
        mContext = context;
        this.mMessageMap = mMessageMap;
        mMessageList = Map2SortedList(mMessageMap);
    }

    public ArrayList<IMMsgBean> Map2SortedList(HashMap<String, IMMsgBean> mMessageMap) {
        ArrayList<IMMsgBean> list = new ArrayList<>();
        Iterator iter = mMessageMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            IMMsgBean value = (IMMsgBean) entry.getValue();
            list.add(value);
        }
        if (list.size()>1){
            QuickSort.eMMessageSort(list,0,list.size()-1);
        }
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_message,parent,false);
        return new MessageHolder(v);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final EMMessage msg = mMessageList.get(position).getMsg();
        //判断信息来源 若来自登陆用户自身则需要显示发送给谁！

        if(holder instanceof MessageHolder){
            String current = SpUtil.getString(Constant.CURRENT_LOGIN_ID,null);
            String from = msg.getFrom();
            if(msg.getFrom() != null && msg.getFrom().equals(SpUtil.getString(Constant.CURRENT_LOGIN_ID,null))){
                ((MessageHolder) holder).mName.setText(msg.getTo());
                mMessageList.get(position).setUnread(false);
            }else {
                ((MessageHolder) holder).mName.setText(msg.getFrom());
            }
            //nickname = mMessageList.get(position).ext().get("nickname");
            //if(//nickname!=null){
                //((MessageHolder) holder).mName.setText(mMessageList.get(position).ext().get("nickname").toString());
            //}else {
            //}
            EMMessageBody body = msg.getBody();
            if(body instanceof EMTextMessageBody){
                ((MessageHolder) holder).mLastestMessage.setText(((EMTextMessageBody) body).getMessage());
            }else if(body instanceof EMImageMessageBody){
                ((MessageHolder) holder).mLastestMessage.setText("[图片]");
            }else if(body instanceof EMVoiceMessageBody){
                ((MessageHolder) holder).mLastestMessage.setText("[语音]");
            }
            if (mMessageList.get(position).isUnread()){
                ((MessageHolder) holder).showRed();
            }else {
                ((MessageHolder) holder).dismissRed();
            }
            //头像优先访问缓存---若没有则网络下载
            //Glide.with(mContext).load("").asBitmap().into(((MessageHolder) holder).mGuestAvatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MessageHolder) holder).dismissRed();
                    mMessageList.get(position).setUnread(false);
                    try {
                        HashMap<String,String> data = new HashMap<String, String>();
                       if(msg.getChatType()== EMMessage.ChatType.Chat){
                           data.put(Constant.CHAT_TYPE,Constant.CHAT_TYPE_SINGLE+"");
                           data.put(Constant.USER_NICKNAME,SpUtil.getString(Constant.USER_NICKNAME,"测试"));
                           if(msg.getFrom().equals(SpUtil.getString(Constant.CURRENT_LOGIN_ID,null))){
                               data.put(Constant.EASEMOB_ID,msg.getTo());
                           }else {
                               data.put(Constant.EASEMOB_ID,msg.getFrom());
                           }
                       }else {
                           data.put(Constant.CHAT_TYPE,Constant.CHAT_TYPE_GROUP+"");
                           data.put(Constant.GROUP_NAME,SpUtil.getString(Constant.GROUP_NAME,""));
                           data.put(Constant.GROUP_ID,msg.getFrom());// TODO: 2017/6/22 验证是否这样去取多人聊天
                       }
                        RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(mContext,5);
                        routerRequest.provider("Chat")
                                .action("Chat").data(data);
                        LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(mContext,routerRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mMessageMap!=null?mMessageMap.size():0;
    }

    public void updateLastestMessage(HashMap<String,IMMsgBean> newMap){
        Iterator iter = newMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            IMMsgBean value = (IMMsgBean) entry.getValue();
            mMessageMap.put(key,value);
        }
        mMessageList = Map2SortedList(mMessageMap);
        notifyRecycler();
    }

    private synchronized void notifyRecycler() {
        if(listener!=null)
            listener.notifyNewMsg();
    }

    public void setOnNewMsgListener(OnNewMsgListener listener){
        this.listener = listener;
    }

    public void refreshList() {
        notifyDataSetChanged();
    }


    public class MessageHolder extends RecyclerView.ViewHolder{
        public CircleImageView mGuestAvatar;
        public CircleImageView mRedPoint;
        public TextView mName;
        public TextView mLastestMessage;


        public MessageHolder(View itemView) {
            super(itemView);
            mGuestAvatar = (CircleImageView) itemView.findViewById(R.id.guest_avatar);
            mName = (TextView) itemView.findViewById(R.id.guest_name);
            mLastestMessage = (TextView) itemView.findViewById(R.id.guest_lastest_inform);
            mRedPoint = (CircleImageView) itemView.findViewById(R.id.guest_unread_remainder);
            mRedPoint.setVisibility(View.GONE);
        }

        public void showRed(){
            mRedPoint.setVisibility(View.VISIBLE);
        }

        public void dismissRed(){
            mRedPoint.setVisibility(View.GONE);
        }
    }
}
