package com.codo.amber_sleepeanuty.module_login.adapter;

/**
 * Created by amber_sleepeanuty on 2017/5/8.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.adapter.RecyclerViewBaseAdapter;
import com.codo.amber_sleepeanuty.library.bean.HospitalsBean;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;
import com.codo.amber_sleepeanuty.module_login.R;

import java.util.List;


/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class HospitalsAdapter extends RecyclerViewBaseAdapter<HospitalsBean.SubjectsBean> {


    public HospitalsAdapter(@NonNull Context context, @NonNull List list, int ItemLayoutRes) {
        super(context, list, ItemLayoutRes);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOTER){
            return new ListHolder(footer);
        }
        if(viewType==TYPE_HEADER){
            return new ListHolder(header);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false);

        return new ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_NORMAL){
            if(holder instanceof ListHolder){
                ((ListHolder) holder).name.setText(CheckNotNull.check(list.get(position-1).getName()));
                ((ListHolder) holder).summary.setText(CheckNotNull.check(list.get(position-1).getSummary()));
                ImageView v = ((ListHolder) holder).pic;
                Glide.with(context).load("http://omizc9kwk.bkt.clouddn.com/9BA6AD87-1563-4352-919A-7C1245277745.png")
                        .placeholder(android.R.color.white).into(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"你点击了"+((ListHolder) holder).name.getText(),Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }else{
                return;
            }
        }else{
            return;
        }

    }

    public class ListHolder extends RecyclerView.ViewHolder{
        //TODO 联网获取数据之后的展示数据格式完善
        public TextView name;
        public TextView summary;
        public ImageView pic;

        public ListHolder(View itemView) {
            super(itemView);
            if(itemView==header||itemView==footer){
                return;
            }
            name = (TextView) itemView.findViewById(R.id.mainpage_item_name);
            summary = (TextView) itemView.findViewById(R.id.mainpage_item_summary);
            pic = (ImageView) itemView.findViewById(R.id.mainpage_item_small_img);
        }


    }
}

