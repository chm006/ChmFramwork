package com.chm.chmframwork.home.ui.fragment.one.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.MainActivity;
import com.chm.chmframwork.home.adapter.GirlsAdapter;
import com.chm.chmframwork.home.api.RemoteHelper;
import com.chm.chmframwork.home.base.BaseFragment;
import com.chm.chmframwork.home.event.TabSelectedEvent;
import com.chm.chmframwork.home.helper.DetailTransition;
import com.chm.chmframwork.home.listener.OnItemClickListener;
import com.chm.chmframwork.home.ui.fragment.one.child.bean.GirlsBean;
import com.chm.chmframwork.widget.swipe.SwipeRefreshLoadLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * http://gank.io/
 * 妹子图
 * Created by chenmin on 2015/12/9.
 */
public class GirlsFragment extends BaseFragment implements SwipeRefreshLoadLayout.OnRefreshListener {
    private List<GirlsBean.ResultsEntity> pics = new ArrayList<>();
    private int page = 1;
    private int size = 10;
    private SwipeRefreshLoadLayout mRefreshLayout;
    private RecyclerView mRecy;
    private GirlsAdapter mAdapter;
    private FloatingActionButton mFab;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    public static GirlsFragment newInstance() {

        Bundle args = new Bundle();

        GirlsFragment fragment = new GirlsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girls, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLoadLayout) view.findViewById(R.id.refresh_layout);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        toolbar.setTitle("每日更新");

        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new GirlsAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                GirlDetailFragment fragment = GirlDetailFragment.newInstance(pics.get(position));

                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Fade());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailTransition());
                    fragment.setSharedElementEnterTransition(new DetailTransition());

                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    fragment.transaction()
                            .addSharedElement(((GirlsAdapter.VH) vh).img, "image_transition")
                            .commit();
                }
                start(fragment);
            }
        });

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
                if (dy > 5) {
                    mFab.hide();
                } else if (dy < -5) {
                    mFab.show();
                }
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity, "Action", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 2000);
    }

    @Override
    public void onLoading() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMore();
            }
        }, 2000);
    }

    @Override
    public void onRefreshPullStateChange(float percent, int state) {

    }

    @Override
    public void onLoadmorePullStateChange(float percent, int state) {

    }

    private void refresh() {
        page = 1;
        RemoteHelper.create(_mActivity).getGirls("福利", size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onNext(@NonNull GirlsBean girlsBean) {
                        pics.clear();
                        for (GirlsBean.ResultsEntity resultsEntity : girlsBean.getResults()) {
                            pics.add(resultsEntity);
                        }
                        mAdapter.setDatas(pics);
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void loadMore() {
        RemoteHelper.create(_mActivity).getGirls("福利", size, ++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onNext(@NonNull GirlsBean girlsBean) {
                        for (GirlsBean.ResultsEntity resultsEntity : girlsBean.getResults()) {
                            pics.add(resultsEntity);
                        }
                        mAdapter.setDatas(pics);
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.ONE) return;

        if (mInAtTop) {
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }
}
