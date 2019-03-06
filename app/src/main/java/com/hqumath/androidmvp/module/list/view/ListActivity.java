package com.hqumath.androidmvp.module.list.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.ProductInfo;
import com.hqumath.androidmvp.bean.ProductListResponse;
import com.hqumath.androidmvp.module.list.contract.ListContract;
import com.hqumath.androidmvp.module.list.presenter.ListPresenter;
import com.hqumath.androidmvp.module.list.presenter.MyRecyclerAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: ListActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 11:12
 * 文件描述: 产品列表界面
 * 注意事项: 支持分页，下拉刷新，上拉加载更多
 * 版权声明:
 * ****************************************************************
 */
public class ListActivity extends BaseMvpActivity<ListPresenter> implements ListContract.View {
    private static final int PRODUCT_TAG = 1;//产品列表 ZS0200001

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    MyRecyclerAdapter recyclerAdapter;

    private List<ProductInfo> mDatas = new ArrayList<ProductInfo>();
    private boolean isPullDown = true;//true表示下拉，false表示上拉
    private int itemCount = 1;//记录上拉加载更多的条目数偏移值


    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    protected void initListener() {

        recyclerAdapter = new MyRecyclerAdapter(mContext, mDatas, R.layout.main_recyclerview_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                isPullDown = true;
                itemCount = 1;
                Map<String, Object> maps = new HashMap<>();
                maps.put("appKey", "mobile");
                maps.put("perpage", "10");
                maps.put("offset", "1");
                maps.put("productCycleStatus", "buying");
                mPresenter.getProductList(maps, PRODUCT_TAG, false);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                isPullDown = false;
                Map<String, Object> maps = new HashMap<>();
                maps.put("appKey", "mobile");
                maps.put("perpage", "10");
                maps.put("offset", itemCount + "");
                maps.put("productCycleStatus", "buying");
                mPresenter.getProductList(maps, PRODUCT_TAG, false);
            }
        });
    }


    @Override
    protected void initData() {
        mPresenter = new ListPresenter(this);
        mPresenter.attachView(this);

        //触发自动刷新
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == PRODUCT_TAG) {
            //下拉刷新，清空历史数据
            if (isPullDown) {
                mDatas.clear();
            }
            List<ProductInfo> list = (((ProductListResponse) object).getSubscribeProductBo());
            if (list.size() == 0) {
                if (isPullDown) {
                    toast("没有数据");
                } else {
                    toast("没有更多数据了");
                    mRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
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

        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.resetNoMoreData();
        }
        if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);

        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(false);
        }
        if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore(false);
        }
    }
}
