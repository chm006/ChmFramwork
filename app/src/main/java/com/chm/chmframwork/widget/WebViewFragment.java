package com.chm.chmframwork.widget;

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
import com.chm.chmframwork.base.BaseBackFragment;

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

    private boolean isBackPressedSupport = true;

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
        settings.setJavaScriptEnabled(true);//设置是否支持JavaScript
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true);//设置是否支持缩放（出现缩放工具）
        settings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true);//是否允许访问文件，默认允许访问
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //结合使用（离线加载）
        settings.setDomStorageEnabled(true);// 开启 DOM storage API 功能
        settings.setDatabaseEnabled(true);//开启 database storage API 功能
        settings.setAppCacheEnabled(true);//开启 Application Caches 功能
        //settings.setSaveFormData(true);//设置WebView是否保存表单数据，默认true，保存数据
        //settings.setSupportMultipleWindows(true);//设置WebView是否支持多屏窗口，默认false，不支持。
        //settings.setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        //重写父类方法，让新打开的网页在当前的WebView中显示
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            isBackPressedSupport = true;
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return false;
            } else {
                return true;
            }
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
        if (webView.canGoBack() && isBackPressedSupport) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        rootView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackPressedSupport = false;
                _mActivity.onBackPressed();
            }
        });
        toolbar.setTitle(mTitle);
    }
}
