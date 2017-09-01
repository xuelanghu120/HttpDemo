package com.huxin.common.utils;

import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.huxin.common.application.Global;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2017/2/16
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class ImageLocationUtils {

    private static final String mNewLine = System.getProperty("line.separator");

//    public static String getInfoFromDatabase(Cursor cursor) {
//        String info = "";
//        if ((null == cursor) || !cursor.moveToFirst()) {
//            return info;
//        }
//        info += "-- DB ----" + mNewLine;
//        info += "bucket display name: " + cursor.getString(0) + mNewLine;
//        info += "bucket ID: " + cursor.getString(1) + mNewLine;
//        info += "date taken: " + formatDate(cursor.getLong(2)) + mNewLine;
//        info += "description: " + cursor.getString(3) + mNewLine;
//        info += "is private: " + cursor.getInt(4) + mNewLine;
//        info += "latitude: " + cursor.getDouble(5) + mNewLine;
//        info += "longitude: " + cursor.getDouble(6) + mNewLine;
//        info += "mini thumb magic: " + cursor.getInt(7) + mNewLine;
//        info += "orientation: " + cursor.getInt(8) + mNewLine;
//        info += "picasa ID: " + cursor.getString(9) + mNewLine;
//
//        info += "file path: " + cursor.getString(10) + mNewLine;
//        info += "data added: " + formatDate(cursor.getLong(11)) + mNewLine;
//        info += "data modified: " + formatDate(cursor.getLong(12)) + mNewLine;
//        info += "display name: " + cursor.getString(13) + mNewLine;
//        info += "mime_type: " + cursor.getString(14) + mNewLine;
//        info += "size: " + cursor.getInt(15) / 1024 + "KB" + mNewLine;
//        info += "title: " + cursor.getString(16) + mNewLine;
//
//        info += "id: " + cursor.getLong(17) + mNewLine;
//        // info += "row count: " + cursor.getString(18) + mNewLine;
//        info += mNewLine;
//        return info;
//    }

    public static String getInfoFromExif(String filepath) {
        String info = "";
        ExifInterface exif;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            return info;
        }

        info += "-- exif ----" + mNewLine;
        // 2.1
        info += "date time: " + exif.getAttribute(ExifInterface.TAG_DATETIME) + mNewLine;
        info += "flash: " + exif.getAttribute(ExifInterface.TAG_FLASH) + mNewLine;
        info += "GPS latitude: " + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) + mNewLine;
        info += "GPS latitude ref: " + exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
                + mNewLine;
        info += "GPS longitude: " + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) + mNewLine;
        info += "GPS longitude ref: " + exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)
                + mNewLine;
        info += "image length: " + exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + mNewLine;
        info += "image width: " + exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH) + mNewLine;
        info += "make: " + exif.getAttribute(ExifInterface.TAG_MAKE) + mNewLine;
        info += "model: " + exif.getAttribute(ExifInterface.TAG_MODEL) + mNewLine;
        info += "orientation: " + exif.getAttribute(ExifInterface.TAG_ORIENTATION) + mNewLine;
        info += "white balance: " + exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE) + mNewLine;
        switch (Build.VERSION.SDK_INT) {
            case Build.VERSION_CODES.HONEYCOMB: // 3.0
                info += "aperture: " + exif.getAttribute(ExifInterface.TAG_APERTURE) + mNewLine;
                info += "exposure time: " + exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)
                        + mNewLine;
                info += "ISO: " + exif.getAttribute(ExifInterface.TAG_ISO) + mNewLine;
            case Build.VERSION_CODES.GINGERBREAD: // 2.3.1
                info += "GPS altitude: " + exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE)
                        + mNewLine;
                info += "GPS altitude ref: "
                        + exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF) + mNewLine;
            case Build.VERSION_CODES.FROYO: // 2.2
                info += "focal length: " + exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)
                        + mNewLine;
                info += "GPS date stamp: " + exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)
                        + mNewLine;
                info += "GPS proccessing method: "
                        + exif.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD) + mNewLine;
                info += "GPS time stamp: " + exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)
                        + mNewLine;
        }
        return info;
    }

    private static String formatDate(long dateMillis) {
        int format = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
        return DateUtils.formatDateTime(Global.getContext(), dateMillis, format);
    }

    /**
     * 将经纬度信息写入JPEG图片文件里
     *
     * @param picPath JPEG图片文件路径
     * @param dLat    纬度
     * @param dLon    经度
     */
    public void writeLatLonIntoJpeg(String picPath, double dLat, double dLon) {
        File file = new File(picPath);
        if (file.exists()) {
            try {
                ExifInterface exif = new ExifInterface(picPath);
                String tagLat = exif
                        .getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String tagLon = exif
                        .getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                if (tagLat == null && tagLon == null) // 无经纬度信息
                {
                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
                            decimalToDMS(dLat));
                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF,
                            dLat > 0 ? "N" : "S"); // 区分南北半球
                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
                            decimalToDMS(dLon));
                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF,
                            dLon > 0 ? "E" : "W"); // 区分东经西经

                    exif.saveAttributes();
                }
            } catch (Exception e) {

            }
        }
    }

    public String decimalToDMS(double coord) {
        String output, degrees, minutes, seconds;
        double mod = coord % 1;
        int intPart = (int) coord;

        degrees = String.valueOf(intPart);

        coord = mod * 60;
        mod = coord % 1;
        intPart = (int) coord;
        if (intPart < 0) {
            intPart *= -1;
        }
        minutes = String.valueOf(intPart);
        coord = mod * 60;
        intPart = (int) coord;
        if (intPart < 0) {
            // Convert number to positive if it's negative.
            intPart *= -1;
        }
        seconds = String.valueOf(intPart);
        output = degrees + "/1," + minutes + "/1," + seconds + "/1";

        return output;
    }


    /**
     * @param oldFilePath 有exif信息的原图
     * @param newFilePath 经过压缩后，丢失exif信息的图片
     * @throws Exception
     */
    public static void saveExif(String oldFilePath, String newFilePath) throws Exception {
        ExifInterface oldExif = new ExifInterface(oldFilePath);
        ExifInterface newExif = new ExifInterface(newFilePath);
        Class<ExifInterface> cls = ExifInterface.class;
        Field[] fields = cls.getFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (!TextUtils.isEmpty(fieldName) && fieldName.startsWith("TAG")) {
                String fieldValue = fields[i].get(cls).toString();
                String attribute = oldExif.getAttribute(fieldValue);
                if (attribute != null) {
                    newExif.setAttribute(fieldValue, attribute);
                }
            }
        }

        newExif.saveAttributes();

    }
}
