package com.amber.module_info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amber.module_info.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class InfoListAdapter extends RecyclerView.Adapter {
    Context mContext;

    public InfoListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new H(LayoutInflater.from(mContext).inflate(R.layout.item_info,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class H extends RecyclerView.ViewHolder{
        CircleImageView mAvatar;
        ImageView mBg;
        TextView mName;
        TextView mDate;
        TextView mCategroy;
        TextView mLikes;
        TextView mTitle;
        TextView mDescription;

        public H(View itemView) {
            super(itemView);
            mAvatar = (CircleImageView) itemView.findViewById(R.id.info_avatar);
            mBg = (ImageView) itemView.findViewById(R.id.info_bg);
            mName = (TextView) itemView.findViewById(R.id.info_name);
            mDate = (TextView) itemView.findViewById(R.id.info_date);
            mCategroy = (TextView) itemView.findViewById(R.id.info_categroy);
            mLikes = (TextView) itemView.findViewById(R.id.info_likes);
            mTitle = (TextView) itemView.findViewById(R.id.info_title);
            mDescription = (TextView) itemView.findViewById(R.id.info_descrip);
        }
    }
}
