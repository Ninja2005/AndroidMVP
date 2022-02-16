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
public class LogInterceptor implements Interceptor {
    private final String TAG = "HTTPTag";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;//打印请求异常信息
        try {
            response = chain.proceed(request.newBuilder().build());
        } catch (Exception e) {
            LogUtil.d(TAG, "=====\n" + request.method() + ": "
                    + request.url() + "\n" + e.getMessage(), false);
            throw e;
        }
        //response.body()调用后，response中的流会被关闭
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        //解析出参
        String content = responseBody.string();
        //JSONObject jsonObject = new JSONObject(content); //统一处理响应数据

        //打印出参
        LogUtil.d(TAG, "=====\n" + request.method() + ": "
                + request.url() + "\n" + content, false);
        return response;
    }
}
