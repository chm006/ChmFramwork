package com.chm.chmframwork.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chm.chmframwork.R;
import com.chm.chmframwork.fragment.OneFragment;
import com.chm.framwork.utilcode.util.ToastUtils;

public class BaseBackFragment extends BaseFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
