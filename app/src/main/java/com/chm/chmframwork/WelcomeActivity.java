package com.chm.chmframwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chm.chmframwork.home.MainActivity;

/**
 * 欢迎页
 * Created by chenmin on 2017/6/5.
 */

public class WelcomeActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextView mTvBtnFlow, mTvBtnWechat, mTvBtnZhihu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mTvBtnFlow = (TextView) findViewById(R.id.tv_btn_flow);
        mTvBtnWechat = (TextView) findViewById(R.id.tv_btn_wechat);
        mTvBtnZhihu = (TextView) findViewById(R.id.tv_btn_zhihu);
        ImageView iv_btn_main = (ImageView) findViewById(R.id.iv_btn_main);

        setSupportActionBar(mToolBar);

        mTvBtnFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, com.chm.chmframwork.demo_flow.MainActivity.class));
            }
        });

        mTvBtnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, com.chm.chmframwork.demo_wechat.MainActivity.class));
            }
        });

        mTvBtnZhihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, com.chm.chmframwork.demo_zhihu.MainActivity.class));
            }
        });

        iv_btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
    }
}
