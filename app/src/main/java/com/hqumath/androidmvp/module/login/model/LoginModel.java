package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.bean.BaseResponse;
import com.hqumath.androidmvp.bean.DemoEntity;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.service.DemoApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        RetrofitClient.getInstance().create(DemoApiService.class).demoGet()

                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<DemoEntity>>() {
                    @Override
                    public void accept(BaseResponse<DemoEntity> response) throws Exception {
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String error = "eror";
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        String result = "result";
                    }
                });
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
