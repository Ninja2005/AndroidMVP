package com.hqumath.androidmvp.net;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
public class BaseApi/*<T> implements Function<BaseResultEntity, T>*/ {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private SoftReference<HttpOnNextListener<Object>> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    private Observable observable;
    private int tag;

    public BaseApi(HttpOnNextListener<Object> listener, RxAppCompatActivity rxAppCompatActivity, int tag, boolean showProgress, Observable observable) {
        this.listener = new SoftReference<>(listener);
        this.rxAppCompatActivity = new SoftReference<>(rxAppCompatActivity);
        this.observable = observable;
        this.tag = tag;
        this.showProgress = showProgress;
    }

    public void sendRequest() {
        observable.compose(getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))/*生命周期管理，避免内存泄漏*/
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber(this));
    }

    public Observable getObservable() {
        return observable;
    }

    public int getTag() {
        return tag;
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
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

    public SoftReference<HttpOnNextListener<Object>> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener<Object> listener) {
        this.listener = new SoftReference<>(listener);
    }

    /*
     * 获取当前rx生命周期
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    /*@Override
    public T apply(Response<T> httpResult) {
        if (httpResult.isSuccessful() && httpResult.body() != null) {
            return httpResult.body();
        } else {
            throw new HandlerException.ResponseThrowable(httpResult.message(),
                    "error: " + httpResult.code() + " " + httpResult.message());
        }
    }*/

    /*@Override
    public T apply(BaseResultEntity httpResult) {
        String resultCode = httpResult.getCode();
        String resultMsg = httpResult.getReason();
        if (!TextUtils.isEmpty(resultCode) && resultCode.equals("1")) {//请求成功
            return (T)httpResult;
        } else {
            throw new HandlerException.ResponseThrowable(resultMsg, resultCode);
        }
    }*/

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
}
