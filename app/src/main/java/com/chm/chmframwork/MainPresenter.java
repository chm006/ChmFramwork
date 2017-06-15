package com.chm.chmframwork;

import android.os.Bundle;

import com.chm.chmframwork.fragment_main.FourFragment;
import com.chm.chmframwork.fragment_main.OneFragment;
import com.chm.chmframwork.fragment_main.ThreeFragment;
import com.chm.chmframwork.fragment_main.TwoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Presenter
 * Created by chenmin on 2017/11/8.
 */

public class MainPresenter implements MainPresenters {

    private MainView view;

    public static final int ONE = 0;
    private static final int TWO = 1;
    private static final int THREE = 2;
    private static final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    MainPresenter(MainView view) {
        this.view = view;
    }

    @Override
    public void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[ONE] = OneFragment.newInstance();
            mFragments[TWO] = TwoFragment.newInstance();
            mFragments[THREE] = ThreeFragment.newInstance();
            mFragments[FOUR] = FourFragment.newInstance();

            view.loadMultipleRootFragment(R.id.container, ONE,
                    mFragments[ONE],
                    mFragments[TWO],
                    mFragments[THREE],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[ONE] = view.findFragment(OneFragment.class);
            mFragments[TWO] = view.findFragment(TwoFragment.class);
            mFragments[THREE] = view.findFragment(ThreeFragment.class);
            mFragments[FOUR] = view.findFragment(FourFragment.class);
        }
    }

    @Override
    public void initBottomBar() {
        view.setBottomBar();
    }

    @Override
    public SupportFragment[] getFragments() {
        return mFragments;
    }
}
