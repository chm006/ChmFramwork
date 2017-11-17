package com.chm.chmframwork;

import android.content.Context;
import android.view.View;

import com.chm.chmframwork.network.RemoteHelper;
import com.chm.chmframwork.bean.GirlsBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter
 * Created by chenmin on 2017/11/7.
 */

class WelcomePresenter implements WelcomePresenters {
    private WelcomeView view;

    WelcomePresenter(WelcomeView view) {
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showToast("加载闪屏页失败，可直接进入首页");
                        view.btnVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        view.btnVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void initListener() {
        view.btnVisibility(View.INVISIBLE);
        view.goMain();
    }
}
