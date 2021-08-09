package com.hqumath.androidmvp.ui.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvp.adapter.MyRecyclerAdapters;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.databinding.FragmentFollowersBinding;
import com.hqumath.androidmvp.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: FollowersFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项: 使用DiffUtil比对更新，减少刷新量
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseFragment implements FollowContract {

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

        recyclerAdapter = new MyRecyclerAdapters.FollowRecyclerAdapter(mContext, mPresenter.mData);
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            /*UserInfoEntity data = mDatas.get(position);
            Intent intent = new Intent(mContext, ProfileDetailActivity.class);
            intent.putExtra("UserName", data.getLogin());
            startActivity(intent);*/
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