package com.huxin.xinpiao.deviceid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/******************************************
 * author: changfeng (changfeng@51huxin.com)
 * createDate: 2016/10/30
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 文件工具类
 ******************************************/

public class DeviceIdFileUtils {

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     * @param strBuffer
     */
    public static void textToFile(final String strBuffer) {
        try {
            // 创建文件对象
            File fileText = new File(getDeviceIdPath());
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);

            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
    }


    private static String readString3(String filePath) {

        String str = "";
        File file = new File(filePath);
        try {
            FileInputStream in = new FileInputStream(file);
            // size  为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "GB2312");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }

    public static String getDeviceIdFromFile(){
        String file = getDeviceIdPath();
        String s1 = DeviceIdFileUtils.readString3(file);
        return s1;
    }

    private static String getDeviceIdPath(){
//        return HuXinSDCardUtil.getSDCardDataPath() +  "deviceId";
        return "deviceId";
    }
}
