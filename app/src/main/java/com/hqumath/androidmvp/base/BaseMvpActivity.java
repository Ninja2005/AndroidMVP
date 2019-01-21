package com.hqumath.androidmvp.base;

/**
 * ****************************************************************
 * 文件名称: BaseMvpActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 防止MVP内存泄漏
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
        super.onDestroy();
    }
}
