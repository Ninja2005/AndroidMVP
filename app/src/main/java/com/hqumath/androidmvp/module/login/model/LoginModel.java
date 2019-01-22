package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HttpOnNextListener;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.service.LoginService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: LoginModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginModel implements LoginContract.Model {
    private RxAppCompatActivity activity;

    public LoginModel(RxAppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    public void login(final Map<String, Object> maps, HttpOnNextListener listener) {

        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(LoginService.class).userLogin(maps);
            }
        };
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void getCache() {

    }

    @Override
    public void getAppConfig() {

    }
}
