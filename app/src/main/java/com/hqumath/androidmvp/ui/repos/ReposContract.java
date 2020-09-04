package com.hqumath.androidmvp.ui.repos;

/**
 * ****************************************************************
 * 文件名称: LoginContract
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:08
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface ReposContract {
    interface View {
        void onSuccess(Object object, int tag);

        void onError(String errorMsg, String code, int tag);
    }

    interface Presenter {
        void getMyRepos(int per_page, long page, int tag, boolean isShowProgress);

        void getStarred(int per_page, long page, int tag, boolean isShowProgress);

        void getReposInfo(String userName, String reposName, int tag, boolean isShowProgress);

        void getCommits(String userName, String reposName, int per_page, long page, int tag, boolean isShowProgress);
    }
}
