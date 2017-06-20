package com.chm.chmframwork.home.ui.fragment.three.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseFragment;
import com.chm.chmframwork.home.ui.fragment.three.child.child.ContentFragment;
import com.chm.chmframwork.home.ui.fragment.three.child.child.MenuListFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 左侧分类切换
 * Created by chenmin on 2017/6/22.
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = ClassifyFragment.class.getSimpleName();

    private Toolbar mToolbar;

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
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

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

            MenuListFragment menuListFragment = MenuListFragment.newInstance(listMenus);
            loadRootFragment(R.id.list_container, menuListFragment);
            replaceLoadRootFragment(R.id.content_container, ContentFragment.newInstance("销量排行"), false);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        Toast.makeText(_mActivity, "onBackPressedSupport-->返回false,交给上层处理!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }
}
