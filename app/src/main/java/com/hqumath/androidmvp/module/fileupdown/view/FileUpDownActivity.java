package com.hqumath.androidmvp.module.fileupdown.view;

import android.Manifest;
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
import com.hqumath.androidmvp.utils.PermissionUtils;
import com.hqumath.androidmvp.widget.DownloadingDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    private DownloadingDialog mDownloadingDialog;

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
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.Group.STORAGE)
                    .onGranted((permissions) -> upload())
                    .onDenied((permissions) -> {
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                            PermissionUtils.showSettingDialog(mContext, permissions);//自定义弹窗 去设置界面
                        }
                    }).start();
        } else if (v == btnDownload) {
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.Group.STORAGE)
                    .onGranted((permissions) -> download())
                    .onDenied((permissions) -> {
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                            PermissionUtils.showSettingDialog(mContext, permissions);//自定义弹窗 去设置界面
                        }
                    }).start();
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
                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe((aLong) -> {
                            //tvMsg.setText("提示:上传中");
                            progressBar.setMax((int) totalBytesCount);
                            progressBar.setProgress((int) currentBytesCount);
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
        String url = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";
        String url1 = "https://static.zifae.com/static-resource/file/arguments.pdf";

        BaseApi baseapi = new BaseApi(new HttpOnNextListener<File>() {

            @Override
            public void onStart() {
                //toast("下载开始");
                showDownloadingDialog();
            }

            public void onComplete() {
                //toast("下载完成");
                dismissDownloadingDialog();
            }

            @Override
            public void onNext(File file) {
                //打开文件
                //UpgradeUtil.installApk(file);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                //toast("下载失败");
                dismissDownloadingDialog();
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                //progressBar.setMax((int) countLength);
                //progressBar.setProgress((int) readLength);

                if (mDownloadingDialog != null && mDownloadingDialog.isShowing()) {
                    mDownloadingDialog.setProgress(readLength, countLength);
                }
            }
        }, mContext) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(FileUpDownService.class).download(url1);
            }
        };
        baseapi.setShowProgress(false);
        RetrofitClient.getInstance().sendHttpDownloadRequest(baseapi, handler);
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
        }
    }
}
