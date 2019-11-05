package com.hqumath.androidmvp.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hqumath.androidmvp.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * ****************************************************************
 * 文件名称: BaseFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseFragment extends RxFragment {
    private View rootView;
    protected Activity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(initContentView(savedInstanceState), container, false);
            initView(rootView);
            initListener();
            initData();//初始化数据走网络请求等
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public abstract int initContentView(Bundle savedInstanceState);

    protected void initView(View rootView) {
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    protected void toast(String s) {
        ToastUtil.toast(mContext, s);
    }
}
