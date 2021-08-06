package com.hqumath.androidmvp.ui.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hqumath.androidmvp.adapter.FollowRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseFragment;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.databinding.FragmentFollowersBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: OneFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项: 使用DiffUtil比对更新，减少刷新量
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseFragment implements FollowContract {

    private FragmentFollowersBinding binding;
    private FollowRecyclerAdapter recyclerAdapter;

    private List<UserInfoEntity> mDatas = new ArrayList<>();
    private boolean isPullDown = true;//true表示下拉，false表示上拉
    private int itemCount = 1;//记录上拉加载更多的条目数偏移值

    @Override
    public android.view.View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /*@Override
    protected void initListener() {
        recyclerAdapter = new FollowRecyclerAdapter(mContext, mDatas, R.layout.recycler_item_followers);
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            UserInfoEntity data = mDatas.get(position);
            Intent intent = new Intent(mContext, ProfileDetailActivity.class);
            intent.putExtra("UserName", data.getLogin());
            startActivity(intent);
        });
        recyclerView.setAdapter(recyclerAdapter);
        refreshLayout.setOnRefreshListener(v -> {
            isPullDown = true;
            itemCount = 1;
            mPresenter.getFollowers(10, itemCount, GET_LIST, false);
        });
        refreshLayout.setOnLoadMoreListener(v -> {
            isPullDown = false;
            mPresenter.getFollowers(10, itemCount, GET_LIST, false);
        });
    }

    @Override
    protected void initData() {
        mPresenter = new FollowPresenter((RxAppCompatActivity) mContext);
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasRequested) {
            hasRequested = true;
            refreshLayout.autoRefresh();//触发自动刷新
        }
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == GET_LIST) {
            List<UserInfoEntity> list = ((List<UserInfoEntity>) object);
            if (list.size() == 0) {
                if (isPullDown) {
                    llNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    toast("没有更多数据了");
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
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
            //新比对更新，DiffUtil
            if (!isPullDown) {
                list.addAll(0, mDatas);
            }
            recyclerAdapter.updateData(list);
            //原全量更新
            *//*if (isPullDown) {
                mDatas.clear();
            }
            mDatas.addAll(list);
            recyclerAdapter.notifyDataSetChanged();*//*

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
    }*/
}