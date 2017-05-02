package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.codo.amber_sleepeanuty.module_login.adapter.RvAdapter;

import java.util.ArrayList;

/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class MainPageActivity extends Activity {
    public ViewPager mViewPager;
    public RecyclerView mRecyclerView;
    public RvAdapter mAdapter;
    public Fragment mFragment1;
    public Fragment mFragment2;
    public ImageView mHeader;
    public FloatingActionButton mFAB;
    public ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    private void initFragment() {
    }


    private void initData() {
        for(int i=0;i<mList.size();i++){
            mList.add("第"+i+"项");
        }
    }

    private void setFooterView(RecyclerView view) {
        View Footer = LayoutInflater.from(this).inflate(R.layout.mainpage_footer_transparent,view,false);
        mAdapter.setFooterView(Footer);
    }

    public void setHeaderView(RecyclerView view) {
        View Header = LayoutInflater.from(this).inflate(R.layout.mainpage_header_transparent,view,false);
        mAdapter.setHeaderView(Header);
    }
}
