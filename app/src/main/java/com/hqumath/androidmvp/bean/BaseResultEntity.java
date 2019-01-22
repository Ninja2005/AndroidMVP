package com.hqumath.androidmvp.bean;

/**
 * 网络请求统一返回格式
 */
public class BaseResultEntity<T> {
    //判断标示
    private int code;
    //提示信息
    private String message;
    //显示数据（用户需要关心的数据）
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
