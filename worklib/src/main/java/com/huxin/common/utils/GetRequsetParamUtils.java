package com.huxin.common.utils;

import java.util.HashMap;
import java.util.Map;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/11/10
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 获取get的请求参数
 ******************************************/

public class GetRequsetParamUtils {

    public static Map<String,String> getGetParam(String getString){
        Map<String,String> map = new HashMap<>();

        int index = getString.indexOf("?");
        if (index<=0){
            return null;
        }
        String params =  getString.substring(index+1);
        String[] splitParams = params.split("&");
        for (String paramItem : splitParams){
            String[] keyValue = paramItem.split("=");
            map.put(keyValue[0],keyValue[1]);
        }
        return map;
    }
}
