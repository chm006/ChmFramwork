package com.chm.chmframwork.base;

import com.chm.framwork.fragmentation.SupportFragment;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class BaseMainFragment extends SupportFragment {

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
