package com.chm.chmframwork.fragment_full.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseBackFragment;
import com.chm.chmframwork.fragment_full.login.bean.User;
import com.chm.chmframwork.fragment_full.login.presenter.UserLoginPresenter;
import com.chm.framwork.utilcode.util.ToastUtils;

/**
 * Created by chm00 on 2017/7/22.
 */

public class LoginFragment extends BaseBackFragment implements IUserLoginView {
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnClear;
    private ProgressBar mPbLoading;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initToolbar(view);
        initViews(view);
        return view;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("登陆");
        initToolbarNav(toolbar);
    }

    private void initViews(View view) {
        mEtUsername = (EditText) view.findViewById(R.id.id_et_username);
        mEtPassword = (EditText) view.findViewById(R.id.id_et_password);

        mBtnClear = (Button) view.findViewById(R.id.id_btn_clear);
        mBtnLogin = (Button) view.findViewById(R.id.id_btn_login);

        mPbLoading = (ProgressBar) view.findViewById(R.id.id_pb_loading);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.login();
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        mEtUsername.setText("");
    }

    @Override
    public void clearPassword() {
        mEtPassword.setText("");
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        ToastUtils.showShort(user.getUsername() + " login success , to MainActivity");
    }

    @Override
    public void showFailedError() {
        ToastUtils.showShort("login failed");
    }
}
