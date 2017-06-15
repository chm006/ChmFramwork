package com.chm.chmframwork.base;

import android.content.Intent;

import com.chm.chmframwork.App;
import com.chm.chmframwork.FullActivity;
import com.chm.chmframwork.MainActivity;
import com.chm.chmframwork.R;
import com.chm.chmframwork.WelcomeActivity;
import com.chm.chmframwork.bean.FragmentBean;
import com.chm.framwork.utilcode.util.ActivityUtils;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment {

    public void startActivity2Fragment(BaseFragment toFragment) {
        FragmentBean bean = new FragmentBean();
        bean.setFragment(toFragment);
        App.fragmentBean = bean;
        ActivityUtils.startActivity(_mActivity, FullActivity.class, R.anim.activity_open, R.anim.activity_close);
    }

    public void startForResultActivity2Fragment(BaseFragment toFragment, int requestCode) {
        FragmentBean bean = new FragmentBean();
        bean.setFragment(toFragment);
        bean.setRequestCode(requestCode);
        App.fragmentBean = bean;
        ActivityUtils.startActivity(_mActivity, FullActivity.class, R.anim.activity_open, R.anim.activity_close);
    }


    public void startFragment(SupportFragment toFragment) {
        start(toFragment);
    }

    public void startForResultFragment(SupportFragment toFragment, int requestCode) {
        startForResult(toFragment, requestCode);
    }
}
