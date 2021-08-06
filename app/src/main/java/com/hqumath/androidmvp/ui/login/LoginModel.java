package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.base.BaseModel;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.net.RetrofitClient;

/**
 * ****************************************************************
 * 文件名称: LoginModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 数据层，数据的获取、存储和处理
 * 注意事项: 提供的数据业务层直接可用，方便自动化测试
 * 版权声明:
 * ****************************************************************
 */
public class LoginModel extends BaseModel {

    public void login(String userName, String passWord, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                //数据校验、处理
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }
}
