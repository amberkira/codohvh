package com.codo.amber_sleepeanuty.module_index.adapter;

import android.view.View;

import com.codo.amber_sleepeanuty.module_index.R;
import com.sivin.Banner;

/**
 * Created by amber_sleepeanuty on 2017/8/28.
 */

public class VBannerHolder extends VBaseHolder {
    public Banner mBanner;
    public VBannerHolder(View itemView) {
        super(itemView);
        mBanner = (Banner) itemView.findViewById(R.id.item_banner);
    }
}
