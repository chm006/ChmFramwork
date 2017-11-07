package com.chm.chmframwork.base;

import android.content.Intent;

import com.chm.chmframwork.App;
import com.chm.chmframwork.R;
import com.chm.chmframwork.ui.FragmentActivity;
import com.chm.chmframwork.ui.FragmentBean;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment {

    public void startActivity2Fragment(BaseFragment toFragment) {
        FragmentBean bean = new FragmentBean();
        bean.setFragment(toFragment);
        App.fragmentBean = bean;
        startActivity(new Intent(_mActivity, FragmentActivity.class));
        //设置启动动画
        _mActivity.overridePendingTransition(R.anim.activity_open, 0);
    }

    public void startForResultActivity2Fragment(BaseFragment toFragment, int requestCode) {
        FragmentBean bean = new FragmentBean();
        bean.setFragment(toFragment);
        bean.setRequestCode(requestCode);
        App.fragmentBean = bean;
        startActivity(new Intent(_mActivity, FragmentActivity.class));
        //设置启动动画
        _mActivity.overridePendingTransition(R.anim.activity_open, 0);
    }


    public void startFragment(SupportFragment toFragment) {
        if (getParentFragment() instanceof BaseFragment) {
            ((BaseFragment) getParentFragment()).start(toFragment);
        }
    }

    public void startForResultFragment(SupportFragment toFragment, int requestCode) {
        if (getParentFragment() instanceof BaseFragment) {
            ((BaseFragment) getParentFragment()).startForResult(toFragment, requestCode);
        }
    }
}
