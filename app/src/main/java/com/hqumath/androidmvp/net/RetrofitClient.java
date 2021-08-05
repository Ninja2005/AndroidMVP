package com.hqumath.androidmvp.net;

import com.hqumath.androidmvp.app.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ****************************************************************
 * 文件名称: RetrofitClient
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:47
 * 文件描述: RetrofitClient封装单例类, 实现网络请求
 * 注意事项: https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 * 每次发送请求，new Retrofit,以便动态更改baseUrl
 * 版权声明:
 * ****************************************************************
 */
public class RetrofitClient {
    private volatile static RetrofitClient INSTANCE;
    private ApiService apiService;

    //获取单例
    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    //构造方法私有
    private RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(6, TimeUnit.SECONDS);
        builder.addInterceptor(new MyInterceptor());//自定义拦截器（token过期后刷新token，打印日志）
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
