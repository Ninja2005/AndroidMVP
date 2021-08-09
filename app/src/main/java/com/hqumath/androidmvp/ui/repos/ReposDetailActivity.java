package com.hqumath.androidmvp.ui.repos;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvp.adapter.MyAdapters;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.databinding.ActivityReposDetailBinding;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.hqumath.androidmvp.utils.StringUtil;

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
public class ReposDetailActivity extends BaseActivity implements ReposDetailContract {
    private ActivityReposDetailBinding binding;
    private ReposDetailPresenter mPresenter;
    private MyAdapters.CommitsRecyclerAdapter recyclerAdapter;

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityReposDetailBinding.inflate(LayoutInflater.from(this));
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
            mPresenter.getReposInfo();
            mPresenter.getCommits(true);
        });
        binding.refreshLayout.setOnLoadMoreListener(v -> mPresenter.getCommits(false));
    }

    @Override
    protected void initData() {
        mPresenter = new ReposDetailPresenter();
        mPresenter.attachView(this);
        //data
        mPresenter.userName = getIntent().getStringExtra("login");
        mPresenter.reposName = getIntent().getStringExtra("name");
        setTitle(mPresenter.reposName);

        recyclerAdapter = new MyAdapters.CommitsRecyclerAdapter(mContext, mPresenter.mData);
        binding.recyclerView.setAdapter(recyclerAdapter);
        //仓库详情
        mPresenter.getReposInfo();
        //提交记录
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
    public void onGetReposInfoSuccess(Object object) {
        ReposEntity data = (ReposEntity) object;
        Glide.with(mContext).load(data.getOwner().getAvatar_url()).into(binding.ivAvatarBg);
        binding.tvDescription.setText(data.getDescription());
        binding.tvFullName.setText(data.getFull_name());
        binding.tvCreatedAt.setText(data.getCreated_at().replace("T", " ")
                .replace("Z", ""));
        String info = String.format(Locale.getDefault(), "Language %s, size %s",
                data.getLanguage(), StringUtil.getSizeString(data.getSize() * 1024));
        binding.tvLanguageSize.setText(info);
    }

    @Override
    public void onGetReposInfoError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
    }

    @Override
    public void onGetListSuccess(boolean isRefresh) {
        recyclerAdapter.notifyDataSetChanged();
        boolean isEmpty = mPresenter.mData.isEmpty();
        if (isRefresh) {
            if (isEmpty) {
                binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
            } else {
                binding.refreshLayout.finishRefresh();
            }
        } else {
            if (isEmpty) {
                CommonUtil.toast("没有更多数据了");
                binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
            } else {
                binding.refreshLayout.finishLoadMore();
            }
        }
        binding.emptyLayout.llEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetListError(String errorMsg, String code, boolean isRefresh) {
        CommonUtil.toast(errorMsg);
        if (isRefresh) {
            binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
        } else {
            binding.refreshLayout.finishLoadMore(false);
        }
        binding.emptyLayout.llEmpty.setVisibility(mPresenter.mData.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
