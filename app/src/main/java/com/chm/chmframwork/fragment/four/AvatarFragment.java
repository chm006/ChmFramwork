package com.chm.chmframwork.fragment.four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseFragment;
import com.chm.chmframwork.fragment.four.loginMVP.view.LoginFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by chenmin on 2017/6/22.
 */
public class AvatarFragment extends BaseFragment implements View.OnClickListener {

    public static AvatarFragment newInstance() {

        Bundle args = new Bundle();

        AvatarFragment fragment = new AvatarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avatar, container, false);
        view.findViewById(R.id.iv_avatar).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                //如果没有登陆
                ((SupportFragment) getParentFragment()).start(LoginFragment.newInstance());
                break;
        }
    }
}
