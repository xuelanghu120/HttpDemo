package com.huxin.common.base.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/11/3
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public abstract class BaseNoSwipeBackActivity extends AppCompatActivity {

    protected static final int RC_SETTINGS_SCREEN = 125;

    private boolean isFromPushu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        //为了重写状态栏颜色,必须在setContentView后立即调用
        setStatusBar();
        //友盟推送设置
//        PushAgent.getInstance(this).onAppStart();
        init();
        Uri data = getIntent().getData();


    }


    protected abstract void bindView();

    protected abstract void init();

    protected void setStatusBar() {
        setStatusBarType(1);
    }


    public void setStatusBarType(int type) {

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {


        MobclickAgent.onResume(this);//友盟统计
        MobclickAgent.onPageStart(getClass().getSimpleName());
        super.onResume();
    }

    @Override
    protected void onPause() {


        MobclickAgent.onPause(this);//友盟统计
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (isFromPushu) {
//            Intent intent = MainUIMananager.toMianActivity();
//            startActivity(intent);
//        }
    }

    // 从设置页面返回
    protected void retunFromSetting() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            retunFromSetting();
        }
    }

}
