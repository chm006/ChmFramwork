package com.chm.chmframwork.ui.loginMVP.model;

import com.chm.chmframwork.ui.loginMVP.bean.User;

/**
 * Created by chm00 on 2017/7/22.
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);

    public interface OnLoginListener {
        void loginSuccess(User user);

        void loginFailed();
    }
}
