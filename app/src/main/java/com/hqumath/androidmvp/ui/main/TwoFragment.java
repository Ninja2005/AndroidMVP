package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpFragment;
import com.hqumath.androidmvp.utils.LogUtil;

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
public class TwoFragment extends BaseMvpFragment {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        LogUtil.d("TAG12", "TwoFragment onCreate");
        return R.layout.fragment_two;
    }

    @Override
    public void onResume() {
        LogUtil.d("TAG12", "TwoFragment onResume");
        super.onResume();
    }
}