package com.amber.module_info;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * Created by amber_sleepeanuty on 2017/9/1.
 */

public class AlphaBlackTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;


    public AlphaBlackTransformation(Context mContext,int mAlpha) {
        this(Glide.get(mContext).getBitmapPool());
    }

    public AlphaBlackTransformation(BitmapPool bitmapPool) {
        mBitmapPool = bitmapPool;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(width, height, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config);
        }
        return null;
    }

    @Override
    public String getId() {
        return "AlphaBlackTransformation";
    }
}
