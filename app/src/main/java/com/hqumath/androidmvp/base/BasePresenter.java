package com.hqumath.androidmvp.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ****************************************************************
 * 文件名称: BasePresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 防止MVP内存泄漏
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BasePresenter<V> {
    protected V mView;
    protected BaseModel mModel;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    public void detachView() {
        this.mView = null;
        if(mModel != null) {
            mModel.dispose();
            mModel = null;
        }
    }
}
