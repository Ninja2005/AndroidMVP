package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.repository.MyModel;

/**
 * ****************************************************************
 * 文件名称: LoginPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 业务逻辑层
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginPresenter extends BasePresenter<LoginContract> {

    public LoginPresenter() {
        mModel = new MyModel();
    }

    public void login(String userName, String passWord) {
        if (mView == null) return;
        mView.showProgress();
        //模拟登陆接口
        ((MyModel) mModel).getUserInfo(userName, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onLoginSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onLoginError(errorMsg, code);
            }
        });
    }
}
