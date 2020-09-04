package com.hqumath.androidmvp.ui.repos;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.utils.StringUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Locale;

/**
 * ****************************************************************
 * 文件名称: ReposDetailActivity
 * 作    者: Created by gyd
 * 创建时间: 2020/9/4 16:33
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class ReposDetailActivity extends BaseMvpActivity<ReposPresenter> implements ReposContract.View {
    private static final int GET_DETAIL = 1;//仓库详情
    private static final int GET_COMMITS = 2;//获取仓库提交记录

    private Toolbar toolbar;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageView ivAvatarBg;
    private TextView tvDescription, tvFullName, tvCreatedAt, tvLanguageSize;

    private String userName, reposName;


    @Override
    public int initContentView() {
        return R.layout.activity_repos_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        toolbar = findViewById(R.id.toolbar);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        ivAvatarBg = findViewById(R.id.iv_avatar_bg);
        tvDescription = findViewById(R.id.tv_description);
        tvFullName = findViewById(R.id.tv_full_name);
        tvCreatedAt = findViewById(R.id.tv_created_at);
        tvLanguageSize = findViewById(R.id.tv_language_size);

    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
//adapter
    }

    @Override
    protected void initData() {
        //data
        userName = getIntent().getStringExtra("login");
        reposName = getIntent().getStringExtra("name");
        //ui
        setTitle(reposName);
        //request
        mPresenter = new ReposPresenter(this);
        mPresenter.attachView(this);
        mPresenter.getReposInfo(userName, reposName, GET_DETAIL, false);
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == GET_DETAIL) {
            ReposEntity data = (ReposEntity) object;
            Glide.with(mContext).load(data.getOwner().getAvatar_url()).into(ivAvatarBg);
            tvDescription.setText(data.getDescription());
            tvFullName.setText(data.getFull_name());
            tvCreatedAt.setText(data.getCreated_at().replace("T", " ")
                    .replace("Z", ""));
            String info = String.format(Locale.getDefault(), "Language %s, size %s",
                    data.getLanguage(), StringUtil.getSizeString(data.getSize() * 1024));
            tvLanguageSize.setText(info);
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);

    }
}
