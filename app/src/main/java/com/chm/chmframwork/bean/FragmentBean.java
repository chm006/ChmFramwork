package com.chm.chmframwork.bean;

import com.chm.chmframwork.base.BaseFragment;

/**
 * FragmentBean
 * Created by chm00 on 2017/11/8.
 */

public class FragmentBean {

    private BaseFragment fragment;
    private int requestCode;

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
