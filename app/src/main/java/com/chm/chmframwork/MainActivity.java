package com.chm.chmframwork;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chm.framwork.fragmentation.SupportActivity;
import com.chm.framwork.fragmentation.anim.DefaultHorizontalAnimator;
import com.chm.framwork.fragmentation.anim.FragmentAnimator;

/**
 * MainActivity
 * Created by chenmin on 2017/6/15.
 */
public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.activity_main_container, MainFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
