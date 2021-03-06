package com.codo.amber_sleepeanuty.library;

import android.content.Context;

import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amber_sleepeanuty on 2017/3/17.
 */

public class RouterRequestPool {
    private static int DEAFAULT_SIZE = 64;
    private static int RESET_NUM = 1000;
    private int index;
    private static RouterRequest[] pool;
    private static RouterRequest standByRequest;
    private static AtomicInteger supplyIndex = new AtomicInteger(0);


    static {
        pool = new RouterRequest[DEAFAULT_SIZE];
        for(int i = 0;i<DEAFAULT_SIZE;i++){
            pool[i] = new RouterRequest();
        }
    }

    public static RouterRequest getAvailableRequest(Context context, int retryTime){
        int temp = supplyIndex.getAndIncrement();
        if(temp>RESET_NUM){
            supplyIndex.compareAndSet(temp,0);
            if (temp>RESET_NUM*2){
                supplyIndex.set(0);
            }
        }
        int loc = temp&(DEAFAULT_SIZE-1);
        standByRequest = pool[loc];
        if(standByRequest.isIdle.compareAndSet(true,false)){
            standByRequest.domain(ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId()));
            standByRequest.action("");
            standByRequest.provider("");
            standByRequest.getData().clear();
            return standByRequest;
        }else{
            if (retryTime < 5) {
                return getAvailableRequest(context, retryTime++);
            } else {
                return new RouterRequest(context);
            }
        }
    }


}
