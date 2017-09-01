package com.huxin.xinpiao.application;

import android.app.Application;

import com.huxin.common.application.Global;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2017/3/13
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class RefWatcherDateCenterManager {

    private volatile static RefWatcherDateCenterManager instance;

    public static RefWatcherDateCenterManager getInstance() {
        if (instance == null) {
            synchronized (RefWatcherDateCenterManager.class) {
                if (instance == null) {
                    instance = new RefWatcherDateCenterManager();
                }
            }
        }
        return instance;
    }

    public RefWatcher mRefWatcher;
    public RefWatcherDateCenterManager() {
        mRefWatcher = LeakCanary.install(((Application) Global.getContext()));
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}
