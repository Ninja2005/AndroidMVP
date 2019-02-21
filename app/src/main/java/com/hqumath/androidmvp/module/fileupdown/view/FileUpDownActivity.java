package com.hqumath.androidmvp.module.fileupdown.view;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseActivity;
import com.hqumath.androidmvp.net.BaseApi;
import com.hqumath.androidmvp.net.HandlerException;
import com.hqumath.androidmvp.net.RetrofitClient;
import com.hqumath.androidmvp.net.listener.HttpOnNextListener;
import com.hqumath.androidmvp.net.service.FileUpDownService;
import com.hqumath.androidmvp.net.upload.ProgressRequestBody;
import com.hqumath.androidmvp.net.upload.UploadProgressListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import java.io.File;

/**
 * ****************************************************************
 * 文件名称: FileUpDownActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/2/20 16:46
 * 文件描述: 文件上传下载
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FileUpDownActivity extends BaseActivity implements View.OnClickListener {

    private Button btnUpload, btnDownload;
    private NumberProgressBar progressBar;

    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public int getLayoutId() {
        return R.layout.activity_fileupdown;
    }

    @Override
    public void initView() {
        btnUpload = findViewById(R.id.btn_upload);
        btnDownload = findViewById(R.id.btn_download);
        progressBar = findViewById(R.id.number_progress_bar);
    }

    @Override
    protected void initListener() {
        btnUpload.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpload) {
            upload();
        } else if (v == btnDownload) {
            download();
        }
    }

    private void upload() {
        File file = new File("/storage/emulated/0/Download/test0.mp4");//11.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("fileUpload", file.getName(),
                new ProgressRequestBody(requestBody, new UploadProgressListener() {
                    @Override
                    public void onProgress(long currentBytesCount, long totalBytesCount) {
                        //Log.d("进度", (float) currentBytesCount / totalBytesCount * 100 + "%");
                        /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {

                            @Override
                            public void accept(Long aLong) {
                                //tvMsg.setText("提示:上传中");
                                progressBar.setMax((int) totalBytesCount);
                                progressBar.setProgress((int) currentBytesCount);
                            }
                        });
                    }
                }));

        BaseApi baseapi = new BaseApi(new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                //mView.onSuccess(o, tag);
                toast("上传成功");
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                //mView.onError(e.getMessage(), e.getCode(), tag);
                toast("上传失败");
            }
        }, mContext) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).uploadFile(part);
            }
        };
        baseapi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpRequest(baseapi);
    }

    private void download() {
        //
        String url = "https://staticuat.zifae.com/apk/android/ZSExchange_V1.1.6_20181212_tencent.apk";
        //String url1 = "https://static.zifae.com/static-resource/file/arguments.pdf";

        BaseApi baseapi = new BaseApi(new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                //mView.onSuccess(o, tag);
                toast("下载成功");
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                //mView.onError(e.getMessage(), e.getCode(), tag);
                toast("下载失败");
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                progressBar.setMax((int) countLength);
                progressBar.setProgress((int) readLength);
            }
        }, mContext) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).download(url);
            }
        };
        baseapi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpDownloadRequest(baseapi, handler);

    }
}
