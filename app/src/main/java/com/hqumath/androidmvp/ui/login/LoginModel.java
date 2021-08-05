package com.hqumath.androidmvp.ui.login;

import com.hqumath.androidmvp.base.BaseModel;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.net.HttpOnNextListener;
import com.hqumath.androidmvp.net.RetrofitClient;

/**
 * 数据的获取、存储、处理
 * Model 层提供的数据应该是业务中直接可用的
 * 方便自动化测试，单元测试
 * 例如：需要缓存的页面。先显示本地数据，同时发起网络请求=>更新本地缓存+刷新页面
 */
public class LoginModel extends BaseModel {

    public void login(String userName, String passWord, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener(){
            @Override
            public void onSuccess(Object object) {
                //校验数据结构 TODO
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }
}
