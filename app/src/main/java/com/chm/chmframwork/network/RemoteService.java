package com.chm.chmframwork.network;

import com.chm.chmframwork.network.bean.GirlsBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 网络请求url
 * Created by chenmin on 2016/9/27.
 */

public interface RemoteService {

    //服务器地址
    interface ServersUrl {
        String GANK_IO = "http://gank.io/";
    }

    //首页妹子图片
    @GET("api/data/{type}/{count}/{page}")
    Observable<GirlsBean> getGirls(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

    //每日数据： http://gank.io/api/day/年/月/日
    @GET("api/day/{day}")
    Observable<Map<String, Object>> getDayData(
            @Path("day") String day
    );

    //获取Android类型的count条数据
    @GET("api/random/data/Android/{count}")
    Observable<Map<String, Object>> randomDatas(
            @Path("count") int count
    );
}
