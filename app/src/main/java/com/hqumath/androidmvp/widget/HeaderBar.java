package com.hqumath.androidmvp.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqumath.androidmvp.R;

/**
 * ****************************************************************
 * 文件名称: HeaderBar
 * 作    者: Created by gyd
 * 创建时间: 2020/9/23 17:21
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class HeaderBar extends RelativeLayout {
    private ImageView iv_left, iv_right;
    private TextView tv_center;

    public HeaderBar(Context context) {
        this(context, null);
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        tv_center = findViewById(R.id.tv_center);

        iv_left.setOnClickListener(v -> {
            if (context instanceof Activity)
                ((Activity) context).finish();
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        /*TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        //标题
        String centerText = typedArray.getString(R.styleable.HeaderBar_center_text);
        if (!TextUtils.isEmpty(centerText))
            tv_center.setText(centerText);
        //文字颜色
        int textColor = typedArray.getColor(R.styleable.HeaderBar_text_color, Color.BLACK);
        tv_center.setTextColor(textColor);
        //可见
        int showViewInteger = typedArray.getInt(R.styleable.HeaderBar_show_views, 0b110);
        String showViewString = Integer.toBinaryString(showViewInteger);
        String showView = String.format("%06d", showViewString);


        typedArray.recycle();*/
    }

    public void setTitle(String title) {
        tv_center.setText(title);
    }

    public void setLeftListener(OnClickListener onClickListener) {
        iv_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        iv_right.setOnClickListener(onClickListener);
    }
}
