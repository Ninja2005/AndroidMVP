package com.hqumath.androidmvp.module.main.model;

import com.hqumath.androidmvp.module.main.contract.MainContract;
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
 * 文件名称: MainModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 11:22
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MainModel implements MainContract.Model {
    private RxAppCompatActivity activity;

    public MainModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getProductList(final Map<String, Object> maps, HttpOnNextListener listener) {

        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(LoginService.class).userLogin(maps);
            }
        };
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }
}