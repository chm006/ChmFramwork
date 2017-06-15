package com.chm.chmframwork.fragment_main.four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseFragment;
import com.chm.chmframwork.fragment_main.FourFragment;

/**
 * MeFragment
 * Created by chenmin on 2017/6/22.
 */
public class MeFragment extends BaseFragment {

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView mTvBtnSettings = (TextView) view.findViewById(R.id.tv_btn_settings);
        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SettingsFragment.newInstance());
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((FourFragment) getParentFragment()).onBackToFirstFragment();
        return true;
    }
}
