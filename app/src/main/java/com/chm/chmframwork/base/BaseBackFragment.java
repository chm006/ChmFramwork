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

    public void startNewFragment(SupportFragment toFragment) {
        ((SupportFragment) getParentFragment()).start(toFragment);
    }

    public void startForResultNewFragment(SupportFragment toFragment, int requestCode) {
        ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
    }

    public void startFragment(SupportFragment toFragment) {
        start(toFragment);
    }

    public void startForResultFragment(SupportFragment toFragment, int requestCode) {
        startForResult(toFragment, requestCode);
    }
}
