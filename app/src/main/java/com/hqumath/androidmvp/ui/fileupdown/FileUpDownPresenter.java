package com.hqumath.androidmvp.ui.fileupdown;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.HttpListener;
import com.hqumath.androidmvp.net.download.DownloadListener;
import com.hqumath.androidmvp.repository.MyModel;
import com.hqumath.androidmvp.utils.FileUtil;

import java.io.File;

public class FileUpDownPresenter extends BasePresenter<FileUpDownPresenter.Contract> {

    public interface Contract {
        void showDownloadProgress();

        void hideDownloadProgress();

        void updateProgress(long readLength, long countLength);

        void onDownloadSuccess(Object object);

        void onDownloadError(String errorMsg, String code);

        void showProgress();

        void hideProgress();

        void onUploadSuccess(Object object);

        void onUploadError(String errorMsg, String code);
    }

    public FileUpDownPresenter() {
        mModel = new MyModel();
    }

    public void download(String url) {
        if (mView == null) return;
        mView.showDownloadProgress();
        File file = FileUtil.getFileFromUrl(url);
        ((MyModel) mModel).download(url, file, new DownloadListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.hideDownloadProgress();
                mView.onDownloadSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.hideDownloadProgress();
                mView.onDownloadError(errorMsg, code);
            }

            @Override
            public void update(long read, long count) {
                if (mView == null) return;
                mView.updateProgress(read,count);
            }
        });
    }

    public void upload(String key, File file) {
        if (mView == null) return;
        mView.showProgress();
        ((MyModel) mModel).upload(key, file, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onUploadSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onUploadError(errorMsg, code);
            }
        });
    }
}


