package com.hqumath.androidmvp.module.fileupdown.presenter;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.module.fileupdown.contract.FileUpDownContract;
import com.hqumath.androidmvp.module.fileupdown.model.FileUpDownModel;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.module.login.model.LoginModel;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.Map;

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

    private FileUpDownModel model;

    public FileUpDownPresenter(RxAppCompatActivity activity) {
        model = new FileUpDownModel(activity);
    }

    /*@Override
    public void login(Map<String, Object> maps, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.login(maps, new HttpOnNextListener() {

            @Override
            public void onNext(Object o) {
                mView.onSuccess(o, tag);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                mView.onError(e.getMessage(), e.getCode(), tag);
            }
        });
    }*/

    @Override
    public void upload(MultipartBody.Part part, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.upload(part, new HttpOnNextListener() {

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
        });
    }

    @Override
    public void download(String url, File file, int tag) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.download(url, file, new HttpOnNextListener<File>() {

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
        });
    }
}
