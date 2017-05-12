package com.codo.amber_sleepeanuty.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/5/8.
 */

public class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;
    public List<T> list;
    public Context context;
    public View header;
    public View footer;

    public int itemLayoutRes;

    public RecyclerViewBaseAdapter(@NonNull Context context, @NonNull List<T> list, int ItemLayoutRes) {
        this.context = context;
        this.list = list;
        itemLayoutRes = ItemLayoutRes;
    }

    public void setHeader(View header){
        this.header = header;
        notifyItemInserted(0);
    }

    public void setFooter(View footer){
        this.footer = footer;
        notifyItemInserted(getItemCount()-1);
    }

    public int getBottomPos(){
        return getItemCount()-1;
    }

    public int getHeaderCount(){
        return header==null?0:1;
    }

    public int getFooterCount(){
        return footer==null?0:1;
    }

    public int getListCount(){
        return list==null?0:list.size();
    }

    public void updata(List<T> list){
        if(list!=null){
            this.list.addAll(list);
        }
    }

    public void clear(){
        this.list.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (header != null && position == 0) {
            return TYPE_HEADER;
        } else if (footer != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getFooterCount()+getHeaderCount()+getListCount();
    }
}
