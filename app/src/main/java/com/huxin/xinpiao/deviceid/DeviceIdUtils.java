package com.huxin.xinpiao.deviceid;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.huxin.common.utils.EncryptUtil;
import com.huxin.common.utils.Logger;
import com.huxin.common.utils.StringUtils;

import java.net.NetworkInterface;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;


/******************************************
 * @author: xuzhu (xuzhu@51huxin.com)
 * @createDate: 2017/4/11
 * @company: (C) Copyright 阳光互信 2016
 * @since: JDK 1.8
 * @Description: 注释写这里
 ******************************************/

public class DeviceIdUtils {

    public static final String TAG = DeviceIdUtils.class.getSimpleName();

    private static String CILIENT_ID;

    public static String getDeiceIdMd5(Context context) {

        if (StringUtils.isEmpty(CILIENT_ID)) {
            String uniImei = PublicPreferencesUtils.getUniImei();
            if (!StringUtils.isEmpty(uniImei)) {
                CILIENT_ID = uniImei;
                return uniImei;
            }
            String realImei = getRealImei(context);
            String androidId = getAndroidId(context);
            String macAddress = getMacAddress(context);
            StringBuilder builder = new StringBuilder();
            builder.append(realImei);
            builder.append(androidId);
            builder.append(getMobileModel());
            builder.append(macAddress);
            if (StringUtils.isEmpty(macAddress + androidId + realImei)) {
                builder.append(System.currentTimeMillis());
            }
            String str = builder.toString();
            String clientId = EncryptUtil.md5(str);
            PublicPreferencesUtils.saveUniImei(clientId);
            CILIENT_ID = clientId;
        }
        return CILIENT_ID;
    }

//    private static String mDeviceImei;
//
//    public static String genImei(final Context context) {
//        if (!StringUtils.isEmpty(mDeviceImei)) {
//            return mDeviceImei;
//        }
//        String uniImei = PublicPreferencesUtils.getUniImei();
//        if (!StringUtils.isEmpty(uniImei)) {
//            mDeviceImei = uniImei;
//            return uniImei;
//        }
//        boolean hasPermission = EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
//        if (hasPermission) {
//            mDeviceImei = getRealImei(context);
//        }
//        if (StringUtils.isEmpty(mDeviceImei)) {
//            // 如果imei号为空或0，取mac地址作为imei号传递
//            mDeviceImei = getMacAddress(context);
//            // 如果mac地址为空或0，则通过uuid生成的imei号来传递
//            if (StringUtils.isEmpty(mDeviceImei)) {
//                mDeviceImei = UUIDUtils.getUUID();
//            }
//        }
//        ;
//        PublicPreferencesUtils.saveUniImei(mDeviceImei);
//        return mDeviceImei;
//    }


    public static String getRealImei(final Context context) {
        String sReallyImei = "";
//        boolean hasPermission = EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
//        if (!hasPermission) {
//            return "";
//        }
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            sReallyImei = tm.getDeviceId();// imei
            //imei获取失败则不进行缓存，获取到“－”
            //imei获取成功则缓存内存与Disk，备后续使用
            if (TextUtils.isEmpty(sReallyImei) || sReallyImei.matches("0+")) {
                sReallyImei = "";
            } else if (sReallyImei.equals("zeros") || sReallyImei.equals("asterisks")) {
                //脏数据处理
                sReallyImei = "";
            }
            return sReallyImei;

        } catch (Exception e) {
            String message = e.getMessage();
            return sReallyImei;
        }
    }


    /**
     * 获取wifi网络下的的MAC地址
     *
     * @return MAC地址
     */

    public static String getMacAddress(Context context) {
        String sMacAddress = "";
        try {
            String wifiInterfaceName = "wlan0";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iF = interfaces.nextElement();
                    if (iF.getName().equalsIgnoreCase(wifiInterfaceName)) {
                        byte[] addr = iF.getHardwareAddress();
                        if (addr == null || addr.length == 0) {
                            break;
                        }

                        StringBuilder buf = new StringBuilder();
                        for (byte b : addr) {
                            buf.append(String.format("%02X:", b));
                        }
                        if (buf.length() > 0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        sMacAddress = buf.toString();
                        break;
                    }
                }
            }
        } catch (Exception se) {
            Logger.d(TAG, "sp.mac.exception:" + sMacAddress, se);
            return "";
        }
        if (TextUtils.isEmpty(sMacAddress)) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();
                sMacAddress = info.getMacAddress();
            } catch (Exception e) {
                Logger.d(TAG, "getMacAddress error", e);
                return "";
            }
        }
        if (TextUtils.isEmpty(sMacAddress) || sMacAddress.equals("02:00:00:00:00:00")) {
            sMacAddress = "";
        }
        Logger.d(TAG, "create.mac:" + sMacAddress);
        return sMacAddress;
    }


    public static String getUniqueID() {
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
                + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
                + Build.TYPE.length() % 10 + Build.USER.length() % 10;
//        }
        return m_szDevIDShort;
    }


    public static String getAndroidID() {
        String androidID = "";
        androidID = Build.ID;
        return androidID;
    }

    public static String getMobileModel() {
        return Build.MODEL;
    }


    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if ("9774d56d682e549c".equals(androidId)) {
            androidId = "";
        }
        return androidId;
    }
}
