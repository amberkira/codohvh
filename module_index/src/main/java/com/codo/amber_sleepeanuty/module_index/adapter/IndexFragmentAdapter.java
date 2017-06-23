package com.codo.amber_sleepeanuty.module_index.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import layout.fragment_index;
import layout.fragment_message;
import layout.fragment_profile;

/**
 * Created by amber_sleepeanuty on 2017/5/10.
 */

public class IndexFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;


    public IndexFragmentAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==3){
            return new fragment_profile();
        }
        if (position==2){
            return new fragment_message();
        }
        return new fragment_index();
    }

    @Override
    public int getCount() {
        return 4;//fragments.size();
    }
}
