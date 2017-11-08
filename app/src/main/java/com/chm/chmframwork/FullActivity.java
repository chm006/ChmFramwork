package com.chm.chmframwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.bean.FragmentBean;
import com.chm.framwork.utilcode.util.ActivityUtils;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * FullActivity
 * Created by chm00 on 2017/11/8.
 */

public class FullActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener, FullView {
    private FullPresenters presenter = new FullPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        presenter.loadRootFragment();

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("FullActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finish();
            //关闭窗体动画显示
            overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
        }
    }

    @Override
    public void onBackToFirstFragment() {
    }

    @Override
    public void loadRootFragment(FragmentBean bean) {
        loadRootFragment(R.id.container, bean.getFragment());
    }
}