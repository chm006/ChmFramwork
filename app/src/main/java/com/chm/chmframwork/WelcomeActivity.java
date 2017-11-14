package com.chm.chmframwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chm.chmframwork.bean.GirlsBean;
import com.chm.framwork.utilcode.util.ActivityUtils;
import com.chm.framwork.utilcode.util.ToastUtils;

/**
 * 欢迎页
 * Created by chenmin on 2017/6/5.
 */

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    private WelcomePresenters presenter = new WelcomePresenter(this);

    private ImageView iv_welcome;
    private Button btn_welcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
        btn_welcome = (Button) findViewById(R.id.btn_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initListener();
        presenter.loadData(this);
    }

    @Override
    public void btnVisibility(int visibility) {
        btn_welcome.setVisibility(visibility);
    }

    @Override
    public void goMain() {
        btn_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(WelcomeActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    @Override
    public void setImageView(GirlsBean girlsBean) {
        Glide.with(this)
                .load(girlsBean.getResults().get(0).getUrl())
                .into(iv_welcome);
    }

    @Override
    public void showToast(String s) {
        ToastUtils.showLong(s);
    }
}
