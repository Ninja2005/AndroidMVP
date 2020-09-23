package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.widget.DownloadingProgressBar;

/**
 * ****************************************************************
 * 文件名称: CustomViewActivity
 * 作    者: Created by gyd
 * 创建时间: 2020/9/22 17:22
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class CustomViewActivity extends BaseActivity {
    private DownloadingProgressBar mProgressBar;

    @Override
    public int initContentView() {
        return R.layout.activity_custom_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mProgressBar = findViewById(R.id.pb_downloading_content);

    }

    @Override
    protected void initListener() {
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        new CountDownTimer(10000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 - millisUntilFinished / 100);
                mProgressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                mProgressBar.setProgress(100);
            }
        }.start();
    }

    @Override
    public void initData() {

    }
}
