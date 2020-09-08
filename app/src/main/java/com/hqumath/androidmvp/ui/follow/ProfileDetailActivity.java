package com.hqumath.androidmvp.ui.follow;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/**
 * ****************************************************************
 * 文件名称: ReposDetailActivity
 * 作    者: Created by gyd
 * 创建时间: 2020/9/4 16:33
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class ProfileDetailActivity extends BaseMvpActivity<FollowPresenter> implements FollowContract.View {
    private static final int GET_DETAIL = 1;//详情

    private Toolbar toolbar;
    private ImageView ivAvatarBg, ivAvatar;
    private TextView tvLocation, tvJoinedTime;
    private TextView tvName, tvCompany, tvEmail, tvBlog;
    private RefreshLayout refreshLayout;

    private String userName;

    @Override
    public int initContentView() {
        return R.layout.activity_profile_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        toolbar = findViewById(R.id.toolbar);
        ivAvatarBg = findViewById(R.id.iv_avatar_bg);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvLocation = findViewById(R.id.tv_location);
        tvJoinedTime = findViewById(R.id.tv_joined_time);
        tvName = findViewById(R.id.tv_name);
        tvCompany = findViewById(R.id.tv_company);
        tvEmail = findViewById(R.id.tv_email);
        tvBlog = findViewById(R.id.tv_blog);
        refreshLayout = findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        refreshLayout.setOnRefreshListener(v -> {
            mPresenter.getUserInfo(userName, GET_DETAIL, false);
        });
    }

    @Override
    protected void initData() {
        //data
        userName = getIntent().getStringExtra("UserName");
        setTitle(userName);

        mPresenter = new FollowPresenter(mContext);
        mPresenter.attachView(this);
        //详情
        refreshLayout.autoRefresh();//触发自动刷新
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == GET_DETAIL) {
            UserInfoEntity data = (UserInfoEntity) object;
            Glide.with(mContext).load(data.getAvatar_url()).into(ivAvatarBg);
            Glide.with(mContext).load(data.getAvatar_url()).into(ivAvatar);
            tvLocation.setText(data.getLocation());
            String time = data.getCreated_at().replace("T", " ").replace("Z", "");
            tvJoinedTime.setText(time);
            tvName.setText(data.getName());
            if (!TextUtils.isEmpty(data.getCompany())) {
                tvCompany.setText(data.getCompany());
                tvCompany.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(data.getEmail())) {
                tvEmail.setText(data.getEmail());
                tvEmail.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(data.getBlog())) {
                tvBlog.setText(data.getBlog());
                tvBlog.setVisibility(View.VISIBLE);
            }
            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh();
                refreshLayout.resetNoMoreData();
            }
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        if (tag == GET_DETAIL) {
            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh(false);
            }
        }
    }
}
