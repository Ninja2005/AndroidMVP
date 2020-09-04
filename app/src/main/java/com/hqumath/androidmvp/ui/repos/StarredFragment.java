package com.hqumath.androidmvp.ui.repos;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.adapter.MyRecyclerAdapter;
import com.hqumath.androidmvp.base.BaseMvpFragment;
import com.hqumath.androidmvp.bean.ReposEntity;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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
public class StarredFragment extends BaseMvpFragment<ReposPresenter> implements ReposContract.View {
    private static final int GET_LIST = 1;

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private MyRecyclerAdapter recyclerAdapter;

    private List<ReposEntity> mDatas = new ArrayList<ReposEntity>();
    private boolean isPullDown = true;//true表示下拉，false表示上拉
    private int itemCount = 1;//记录上拉加载更多的条目数偏移值

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_swipe_list;
    }

    @Override
    protected void initView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initListener() {
        recyclerAdapter = new MyRecyclerAdapter(mContext, mDatas, R.layout.item_repos);
        recyclerView.setAdapter(recyclerAdapter);
        refreshLayout.setOnRefreshListener(v -> {
            isPullDown = true;
            itemCount = 1;
            mPresenter.getStarred(10, itemCount, GET_LIST, false);
        });
        refreshLayout.setOnLoadMoreListener(v -> {
            isPullDown = false;
            mPresenter.getStarred(10, itemCount, GET_LIST, false);
        });
    }

    @Override
    protected void initData() {
        mPresenter = new ReposPresenter((RxAppCompatActivity) mContext);
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonUtil.isNetworkAvailable() && !hasRequested) {
            hasRequested = true;
            refreshLayout.autoRefresh();//触发自动刷新
        }
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == GET_LIST) {
            //下拉刷新，清空历史数据
            if (isPullDown) {
                mDatas.clear();
            }
            List<ReposEntity> list = ((List<ReposEntity>) object);
            if (list.size() == 0) {
                if (isPullDown) {
                    toast("没有数据");
                } else {
                    toast("没有更多数据了");
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    recyclerAdapter.notifyDataSetChanged();
                    return;
                }
            }
            //上拉刷新 偏移量+1
            if (!isPullDown) {
                itemCount += 1;
            } else {
                itemCount = 2;
            }
            mDatas.addAll(list);
            recyclerAdapter.notifyDataSetChanged();
        }

        if (refreshLayout.getState() == RefreshState.Refreshing) {
            refreshLayout.finishRefresh();
            refreshLayout.resetNoMoreData();
        }
        if (refreshLayout.getState() == RefreshState.Loading) {
            refreshLayout.finishLoadMore();
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