package com.hqumath.androidmvp.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ****************************************************************
 * 文件名称: BaseActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initContentView(savedInstanceState));
        //初始化状态栏样式
        //initImmersionBar();
        //事件监听
        initListener();
        //初始化数据
        initData();
    }

    protected abstract View initContentView(Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();

    //沉浸式状态栏，默认文字黑色
    /*protected void initImmersionBar() {
        ImmersionBar.with(this)
                .reset()
                .statusBarDarkFont(true, 0.2f)
                .init();
        //文字白色
        //ImmersionBar.with(mContext).reset().init();
    }*/
}
