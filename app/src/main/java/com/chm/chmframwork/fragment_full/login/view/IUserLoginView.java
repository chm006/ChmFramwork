package com.chm.chmframwork.fragment_full.login.view;

import com.chm.chmframwork.fragment_full.login.bean.User;

/**
 * Created by chm00 on 2017/7/22.
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();

}
