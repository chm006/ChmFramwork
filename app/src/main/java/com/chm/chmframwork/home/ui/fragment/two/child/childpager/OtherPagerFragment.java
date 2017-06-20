package com.chm.chmframwork.home.ui.fragment.two.child.childpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseFragment;

/**
 * Created by chenmin on 2017/6/22.
 */
public class OtherPagerFragment extends BaseFragment {
    private static final String ARG_TYPE = "arg_pos";

    private String desc;

    public static OtherPagerFragment newInstance(String desc) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, desc);
        OtherPagerFragment fragment = new OtherPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        desc = getArguments().getString(ARG_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_pager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText(desc);
    }
}
