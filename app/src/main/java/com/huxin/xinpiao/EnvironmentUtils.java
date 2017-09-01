package com.huxin.xinpiao;

import com.huxin.common.utils.SPUtil;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2017/1/5
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class EnvironmentUtils {
    //添加自动，9为正式环境
    public static int getEnvironment() {
            return 9;
    }

    public static void setEnvironment(int environment) {
        SPUtil.putInt("ENVIRONMENT_KEY", environment);
        
        
        
    }
}
