package com.hqumath.androidmvp.ui.fileupdown;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.databinding.ActivityFileupdownBinding;
import com.hqumath.androidmvp.utils.CommonUtil;
import com.hqumath.androidmvp.utils.FileUtil;
import com.hqumath.androidmvp.widget.DownloadingDialog;

import java.io.File;

public class FileUpDownActivity extends BaseActivity implements FileUpDownPresenter.Contract {

    private ActivityFileupdownBinding binding;
    private FileUpDownPresenter mPresenter;
    private DownloadingDialog mDownloadingDialog;

    public final static String url = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityFileupdownBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.btnDownload.setOnClickListener(v -> {
            mPresenter.download(url);
        });
        binding.btnUpload.setOnClickListener(v -> {
            File file = FileUtil.getFileFromUrl(url);
            mPresenter.upload("testFile", file);
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
        String fileName = ((File) object).getName();
        CommonUtil.toast(fileName + "Download success.");
    }

    @Override
    public void onDownloadError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
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
    public void onUploadSuccess(Object object) {
        CommonUtil.toast("Upload success.");
    }

    @Override
    public void onUploadError(String errorMsg, String code) {
        CommonUtil.toast(errorMsg);
    }

    @Override
    public void showDownloadProgress() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.show();
    }

    @Override
    public void hideDownloadProgress() {
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
