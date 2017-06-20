package com.chm.chmframwork.home.ui.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseBackFragment;

/**
 * WebView
 * Created by chenmin on 2016/11/1.
 */
public class WebViewFragment extends BaseBackFragment {
    private static final String ARG_TITLE_TITLE = "WebView_title";
    private static final String ARG_TITLE_URL = "WebView_url";

    private View rootView;
    private Toolbar toolbar;
    private ProgressBar progress_bar;
    private WebView webView;
    private String url = "http://www.baidu.com";
    private String mTitle;

    public static WebViewFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_TITLE, title);
        args.putString(ARG_TITLE_URL, url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(ARG_TITLE_TITLE);
            url = bundle.getString(ARG_TITLE_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        initToolbar();
        initWebView();
        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        progress_bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        webView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);//设置启用或禁止访问文件数据
        settings.setBuiltInZoomControls(true);//设置是否支持缩放（出现缩放工具）
        settings.setUseWideViewPort(true);//扩大比例的缩放
        settings.setJavaScriptEnabled(true);//设置是否支持JavaScript
        settings.setSupportZoom(true);//设置是否支持变焦（设置可以支持缩放 ）
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        //重写父类方法，让新打开的网页在当前的WebView中显示
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress_bar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress_bar.setVisibility(View.VISIBLE);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        //获得网页的加载进度，显示在右上角的TextView控件中
        public void onProgressChanged(WebView view, int newProgress) {
            progress_bar.setProgress(newProgress);
        }

        //获得网页的标题，作为应用程序的标题进行显示
        public void onReceivedTitle(WebView view, String title) {
            toolbar.setTitle(title);
        }
    }

    //回退键监听
    @Override
    public boolean onBackPressedSupport() {
        if (webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(mTitle);
    }
}
