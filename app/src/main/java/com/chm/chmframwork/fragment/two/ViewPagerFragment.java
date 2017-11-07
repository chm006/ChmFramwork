package com.chm.chmframwork.fragment.two;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chm.chmframwork.R;
import com.chm.chmframwork.adapter.PagerFragmentAdapter;
import com.chm.chmframwork.base.BaseFragment;
import com.chm.chmframwork.fragment.two.childpager.BroadCastPagerFragment;
import com.chm.chmframwork.fragment.two.childpager.ProviderPagerFragment;
import com.chm.chmframwork.fragment.two.childpager.ServicePagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶端分类切换
 * Created by chenmin on 2017/6/22.
 */
public class ViewPagerFragment extends BaseFragment {
    private String[] pageTitles;
    private List<Fragment> fragments;

    public static ViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        pageTitles = new String[]{"Service", "ContentProvider", "BroadCastReceiver"};
        fragments = new ArrayList<>();

        fragments.add(ServicePagerFragment.newInstance(pageTitles[0]));
        fragments.add(ProviderPagerFragment.newInstance());
        fragments.add(BroadCastPagerFragment.newInstance(pageTitles[2]));
    }

    private void initView(View view) {
        TabLayout tab = (TabLayout) view.findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        /*for (int i = 0; i < fragments.size(); i++) {
            tab.addTab(tab.newTab());
        }*/

        PagerFragmentAdapter adapter = new PagerFragmentAdapter(
                getChildFragmentManager(), pageTitles, fragments);
        //viewpager加载adapter
        viewPager.setAdapter(adapter);
        //TabLayout加载viewpager
        tab.setupWithViewPager(viewPager);
    }
}
