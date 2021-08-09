package com.hqumath.androidmvp.ui.follow;

public interface ProfileDetailContract {
    void onGetUserInfoSuccess(Object object);

    void onGetUserInfoError(String errorMsg, String code);
}
