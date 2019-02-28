package com.hqumath.androidmvp.net.subscribers;


import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.download.DownloadProgressListener;

/**
 * 断点下载处理类Subscriber
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressDownSubscriber<T> extends ProgressSubscriber<T> implements DownloadProgressListener {

    /**
     * 构造
     *
     * @param api
     */
    public ProgressDownSubscriber(BaseApi api) {
        super(api);
    }

    @Override
    public void update(long read, long count, boolean done) {
        mSubscriberOnNextListener.get().updateProgress(read, count);
    }
}