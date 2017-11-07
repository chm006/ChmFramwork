package com.chm.chmframwork;

import com.chm.chmframwork.home.ui.fragment.one.child.bean.GirlsBean;

/**
 * view
 * Created by chenmin on 2017/11/7.
 */

interface WelcomeView_I {

    void btnVisibility(int visibility);

    void goMain();

    void setImageView(GirlsBean girlsBean);

    void showToast(String s);
}
