package com.huxin.xinpiao.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.moduth.blockcanary.BlockCanary;
import com.huxin.common.application.BaseApplication;
import com.huxin.common.utils.Logger;
import com.huxin.common.utils.MMLogger;
import com.huxin.xinpiao.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by 56417 on 2016/9/14.
 */

public class HXApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initUserInfo();
        initPush();
        initEnvir();
        initSDPath();

    }



    //初始化文件路径
    private void initSDPath() {
//        Flowable.just("")
//                .observeOn(MMSchedulers.workThread())
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        HuXinSDCardUtil.createCacheFile();
//                    }
//                })
//                .subscribe(new MySubscriber());

    }

    private void initPush() {
    }

    private void initUserInfo() {
//        Flowable.just("")
//                .observeOn(MMSchedulers.workThread())
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        LoginDataCenterManager.getInstance().initUserInfo();
//                    }
//                })
//                .subscribe();
    }

    public RefWatcher mRefWatcher;

    /**
     * 初始化debug和release环境
     */
    private void initEnvir() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (BuildConfig.DEBUG) {
            //内存泄漏分析
            mRefWatcher = LeakCanary.install(HXApplication.this);
            //函数调用时间分析
            BlockCanary.install(HXApplication.this, new AppBlockCanaryContext()).start();
        }
        //debug情况下日志
        MMLogger.init("HttpWork", BuildConfig.DEBUG);
        Logger.setIsDebug(BuildConfig.DEBUG);
    }

    public static RefWatcher getRefWatcher(Context context) {
        HXApplication application = (HXApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
