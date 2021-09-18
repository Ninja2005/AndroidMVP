package com.hqumath.androidmvp.ui.follow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.databinding.ActivityProfileDetailBinding;
import com.hqumath.androidmvp.utils.CommonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileDetailActivity extends BaseActivity implements ProfileDetailContract {
    private ActivityProfileDetailBinding binding;
    private ProfileDetailPresenter mPresenter;

    public static Intent getStartIntent(Context mContext, String mUserName) {
        Intent intent = new Intent(mContext, ProfileDetailActivity.class);
        intent.putExtra("UserName", mUserName);
        return intent;
    }

    @Override
    protected View initContentView(Bundle savedInstanceState) {
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.refreshLayout.setOnRefreshListener(v -> {
            mPresenter.getUserInfo();
        });
    }

    @Override
    protected void initData() {
        mPresenter = new ProfileDetailPresenter();
        mPresenter.attachView(this);
        //data
        mPresenter.userName = getIntent().getStringExtra("UserName");
        setTitle(mPresenter.userName);
        //详情
        binding.refreshLayout.autoRefresh();//触发自动刷新
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void onGetUserInfoSuccess(Object object) {
        UserInfoEntity data = (UserInfoEntity) object;
        Glide.with(mContext).load(data.getAvatar_url()).into(binding.ivAvatarBg);
        Glide.with(mContext).load(data.getAvatar_url()).into(binding.ivAvatar);
        binding.tvLocation.setText(data.getLocation());
        //时间格式化
        String date = data.getCreated_at();//2011-12-29T04:45:11Z
        date = date.replace("Z", " UTC");//UTC是世界标准时间
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format1.parse(date);
            String date2 = format2.format(date1);
            binding.tvJoinedTime.setText(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.tvName.setText(data.getName());
        if (!TextUtils.isEmpty(data.getCompany())) {
            binding.tvCompany.setText(data.getCompany());
            binding.tvCompany.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(data.getEmail())) {
            binding.tvEmail.setText(data.getEmail());
            binding.tvEmail.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(data.getBlog())) {
            binding.tvBlog.setText(data.getBlog());
            binding.tvBlog.setVisibility(View.VISIBLE);
        }
        binding.refreshLayout.finishRefresh();
    }

    @Override
    public void onGetUserInfoError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
        binding.refreshLayout.finishRefresh(false);
    }
}
