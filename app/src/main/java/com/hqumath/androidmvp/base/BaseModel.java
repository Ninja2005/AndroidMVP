package com.hqumath.androidmvp.base;

import androidx.annotation.NonNull;

import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.HttpListener;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ****************************************************************
 * 文件名称: BaseModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 防止MVP内存泄漏
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BaseModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();//rxjava订阅者

    //网络请求
    protected void sendRequest(Observable observable, HttpListener listener) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        listener.onSuccess(o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        HandlerException.ResponseThrowable throwable = HandlerException.handleException(e);
                        listener.onError(throwable.getMessage(), throwable.getCode());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //解除所有订阅者
    protected void dispose() {
        compositeDisposable.clear();
    }
}
