package com.hqumath.androidmvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.app.Constant;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.databinding.ActivityLoginBinding;
import com.hqumath.androidmvp.ui.main.MainActivity;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.hqumath.androidmvp.utils.SPUtil;

/**
 * ****************************************************************
 * 文件名称: LoginActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginActivity extends BaseActivity implements LoginContract {

    private ActivityLoginBinding binding;
    private LoginPresenter mPresenter;

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.etPwd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                loginRequest();
                return true;
            }
            return false;
        });
        binding.btnLogin.setOnClickListener(v -> {
            loginRequest();
        });
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        String userName = SPUtil.getInstance().getString(Constant.USER_NAME);
        if(!TextUtils.isEmpty(userName)) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            //测试数据
            binding.etName.setText("JakeWharton");
            binding.etPwd.setText("1234");
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    /**
     * 登录请求
     */
    private void loginRequest() {
        String name = binding.etName.getText().toString().trim();
        String pwd = binding.etPwd.getText().toString().trim();
        boolean valid = true;
        if (TextUtils.isEmpty(name)) {
            valid = false;
            binding.llName.setError(getString(R.string.user_name_warning));
        } else {
            binding.llName.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(pwd)) {
            valid = false;
            binding.llPwd.setError(getString(R.string.password_warning));
        } else {
            binding.llPwd.setErrorEnabled(false);
        }
        if (valid) {
            binding.btnLogin.setEnabled(false);
            mPresenter.login(name, pwd);
        }
    }

    @Override
    public void showProgress() {
        showProgressDialog("loading");
    }

    @Override
    public void hideProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onLoginSuccess(Object object) {
        UserInfoEntity user = (UserInfoEntity) object;
        CommonUtil.toast(user.getName() + "已登录");
        //binding.btnLogin.setEnabled(true);
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
        binding.btnLogin.setEnabled(true);
    }
}
