package com.huxin.xinpiao.deviceid;

import android.util.Base64;

import java.util.UUID;

/******************************************
 * @author: xuzhu (xuzhu@51huxin.com)
 * @createDate: 2017/4/11
 * @company: (C) Copyright 阳光互信 2016
 * @since: JDK 1.8
 * @Description: 注释写这里
 ******************************************/

public class UUIDUtils {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        String deviceId = Base64.encodeToString(uuid.getBytes(), Base64.DEFAULT);
        return deviceId;
    }
}
