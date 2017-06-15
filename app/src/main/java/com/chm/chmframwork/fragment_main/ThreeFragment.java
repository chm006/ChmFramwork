package com.chm.chmframwork.fragment_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.fragment_main.three.child.ClassifyFragment;

/**
 * 第三个页面
 * Created by chenmin on 2017/6/15.
 */
public class ThreeFragment extends BaseMainFragment {

    public static ThreeFragment newInstance() {
        Bundle args = new Bundle();
        ThreeFragment fragment = new ThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            // ShopFragment是flow包里的
            loadRootFragment(R.id.three_container, ClassifyFragment.newInstance());
        } else { // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(ClassifyFragment.class) == null) {
                loadRootFragment(R.id.three_container, ClassifyFragment.newInstance());
            }
        }
    }
}
