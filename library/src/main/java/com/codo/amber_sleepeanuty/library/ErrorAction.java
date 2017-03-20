package com.codo.amber_sleepeanuty.library;


import android.content.Context;

import com.codo.amber_sleepeanuty.library.base.BaseAction;

import java.util.HashMap;

/**
 * Created by amber_sleepeanuty on 2017/3/20.
 */

public class ErrorAction extends BaseAction{
    int DEAFAULTCODE;
    String DEAFAULTMSG;
    boolean asycn;

    public ErrorAction() {
        DEAFAULTCODE = 0x0010;
        DEAFAULTMSG = "ooops!!!!!";
        asycn = false;
    }

    public ErrorAction(boolean isAsycn,int code,String msg){
        asycn = isAsycn;
        DEAFAULTCODE = code;
        DEAFAULTMSG = msg;
    }

    @Override
    public ActionResult invoke(Context context, HashMap<String, String> requestData) {
        return null;
    }

    @Override
    public  ActionResult invoke(Context context, RouterRequest requestData) {
        String code = requestData.getData().get("code");
        String msg = requestData.getData().get("msg");
        if(null != code&&null != msg){
            DEAFAULTCODE = Integer.valueOf(code);
            DEAFAULTMSG = msg;
            return new ActionResult(context,DEAFAULTCODE,DEAFAULTMSG);
        }

        return new ActionResult(context,DEAFAULTCODE,DEAFAULTMSG);
    }


    @Override
    public boolean isAsyn() {
        return asycn;
    }
}
