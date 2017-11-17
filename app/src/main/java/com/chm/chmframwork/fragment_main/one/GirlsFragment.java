package com.chm.chmframwork.fragment_main.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chm.chmframwork.R;
import com.chm.chmframwork.adapter.GirlsAdapter;
import com.chm.chmframwork.base.BaseMainFragment;
import com.chm.chmframwork.bean.GirlsBean;
import com.chm.chmframwork.fragment_back.GirlDetailFragment;
import com.chm.chmframwork.listener.OnItemClickListener;
import com.chm.chmframwork.network.RemoteHelper;
import com.chm.chmframwork.widget.swipe.SwipeRefreshLoadLayout;
import com.chm.framwork.fragmentation.ISupportActivity;
import com.chm.framwork.fragmentation.anim.DefaultHorizontalAnimator;
import com.chm.framwork.fragmentation.anim.DefaultNoAnimator;
import com.chm.framwork.fragmentation.anim.DefaultVerticalAnimator;

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
public class GirlsFragment extends BaseMainFragment implements SwipeRefreshLoadLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private List<GirlsBean.ResultsEntity> pics = new ArrayList<>();
    private int page = 1;
    private int size = 10;
    private SwipeRefreshLoadLayout mRefreshLayout;
    private LinearLayoutManager manager;
    private RecyclerView mRecy;
    private GirlsAdapter mAdapter;
    private FloatingActionButton mFab;
    private Toolbar toolbar;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private boolean loading = false;

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
        initView(view);
        refresh();
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_anim:
                final PopupMenu popupMenu = new PopupMenu(_mActivity, toolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.girls_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_anim_veritical:
                                ((ISupportActivity) _mActivity).setFragmentAnimator(new DefaultVerticalAnimator());
                                Toast.makeText(_mActivity, R.string.anim_v, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_anim_horizontal:
                                ((ISupportActivity) _mActivity).setFragmentAnimator(new DefaultHorizontalAnimator());
                                Toast.makeText(_mActivity, R.string.anim_h, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_anim_none:
                                ((ISupportActivity) _mActivity).setFragmentAnimator(new DefaultNoAnimator());
                                Toast.makeText(_mActivity, R.string.anim_none, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
                break;
        }
        return true;
    }

    private void initView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLoadLayout) view.findViewById(R.id.refresh_layout);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        toolbar.setTitle("每日更新");
        toolbar.inflateMenu(R.menu.girls);
        toolbar.setOnMenuItemClickListener(this);

        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new GirlsAdapter(_mActivity);
        manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                GirlDetailFragment fragment = GirlDetailFragment.newInstance(mAdapter.getItem(position));
                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Fade());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailTransition());
                    fragment.setSharedElementEnterTransition(new DetailTransition());
                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    fragment.setSharedElementEnterTransition(extraTransaction().addSharedElement(((GirlsAdapter.VH) vh).img, getString(R.string.image_transition)));
                    startNewFragment(fragment);
                } else {
                    startNewFragment(fragment);
                }*/
                startFragment(1, fragment);
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

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //向下滚动
                if (dy > 0) {
                    int visibleItemCount = manager.getChildCount();
                    int totalItemCount = manager.getItemCount();
                    int pastVisiblesItems = manager.findFirstVisibleItemPosition();
                    //滑动到最后两张的时候继续加载更多
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= (totalItemCount - 2)) {
                        loading = true;
                        loadMore();
                    }
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
        //refresh();
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
                    }

                    @Override
                    public void onNext(@NonNull GirlsBean girlsBean) {
                        if (null != girlsBean) {
                            if (!girlsBean.getResults().isEmpty()) {
                                for (GirlsBean.ResultsEntity resultsEntity : girlsBean.getResults()) {
                                    pics.add(resultsEntity);
                                }
                                mAdapter.setDatas(pics);
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRefreshLayout.finishLoadmore();
                        loading = false;
                    }

                    @Override
                    public void onComplete() {
                        mRefreshLayout.finishLoadmore();
                        loading = false;
                    }
                });
    }

    private void scrollToTop() {
        if (mInAtTop) {
            onRefresh();
        } else {
            mRecy.smoothScrollToPosition(0);
        }
    }

    //选择tab事件
    /*public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != 0) {
            return;
        }
        scrollToTop();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
    }
}
