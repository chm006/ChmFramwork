package com.chm.chmframwork.home.ui.fragment.four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseMainFragment;
import com.chm.chmframwork.home.ui.fragment.four.child.AvatarFragment;
import com.chm.chmframwork.home.ui.fragment.four.child.MeFragment;

/**
 * 第四个页面
 * Created by chenmin on 2017/6/15.
 */
public class FourFragment extends BaseMainFragment {
    private Toolbar mToolbar;
    private View mView;

    public static FourFragment newInstance() {

        Bundle args = new Bundle();

        FourFragment fragment = new FourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_four, container, false);
        return mView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadFragment();
        } else {  // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(AvatarFragment.class) == null) {
                loadFragment();
            }
        }

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mToolbar.setTitle("我的");
    }

    private void loadFragment() {
        loadRootFragment(R.id.four_container_upper, AvatarFragment.newInstance());
        loadRootFragment(R.id.four_container_lower, MeFragment.newInstance());
    }

    public void onBackToFirstFragment() {
        _mBackToFirstListener.onBackToFirstFragment();
    }
}
