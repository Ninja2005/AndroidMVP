package com.hqumath.androidmvp.module.login.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.login.model.LoginModel;
import com.hqumath.androidmvp.net.HttpOnNextListener;
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

    LoginModel model;

    public LoginPresenter(RxAppCompatActivity activity){
        model = new LoginModel(activity);
    }

    @Override
    public void login(Map<String, Object> maps, final int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.login(maps, new HttpOnNextListener(){

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public  void onError(Throwable e){
                mView.onError(e.getMessage(), tag);
            }
        });
    }
}
