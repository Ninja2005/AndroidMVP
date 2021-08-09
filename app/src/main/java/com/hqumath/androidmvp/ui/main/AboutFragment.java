package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.databinding.FragmentAboutBinding;
import com.hqumath.androidmvp.databinding.FragmentSettingsBinding;
import com.hqumath.androidmvp.ui.login.LoginActivity;
import com.hqumath.androidmvp.ui.repos.ReposDetailActivity;
import com.hqumath.androidmvp.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: AboutFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class AboutFragment extends BaseFragment {

    private FragmentAboutBinding binding;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.llSourcecode.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ReposDetailActivity.class);
            intent.putExtra("name", "AndroidMVP");
            intent.putExtra("login", "ninja2005");
            startActivity(intent);
        });
        binding.llProfile.setOnClickListener(v -> {
            //TODO
            /*Intent intent = new Intent(mContext, ProfileDetailActivity.class);
            intent.putExtra("UserName", "ninja2005");
            startActivity(intent);*/
        });
    }

    @Override
    protected void initData() {
        binding.tvVersion.setText(CommonUtil.getVersion());
    }

}