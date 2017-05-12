package com.codo.amber_sleepeanuty.module_index;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.module_index.adapter.IndexFragmentAdapter;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import layout.fragment_index;

public class IndexActivity extends AppCompatActivity {
    public ViewPager viewPager;
    public TabLayout tablayout;
    public IndexFragmentAdapter frangmentAdapter;
    public List<Fragment> list;

    /*
    tabItem 分页数据
     */
    public String[] tabName = {"首页","订单","消息","我的"};
    public int[] imgRes =
            {R.drawable.img_index_selector,R.drawable.img_order_selector,R.drawable.img_msg_selector,R.drawable.img_profile_selector};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.layout_index);
        initFragments();
        initView();

    }

    private void initFragments() {

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.index_viewpager);
        frangmentAdapter = new IndexFragmentAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(frangmentAdapter);

        tablayout = (TabLayout) findViewById(R.id.index_tablayout);
        setTabs(tablayout,this.getLayoutInflater(),tabName,imgRes);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void setTabs(TabLayout layout, LayoutInflater inflater, String[] tabName, int[] imgRes){
        for(int i = 0;i<tabName.length;i++){
            TabLayout.Tab tab = layout.newTab();
            View view = inflater.inflate(R.layout.tab_item_layout,null);
            TextView tvTitle = (TextView)view.findViewById(R.id.tab_item_tx);
            tvTitle.setText(tabName[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.tab_item_img);
            imgTab.setImageResource(imgRes[i]);
            tab.setCustomView(view);
            layout.addTab(tab);
        }
    }
}
