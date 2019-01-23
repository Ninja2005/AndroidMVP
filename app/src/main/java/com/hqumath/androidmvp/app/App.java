package com.hqumath.androidmvp.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * ****************************************************************
 * 文件名称: App
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 15:31
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//方法数超过65k
    }
}
