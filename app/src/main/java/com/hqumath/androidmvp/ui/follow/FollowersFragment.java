package com.hqumath.androidmvp.ui.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvp.adapter.MyRecyclerAdapters;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.databinding.FragmentFollowersBinding;
import com.hqumath.androidmvp.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: FollowersFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述: 使用 Room 持久化存储列表数据
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseFragment implements FollowPresenter.Contract {

    private FragmentFollowersBinding binding;
    private FollowPresenter mPresenter;
    private MyRecyclerAdapters.FollowRecyclerAdapter recyclerAdapter;
    protected boolean hasRequested;//在onResume中判断是否已经请求过数据。用于懒加载

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.refreshLayout.setOnRefreshListener(v -> mPresenter.getFollowers(true));
        binding.refreshLayout.setOnLoadMoreListener(v -> mPresenter.getFollowers(false));
    }

    @Override
    protected void initData() {
        mPresenter = new FollowPresenter();
        mPresenter.attachView(this);

        recyclerAdapter = new MyRecyclerAdapters.FollowRecyclerAdapter(mContext, new ArrayList<>());
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            List<UserInfoEntity> list = mPresenter.mData.getValue();
            if (list != null && list.size() > position) {
                UserInfoEntity data = list.get(position);
                startActivity(ProfileDetailActivity.getStartIntent(mContext, data.getLogin()));
            }
        });
        binding.recyclerView.setAdapter(recyclerAdapter);
        //根据数据库刷新列表
        mPresenter.mData.observe(this, list -> {
            recyclerAdapter.setData(list);
            recyclerAdapter.notifyDataSetChanged();
            binding.emptyLayout.llEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        });
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
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onGetListSuccess(boolean isRefresh, boolean isNewDataEmpty) {
        if (isRefresh) {
            if (isNewDataEmpty) {
                binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
            } else {
                binding.refreshLayout.finishRefresh();
            }
        } else {
            if (isNewDataEmpty) {
                binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
            } else {
                binding.refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onGetListError(String errorMsg, String code, boolean isRefresh) {
        CommonUtil.toast(errorMsg);
        if (isRefresh) {
            binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
        } else {
            binding.refreshLayout.finishLoadMore(false);
        }
    }
}