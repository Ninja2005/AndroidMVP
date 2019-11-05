package com.hqumath.androidmvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hqumath.androidmvp.app.AppManager;
import com.hqumath.androidmvp.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

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
public abstract class BaseActivity extends RxAppCompatActivity {
    protected BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initContentView());

        AppManager.getInstance().addActivity(this);
        initView(savedInstanceState);
        initListener();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }

    public abstract int initContentView();

    public abstract void initView(Bundle savedInstanceState);

    protected void initListener() {
    }

    protected void initData() {
    }

    protected void toast(String s) {
        ToastUtil.toast(this, s);
    }
}
