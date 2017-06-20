package com.chm.chmframwork.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chm.chmframwork.home.ui.fragment.two.child.childpager.OtherPagerFragment;

/**
 * Created by chenmin on 2017/6/15.
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"PagerFragmentAdapter1", "PagerFragmentAdapter2", "PagerFragmentAdapter3"};

    public PagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String desc = mTab[position];
        return OtherPagerFragment.newInstance(desc);
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
