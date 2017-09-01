package com.huxin.common.utils;


import android.util.Base64;

public class Base64Utils {
    /**
     * 解密
     *
     * @param baseString
     * @return
     */
    public static String base64Decrypt(String baseString) {
        // 对base64加密后的数据进行解密
        return new String(Base64.decode(baseString.getBytes(), Base64.URL_SAFE));
    }

    /**
     * 这里 encodeToString 则直接将返回String类型的加密数据
     * 跳转加密
     * @param string
     * @return
     */
    public static String base64Encode(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.URL_SAFE);
    }

    /**
     * 正常请求用默认方式加密
     * @param string
     * @return
     */
    public static String base64EncodeJson(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
    }

}