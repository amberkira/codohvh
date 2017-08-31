package com.amber.module_info;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.umeng.message.PushAgent;

/**
 * Created by amber_sleepeanuty on 2017/8/31.
 */

public class DetailActivity extends Activity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        PushAgent.getInstance(this).onAppStart();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_info);

    }
}
