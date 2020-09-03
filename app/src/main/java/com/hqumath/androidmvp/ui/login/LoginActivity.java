package com.hqumath.androidmvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.ui.main.MainActivity;

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
    private static final int LOGIN_TAG = 1;//登录

    private EditText etName, etPwd;
    private TextInputLayout llName, llPwd;
    private Button btnLogin;

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        llName = findViewById(R.id.ll_name);
        llPwd = findViewById(R.id.ll_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initListener() {
        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginRequest();
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(v -> {
            loginRequest();
        });
    }

    private void loginRequest() {
        String name = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        boolean valid = true;//防止快速点击
        if (TextUtils.isEmpty(name)) {
            valid = false;
            llName.setError(getString(R.string.user_name_warning));
        } else {
            llName.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(pwd)) {
            valid = false;
            llPwd.setError(getString(R.string.password_warning));
        } else {
            llPwd.setErrorEnabled(false);
        }
        if (valid) {
            btnLogin.setEnabled(false);
            mPresenter.login("JakeWharton", LOGIN_TAG, true);
        }
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == LOGIN_TAG) {
            UserInfoEntity user = (UserInfoEntity) object;
            toast(user.getName() + "已登录");

            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        if (tag == LOGIN_TAG) {
            btnLogin.setEnabled(true);
        }
    }

}
