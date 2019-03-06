package com.hqumath.androidmvp.module.list.model;

import com.hqumath.androidmvp.module.list.contract.ListContract;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.service.MainService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: ListModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 11:22
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ListModel implements ListContract.Model {
    private RxAppCompatActivity activity;

    public ListModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getProductList(final Map<String, Object> maps, HttpOnNextListener listener, boolean isShowProgress) {

        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MainService.class).getProductList(maps);
            }
        };
        baseApi.setShowProgress(isShowProgress);
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }
}