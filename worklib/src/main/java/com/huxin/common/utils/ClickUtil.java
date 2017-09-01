package com.huxin.common.utils;


public class ClickUtil {

    private static long lastClickTime;

    /**
     * 防止连点
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
