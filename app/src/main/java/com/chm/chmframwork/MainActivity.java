package com.chm.chmframwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.event.TabSelectedEvent;
import com.chm.chmframwork.widget.BottomBar;
import com.chm.chmframwork.widget.BottomBarTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * tip: 多使用右上角的"查看栈视图"
 * 底部带BottomBar的主页
 * Created by chenmin on 2017/6/15.
 */
public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener, MainView {

    private MainPresenters presenter = new MainPresenter(this);

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        presenter.initFragments(savedInstanceState);

        initView();

        presenter.initBottomBar();

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }
        });
    }

    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
    }

    @Override
    public void setBottomBar() {
        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_home_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_discover_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_message_white_24dp))
                .addItem(new BottomBarTab(this, R.mipmap.ic_account_circle_white_24dp));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                SupportFragment[] fragments = presenter.getFragments();
                showHideFragment(fragments[position], fragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = presenter.getFragments()[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

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
    public void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments) {
        super.loadMultipleRootFragment(containerId, showPosition, toFragments);
    }

    @Override
    public <T extends SupportFragment> T findFragment(Class<T> c) {
        return super.findFragment(c);
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        //如果不是首页fragment，则回到首页fragment
        mBottomBar.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onHiddenBottombarEvent(boolean hidden) {
        if (hidden) {
            mBottomBar.hide();
        } else {
            mBottomBar.show();
        }
    }
}
