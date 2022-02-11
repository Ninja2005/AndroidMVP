package com.hqumath.androidmvp.ui.follow;

import androidx.lifecycle.LiveData;

import com.hqumath.androidmvp.app.AppExecutors;
import com.hqumath.androidmvp.app.Constant;
import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.repository.AppDatabase;
import com.hqumath.androidmvp.repository.MyModel;
import com.hqumath.androidmvp.utils.SPUtil;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: FollowPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 使用 Room 持久化存储列表数据，Network => DB => LiveData => RecyclerView
 * 注意事项: 自动加载数据库中全部数据，不能逐渐加载
 * 版权声明:
 * ****************************************************************
 */
public class FollowPresenter extends BasePresenter<FollowPresenter.Contract> {

    public interface Contract {
        void onGetListSuccess(boolean isRefresh, boolean isNewDataEmpty);

        void onGetListError(String errorMsg, String code, boolean isRefresh);
    }

    private final static int pageSize = 10;//分页
    private long pageIndex;//索引
    public LiveData<List<UserInfoEntity>> mData;//列表数据

    public FollowPresenter() {
        mModel = new MyModel();
        mData = AppDatabase.getInstance().userInfoDao().loadAll();//UserInfoDao_Impl 内部做了线程切换
    }

    @Override
    public void detachView() {
        super.detachView();
        AppDatabase.getInstance().close();//关闭数据库
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
                AppExecutors.getInstance().workThread().execute(() -> {
                    if (isRefresh) //下拉覆盖，上拉增量
                        AppDatabase.getInstance().userInfoDao().deleteAll();
                    if (!list.isEmpty())
                        AppDatabase.getInstance().userInfoDao().insertAll(list);
                });
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
