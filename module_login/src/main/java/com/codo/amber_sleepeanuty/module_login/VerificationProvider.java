package com.codo.amber_sleepeanuty.module_login;

import com.codo.amber_sleepeanuty.library.util.LogUtil;

import java.util.Random;

/**
 * Created by amber_sleepeanuty on 2017/4/24.
 */

public class VerificationProvider {
    private static VerificationProvider mProvider;
    private static final int LEAST_DIGIT = 4;
    private static final int MAX_DIGIT = 8;

    private  VerificationProvider(){
    }

    public static VerificationProvider getInstance(){
        if(mProvider==null){
            mProvider = new VerificationProvider();
        }
        return mProvider;
    }

    public static String Build(int digit){
        if(digit<LEAST_DIGIT){
            digit = LEAST_DIGIT;
        }else if(digit>8){
            digit = MAX_DIGIT;
        }
        String rand = "";
        for(int i = 0;i<digit;i++){
            rand += String.valueOf((int)(Math.random()*10));
        }
        LogUtil.d("VERIFICATION",rand);
        return rand;

    }
}
