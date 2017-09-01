package com.huxin.common.utils;

import com.huxin.common.application.Global;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2017/2/23
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class ResUtils {
    public static String getString(int resId){
        return Global.getContext().getResources().getString(resId);
    }

    public static String[] getStringArray(int resId){
        return Global.getContext().getResources().getStringArray(resId);
    }
}
