package com.chm.chmframwork.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chm.chmframwork.App;
import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseMainFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * FragmentActivity
 * Created by chm00 on 2017/11/8.
 */

public class FragmentActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (App.fragmentBean == null) {
            return;
        }
        FragmentBean bean = App.fragmentBean;
        loadRootFragment(R.id.container, bean.getFragment());
        App.fragmentBean = null;

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("FragmentActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
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
            overridePendingTransition(0, R.anim.activity_close);
        }
    }

    @Override
    public void onBackToFirstFragment() {
    }
}