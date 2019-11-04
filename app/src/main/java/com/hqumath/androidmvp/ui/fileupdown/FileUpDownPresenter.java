package com.hqumath.androidmvp.ui.fileupdown;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.service.FileUpDownService;
import com.hqumath.androidmvp.ui.fileupdown.FileUpDownContract;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: FileUpDownPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FileUpDownPresenter extends BasePresenter<FileUpDownContract.View> implements FileUpDownContract.Presenter {

    private RxAppCompatActivity activity;

    public FileUpDownPresenter(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void upload(MultipartBody.Part part, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        BaseApi baseApi = new BaseApi(new HttpOnNextListener() {

            @Override
            public void onStart() {
                mView.showProgressDialog();
            }

            public void onComplete() {
                mView.dismissProgressDialog();
            }

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);//上传成功
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.dismissProgressDialog();
                mView.onError(e.getMessage(), e.getCode(), tag);//上传失败
            }
        }, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).uploadFile(part);
            }
        };
        baseApi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpRequest(baseApi);
    }

    @Override
    public void download(String url, File file, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        BaseApi baseApi = new BaseApi(new HttpOnNextListener<File>() {

            @Override
            public void onStart() {
                mView.showProgressDialog();
            }

            public void onComplete() {
                mView.dismissProgressDialog();
            }

            @Override
            public void onNext(File file) {
                mView.onSuccess(file, tag);//下载成功
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.dismissProgressDialog();
                mView.onError(e.getMessage(), e.getCode(), tag);//上传失败
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                mView.updateProgress(readLength, countLength);
            }
        }, activity) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).download(url);
            }
        };
        baseApi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpDownloadRequest(baseApi, file);
    }
}
