package com.chm.chmframwork.fragment_back.login;

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
