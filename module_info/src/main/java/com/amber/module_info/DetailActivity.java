package com.amber.module_info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.bean.ContentBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.umeng.message.PushAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailActivity extends AppCompatActivity {
    Bundle mInitBundle;
    Toolbar mToolBar;
    ImageView mImg;
    TextView mTitle;
    TextView mDetail;

    String title;
    String name;
    String imgUrl;
    String contentUrl;
    String content;
    boolean isLike;
    boolean isCollect;

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1) {
                mDetail.setText(content);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        PushAgent.getInstance(this).onAppStart();
        LoadArticle();
    }

    private void LoadArticle() {
        APIService.Factory.createService(this).content(Integer.valueOf(contentUrl))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ContentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ContentBean contentBean) {
                        content = contentBean.getContent();
                        h.sendEmptyMessage(1);
                    }
                });
    }

    private void initView() {
        mInitBundle = getIntent().getExtras();
        mImg = (ImageView) findViewById(R.id.detail_img);
        mTitle = (TextView) findViewById(R.id.detail_title);
        mDetail = (TextView) findViewById(R.id.detail_content);
        mToolBar = (Toolbar) findViewById(R.id.detail_bar);

        if(mInitBundle == null){
            //LoadArticle() // TODO: 2017/9/1 对通过推送方式打开的文章进行加载
        }else{
            title = mInitBundle.getString("title");
            name = mInitBundle.getString("name");
            imgUrl = mInitBundle.getString("imgurl");
            contentUrl = mInitBundle.getString("txturl");
        }
        Glide.with(this).load(imgUrl).into(mImg);
        mTitle.setText(title);


        isLike = false;
        isCollect = false;

        mToolBar.setTitle("");
        mToolBar.setNavigationIcon(R.mipmap.infoback);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                DetailActivity.this.finish();

            }
        });

        setSupportActionBar(mToolBar);

        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_collect) {
                    if (!isCollect){
                        item.setIcon(R.mipmap.collect_sele);
                        isCollect = true;
                    }else {
                        item.setIcon(R.mipmap.collect_unsel);
                        isCollect = false;
                    }
                }else if (item.getItemId() == R.id.item_like) {
                    if (!isLike) {
                        item.setIcon(R.mipmap.like_sele);
                        isLike = true;
                    } else {
                        item.setIcon(R.mipmap.like_unsel);
                        isLike = false;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }
}
