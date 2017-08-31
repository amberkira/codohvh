package com.codo.amber_sleepeanuty.module_index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.bean.FriendListBean;
import com.codo.amber_sleepeanuty.module_index.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amber_sleepeanuty on 2017/6/28.
 */

public class FriendlListAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<FriendListBean.Infolist> mList;

    public FriendlListAdapter(Context context, List<FriendListBean.Info> list){
        mContext = context;
        mList = assembleList(list);
        notifyDataSetChanged();

    }

    private List<FriendListBean.Infolist> assembleList(List<FriendListBean.Info> list) {

        List<FriendListBean.Infolist> temp = new ArrayList<>();
        for(int i = 0; i <list.size(); i++){
            temp.addAll(list.get(i).getInfolist());
        }

        return temp;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_friendlist,parent,false);
        return new FriendListHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FriendListHolder){
            ((FriendListHolder) holder).name.setText(mList.get(position).getNickname());
            Glide.with(mContext).load(mList.get(position).getPortrait()).into(((FriendListHolder) holder).avatar);
        }

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class FriendListHolder extends RecyclerView.ViewHolder{
        CircleImageView avatar;
        TextView name;

        public FriendListHolder(View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.friend_avatar);
            name = (TextView) itemView.findViewById(R.id.friend_name);
        }
    }
}
