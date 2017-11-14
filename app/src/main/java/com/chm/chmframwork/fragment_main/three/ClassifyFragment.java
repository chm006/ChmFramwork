package com.chm.chmframwork.fragment_main.three;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.fragment_main.three.child.ContentFragment;
import com.chm.chmframwork.fragment_main.three.child.MenuListFragment;
import com.chm.framwork.fragmentation.SupportFragment;

import java.util.ArrayList;

/**
 * 左侧分类切换
 * Created by chenmin on 2017/6/22.
 */
public class ClassifyFragment extends BaseMainFragment {
    public static final String TAG = ClassifyFragment.class.getSimpleName();

    public static ClassifyFragment newInstance() {
        Bundle args = new Bundle();
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mToolbar.setTitle("左侧分类切换");

        if (savedInstanceState == null) {
            ArrayList<String> listMenus = new ArrayList<>();
            listMenus.add("1");
            listMenus.add("2");
            listMenus.add("3");
            listMenus.add("4");
            listMenus.add("5");
            listMenus.add("6");
            listMenus.add("7");
            listMenus.add("8");
            listMenus.add("9");
            listMenus.add("10");
            listMenus.add("11");
            listMenus.add("12");
            listMenus.add("13");
            listMenus.add("14");
            listMenus.add("15");
            listMenus.add("16");

            loadRootFragment(R.id.list_container, MenuListFragment.newInstance(listMenus));
            loadRootFragment(R.id.content_container, ContentFragment.newInstance("销量排行"), false, false);
        }
    }

    /**
     * 替换加载 内容Fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }
}
