package com.hqumath.androidmvp.module.list.contract;

import com.hqumath.androidmvp.net.listener.HttpOnNextListener;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: ListContract
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 11:19
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface ListContract {
    interface Model {
        void getProductList(final Map<String, Object> maps, HttpOnNextListener listener, boolean isShowProgress);
    }

    interface View {
        void onSuccess(Object object, int tag);

        void onError(String errorMsg, String code, int tag);
    }

    interface Presenter {
        void getProductList(Map<String, Object> maps, final int tag, boolean isShowProgress);
    }
}
