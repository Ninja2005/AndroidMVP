package com.hqumath.androidmvp.net;

import com.hqumath.androidmvp.BuildConfig;
import com.hqumath.androidmvp.net.download.DownloadInterceptor;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.subscribers.ProgressDownSubscriber;
import com.hqumath.androidmvp.net.subscribers.ProgressSubscriber;
import com.hqumath.androidmvp.utils.FileUtil;
import com.hqumath.androidmvp.utils.LogUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

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

    //构造方法私有
    private RetrofitClient() {
    }

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

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     * @param baseUrl api地址
     */
    public void sendHttpRequest(BaseApi basePar, String baseUrl) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.i("RxRetrofit", "Retrofit====Message:" + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));//打印的等级
        }

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(retrofit)
                /*生命周期管理*/
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(basePar);

        /*链接式对象返回*/
        SoftReference<HttpOnNextListener> httpOnNextListener = basePar.getListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(observable);
        }

        /*数据回调*/
        observable.subscribe(subscriber);
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void sendHttpRequest(BaseApi basePar) {
        sendHttpRequest(basePar, AppNetConfig.baseUrl);
    }

    /**
     * 处理http下载请求
     *
     * @param basePar 封装的请求数据
     */
    public void sendHttpDownloadRequest(BaseApi basePar, File file) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);

        //下载拦截器
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(basePar);
        builder.addInterceptor(new DownloadInterceptor(subscriber));

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppNetConfig.baseUrl)
                .build();

        /*rx处理*/
        basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                /*.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                        basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))*/
                /*生命周期管理*/
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*结果判断*/
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        FileUtil.writeFile(responseBody, file);
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                /*回调线程*/
                .subscribe(subscriber);
    }
}
