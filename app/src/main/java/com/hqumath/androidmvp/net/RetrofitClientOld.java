package com.hqumath.androidmvp.net;

import android.text.TextUtils;
import com.hqumath.androidmvp.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * RetrofitClient封装单例类, 实现网络请求
 */
public class RetrofitClientOld {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    //private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;
    //服务端根路径
    public static String baseUrl = "https://www.oschina.net/";

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetrofitClientOld INSTANCE = new RetrofitClientOld();
    }

    public static RetrofitClientOld getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClientOld() {
        this(baseUrl);
    }

    private RetrofitClientOld(String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtil.i("RxRetrofit", "Retrofit====Message:" + message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))//打印的等级
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClientOld.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClientOld.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }
}
