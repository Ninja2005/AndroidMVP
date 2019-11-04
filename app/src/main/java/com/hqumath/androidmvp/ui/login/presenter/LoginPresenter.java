package com.hqumath.androidmvp.ui.login.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.ui.login.contract.LoginContract;
import com.hqumath.androidmvp.ui.login.model.LoginModel;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: LoginPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginModel model;

    public LoginPresenter(RxAppCompatActivity activity) {
        model = new LoginModel(activity);
    }

    @Override
    public void login(Map<String, Object> maps, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.login(maps, new HttpOnNextListener() {

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.onError(e.getMessage(), e.getCode(), tag);
            }
        });
    }
}
