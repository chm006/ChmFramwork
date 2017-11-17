package com.chm.chmframwork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chm.chmframwork.R;
import com.chm.framwork.fragmentation.SupportFragment;
import com.chm.framwork.fragmentation.SwipeBackFragment;

public class BaseBackFragment extends SwipeBackFragment {

    private static final String TAG = "BaseBackFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }

    public void startFragment(int index, SupportFragment toFragment) {
        if (index < 1) {
            start(toFragment);
        } else {
            SupportFragment supportFragment = (SupportFragment) getParentFragment();
            for (int i = 1; i < index; i++) {
                supportFragment = (SupportFragment) supportFragment.getParentFragment();
            }
            supportFragment.start(toFragment);
        }
    }

    public void startForResultFragment(int index, SupportFragment toFragment, int requestCode) {
        if (index < 1) {
            startForResult(toFragment, requestCode);
        } else {
            SupportFragment supportFragment = (SupportFragment) getParentFragment();
            for (int i = 1; i < index; i++) {
                supportFragment = (SupportFragment) supportFragment.getParentFragment();
            }
            supportFragment.startForResult(toFragment, requestCode);
        }
    }
}
