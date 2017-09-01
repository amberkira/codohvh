package com.codo.amber_sleepeanuty.module_index.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.bean.VIndexBean;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.module_index.IndexActivity;
import com.codo.amber_sleepeanuty.module_index.R;
import com.sivin.BannerAdapter;

import java.util.HashMap;


/**
 * Created by amber_sleepeanuty on 2017/8/28.
 */

public class VIndexAdapter extends DelegateAdapter.Adapter<VBaseHolder> {

    public static final int BANNER = 1;
    public static final int SERVICE = 2;
    public static final int ARTICLE = 3;

    public Context mContext;
    public int mCount;
    public VIndexBean mBean;
    public LayoutHelper mHelper;
    public int mType;

    public VIndexAdapter(Context mContext, LayoutHelper mHelper,int mCount,VIndexBean mBean,int mType) {
        this.mContext = mContext;
        this.mCount = mCount;
        this.mBean = mBean;
        this.mHelper = mHelper;
        this.mType = mType;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @Override
    public VBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (mType){
            case BANNER:{
                return new VBannerHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner,parent,false));
            }
            case SERVICE:{
                break;
            }
            case ARTICLE:{
                return new VarticleHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article,parent,false));
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    @Override
    public void onBindViewHolder(VBaseHolder holder, final int position) {

        if (holder instanceof VarticleHolder) {
            ((VarticleHolder) holder).mlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/8/29 根据item.geturl跳转到详情页面
                    HashMap<String,String> data = new HashMap<String, String>();
                    data.put(Constant.INFO_TYPE,mBean.getItem().getUrl().get(position));
                    RouterRequest request = RouterRequestPool.getAvailableRequest(mContext, 5);
                    request.provider("Info").action("Info").data(data);
                    try {
                        LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(mContext,request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Glide.with(mContext).load(mBean.getItem().getImgUrl().get(position)).into(((VarticleHolder) holder).mPic);
            ((VarticleHolder) holder).mTxt.setText(mBean.getItem().getTxt().get(position)==null?"null":mBean.getItem().getTxt().get(position));

        }else if (holder instanceof VBannerHolder) {
            BannerAdapter<String> adapter = new BannerAdapter<String>(mBean.getBanner().getUrl()) {

                @Override
                protected void bindTips(TextView tv, String s) {

                }

                @Override
                public void bindImage(ImageView imageView, String s) {
                    Glide.with(mContext).load(s).into(imageView);
                }
            };
            ((VBannerHolder) holder).mBanner.setBannerAdapter(adapter);
            ((VBannerHolder) holder).mBanner.notifyDataHasChanged();
        }
    }


}
