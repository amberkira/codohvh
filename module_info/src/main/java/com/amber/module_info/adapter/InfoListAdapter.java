package com.amber.module_info.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amber.module_info.DetailActivity;
import com.amber.module_info.R;
import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.bean.InfoListBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class InfoListAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<InfoListBean.ListBean> mList;

    public InfoListAdapter(Context mContext,List<InfoListBean.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new H(LayoutInflater.from(mContext).inflate(R.layout.item_info,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final InfoListBean.ListBean bean = mList.get(position);
        if (holder instanceof H){
            if (bean.getTitle() != null)
                ((H) holder).mTitle.setText(bean.getTitle());
            if (bean.getAvatar() != null)
                Glide.with(mContext).load(bean.getAvatar()).bitmapTransform(new GrayscaleTransformation(mContext)).into(((H) holder).mAvatar);
            if (bean.getTime() != null)
                ((H) holder).mDate.setText(bean.getTime());
            if (bean.getWriter() != null)
                ((H) holder).mName.setText(bean.getWriter());
            if (bean.getDescription() != null)
                ((H) holder).mName.setText(bean.getDescription());
            if (bean.getImgUrl() != null)
                Glide.with(mContext).load(bean.getImgUrl()).into(((H) holder).mBg);
            if (bean.getLikes() != null)
                ((H) holder).mLikes.setText(bean.getLikes());
            if (bean.getCategory()!=null){
                ((H) holder).mCategroy.setText(bean.getCategory());
            }
            ((H) holder).mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",bean.getTitle());
                    bundle.putString("name",bean.getWriter());
                    bundle.putString("imgurl",bean.getImgUrl());
                    bundle.putString("txturl",bean.getTxtUrl());
                    Intent it = new Intent(mContext, DetailActivity.class);
                    it.putExtras(bundle);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(it);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mList == null?0:mList.size();
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
        LinearLayout mLayout;

        public H(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.info_layout);
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
