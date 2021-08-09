package com.hqumath.androidmvp.ui.repos;

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
public interface ReposContract {
    void onGetListSuccess(boolean isRefresh);

    void onGetListError(String errorMsg, String code, boolean isRefresh);
}
