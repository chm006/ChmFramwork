package com.chm.chmframwork.fragment_full.login.presenter;

import android.os.Handler;

import com.chm.chmframwork.fragment_full.login.bean.User;
import com.chm.chmframwork.fragment_full.login.model.IUserBiz;
import com.chm.chmframwork.fragment_full.login.model.UserBiz;
import com.chm.chmframwork.fragment_full.login.view.IUserLoginView;

/**
 * Created by chm00 on 2017/7/22.
 */

public class UserLoginPresenter implements IUserLoginPresenter {
    private Handler handler = new Handler();
    private IUserLoginView iUserLoginView;
    private IUserBiz iUserBiz;

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        this.iUserBiz = new UserBiz();
    }


    @Override
    public void login() {
        iUserLoginView.showLoading();
        iUserBiz.login(iUserLoginView.getUserName(), iUserLoginView.getPassword(), new IUserBiz.OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.toMainActivity(user);
                        iUserLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.showFailedError();
                        iUserLoginView.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void clear() {
        iUserLoginView.clearUserName();
        iUserLoginView.clearPassword();
    }
}
