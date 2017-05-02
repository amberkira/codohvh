package com.codo.amber_sleepeanuty.module_login.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.module_login.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;

    public View header;
    public View footer;
    public List<String> originList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOTER){
            return new ListHolder(footer);
        }
        if(viewType==TYPE_HEADER){
            return new ListHolder(header);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_NORMAL){
            if(holder instanceof ListHolder){
                ((ListHolder) holder).tv.setText(originList.get(position-1));
                return;
            }else{
                return;
            }
        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        if(header!=null&&footer!=null){
            return originList.size()+2;
        }else if (header!=null&&footer==null){
            return originList.size()+1;
        }else if(header==null&&footer!=null){
            return originList.size()+1;
        }else{
            return originList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_NORMAL;
        if(position==0&&header!=null){
            type = TYPE_HEADER;
        }
        if(position == getItemCount()-1&&footer!=null){
            type = TYPE_FOOTER;
        }
        return type;
    }

    public void setFooterView(View footer) {
        this.footer = footer;
        notifyItemInserted(getItemCount()-1);
    }


    public void setHeaderView(View header) {
        this.header = header;
        notifyItemInserted(0);
    }

    public void initData() {
        //TODO 联网获取数据
        originList = new ArrayList<>(10);
        for (String s:originList
             ) {
            s = "测试";
            originList.add(s);
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        //TODO 联网获取数据之后的展示数据格式完善
        public  TextView tv;

        public ListHolder(View itemView) {
            super(itemView);
            if(itemView==header||itemView==footer){
                return;
            }
            tv = (TextView) itemView.findViewById(R.id.test_item);
        }
    }
}
