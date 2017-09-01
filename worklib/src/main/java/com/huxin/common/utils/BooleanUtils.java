package com.huxin.common.utils;

/**
 * boolean判断
 */
public class BooleanUtils {
    /**
     * 有true则true 有一个是true返回true
     * @param bs
     * @return
     */
    public static boolean isBooleanTrue(Boolean... bs) {
        for (Boolean b : bs) {
            if (b) {
                return true;
            }
        }
        return false;
    }
    /**
     * 有false 则false,有一个是false返回true
     * 全部为true才是true
     * @param bs
     * @return
     */
    public static boolean isBooleanFalse(Boolean... bs) {
        for (Boolean b : bs) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全部是turn才是false
     * 有一个是false就是true
     * @param bs
     * @return
     */
    public static boolean isAllFalse(Boolean... bs) {
        for (Boolean b : bs) {
            if (b) {
                return false;
            }
        }
        return true;
    }
}
