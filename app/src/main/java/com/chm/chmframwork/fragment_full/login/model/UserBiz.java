package com.chm.chmframwork.fragment_full.login.model;

import com.chm.chmframwork.fragment_full.login.bean.User;

/**
 * Created by chm00 on 2017/7/22.
 */

public class UserBiz implements IUserBiz {
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        //网络请求等等
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟登录成功
                if ("123".startsWith(username) && "123".startsWith(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    loginListener.loginSuccess(user);
                } else {
                    loginListener.loginFailed();
                }
            }
        }).start();
    }
}
