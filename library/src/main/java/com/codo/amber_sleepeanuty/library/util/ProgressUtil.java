package com.codo.amber_sleepeanuty.library.util;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by amber_sleepeanuty on 2017/3/30.
 */

public class ProgressUtil extends ProgressBar{

    private static final String LOADING = "加载中";
    private static final String FAIL = "加载失败";


    public ProgressUtil(Context context) {
        this(context,null);
    }

    public ProgressUtil(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
