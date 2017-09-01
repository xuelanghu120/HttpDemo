package com.huxin.common.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {
    private static Stack<Activity> activityStack = new Stack<Activity>();

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    public static void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }


    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        Iterator<Activity> eIterator = activityStack.iterator();
        while (eIterator.hasNext()) {
            Activity tempActivity = eIterator.next();
            if (!tempActivity.isFinishing()) {
                tempActivity.finish();
            }
            eIterator.remove();
        }
    }

}