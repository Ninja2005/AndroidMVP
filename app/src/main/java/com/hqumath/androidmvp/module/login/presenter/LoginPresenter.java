package com.hqumath.androidmvp.module.login.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.login.model.LoginModel;

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

    public LoginPresenter(){
        model = new LoginModel();
    }

    @Override
    public void login(String userCode, String passWord) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.login(userCode, userCode);
    }
}
