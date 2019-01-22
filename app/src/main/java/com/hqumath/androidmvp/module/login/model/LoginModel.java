package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.bean.BaseResponse;
import com.hqumath.androidmvp.bean.DemoEntity;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.net.RetrofitClientOld;
import com.hqumath.androidmvp.net.service.DemoApiService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

    @Override
    public void login(String userCode, String passWord) {

//        final ProgressDialog pd = new ProgressDialog();

        Observer observer = new Observer<BaseResponse<DemoEntity>>() {
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
        RetrofitClientOld.execute(service.demoGet(), observer);

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
