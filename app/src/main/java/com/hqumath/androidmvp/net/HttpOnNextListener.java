package com.hqumath.androidmvp.net;


/**
 * 成功回调处理
 */
public interface HttpOnNextListener<T> {
    void onSuccess(T object, int tag);
    void onError(String errorMsg, String code, int tag);
}