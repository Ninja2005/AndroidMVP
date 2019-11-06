package com.hqumath.androidmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpFragment;
import com.hqumath.androidmvp.ui.fileupdown.FileUpDownActivity;
import com.hqumath.androidmvp.ui.list.ListActivity;
import com.hqumath.androidmvp.ui.login.LoginActivity;

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
public class DemoFragment extends BaseMvpFragment implements View.OnClickListener {

    private Button btnLogin, btnList, btnFileUpDown;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_demo;
    }

    @Override
    public void initView(View rootView) {
        btnLogin = rootView.findViewById(R.id.btn_login);
        btnList = rootView.findViewById(R.id.btn_list);
        btnFileUpDown = rootView.findViewById(R.id.btn_fileupdown);
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnFileUpDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else if (v == btnList) {
            mContext.startActivity(new Intent(mContext, ListActivity.class));
        } else if (v == btnFileUpDown) {
            mContext.startActivity(new Intent(mContext, FileUpDownActivity.class));
        }
    }
}