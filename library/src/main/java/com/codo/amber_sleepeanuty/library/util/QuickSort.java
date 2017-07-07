package com.codo.amber_sleepeanuty.library.util;

import com.codo.amber_sleepeanuty.library.bean.IMMsgBean;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;

/**
 * Created by amber_sleepeanuty on 2017/6/20.
 */

public class QuickSort {
    public static void eMMessageSort(ArrayList<IMMsgBean> list, int start, int end){
        if(start < end) {
            int result = partition(list, start, end);
            eMMessageSort(list, start, result-1);//对low到result-1下标间数进行排序
            eMMessageSort(list, result+1, end);//对result+1到high下标间数进行排序
        }
    }
     static int cnt = 0;
    private static int partition(ArrayList<IMMsgBean> list, int low, int high) {
        long key = list.get(low).getMsg().getMsgTime();
        while(low < high) {
            while(low<high && list.get(high).getMsg().getMsgTime()<=key) {
                high--;
                cnt++;
            }
            IMMsgBean temp = list.get(high);
            list.add(high,list.get(low));
            list.remove(high+1);
            list.add(low,temp);
            list.remove(low+1);
            while(low<high && list.get(low).getMsg().getMsgTime()>=key) {
                low++;
                cnt++;
            }
            temp = list.get(high);
            list.add(high,list.get(low));
            list.remove(high+1);
            list.add(low,temp);
            list.remove(low+1);
        }
        return low;
    }
}
