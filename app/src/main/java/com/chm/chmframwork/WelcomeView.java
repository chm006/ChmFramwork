package com.chm.chmframwork;

import com.chm.chmframwork.bean.GirlsBean;

/**
 * View
 * Created by chenmin on 2017/11/7.
 */

interface WelcomeView {

    void btnVisibility(int visibility);

    void goMain();

    void setImageView(GirlsBean girlsBean);

    void showToast(String s);
}
