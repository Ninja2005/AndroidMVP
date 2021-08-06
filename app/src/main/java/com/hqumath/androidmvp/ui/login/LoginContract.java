package com.hqumath.androidmvp.ui.login;

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
    void showProgress();

    void hideProgress();

    void onLoginSuccess(Object object);

    void onLoginError(String errorMsg, String code);
}
