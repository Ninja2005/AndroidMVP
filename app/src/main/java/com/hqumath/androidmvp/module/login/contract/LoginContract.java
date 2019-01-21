package com.hqumath.androidmvp.module.login.contract;

/**
 * ****************************************************************
 * 文件名称: LoginContract
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:08
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface LoginContract {
    interface Model {
        void login(String userCode, String passWord);

        String getToken();

        String getUserName();

        void clearData();//清空个人数据本地缓存

        void getCache();//获取个人数据本地缓存

        void getAppConfig();//获取配置
    }

    interface View {
        void showLoading();

        void hideLoading();

        void onError(String msg);

//        void onSuccess(LoginResponse bean);
    }

    interface Presenter {
        void login(String userCode, String passWord);
    }
}
