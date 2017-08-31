package com.amber.module_info;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;

import com.amber.module_info.adapter.InfoListAdapter;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.VIndexBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.google.android.gms.common.api.Api;
import com.umeng.message.PushAgent;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InfoActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    InfoListAdapter mAdapter;
    int mType;
    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                mAdapter = new InfoListAdapter(getBaseContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        onStartNetwork();
        PushAgent.getInstance(this).onAppStart();
    }

    private void onStartNetwork() {
        mType = getIntent().getExtras().getInt(Constant.INFO_TYPE);
        startNetwork(mType);
    }

    private void startNetwork(int mType) {
        switch (mType){
            case Constant.INFO_1:{
                LoadType1();
                break;
            }
            case Constant.INFO_2:{
                LoadType2();
                break;
            }
            case Constant.INFO_3:{
                LoadType3();
                break;
            }
            case Constant.INFO_4:{
                LoadType4();
                break;
            }
        }
    }

    private void LoadType1() {
        LoadType(1);
    }

    private void LoadType2() {
        LoadType(2);
    }

    private void LoadType3() {
        LoadType(3);
    }

    private void LoadType4() {
        LoadType(4);
    }

    public void LoadType(int type){
        APIService.Factory.createService(this).article(type)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VIndexBean>() {
                    @Override
                    public void call(VIndexBean bean) {

                    }
                });

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_info);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
