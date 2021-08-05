package com.hqumath.androidmvp.net;


import com.hqumath.androidmvp.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 自定义拦截器
 * 打印日志
 */
public class MyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;//打印请求异常信息
        try {
            response = chain.proceed(request.newBuilder().build());
        } catch (Exception e) {
            LogUtil.d("HTTP", "网络请求异常\n" + e);
            throw e;
        }
        //response.body()调用后，response中的流会被关闭
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        //解析出参
        String content = responseBody.string();
        /*JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            String code = jsonObject.optString("code", "");
            if (code.equals("-100")) {
                //特殊错误号处理
            }
        }*/
        //打印出参
        LogUtil.d("HTTP", "=====\n" + response.request().method() + ": "
                + response.request().url() + "\n" + content);
        return response;
    }
}
