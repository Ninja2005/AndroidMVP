package com.hqumath.androidmvp.ui.follow;

import com.hqumath.androidmvp.app.Constant;
import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.repository.MyModel;
import com.hqumath.androidmvp.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: FollowPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowPresenterOld extends BasePresenter<FollowPresenterOld.FollowContract> {

    public interface FollowContract {
        void onGetListSuccess(boolean isRefresh, boolean isNewDataEmpty);

        void onGetListError(String errorMsg, String code, boolean isRefresh);
    }

    private final static int pageSize = 10;//分页
    private long pageIndex;//索引
    public List<UserInfoEntity> mData = new ArrayList<>();//列表数据

    public FollowPresenterOld() {
        mModel = new MyModel();
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getFollowers(boolean isRefresh) {
        if (mView == null) return;
        if (isRefresh) {
            pageIndex = 1;
        }
        String userName = SPUtil.getInstance().getString(Constant.USER_NAME);
        ((MyModel) mModel).getFollowers(userName, pageSize, pageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                List<UserInfoEntity> list = (List<UserInfoEntity>) object;
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
