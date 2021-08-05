package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.net.HttpOnNextListener;

/**
 * ****************************************************************
 * 文件名称: LoginContract
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:08
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface LoginContract {
    interface View {
        void showProgress();

        void hideProgress();

        void onLoginSuccess(Object object);

        void onLoginError(String errorMsg, String code);
    }

    interface Presenter {
        void login(String userName, String passWord);
    }

    interface Model {
        void login(String userName, String passWord, HttpOnNextListener<Object> listener);
    }
}
