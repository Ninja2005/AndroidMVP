package com.hqumath.androidmvp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * ****************************************************************
 * 文件名称: NoScrollViewPager
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 15:44
 * 文件描述: 禁止左右滑动的ViewPager
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
