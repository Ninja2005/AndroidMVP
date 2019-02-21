package com.hqumath.androidmvp.module.main.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.main.contract.MainContract;
import com.hqumath.androidmvp.module.main.model.MainModel;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: MainPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/12 15:24
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainModel model;

    public MainPresenter(RxAppCompatActivity activity) {
        model = new MainModel(activity);
    }

    @Override
    public void getProductList(Map<String, Object> maps, int tag, boolean isShowProgress) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.getProductList(maps, new HttpOnNextListener() {

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.onError(e.getMessage(), e.getCode(), tag);
            }
        }, isShowProgress);
    }
}
