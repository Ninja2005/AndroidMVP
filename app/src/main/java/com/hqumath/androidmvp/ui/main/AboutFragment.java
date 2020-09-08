package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.ui.follow.ProfileDetailActivity;
import com.hqumath.androidmvp.ui.repos.ReposDetailActivity;
import com.hqumath.androidmvp.utils.CommonUtil;

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
public class AboutFragment extends BaseFragment {
    private TextView tvVersion;
    private LinearLayout llSourcecode, llProfile;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View rootView) {
        tvVersion = rootView.findViewById(R.id.tv_version);
        llSourcecode = rootView.findViewById(R.id.ll_sourcecode);
        llProfile = rootView.findViewById(R.id.ll_profile);
    }

    @Override
    protected void initListener() {
        llSourcecode.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ReposDetailActivity.class);
            intent.putExtra("name", "AndroidMVP");
            intent.putExtra("login", "ninja2005");
            startActivity(intent);
        });
        llProfile.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProfileDetailActivity.class);
            intent.putExtra("UserName", "ninja2005");
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        tvVersion.setText(CommonUtil.getVersion());
    }
}