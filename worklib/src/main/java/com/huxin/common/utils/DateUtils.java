package com.huxin.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/11/1
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class DateUtils {
    public int[] getYear(String dateString) {
        int[] time = new int[3];
        SimpleDateFormat sim = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date parse = sim.parse(dateString);

            Date date = new Date();
            time[0] = parse.getYear();
            time[1] = parse.getMonth();
            time[2] = parse.getDay();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static boolean isOutTime(String dateString) {

        boolean isOutTiem = false;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date1 = sim.parse(dateString);
            Date date2 = new Date();
            int i = date1.compareTo(date2);
            isOutTiem = (i < 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isOutTiem;
    }

    public static int getDDCouont(String time) {
        long date = formatToLong(time, "yyyy-MM-dd");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = formatter.format(currentTime);

        long now = System.currentTimeMillis();
        int DD = (int) (now - date) / (1000 * 60 * 60 * 24);
        return Math.abs(DD);
    }
//    String sDt = "08/31/2006 21:08:00";
//    SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//    Date dt2 = sdf.parse(sDt);
//    //继续转换得到秒数的long型
//    long lTime = dt2.getTime() / 1000;

    /**
     * 字符串转为long
     *
     * @param time     字符串时间,注意:格式要与template定义的一样
     * @param template 要格式化的格式:如time为09:21:12那么template为"HH:mm:ss"
     * @return long
     */
    public static long formatToLong(String time, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        try {
            Date d = sdf.parse(time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
