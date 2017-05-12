package com.codo.amber_sleepeanuty.module_login.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codo.amber_sleepeanuty.library.util.LogUtil;

import java.util.List;

import layout.mainpage_fragment;

/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class VpAdapter extends FragmentPagerAdapter {
    public FragmentManager fragmentManager;
    public List<Fragment> list;
    public VpAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new mainpage_fragment();
            case 1:
                return new mainpage_fragment();
            default:
                return new mainpage_fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
