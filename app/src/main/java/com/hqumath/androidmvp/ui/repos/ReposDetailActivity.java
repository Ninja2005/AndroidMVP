package com.hqumath.androidmvp.ui.repos;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.adapter.CommitsRecyclerAdapter;
import com.hqumath.androidmvp.adapter.ReposRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.CommitEntity;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.utils.StringUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import java.util.ArrayList;
import java.util.List;
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
    private static final int GET_LIST = 2;//获取仓库提交记录

    private Toolbar toolbar;
    private ImageView ivAvatarBg;
    private TextView tvDescription, tvFullName, tvCreatedAt, tvLanguageSize;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout llNoData;

    private CommitsRecyclerAdapter recyclerAdapter;

    private String userName, reposName;
    private List<CommitEntity> mDatas = new ArrayList<>();
    private boolean isPullDown = true;//true表示下拉，false表示上拉
    private int itemCount = 1;//记录上拉加载更多的条目数偏移值

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
        ivAvatarBg = findViewById(R.id.iv_avatar_bg);
        tvDescription = findViewById(R.id.tv_description);
        tvFullName = findViewById(R.id.tv_full_name);
        tvCreatedAt = findViewById(R.id.tv_created_at);
        tvLanguageSize = findViewById(R.id.tv_language_size);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        llNoData = findViewById(R.id.ll_no_data);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerAdapter = new CommitsRecyclerAdapter(mContext, mDatas, R.layout.recycler_item_commits);
        recyclerView.setAdapter(recyclerAdapter);
        refreshLayout.setOnRefreshListener(v -> {
            //仓库详情
            mPresenter.getReposInfo(userName, reposName, GET_DETAIL, false);
            //提交记录
            isPullDown = true;
            itemCount = 1;
            mPresenter.getCommits(userName, reposName, 10, itemCount, GET_LIST, false);
        });
        refreshLayout.setOnLoadMoreListener(v -> {
            isPullDown = false;
            mPresenter.getCommits(userName, reposName, 10, itemCount, GET_LIST, false);
        });
    }

    @Override
    protected void initData() {
        //data
        userName = getIntent().getStringExtra("login");
        reposName = getIntent().getStringExtra("name");
        setTitle(reposName);

        mPresenter = new ReposPresenter(mContext);
        mPresenter.attachView(this);
        //仓库详情
        mPresenter.getReposInfo(userName, reposName, GET_DETAIL, false);
        //提交记录
        refreshLayout.autoRefresh();//触发自动刷新
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
        } else if (tag == GET_LIST) {
            //下拉刷新，清空历史数据
            if (isPullDown) {
                mDatas.clear();
            }
            List<CommitEntity> list = ((List<CommitEntity>) object);
            if (list.size() == 0) {
                if (isPullDown) {
                    //toast("没有数据");
                    llNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    toast("没有更多数据了");
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    recyclerAdapter.notifyDataSetChanged();
                    return;
                }
            } else {
                llNoData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            //上拉刷新 偏移量+1
            if (!isPullDown) {
                itemCount += 1;
            } else {
                itemCount = 2;
            }
            mDatas.addAll(list);
            recyclerAdapter.notifyDataSetChanged();

            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh();
                refreshLayout.resetNoMoreData();
            }
            if (refreshLayout.getState() == RefreshState.Loading) {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        if (tag == GET_LIST) {
            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh(false);
            }
            if (refreshLayout.getState() == RefreshState.Loading) {
                refreshLayout.finishLoadMore(false);
            }
        }
    }
}
