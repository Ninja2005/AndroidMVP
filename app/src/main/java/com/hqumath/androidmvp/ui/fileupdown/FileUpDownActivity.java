package com.hqumath.androidmvp.ui.fileupdown;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.hqumath.androidmvp.R;
import com.hqumath.androidmvp.base.BaseMvpActivity;
import com.hqumath.androidmvp.ui.fileupdown.FileUpDownContract;
import com.hqumath.androidmvp.ui.fileupdown.FileUpDownPresenter;
import com.hqumath.androidmvp.net.upload.ProgressRequestBody;
import com.hqumath.androidmvp.utils.FileUtils;
import com.hqumath.androidmvp.utils.PermissionUtils;
import com.hqumath.androidmvp.widget.DownloadingDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * ****************************************************************
 * 文件名称: FileUpDownActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/2/20 16:46
 * 文件描述: 文件上传下载
 * 注意事项: 支持app安装，优化权限管理
 * 版权声明:
 * ****************************************************************
 */
public class FileUpDownActivity extends BaseMvpActivity<FileUpDownPresenter> implements FileUpDownContract.View,
        View.OnClickListener {
    private static final int UPLOAD_TAG = 1;//上传
    private static final int DOWNLOAD_TAG = 2;//下载

    private Button btnUpload, btnDownload;

    private DownloadingDialog mDownloadingDialog;

    @Override
    public int initContentView() {
        return R.layout.activity_fileupdown;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btnUpload = findViewById(R.id.btn_upload);
        btnDownload = findViewById(R.id.btn_download);
    }

    @Override
    protected void initListener() {
        btnUpload.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new FileUpDownPresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpload) {
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.READ_EXTERNAL_STORAGE)
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
        File file = new File("/storage/emulated/0/Download/11.jpg");//test0.mp4");//
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("fileUpload", file.getName(),
                new ProgressRequestBody(requestBody, this::updateProgress));
        mPresenter.upload(part, UPLOAD_TAG);
    }

    private void download() {
        String url = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";
//        String url = "https://static.zifae.com/static-resource/file/arguments.pdf";

        File file = FileUtils.getFileFromUrl(url);
//        if (file.exists()) {
//            installPackage(file);
//        } else {
            mPresenter.download(url, file, DOWNLOAD_TAG);
//        }
    }

    @Override
    public void onSuccess(Object object, int tag) {
        if (tag == UPLOAD_TAG) {
            toast("上传成功");
        } else if (tag == DOWNLOAD_TAG) {
            toast("下载成功");
            installPackage((File) object);
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
    }

    @Override
    public void showProgressDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
        }
    }

    @Override
    public void updateProgress(long readLength, long countLength) {
        //回到主线程
        Observable.just(readLength).observeOn(AndroidSchedulers.mainThread()).subscribe((current) -> {
            if (mDownloadingDialog != null && mDownloadingDialog.isShowing()) {
                mDownloadingDialog.setProgress(readLength, countLength);
            }
        });
    }

    /**
     * 安装app
     *
     * @param file
     */
    private void installPackage(File file) {
        AndPermission.with(mContext)
                .install()
                .file(file)
                .rationale(PermissionUtils::showInstallDialog)//授权安装app弹窗
                .onGranted(null)
                .onDenied(null).start();
    }
}