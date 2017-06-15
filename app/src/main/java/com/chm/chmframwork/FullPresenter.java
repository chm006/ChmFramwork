package com.chm.chmframwork;

import com.chm.chmframwork.bean.FragmentBean;

/**
 * Presenter
 * Created by chenmin on 2017/11/8.
 */

class FullPresenter implements FullPresenters {
    private FullView view;

    FullPresenter(FullView view) {
        this.view = view;
    }

    @Override
    public void loadRootFragment() {
        if (App.fragmentBean == null) {
            return;
        }
        FragmentBean bean = App.fragmentBean;
        view.loadRootFragment(bean);
        App.fragmentBean = null;
    }
}
