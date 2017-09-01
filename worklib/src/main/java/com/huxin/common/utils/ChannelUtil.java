package com.huxin.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * 渠道工具类
 */
public class ChannelUtil {

    private static final String CHANNEL_KEY = "UMENG_CHANNEL";
    private static final String CHANNEL_DEFAULT = "unknown";
    private volatile static String channel;


    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 获取渠道名
     * @param context 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        if (TextUtils.isEmpty(channel)) {
            if (TextUtils.isEmpty(channel)) {
                channel = getChannelFromAppInfo(context);
                if (TextUtils.isEmpty(channel)) {
                    channel = CHANNEL_DEFAULT;
                }
            }
        }
       return channel;
    }

    public static String getChannelFromAppInfo(Context context){
        String channelName = CHANNEL_DEFAULT;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

}
