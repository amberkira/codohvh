package com.codo.amber_sleepeanuty.library.util;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.R;
import com.codo.amber_sleepeanuty.library.bean.DailyBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.service.CheckService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/9/7.
 */

public class DailyAttendanceBoard extends Dialog implements View.OnClickListener{

    private Button cancel;
    private Button submit;
    public Context mContext;

    public DailyAttendanceBoard(Context context) {
        this(context,0);
        mContext = context;
    }

    public DailyAttendanceBoard(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static final class Builder{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dailyattendence);
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel){
            this.dismiss();
        }else if (v.getId() == R.id.submit){
            this.dismiss();
            mContext.startService(new Intent(mContext, CheckService.class));
        }

    }


}
