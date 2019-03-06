package com.hqumath.androidmvp.module.list.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.list.contract.ListContract;
import com.hqumath.androidmvp.module.list.model.ListModel;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: ListPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/12 15:24
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ListPresenter extends BasePresenter<ListContract.View> implements ListContract.Presenter {

    private ListModel model;

    public ListPresenter(RxAppCompatActivity activity) {
        model = new ListModel(activity);
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
