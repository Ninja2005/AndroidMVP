package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.ui.login.LoginActivity;

/**
 * ****************************************************************
 * 文件名称: OneFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class SettingsFragment extends BaseFragment {
    private View vLogout;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_settings;
    }

    protected void initView(View rootView) {
        vLogout = rootView.findViewById(R.id.v_logout);
    }

    protected void initListener() {
        vLogout.setOnClickListener(v -> {
            startActivity(new Intent(mContext, LoginActivity.class));
            mContext.finish();
        });
    }

}