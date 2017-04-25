package com.codo.amber_sleepeanuty.library.util;

/**
 * Created by amber_sleepeanuty on 2017/4/25.
 */

public class ThrottleUtil {
    private static long lastClick = 0;
    private static final long THRESHOLD_TIME = 60*1000;

    public static boolean isRapid(){
        long current = System.currentTimeMillis();
        boolean b = true;
        if (current-lastClick>THRESHOLD_TIME){
            b = false;
        }
        lastClick = current;
        return b;
    }
}
