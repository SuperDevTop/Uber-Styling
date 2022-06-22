package com.foxy.arrive5.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by parangat on 30/10/18.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

}
