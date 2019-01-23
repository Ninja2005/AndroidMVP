package com.hqumath.androidmvp.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * 注意事项: https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 */
public class ProgressSubscriber<T> implements Observer<T> {
    /*是否弹框*/
    private boolean showPorgress = true;
    /* 软引用回调接口*/
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求数据*/
    //private BaseApi api;


    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        //this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCancel();
                        }
                        //onCancelProgress();
                    }
                });
            }
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
        /*缓存并且有网*/
        /*if (api.isCache() && AppUtil.isNetworkAvailable(App.getContext())) {
         *//*获取缓存数据*//*
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                    }
                    onCompleted();
                    dispose();
                }
            }
        }*/
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        /*if (api.isCache()) {
            Observable.just(api.getUrl()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onNext(String s) {
                    *//*获取缓存数据*//*
                    CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        throw new HttpTimeException("网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < api.getCookieNoNetWorkTime()) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                        }
                    } else {
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        throw new HttpTimeException("网络错误");
                    }
                }
            });
        } else*/
        {
            errorDo(e);
        }
    }

    /*错误统一处理*/
    private void errorDo(Throwable e) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(HandlerException.handleException(e));
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    /*public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }*/
    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}