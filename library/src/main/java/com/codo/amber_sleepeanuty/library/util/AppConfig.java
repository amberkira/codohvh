package com.codo.amber_sleepeanuty.library.util;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.Constant;

/**
 * Created by amber_sleepeanuty on 2017/4/24.
 */

public class AppConfig {

    private static String KEY_IS_SEE = "key_is_see";
    private static String KEY_IS_Cache = "key_is_cache";


    /**
     * 设置是否缓存数据
     *
     * @param context
     * @param isSee
     */
    public static void setIsCache(Context context, boolean isSee) {
        SpUtil.saveBoolean(KEY_IS_Cache, isSee);
    }

    /**
     * 获取是否缓存数据
     *
     * @param context
     * @return
     */
    public static boolean getIsCache(Context context) {
        return (boolean) SpUtil.getBoolean(KEY_IS_Cache, true);
    }

    public static int getInitTime() {
        return SpUtil.getInt(Constant.INIT_TIMES,0);
    }

}
