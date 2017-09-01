package com.huxin.common.utils;

import com.huxin.common.application.Global;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/12/16
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class ColorUtils {
    public static int getResColor(int resId) {
        return Global.getContext().getResources().getColor(resId);
    }
}
