package com.hqumath.androidmvp.module.main.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.main.contract.MainContract;

import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: MainPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/12 15:24
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Override
    public void getProductList(Map<String, Object> maps, int tag) {

    }
}
