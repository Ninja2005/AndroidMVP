package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.databinding.FragmentSettingsBinding;
import com.hqumath.androidmvp.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvp.ui.fileupdown.FileUpDownActivity;
import com.hqumath.androidmvp.ui.login.LoginActivity;
import com.hqumath.androidmvp.utils.SPUtil;

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

    private FragmentSettingsBinding binding;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.fileUpDown.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FileUpDownActivity.class);
            startActivity(intent);
        });
        binding.vLogout.setOnClickListener(v -> {
            SPUtil.getInstance().clear();
            startActivity(new Intent(mContext, LoginActivity.class));
            mContext.finish();
        });
    }

    @Override
    protected void initData() {
    }

}