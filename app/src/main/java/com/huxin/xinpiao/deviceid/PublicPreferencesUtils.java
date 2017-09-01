package com.huxin.xinpiao.deviceid;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxin.common.application.Global;
import com.huxin.common.utils.StringUtils;


/******************************************
 * @author: xuzhu (xuzhu@51huxin.com)
 * @createDate: 2017/4/11
 * @company: (C) Copyright 阳光互信 2016
 * @since: JDK 1.8
 * @Description: 注释写这里
 ******************************************/

public class PublicPreferencesUtils {
    private static String name = "PublicPreferencesUtils";

    private PublicPreferencesUtils() {
    }

    /**
     * 获取SharedPreferences实例对象
     *
     * @param
     * @return
     */
    private static SharedPreferences getSharedPreference() {
        return Global.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    /**
     * 保存一个String类型的值！
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferences sharedPreference = getSharedPreference();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取String的value
     *
     * @param key      名字
     * @param defValue 默认值
     * @return
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getString(key, defValue);
    }


    //1
    public static void saveUniImei(String uniImei) {
        putString("uniImei",uniImei);
        DeviceIdFileUtils.textToFile(uniImei);
    }

    public static String getUniImei(){
        String uniImei = getString("uniImei", "");
        if (StringUtils.isEmpty(uniImei)){
            uniImei = DeviceIdFileUtils.getDeviceIdFromFile();
        }
        return uniImei;
    }



}
