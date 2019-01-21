package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.module.login.contract.LoginContract;

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
        //TODO
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
