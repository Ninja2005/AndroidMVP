package com.hqumath.androidmvp.ui.follow;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.repository.MyModel;

public class ProfileDetailPresenter extends BasePresenter<ProfileDetailContract> {

    public String userName;

    public ProfileDetailPresenter() {
        mModel = new MyModel();
    }

    public void getUserInfo() {
        if (mView == null) return;
        ((MyModel) mModel).getUserInfo(userName, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.onGetUserInfoSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.onGetUserInfoError(errorMsg, code);
            }
        });
    }
}

