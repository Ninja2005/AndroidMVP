package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.databinding.ActivityLoginBinding;
import com.hqumath.androidmvp.ui.login.LoginActivity;

public class TestActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected View initContentView(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}
