package com.huxin.common.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/******************************************
 * author: changfeng (changfeng@51huxin.com)
 * createDate: 2016/11/17
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 数字转换工具类
 ******************************************/

public class NumberConvertUtils {
    /**
     * 把数字转换为千分位的格式，如：1234567--1,234,567
     * @param num
     * @return
     */
    public static String num2Thousands(double num){
        NumberFormat format = NumberFormat.getInstance();
        return format.format(num);
    }

    /**
     * 把千分位格式的数字转为正常格式，如：123,456--123456(不支持小数)
     * @param thousandsNum
     * @return
     */
    public static Number thousands2Num(String thousandsNum) throws ParseException {
        return new DecimalFormat().parse(thousandsNum);
    }

    /**
     * 带千分位，保留两位小数
     * @param num
     * @return
     */
    public static String num2Thousand(float num) {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(num);
    }
    /**
     * 带千分位，保留两位小数
     * @param num
     * @return
     */
    public static String num2Thousand(double num) {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(num);
    }

    public static String num2ThousandNoComma(float num) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(num);
    }

    // "12345678" - "123,456.78"
    public static String setRepaymentDetailsFouuns(String founds) {
        if (TextUtils.isEmpty(founds)){
            return "";
        }
        String s = ArithmeticUtil.fen2Yuan(founds);
        String s1 = NumberConvertUtils.num2Thousand(Double.parseDouble(s));
//        String format = String.format(ResUtils.getString(R.stringReq.total_should_repay), s1);
        return  s1;
    }

    // 12345678   -   "123.456,78"
    public static String setRepaymentDetailsFouuns(int founds) {
        String s = ArithmeticUtil.fen2Yuan(founds);
        String s1 = NumberConvertUtils.num2Thousand(Double.parseDouble(s));
//        String format = String.format(ResUtils.getString(R.stringReq.total_should_repay), s1);
        return  s1;
    }
}
