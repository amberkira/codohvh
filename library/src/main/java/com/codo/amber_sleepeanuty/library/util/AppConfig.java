package com.codo.amber_sleepeanuty.library.util;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.LoginBean;

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

    public static void LoginConfig(LoginBean bean){

        if (bean == null){
            return;
        }

        if (bean.getServer().getErrno()==1001){
            return;
        }
        LoginBean.Server.Info info = bean.getServer().getInfo();
        SpUtil.saveString(Constant.USER_NICKNAME,info.getUsername());
        SpUtil.saveString(Constant.USER_NAME,info.getUserid());
        SpUtil.saveString(Constant.EASEMOB_ID,info.getUserid());
        SpUtil.saveString(Constant.USER_AVATAR,info.getPortrait());
        SpUtil.saveString(Constant.SESSION_ID,info.getSessionid());
        SpUtil.saveString(Constant.LATITUDE,info.getLat());
        SpUtil.saveString(Constant.LONGITUDE,info.getLng());
        SpUtil.saveString(Constant.MOBILE,info.getMobile());
        SpUtil.saveString(Constant.DEVICE_ID,info.getDevid());
        SpUtil.saveLong(Constant.CUR_TIME,info.getCurtime());
        SpUtil.saveLong(Constant.LAST_CHECK,info.getLastsignin());
        SpUtil.saveInt(Constant.DEVICE_ID,info.getCredits());


    }


}
