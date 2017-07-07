package com.codo.amber_sleepeanuty.library.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * Created by amber_sleepeanuty on 2017/6/20.
 * 适配android 7.0 访问照片相机
 */



public class FileProvider7 {
    public static Uri getUrifromFile(Context context,File file){
        Uri fileUri = null;
        if(Build.VERSION.SDK_INT >= 24){
            fileUri = getUriForFile24(context,file);
        }else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName()+".android7.fileprovider",file);
        return fileUri;
    }

}
