package com.codo.amber_sleepeanuty.module_login.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by amber_sleepeanuty on 2017/5/1.
 */

public class VpAdapter extends FragmentPagerAdapter {
    public FragmentManager fragmentManager;
    public VpAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
