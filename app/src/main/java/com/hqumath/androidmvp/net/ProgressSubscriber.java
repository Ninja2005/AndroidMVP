package com.hqumath.androidmvp.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * 注意事项: https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 */
public class ProgressSubscriber<T> implements Observer<T> {
    /*是否弹框*/
    private boolean isShowProgress;
    /* 软引用回调接口*/
    protected SoftReference<HttpOnNextListener<Object>> mSubscriberOnNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求标识*/
    private int tag;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        this.tag = api.getTag();
        this.isShowProgress = api.isShowProgress();
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
            pd.setMessage("正在加载，请稍后....");
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowProgress) return;
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
        if (!isShowProgress) return;
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
        if (e instanceof TokenException) return;//特殊异常时，不再提示错误
        if (mSubscriberOnNextListener.get() != null) {
            HandlerException.ResponseThrowable throwable = HandlerException.handleException(e);
            mSubscriberOnNextListener.get().onError(throwable.getMessage(), throwable.getCode(), tag);
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
            mSubscriberOnNextListener.get().onSuccess(t, tag);
        }
    }
}