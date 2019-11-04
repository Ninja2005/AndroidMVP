package com.hqumath.androidmvp.ui.list;

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
    interface View {
        void onSuccess(Object object, int tag);

        void onError(String errorMsg, String code, int tag);
    }

    interface Presenter {
        void getProductList(Map<String, Object> maps, final int tag, boolean isShowProgress);
    }
}
