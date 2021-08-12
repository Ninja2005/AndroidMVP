package com.hqumath.androidmvp.ui.fileupdown;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.download.DownloadListener;
import com.hqumath.androidmvp.repository.MyModel;
import com.hqumath.androidmvp.utils.FileUtil;

import java.io.File;

public class FileUpDownPresenter extends BasePresenter<FileUpDownPresenter.Contract> {

    public interface Contract {
        void onDownloadSuccess(Object object);

        void onDownloadError(String errorMsg, String code);

        void showProgress();

        void hideProgress();

        void updateProgress(long readLength, long countLength);
    }

    public FileUpDownPresenter() {
        mModel = new MyModel();
    }

    public void download(String url) {
        if (mView == null) return;
        mView.showProgress();
        File file = FileUtil.getFileFromUrl(url);
        ((MyModel) mModel).download(url, file, new DownloadListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onDownloadSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.hideProgress();
                mView.onDownloadError(errorMsg, code);
            }

            @Override
            public void update(long read, long count) {
                if (mView == null) return;
                mView.updateProgress(read,count);
            }
        });
    }
}


