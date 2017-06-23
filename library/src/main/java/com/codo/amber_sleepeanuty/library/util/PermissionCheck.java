package com.codo.amber_sleepeanuty.library.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by amber_sleepeanuty on 2017/5/26.
 */


public class PermissionCheck {


    private final Context mContext;

    public PermissionCheck(Context context) {
        mContext = context.getApplicationContext();

    }

    /**
     * 权限检查
     * @param context
     * @param permission  调用：Manifest.permission.XXXXXXX
     * @return
     */
    public static boolean Check(Context context,String permission){
        if(ContextCompat.checkSelfPermission(context, permission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }


    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
