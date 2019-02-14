package com.hqumath.androidmvp.module.main.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.module.main.contract.MainContract;
import com.hqumath.androidmvp.module.main.presenter.MainPresenter;
import com.hqumath.androidmvp.module.main.presenter.MyRecyclerAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 11:12
 * 文件描述: 列表下拉刷新，上拉加载更多
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    protected void initListener() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        MyRecyclerAdapter recyclerAdapter = new MyRecyclerAdapter(mContext, list, R.layout.main_recyclerview_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(recyclerAdapter);

        /*mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh(initData1());
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();//setNoMoreData(false);
                    }
                }, 2000);
            }
        });*/
        /*mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() > 30) {
                            toast("数据全部加载完毕");
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        } else {
                            mAdapter.loadMore(initData1());
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });*/
    }


    @Override
    protected void initData() {
        //触发自动刷新
        //mRefreshLayout.autoRefresh();
    }

    @Override
    public void onSuccess(Object object, int tag) {

    }

    @Override
    public void onError(String errorMsg, String code, int tag) {

    }
}
