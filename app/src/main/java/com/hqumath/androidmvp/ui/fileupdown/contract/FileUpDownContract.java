package com.hqumath.androidmvp.ui.fileupdown.contract;

import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import okhttp3.MultipartBody;

import java.io.File;

/**
 * ****************************************************************
 * 文件名称: FileUpDownContract
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:08
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface FileUpDownContract {
    interface Model {
        void upload(MultipartBody.Part part, HttpOnNextListener listener);
        void download(String url, File file, HttpOnNextListener listener);
    }

    interface View {
        void onSuccess(Object object, int tag);

        void onError(String errorMsg, String code, int tag);

        void showProgressDialog();

        void dismissProgressDialog();

        void updateProgress(long readLength, long countLength);
    }

    interface Presenter {
        void upload(MultipartBody.Part part, int tag);
        void download(String url, File file, int tag);
    }
}
