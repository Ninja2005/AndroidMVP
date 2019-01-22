package com.hqumath.androidmvp.app;

import android.app.Application;
import android.content.Context;

/**
 * ****************************************************************
 * 文件名称: AndroidMvpApp
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 15:31
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AndroidMvpApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
