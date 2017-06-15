package com.chm.chmframwork.fragment_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.fragment_main.one.GirlsFragment;

/**
 * 第一个页面
 * Created by chenmin on 2017/6/15.
 */
public class OneFragment extends BaseMainFragment {

    public static OneFragment newInstance() {
        Bundle args = new Bundle();
        OneFragment fragment = new OneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.one_container, GirlsFragment.newInstance());
        }
    }
}
