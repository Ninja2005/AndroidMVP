package com.hqumath.androidmvp.ui.main;

import androidx.annotation.NonNull;

import com.hqumath.androidmvp.base.BasePresenter;
import com.hqumath.androidmvp.bean.UserInfoEntity;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.RetrofitClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 业务逻辑层
 */
public class MainPresenter extends BasePresenter<MainContract> {

    public void login(String userName, String passWord) {
        if (mView == null) return;
        RetrofitClient.getInstance().getApiService().getUserInfo(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserInfoEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //compositeDisposable.add(d); TODO
                        if (mView == null) return;
                        mView.showProgress();
                    }

                    @Override
                    public void onNext(@NonNull UserInfoEntity userInfoEntity) {
                        if (mView == null) return;
                        mView.hideProgress();
                        mView.onLoginSuccess(userInfoEntity);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (mView == null) return;
                        HandlerException.ResponseThrowable throwable = HandlerException.handleException(e);
                        mView.hideProgress();
                        mView.onLoginError(throwable.getMessage(), throwable.getCode());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
