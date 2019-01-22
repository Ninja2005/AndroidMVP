package com.hqumath.androidmvp.module.login.contract;

import com.hqumath.androidmvp.net.HttpOnNextListener;

import java.util.Map;

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
        void login(final Map<String, Object> maps, HttpOnNextListener listener);

        String getToken();

        String getUserName();

        void clearData();//清空个人数据本地缓存

        void getCache();//获取个人数据本地缓存

        void getAppConfig();//获取配置
    }

    interface View {
        void onSuccess(Object object, int tag);

        void onError(String msg, int tag);
    }

    interface Presenter {
        void login(Map<String, Object> maps, final int tag);
    }
}
