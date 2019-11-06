package com.hqumath.androidmvp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseFragment;
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
public class OneFragment extends BaseMvpFragment {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_one;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}