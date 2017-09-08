package com.codo.amber_sleepeanuty.module_index.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.codo.amber_sleepeanuty.module_index.R;

/**
 * Created by amber_sleepeanuty on 2017/9/7.
 */

public class VHeadHolder extends VBaseHolder {
    public ProgressBar bar;
    public VHeadHolder(View itemView) {
        super(itemView);
        bar = (ProgressBar) itemView.findViewById(R.id.item_header);
    }
}
