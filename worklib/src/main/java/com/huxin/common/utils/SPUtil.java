package com.huxin.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.huxin.common.application.Global;


public class SPUtil {

     private static String name = "huxin";

    private SPUtil() {
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
     * 保存一个Boolean类型的值！
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, Boolean value) {
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存一个int类型的值！
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存一个float类型的值！
     *
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 保存一个long类型的值！
     *
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 保存一个String类型的值！
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
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

    /**
     * 获取int的value
     *
     * @param key      名字
     * @param defValue 默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getInt(key, defValue);
    }

    /**
     * 获取float的value
     *
     * @param key      名字
     * @param defValue 默认值
     * @return
     */
    public static float getFloat(String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * 获取boolean的value
     *
     * @param key      名字
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 获取long的value
     * @param key      名字
     * @param defValue 默认值
     * @return
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getLong(key, defValue);
    }

    public static void clear(){
        SharedPreferences sharedPreference = getSharedPreference();
        Editor editor = sharedPreference.edit();
        editor.clear();
        editor.apply();
    }

}