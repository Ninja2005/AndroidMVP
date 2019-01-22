package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HttpOnNextListener;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.service.DemoApiService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import retrofit2.Retrofit;

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
    public void login(String userCode, String passWord, HttpOnNextListener listener) {

        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(DemoApiService.class).demoGet();
            }
        };
        RetrofitClient.getInstance().sendHttpRequest(baseApi);


        /*Observer observer = new Observer<BaseResponse<DemoEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<DemoEntity> response) {
                //请求成功
                if (response.getCode() == 1) {
                    //将实体赋给LiveData
                    for (DemoEntity.ItemsEntity entity : response.getResult().getItems()) {
                        String name = entity.getName();
                    }
                } else {
                    //code错误时也可以定义Observable回调到View层去处理
                    String msg = "数据错误";
                }
            }

            @Override
            public void onError(Throwable e) {
                String error = "eror";
            }

            @Override
            public void onComplete() {
                String result = "result";
            }
        };

        DemoApiService service = RetrofitClientOld.getInstance().create(DemoApiService.class);
        RetrofitClientOld.execute(service.demoGet(), observer);*/

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
