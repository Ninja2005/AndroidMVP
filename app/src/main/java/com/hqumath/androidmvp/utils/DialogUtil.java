package com.hqumath.androidmvp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hqumath.androidmvp.R;

/**
 * ****************************************************************
 * 文件名称: AlterDialogTool
 * 作    者: Created by gyd
 * 创建时间: 2018/10/28 23:01
 * 文件描述: 通用弹窗界面
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */

public class DialogUtil extends Dialog {
    private Activity activity;
    private TextView messageView;//内容
    private TextView messageView2;//第二条内容
    private TextView oneBtnView;        //只有一个确认键的情况
    private View twoBtnView;            //确认和取消的情况
    private TextView confirmBtn;
    private TextView cancelBtn;
    private final View mView;

    //ex.
        /*DialogUtil alterDialogUtils = new DialogUtil(mContext);
        alterDialogUtils.setOneOrTwoBtn(true);
        alterDialogUtils.setTitle("提示");
        alterDialogUtils.setMessage("是否确认退出驾驶？");
        alterDialogUtils.setTwoConfirmBtn("确定", v -> alterDialogUtils.dismiss());
        alterDialogUtils.setTwoCancelBtn("取消", v -> alterDialogUtils.dismiss());
        alterDialogUtils.show();*/

    public DialogUtil(Activity context, int theme, int messageLayout) {
        super(context, theme);
        this.activity = context;
        mView = LayoutInflater.from(getContext()).inflate(messageLayout, null);
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        messageView = (TextView) mView.findViewById(R.id.message);
        messageView2 = (TextView) mView.findViewById(R.id.message2);
        oneBtnView = (TextView) mView.findViewById(R.id.only_confirm_btn);
        twoBtnView = mView.findViewById(R.id.two_btn_layout);
        confirmBtn = (TextView) mView.findViewById(R.id.yes);
        cancelBtn = (TextView) mView.findViewById(R.id.no);
        setContentView(mView);
    }

    public DialogUtil(Activity context, int messageLayout) {
        this(context, R.style.dialog_common, messageLayout);
    }

    public DialogUtil(Activity context) {
        this(context, R.style.dialog_common, R.layout.dialog_common);
        //对话框宽度
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        setAlertDialogWidth(dm.widthPixels * 8 / 10);
    }

    public void setTitle(String title) {
        TextView mTitle = (TextView) mView.findViewById(R.id.title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    public <T extends View> T getView(int viewId, Class<T> clazz) {
        return (T) mView.findViewById(viewId);
    }

    public void setAlertDialogSize(int width, int height) {
        getWindow().setLayout(width, height);
    }

    /**
     * @param width 对话框的宽度
     */
    public void setAlertDialogWidth(int width) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        getWindow().setLayout(width, attrs.height);
    }

    /**
     * 设置按键类型
     *
     * @param one true 只有一个确认按键 ； false 显示 确认 和取消 按键
     */
    public void setOneOrTwoBtn(boolean one) {
        if (one) {
            if (oneBtnView != null)
                oneBtnView.setVisibility(View.VISIBLE);
            if (twoBtnView != null)
                twoBtnView.setVisibility(View.INVISIBLE);
        } else {
            if (oneBtnView != null)
                oneBtnView.setVisibility(View.INVISIBLE);
            if (twoBtnView != null)
                twoBtnView.setVisibility(View.VISIBLE);
        }
    }

    public void setMessage(int resid) {
        messageView.setText(resid);
    }

    public void setMessage(String message) {
        messageView.setText(message);
    }

    public void setMessageGravity(int gravity) {
        messageView.setGravity(gravity);
    }

    public void setMessage2(String message) {
        messageView2.setVisibility(View.VISIBLE);
        messageView2.setText(message);
    }

    public void setOneConfirmBtn(int resid, View.OnClickListener listener) {
        setOneOrTwoBtn(true);
        if (resid > 0) {
            oneBtnView.setText(resid);
        }
        oneBtnView.setOnClickListener(listener);
    }

    public void setOneConfirmBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(true);
        if (text != null) {
            oneBtnView.setText(text);
        }
        oneBtnView.setOnClickListener(listener);
    }

    public void setTwoConfirmBtn(int resid, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (resid > 0) {
            confirmBtn.setText(resid);
        }
        confirmBtn.setOnClickListener(listener);
    }

    public void setTwoConfirmBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (text != null) {
            confirmBtn.setText(text);
        }
        confirmBtn.setOnClickListener(listener);
    }

    public void setTwoCancelBtn(int resid, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (resid > 0) {
            cancelBtn.setText(resid);
        }
        cancelBtn.setOnClickListener(listener);
    }

    public void setTwoCancelBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (text != null) {
            cancelBtn.setText(text);
        }
        cancelBtn.setOnClickListener(listener);
    }
}
