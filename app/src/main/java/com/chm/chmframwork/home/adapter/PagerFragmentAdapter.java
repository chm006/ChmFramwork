package com.chm.chmframwork.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmin on 2017/6/15.
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] pageTitles;
    private List<Fragment> fragments = new ArrayList<>();

    public PagerFragmentAdapter(FragmentManager fm, String[] pageTitles, List<Fragment> fragments) {
        super(fm);
        this.pageTitles = pageTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
