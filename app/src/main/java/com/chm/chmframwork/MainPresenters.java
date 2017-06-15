package com.chm.chmframwork;

import android.os.Bundle;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Presenter
 * Created by chenmin on 2017/11/8.
 */

interface MainPresenters {

    void initFragments(Bundle savedInstanceState);

    void initBottomBar();

    SupportFragment[] getFragments();
}
