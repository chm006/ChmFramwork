package com.chm.chmframwork.base;

import com.chm.framwork.fragmentation.SupportFragment;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class BaseMainFragment extends SupportFragment {

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
