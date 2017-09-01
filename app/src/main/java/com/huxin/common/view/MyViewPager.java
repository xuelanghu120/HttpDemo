package com.huxin.common.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/10/25
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: viewpager不预加载
 ******************************************/

public class MyViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll){
            return super.onTouchEvent(ev);
        }else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll){
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }

    }
}
