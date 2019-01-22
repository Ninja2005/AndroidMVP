package com.hqumath.androidmvp.module.login.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.bean.DemoEntity;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.login.presenter.LoginPresenter;

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
                mPresenter.login(mEdtUserCode.getText().toString().trim(),
                        mEdtPwd.getText().toString().trim());
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onSuccess(Object object) {
        DemoEntity o = (DemoEntity)object;
    }

    @Override
    public void onError(String msg) {

    }


}
