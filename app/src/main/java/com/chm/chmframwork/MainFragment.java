package com.chm.chmframwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.fragment_main.four.FourFragment;
import com.chm.chmframwork.fragment_main.one.GirlsFragment;
import com.chm.chmframwork.fragment_main.three.ClassifyFragment;
import com.chm.chmframwork.fragment_main.two.ViewPagerFragment;
import com.chm.chmframwork.widget.BottomBar;
import com.chm.chmframwork.widget.BottomBarTab;
import com.chm.framwork.fragmentation.SupportFragment;
import com.chm.framwork.utilcode.util.ToastUtils;

/**
 * MainFragment
 * Created by chenmin on 2017/11/14.
 */
public class MainFragment extends BaseMainFragment {

    // 再点一次退出程序时间设置
    private final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private final int ONE = 0;
    private final int TWO = 1;
    private final int THREE = 2;
    private final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar bottomBar;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(GirlsFragment.class);
        if (firstFragment == null) {
            mFragments[ONE] = GirlsFragment.newInstance();
            mFragments[TWO] = ViewPagerFragment.newInstance();
            mFragments[THREE] = ClassifyFragment.newInstance();
            mFragments[FOUR] = FourFragment.newInstance();
            loadMultipleRootFragment(R.id.fragment_main_container, ONE,
                    mFragments[ONE],
                    mFragments[TWO],
                    mFragments[THREE],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[ONE] = findFragment(GirlsFragment.class);
            mFragments[TWO] = findFragment(ViewPagerFragment.class);
            mFragments[THREE] = findFragment(ClassifyFragment.class);
            mFragments[FOUR] = findFragment(FourFragment.class);
        }
    }

    private void initView(View view) {
        bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        bottomBar.addItem(new BottomBarTab(_mActivity, R.drawable.ic_home_white_24dp))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp));
        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        if (0 == bottomBar.getCurrentItemPosition()) {   // 如果是 第一个Fragment 则退出app
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtils.showShort(R.string.BaseMainFragment_string);
            }
        } else {                                    // 如果不是,则回到第一个Fragment
            onBackToFirstFragment();
        }
        return true;
    }

    private void onBackToFirstFragment() {
        bottomBar.setCurrentItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
