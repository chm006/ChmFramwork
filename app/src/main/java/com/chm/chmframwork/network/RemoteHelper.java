package com.chm.chmframwork.network;

import android.annotation.SuppressLint;
import android.content.Context;

import com.chm.framwork.utilcode.util.StringUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * Created by chenmin on 2017/6/22.
 */

public class RemoteHelper {
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    private static Retrofit retrofit;
    private static OkHttpClient client;
    private static String serverUrl = "";

    public static RemoteService create(Context context) {
        return getRetrofit(context, RemoteService.ServersUrl.GANK_IO).create(RemoteService.class);
    }

    private static Retrofit getRetrofit(Context context, String serverUrl) {
        if (isNeedCreateNew(context, serverUrl)) {
            synchronized (RemoteHelper.class) {
                if (isNeedCreateNew(context, serverUrl)) {
                    ctx = context;

                    if (client == null) {
                        client = new OkHttpClient.Builder()
                                .connectTimeout(3, TimeUnit.SECONDS)    // 连接超时
                                .writeTimeout(3, TimeUnit.SECONDS)
                                .readTimeout(3, TimeUnit.SECONDS)       // 读取超时
                                .build();
                    }

                    retrofit = new Retrofit.Builder()
                            .baseUrl(serverUrl)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * @param context 上下文
     * @return boolean
     */
    private static boolean isNeedCreateNew(Context context, String serverUrl) {
        return retrofit == null || ctx == null || !ctx.equals(context) || !StringUtils.equals(RemoteHelper.serverUrl, serverUrl);
    }
}
