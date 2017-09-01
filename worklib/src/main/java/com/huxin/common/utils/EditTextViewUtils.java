package com.huxin.common.utils;

import android.text.InputType;
import android.widget.EditText;

import java.util.regex.Pattern;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/12/29
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class EditTextViewUtils {


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    public static int getHanZiCount(String name){
        int count = 0;
        if (EditTextViewUtils.checkNameChese(name)) {
            return name.length();
        }
        return count;
    }

    /**
     * 设置inputmethod为pwd时候密码显示与否
     * @param editText
     */
    public static void setPwdIsShow(EditText editText){
        int inputType = editText.getInputType();
        if (inputType ==  InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            showPwd(editText);
        }else {
            hidePwd(editText);
        }
    }
    //设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
    public static void showPwd(EditText editText){
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }
    //设置密码不可见
    public static void hidePwd(EditText editText){
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }
}
