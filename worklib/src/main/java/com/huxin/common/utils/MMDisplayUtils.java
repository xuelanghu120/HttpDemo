package com.huxin.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import com.huxin.common.application.Global;

import java.lang.reflect.Field;


public class MMDisplayUtils {

    private static int mScreenWidth;
    private static int mScreenHeight;

    /**
     * 全局的一些变量的初始化
     */
    public static void init(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenHeight = wm.getDefaultDisplay().getHeight();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenWidth() {
        if (0 == mScreenWidth) {
            WindowManager wm = (WindowManager) Global.getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
        }
        return mScreenWidth;
    }

    public static int getScreenHeight() {
        if (0 == mScreenHeight) {
            WindowManager wm = (WindowManager) Global.getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }
        return mScreenHeight;
    }
    public static int contentTop;//状态栏高
    public static int contentTop() {
        if (0 == contentTop) {
            try {
                Class c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                contentTop = Global.getContext().getResources().getDimensionPixelSize(x);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return contentTop;
    }


}

