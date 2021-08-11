package com.hqumath.androidmvp.ui.repos;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.bean.CommitEntity;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.repository.MyModel;

import java.util.ArrayList;
import java.util.List;

public class ReposDetailPresenter extends BasePresenter<ReposDetailContract> {

    private final static int pageSize = 10;//分页
    private long pageIndex;//索引
    public List<CommitEntity> mData = new ArrayList<>();//列表数据

    public String userName;
    public String reposName;

    public ReposDetailPresenter() {
        mModel = new MyModel();
    }

    public void getReposInfo() {
        if (mView == null) return;
        ((MyModel) mModel).getReposInfo(userName, reposName, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.onGetReposInfoSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.onGetReposInfoError(errorMsg, code);
            }
        });
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getCommits(boolean isRefresh) {
        if (mView == null) return;
        if (isRefresh) {
            pageIndex = 1;
        }
        ((MyModel) mModel).getCommits(userName, reposName, pageSize, pageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                List<CommitEntity> list = (List<CommitEntity>) object;
                pageIndex++;//偏移量+1
                if (isRefresh) //下拉覆盖，上拉增量
                    mData.clear();
                if (!list.isEmpty())
                    mData.addAll(list);
                mView.onGetListSuccess(isRefresh, list.isEmpty());
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.onGetListError(errorMsg, code, isRefresh);
            }
        });
    }
}
