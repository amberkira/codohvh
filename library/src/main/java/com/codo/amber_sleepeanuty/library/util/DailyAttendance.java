package com.codo.amber_sleepeanuty.library.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.DailyBean;
import com.codo.amber_sleepeanuty.library.network.APIService;

import java.util.Calendar;
import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/9/6.
 */

public class DailyAttendance {
    boolean isCheck;
    OnCheckListener L;

    public DailyAttendance() {
        this.isCheck = false;
    }

    public boolean isCheck(){
        APIService.Factory.createService(CodoApplication.getCodoApplication())
                .isSameDay(SpUtil.getString(Constant.USER_NAME,null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyBean>() {
                    @Override
                    public void call(DailyBean dailyBean) {

                        Long now = dailyBean.getCurtime();
                        Long last = dailyBean.getLastsignin();
                        Log.e("check","call enter"+" now:"+now+" last"+last);

                        if (now != null && last != null){
                            isCheck = isSameDay(now*1000,last*1000);
                            Log.e("check",isCheck+"");
                            if (!isCheck){
                                L.notifyIsCheck();
                            }
                        }
                    }
                });

        return isCheck;

    }


    public boolean isSameDay(@NonNull Long now,@NonNull Long last){
        Date now1 = new Date(now);
        Date now2 = new Date(now*1000);
        Date last1 = new Date(last);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(last1);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    public void setOnCheckListener(OnCheckListener l){
        L = l;
    }

    public void clear(){
        L = null;
    }


    public interface OnCheckListener{
        void notifyIsCheck();
    }


}
