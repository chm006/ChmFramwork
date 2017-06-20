package com.chm.chmframwork.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseMainFragment;
import com.chm.chmframwork.home.event.TabSelectedEvent;
import com.chm.chmframwork.home.ui.fragment.four.FourFragment;
import com.chm.chmframwork.home.ui.fragment.one.OneFragment;
import com.chm.chmframwork.home.ui.fragment.three.ThreeFragment;
import com.chm.chmframwork.home.ui.fragment.two.TwoFragment;
import com.chm.chmframwork.home.ui.view.BottomBar;
import com.chm.chmframwork.home.ui.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * tip: 多使用右上角的"查看栈视图"
 * Created by chenmin on 2017/6/15.
 */
public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {
    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;
    public static final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            mFragments[ONE] = OneFragment.newInstance();
            mFragments[TWO] = TwoFragment.newInstance();
            mFragments[THREE] = ThreeFragment.newInstance();
            mFragments[FOUR] = FourFragment.newInstance();

            loadMultipleRootFragment(R.id.container, ONE,
                    mFragments[ONE],
                    mFragments[TWO],
                    mFragments[THREE],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[ONE] = findFragment(OneFragment.class);
            mFragments[TWO] = findFragment(TwoFragment.class);
            mFragments[THREE] = findFragment(ThreeFragment.class);
            mFragments[FOUR] = findFragment(FourFragment.class);
        }

        initView();

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity2", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }
        });
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_home_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_discover_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_message_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_account_circle_white_24dp));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof OneFragment) {
                        //currentFragment.popToChild(OneHomeFragment.class, false);
                    } else if (currentFragment instanceof TwoFragment) {
                        //currentFragment.popToChild(ViewPagerFragment.class, false);
                    } else if (currentFragment instanceof ThreeFragment) {
                        //currentFragment.popToChild(ShopFragment.class, false);
                    } else if (currentFragment instanceof FourFragment) {
                        //currentFragment.popToChild(MeFragment.class, false);
                    }
                    return;
                }

                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
//    @Subscribe
//    public void onHiddenBottombarEvent(boolean hidden) {
//        if (hidden) {
//            mBottomBar.hide();
//        } else {
//            mBottomBar.show();
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
