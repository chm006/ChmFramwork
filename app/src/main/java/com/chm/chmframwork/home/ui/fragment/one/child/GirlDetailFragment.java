package com.chm.chmframwork.home.ui.fragment.one.child;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chm.chmframwork.R;
import com.chm.chmframwork.home.adapter.GirlDetailAdapter;
import com.chm.chmframwork.home.api.RemoteHelper;
import com.chm.chmframwork.home.base.BaseBackFragment;
import com.chm.chmframwork.home.listener.OnItemClickListener;
import com.chm.chmframwork.home.ui.fragment.one.child.bean.GirlsBean;
import com.chm.chmframwork.home.ui.view.WebViewFragment;
import com.chm.framwork.utilcode.util.TimeUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 妹子放大图
 * Created by chenmin on 2017/6/22.
 */

public class GirlDetailFragment extends BaseBackFragment implements View.OnClickListener {
    private static final String ARG_ITEM_URL = "arg_item_url";
    private static final String ARG_ITEM_TIME = "arg_item_time";

    private String pic;
    private String day;

    private Toolbar mToolbar;
    private ImageView mImgDetail;
    private FloatingActionButton mFab;

    private List<Map<String, Object>> results = new ArrayList<>();
    private RecyclerView mRecy;
    private GirlDetailAdapter mAdapter;

    public static GirlDetailFragment newInstance(GirlsBean.ResultsEntity girlsBean) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM_URL, girlsBean.getUrl());
        args.putString(ARG_ITEM_TIME, girlsBean.getPublishedAt());
        GirlDetailFragment fragment = new GirlDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pic = getArguments().getString(ARG_ITEM_URL);
        // 2017-06-15T13:55:57.947Z
        String time = getArguments().getString(ARG_ITEM_TIME);
        assert time != null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = TimeUtils.string2Date(time.split("T")[0], sdf1);
        day = TimeUtils.date2String(date, sdf2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mImgDetail = (ImageView) view.findViewById(R.id.img_detail);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        mToolbar.setTitle("");
        initToolbarNav(mToolbar);

        Glide.with(_mActivity)
                .load(pic)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(0xFFC7EDCC), resource});
                        td.setCrossFadeEnabled(true);
                        mImgDetail.setImageDrawable(td);
                        td.startTransition(500);
                    }
                });

        mFab.setOnClickListener(this);

        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mAdapter = new GirlDetailAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        //添加装饰类
        mRecy.addItemDecoration(new DividerItemDecoration(_mActivity, 1));
        mRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                Map<String, Object> item = results.get(position);
                String desc = (String) item.get("desc");
                String url = (String) item.get("url");
                start(WebViewFragment.newInstance(desc, url));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        RemoteHelper.create(_mActivity).getDayData(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, Object>>() {
                    @Override
                    public void accept(@NonNull Map<String, Object> data) throws Exception {
                        results.clear();
                        List<String> category = (List<String>) data.get("category");
                        LinkedTreeMap<String, List<Map<String, Object>>> results_data = (LinkedTreeMap<String, List<Map<String, Object>>>) data.get("results");
                        for (int i = 0; i < category.size(); i++) {
                            String s = category.get(i);
                            List<Map<String, Object>> list = results_data.get(s);
                            results.addAll(list);
                        }
                        mAdapter.setDatas(results);
                    }
                });
    }

    @Override
    public void onClick(View v) {
    }
}
