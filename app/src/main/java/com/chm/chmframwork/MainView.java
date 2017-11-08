package com.chm.chmframwork;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * View
 * Created by chenmin on 2017/11/8.
 */

interface MainView {

    void setBottomBar();

    void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments);

    <T extends SupportFragment> T findFragment(Class<T> c);
}
