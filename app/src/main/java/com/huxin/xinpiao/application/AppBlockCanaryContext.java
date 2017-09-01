package com.huxin.xinpiao.application;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.huxin.common.application.Global;
import com.huxin.xinpiao.BuildConfig;


public class AppBlockCanaryContext extends BlockCanaryContext {

    /**
     * 标示符，可以唯一标示该安装版本号，如版本+渠道名+编译平台
     *
     * @return apk唯一标示符
     */
    @Override
    public String getQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = Global.getContext().getPackageManager()
                    .getPackageInfo(Global.getContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return qualifier;
    }

    @Override
    public boolean isNeedDisplay() {
        return BuildConfig.DEBUG;
    }

    @Override
    public int getConfigBlockThreshold() {
        return 1000;
    }
}
