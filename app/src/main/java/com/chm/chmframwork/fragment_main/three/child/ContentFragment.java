package com.chm.chmframwork.fragment_main.three.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseFragment;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * ContentFragment
 * Created by chenmin on 2017/6/22.
 */

public class ContentFragment extends BaseFragment {
    private static final String ARG_MENU = "arg_menu";

    private String mMenu;

    public static ContentFragment newInstance(String menu) {
        Bundle args = new Bundle();
        args.putString(ARG_MENU, menu);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mMenu = args.getString(ARG_MENU);
        }
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView mTvContent = (TextView) view.findViewById(R.id.tv_content);
        String s = "Fragment内容:\n" + mMenu;
        mTvContent.setText(s);
    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,可以在此处理返回按键事件
        return super.onBackPressedSupport();
    }
}
