package com.hqumath.androidmvp.ui.fileupdown;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.databinding.ActivityFileupdownBinding;
import com.hqumath.androidmvp.ui.login.LoginPresenter;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.hqumath.androidmvp.widget.DownloadingDialog;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FileUpDownActivity extends BaseActivity implements FileUpDownPresenter.Contract {

    private ActivityFileupdownBinding binding;
    private FileUpDownPresenter mPresenter;
    private DownloadingDialog mDownloadingDialog;

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityFileupdownBinding.inflate(LayoutInflater.from(this));
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.btnUpload.setOnClickListener(v -> {

        });
        binding.btnDownload.setOnClickListener(v -> {
            String url = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";
            mPresenter.download(url);
        });
    }

    @Override
    protected void initData() {
        mPresenter = new FileUpDownPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        hideProgress();
    }

    @Override
    public void onDownloadSuccess(Object object) {
        String fileName = ((File)object).getName();
        CommonUtil.toast(fileName + "下载成功");
    }

    @Override
    public void onDownloadError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
    }

    @Override
    public void showProgress() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
        }
    }

    @Override
    public void updateProgress(long readLength, long countLength) {
        binding.getRoot().post(() -> {
            if (mDownloadingDialog != null && mDownloadingDialog.isShowing()) {
                mDownloadingDialog.setProgress(readLength, countLength);
            }
        });
    }
}
