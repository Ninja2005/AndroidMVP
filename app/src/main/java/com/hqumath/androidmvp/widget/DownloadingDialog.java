package com.hqumath.androidmvp.widget;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import com.hqumath.androidmvp.R;

/**
 * 上传下载进度对话框
 */
public class DownloadingDialog extends AppCompatDialog {
    private DownloadingProgressBar mProgressBar;

    public DownloadingDialog(Context context) {
        super(context, R.style.AppDialogTheme);
        setContentView(R.layout.dialog_downloading);
        mProgressBar = findViewById(R.id.pb_downloading_content);
        setCancelable(false);
    }

    public void setProgress(long progress, long maxProgress) {
        mProgressBar.setMax((int) maxProgress);
        mProgressBar.setProgress((int) progress);
    }

    @Override
    public void show() {
        super.show();
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }
}
