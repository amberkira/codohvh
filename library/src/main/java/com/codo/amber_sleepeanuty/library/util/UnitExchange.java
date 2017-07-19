package com.codo.amber_sleepeanuty.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by amber_sleepeanuty on 2017/6/19.
 */

public class UnitExchange {

    public static int dx2px(Context context, float dx){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dx * scale + 0.5f);
    }

    public static int px2dip(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Bitmap getImageSize(Bitmap bitmap) {
        ImageSize imageSize = new ImageSize();
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteTmp = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteTmp, 0, byteTmp.length, bitmapOptions);
        int outWidth = bitmapOptions.outWidth;
        int outHeight = bitmapOptions.outHeight;
        int maxWidth = 400;
        int maxHeight = 400;
        int minWidth = 150;
        int minHeight = 150;
        if (outWidth / maxWidth > outHeight / maxHeight) {//
            if (outWidth >= maxWidth) {//
                imageSize.setWidth(maxWidth);
                imageSize.setHeight(outHeight * maxWidth / outWidth);
            } else {
                imageSize.setWidth(outWidth);
                imageSize.setHeight(outHeight);
            }
            if (outHeight < minHeight) {
                imageSize.setHeight(minHeight);
                int width = outWidth * minHeight / outHeight;
                if (width > maxWidth) {
                    imageSize.setWidth(maxWidth);
                } else {
                    imageSize.setWidth(width);
                }
            }
        } else {
            if (outHeight >= maxHeight) {
                imageSize.setHeight(maxHeight);
                imageSize.setWidth(outWidth * maxHeight / outHeight);
            } else {
                imageSize.setHeight(outHeight);
                imageSize.setWidth(outWidth);
            }
            if (outWidth < minWidth) {
                imageSize.setWidth(minWidth);
                int height = outHeight * minWidth / outWidth;
                if (height > maxHeight) {
                    imageSize.setHeight(maxHeight);
                } else {
                    imageSize.setHeight(height);
                }
            }
        }
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = Math.max(bitmapOptions.outHeight/imageSize.height,bitmapOptions.outWidth/imageSize.width);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteTmp, 0, byteTmp.length, bitmapOptions);

        return bitmap1;
    }

    public static int MeasureImageSize(BitmapFactory.Options opt){

        ImageSize imageSize = new ImageSize();
        int outWidth = opt.outWidth;
        int outHeight = opt.outHeight;
        int maxWidth = 400;
        int maxHeight = 400;
        int minWidth = 150;
        int minHeight = 150;
        int samplesize = 1;

        if (outWidth / maxWidth > outHeight / maxHeight) {//
            if (outWidth >= maxWidth) {//
                imageSize.setWidth(maxWidth);
                imageSize.setHeight(outHeight * maxWidth / outWidth);
            } else {
                imageSize.setWidth(outWidth);
                imageSize.setHeight(outHeight);
            }
            if (outHeight < minHeight) {
                imageSize.setHeight(minHeight);
                int width = outWidth * minHeight / outHeight;
                if (width > maxWidth) {
                    imageSize.setWidth(maxWidth);
                } else {
                    imageSize.setWidth(width);
                }
            }
        } else {
            if (outHeight >= maxHeight) {
                imageSize.setHeight(maxHeight);
                imageSize.setWidth(outWidth * maxHeight / outHeight);
            } else {
                imageSize.setHeight(outHeight);
                imageSize.setWidth(outWidth);
            }
            if (outWidth < minWidth) {
                imageSize.setWidth(minWidth);
                int height = outHeight * minWidth / outWidth;
                if (height > maxHeight) {
                    imageSize.setHeight(maxHeight);
                } else {
                    imageSize.setHeight(height);
                }
            }
        }

        return samplesize = Math.max(outHeight/imageSize.height,outWidth/imageSize.width);
    }

    public static String Timestamp2DataString(long timestamp){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time=new Long(timestamp);
        String s = format.format(time);
        return s;
    }
}
