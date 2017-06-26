package com.codo.amber_sleepeanuty.module_index.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import layout.GuestMessageFragment;

/**
 * Created by amber_sleepeanuty on 2017/6/19.
 */

public class MessageFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;


    public MessageFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return GuestMessageFragment.newInstance();
        }
        return GuestMessageFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
