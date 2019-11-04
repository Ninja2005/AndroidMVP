package com.hqumath.androidmvp.ui.list;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.service.MainService;
import com.hqumath.androidmvp.ui.list.ListContract;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: ListPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/12 15:24
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ListPresenter extends BasePresenter<ListContract.View> implements ListContract.Presenter {

    private RxAppCompatActivity activity;

    public ListPresenter(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getProductList(Map<String, Object> maps, int tag, boolean isShowProgress) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        BaseApi baseApi = new BaseApi(new HttpOnNextListener() {

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.onError(e.getMessage(), e.getCode(), tag);
            }
        }, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MainService.class).getProductList(maps);
            }
        };
        baseApi.setShowProgress(isShowProgress);
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }
}
