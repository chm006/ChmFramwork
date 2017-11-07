package com.chm.chmframwork;

import android.content.Context;
import android.view.View;

import com.chm.chmframwork.home.api.RemoteHelper;
import com.chm.chmframwork.home.ui.fragment.one.child.bean.GirlsBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter
 * Created by chenmin on 2017/11/7.
 */

class WelcomePresenter implements WelcomePresenter_I {
    private WelcomeView_I view;

    WelcomePresenter(WelcomeView_I view) {
        this.view = view;
    }

    @Override
    public void loadData(Context context) {
        RemoteHelper.create(context).getGirls("福利", 1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull GirlsBean girlsBean) {
                        view.setImageView(girlsBean);
                        view.btnVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showToast("加载闪屏页失败，可直接进入首页");
                        view.btnVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void initListener() {
        view.btnVisibility(View.INVISIBLE);
        view.goMain();
    }
}
