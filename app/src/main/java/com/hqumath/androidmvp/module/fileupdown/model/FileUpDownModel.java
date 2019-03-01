package com.hqumath.androidmvp.module.fileupdown.model;

import com.hqumath.androidmvp.module.fileupdown.contract.FileUpDownContract;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.service.FileUpDownService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: FileUpDownModel
 * 作    者: Created by gyd
 * 创建时间: 2019/3/1 10:51
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FileUpDownModel implements FileUpDownContract.Model {
    private RxAppCompatActivity activity;

    public FileUpDownModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void upload(MultipartBody.Part part, HttpOnNextListener listener) {
        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).uploadFile(part);
            }
        };
        baseApi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }

    @Override
    public void download(String url, HttpOnNextListener listener) {
        BaseApi baseApi = new BaseApi(listener, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).download(url);
            }
        };
        baseApi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpDownloadRequest(baseApi);
    }
}