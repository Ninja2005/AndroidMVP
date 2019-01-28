package com.hqumath.androidmvp.base;

/**
 * ****************************************************************
 * 文件名称: BaseMvpFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 防止MVP内存泄漏
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
        super.onDestroyView();
    }
}
