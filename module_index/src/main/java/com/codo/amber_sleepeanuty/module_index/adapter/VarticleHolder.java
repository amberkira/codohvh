package com.codo.amber_sleepeanuty.module_index.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.module_index.R;

/**
 * Created by amber_sleepeanuty on 2017/8/28.
 */

public class VarticleHolder extends VBaseHolder {
    public ImageView mPic;
    public TextView mTxt;
    public LinearLayout mlayout;

    public VarticleHolder(View itemView) {
        super(itemView);
        mPic = (ImageView) itemView.findViewById(R.id.item_img);
        mTxt = (TextView) itemView.findViewById(R.id.item_title);
        mlayout = (LinearLayout) itemView.findViewById(R.id.itemlayout);
    }
}
