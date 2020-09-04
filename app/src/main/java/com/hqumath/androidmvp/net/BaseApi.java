package com.hqumath.androidmvp.net;

import android.text.TextUtils;
import com.hqumath.androidmvp.bean.BaseResultEntity;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.ref.SoftReference;

/**
 * ****************************************************************
 * 文件名称: BaseApi
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:08
 * 文件描述: 请求数据统一封装类
 * 注意事项: https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseApi<T> implements Function<Response<T>, T> {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    //private String baseUrl = AppNetConfig.baseUrl;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method = "";
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*缓存url-可手动设置*/
    private String cacheUrl;

    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(false);
    }

    public abstract Observable getObservable(Retrofit retrofit);

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }


    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }


    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /*
     * 获取当前rx生命周期
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    @Override
    public T apply(Response<T> httpResult) {
        if (httpResult.isSuccessful() && httpResult.body() != null) {
            return httpResult.body();
        } else {
            throw new HandlerException.ResponseThrowable(httpResult.message(),
                    "error: " + httpResult.code() + " " + httpResult.message());
        }
    }

    /*@Override
    public T apply(BaseResultEntity<T> httpResult) {
        int resultCode = httpResult.getCode();
        String resultMsg = httpResult.getMsg();
        if (resultCode == 0) {//请求成功
            return httpResult.getData() == null ? (T) "{}" : httpResult.getData();
        } else {
            throw new HandlerException.ResponseThrowable(resultMsg, resultCode + "");
        }
    }*/

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }
}
