package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.net.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * ****************************************************************
 * 文件名称: LoginPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: https://www.jianshu.com/p/8fb4c0ae006e
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginModel mModel;

    public LoginPresenter(RxAppCompatActivity activity) {
        mModel = new LoginModel(activity);
    }

    @Override
    public void login(String userName, String passWord) {
        if (mView != null)
            mView.showProgress();
        mModel.login(userName, passWord, new HttpOnNextListener<Object>() {
            @Override
            public void onSuccess(Object object, int tag) {
                if (mView != null) {
                    mView.onLoginSuccess(object);
                    mView.hideProgress();
                }
            }

            @Override
            public void onError(String errorMsg, String code, int tag) {
                if (mView != null) {
                    mView.onLoginError(errorMsg, code);
                    mView.hideProgress();
                }
            }
        });
    }
}
