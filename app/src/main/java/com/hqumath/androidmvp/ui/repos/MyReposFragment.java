package com.hqumath.androidmvp.ui.repos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvp.adapter.MyAdapters;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvp.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: MyReposFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyReposFragment extends BaseFragment implements ReposContract {

    private FragmentSwipeListBinding binding;
    private MyReposPresenter mPresenter;
    private MyAdapters.ReposRecyclerAdapter recyclerAdapter;
    protected boolean hasRequested;//在onResume中判断是否已经请求过数据。用于懒加载

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSwipeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.refreshLayout.setOnRefreshListener(v -> mPresenter.getMyRepos(true));
        binding.refreshLayout.setOnLoadMoreListener(v -> mPresenter.getMyRepos(false));
    }

    @Override
    protected void initData() {
        mPresenter = new MyReposPresenter();
        mPresenter.attachView(this);

        recyclerAdapter = new MyAdapters.ReposRecyclerAdapter(mContext, mPresenter.mData);
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            ReposEntity data = mPresenter.mData.get(position);
            Intent intent = new Intent(mContext, ReposDetailActivity.class);
            intent.putExtra("name", data.getName());
            intent.putExtra("login", data.getOwner().getLogin());
            startActivity(intent);
        });
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasRequested) {
            hasRequested = true;
            binding.refreshLayout.autoRefresh();//触发自动刷新
        }
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