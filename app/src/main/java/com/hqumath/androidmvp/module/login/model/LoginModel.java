package com.hqumath.androidmvp.module.login.model;

import com.hqumath.androidmvp.bean.BaseResponse;
import com.hqumath.androidmvp.bean.DemoEntity;
import com.hqumath.androidmvp.bean.ResponseThrowable;
import com.hqumath.androidmvp.module.login.contract.LoginContract;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.service.DemoApiService;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * ****************************************************************
 * 文件名称: LoginModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public void login(String userCode, String passWord) {
        /*RetrofitClient.getInstance().create(DemoApiService.class)
                .demoGet()
//                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
//                .compose(RxUtils.schedulersTransformer()) //线程调度
//                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<DemoEntity>>() {
                    @Override
                    public void accept(BaseResponse<DemoEntity> response) throws Exception {
                        itemIndex = 0;
                        //清除列表
                        observableList.clear();
                        //请求成功
                        if (response.getCode() == 1) {
                            //将实体赋给LiveData
                            for (DemoEntity.ItemsEntity entity : response.getResult().getItems()) {
                                NetWorkItemViewModel itemViewModel = new NetWorkItemViewModel(NetWorkViewModel.this, entity);
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                    }
                });*/
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void getCache() {

    }

    @Override
    public void getAppConfig() {

    }
}
