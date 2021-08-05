package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HttpOnNextListener;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 数据的获取、存储、状态变化
 */
public class LoginModel implements LoginContract.Model {

    private RxAppCompatActivity activity;

    public LoginModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void login(String userName, String passWord, HttpOnNextListener<Object> listener) {
        new BaseApi(listener, activity, 0, false,
                RetrofitClient.getInstance().getApiService().getUserInfo(userName)).sendRequest();
    }
}
