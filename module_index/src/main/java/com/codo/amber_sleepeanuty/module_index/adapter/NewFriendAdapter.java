package com.codo.amber_sleepeanuty.module_index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.module_index.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amber_sleepeanuty on 2017/6/27.
 */

public class NewFriendAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<ApplyBean> mList;

    public NewFriendAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mList.add(0,new ApplyBean("测试数据","测试数据"));//// TODO: 2017/6/27 记得删除
        mList.add(0,new ApplyBean("测试数据","测试数据"));//// TODO: 2017/6/27 记得删除
        mList.add(0,new ApplyBean("测试数据","测试数据"));//// TODO: 2017/6/27 记得删除

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_new_friend,parent,false);
        return new ApplyHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ApplyHolder){
            final ApplyBean temp = mList.get(position);
            ((ApplyHolder) holder).mApplierName.setText("用户");// TODO: 2017/6/27 访问接口采集
            if(temp.getReason()!=null&& !TextUtils.isEmpty(temp.getReason()))
            ((ApplyHolder) holder).mApplierReason.setText(mList.get(position).getReason());

            ((ApplyHolder) holder).mBtnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(temp.getName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });

            ((ApplyHolder) holder).mBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(temp.getName());
                        // TODO: 2017/6/27 之后访问接口获取相应值跳转至chatactivity 
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
            // TODO: 2017/6/27 获取头像以及之后点击头像去persondetailactivity 
        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void addNewApply(String name ,String reason){
        mList.add(0,new ApplyBean(name,reason));
    }

    class ApplyHolder extends RecyclerView.ViewHolder {
        public TextView mBtnAccept;
        public TextView mBtnDecline;
        public TextView mApplierName;
        public TextView mApplierReason;
        public CircleImageView mApplierAvatar;

        public ApplyHolder(View itemView) {
            super(itemView);
            mBtnAccept = (TextView) itemView.findViewById(R.id.btn_accept);
            mBtnDecline = (TextView) itemView.findViewById(R.id.btn_decline);
            mApplierName = (TextView) itemView.findViewById(R.id.tv_applier_name);
            mApplierReason = (TextView) itemView.findViewById(R.id.tv_reason);
            mApplierAvatar = (CircleImageView) itemView.findViewById(R.id.applier_avatar);
        }
    }

    class ApplyBean{
        String name;
        String reason;

        public ApplyBean(String name, String reason) {
            this.reason = reason;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }



}
