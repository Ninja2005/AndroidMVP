package com.hqumath.androidmvp.module.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.LoginResponse;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.login.presenter.LoginPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: LoginActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 登录界面
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    private static final int LOGIN_TAG = 1;//登录 ZS0100003

    private EditText mEdtUserCode, mEdtPwd;
    private Button mBtnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mEdtUserCode = findViewById(R.id.edt_name);
        mEdtPwd = findViewById(R.id.edt_pwd);
        mBtnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("appKey", "mobile");
                maps.put("loginAccount", mEdtUserCode.getText().toString().trim());
                maps.put("userPsw", mEdtPwd.getText().toString().trim());
                mPresenter.login(maps, LOGIN_TAG);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == LOGIN_TAG) {
            String name = ((LoginResponse) object).getName();
            toast(name + "已登录");
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
    }

}
