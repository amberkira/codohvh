package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.codo.amber_sleepeanuty.module_login.adapter.VpAdapter;

import java.util.ArrayList;

/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class MainPageActivity extends AppCompatActivity {
    public ViewPager mViewPager;
    public FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage_layout);
        mViewPager = (ViewPager) findViewById(R.id.mainpage_viewpager);
        VpAdapter v = new VpAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(v);
        mFAB = (FloatingActionButton) findViewById(R.id.mainpage_fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
