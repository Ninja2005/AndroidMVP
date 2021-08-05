package com.hqumath.androidmvp.ui.login;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.databinding.ActivityLoginBinding;

/**
 * ****************************************************************
 * 文件名称: LoginActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 处理用户的交互和输入事件
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private ActivityLoginBinding binding;
    private LoginPresenter mPresenter;

    @Override
    public View initContentView() {
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
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
        mPresenter = new LoginPresenter(mContext);
        mPresenter.attachView(this);

        binding.etName.setText("JakeWharton");
        binding.etPwd.setText("1234");
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
        boolean valid = true;//防止快速点击
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
        binding.progressBar.show();
    }

    @Override
    public void hideProgress() {
        binding.progressBar.hide();
    }

    @Override
    public void onLoginSuccess(Object object) {
        UserInfoEntity user = (UserInfoEntity) object;
        toast(user.getName() + "已登录");
        binding.btnLogin.setEnabled(true);
    }

    @Override
    public void onLoginError(String errorMsg, String code) {
        toast(errorMsg);
        binding.btnLogin.setEnabled(true);
    }
}
