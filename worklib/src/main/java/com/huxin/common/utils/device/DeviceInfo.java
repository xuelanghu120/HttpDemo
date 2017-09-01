package com.huxin.common.utils.device;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.huxin.common.application.Global;
import com.huxin.common.utils.EncryptUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.UUID;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceInfo {

    public final static int ICE_CREAM_SANDWICH_MR1 = 15;
    public final static int JELLY_BEAN = 16;
    public final static int JELLY_BEAN_MR1 = 17;
    public final static int JELLY_BEAN_MR2 = 18;

    public static String MACADDRESS;
    public static String RESOLUTION;

    private static String sPackageName;
    private static String sVersionName;
    private static int sVersionCode;

    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
        MACADDRESS = getLocalMacAddress(context);
        RESOLUTION = getResolution(context);
    }

    public static String getModelAndFactor() {
        return Build.MODEL + "/" + Build.MANUFACTURER;
    }

    /**
     * 获得手机型号
     *
     * @return
     */
    public static String getMobileModel() {
        return Build.MODEL;
    }

    /**
     * 获得手机制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getUuid() {
        return new DeviceUuidFactory(sContext).getDeviceUuid();
    }

    public static String getOsVersion() {
        String sdkStr = Build.VERSION.SDK_INT + "";
        switch (Build.VERSION.SDK_INT) {
            case Build.VERSION_CODES.BASE:
                sdkStr = "October 2008: The original, first, version of Android.";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                sdkStr = "Android 1.1.";
                break;
            case Build.VERSION_CODES.CUPCAKE:
                sdkStr = " Android 1.5";
                break;
            case Build.VERSION_CODES.CUR_DEVELOPMENT:
                break;
            case Build.VERSION_CODES.DONUT:
                sdkStr = "Android 1.6";
                break;
            case Build.VERSION_CODES.ECLAIR:
                sdkStr = "Android 2.0";
                break;
            case Build.VERSION_CODES.ECLAIR_0_1:
                sdkStr = "Android 2.0.1";
                break;
            case Build.VERSION_CODES.ECLAIR_MR1:
                sdkStr = "Android 2.1";
                break;
            case Build.VERSION_CODES.FROYO:
                sdkStr = "Android 2.2";
                break;
            case Build.VERSION_CODES.GINGERBREAD:
                sdkStr = "Android 2.3.3";
                break;
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                sdkStr = "Android 2.3.3";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                sdkStr = "Android 3.0";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                sdkStr = "Android 3.1";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                sdkStr = "Android 3.2";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                sdkStr = "Android 4.0";
                break;
            case ICE_CREAM_SANDWICH_MR1:
                sdkStr = "Android 4.0.3";
                break;
            case JELLY_BEAN:
                sdkStr = "Android 4.1";
                break;
            case JELLY_BEAN_MR1:
                sdkStr = "Android 4.2";
                break;
            case JELLY_BEAN_MR2:
                sdkStr = "Android 4.3";
                break;
            case Build.VERSION_CODES.KITKAT:
                sdkStr = "Android 4.4";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
                sdkStr = "Android LOLLIPOP";
                break;
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                sdkStr = "Android LOLLIPOP_MR1";
                break;
            case Build.VERSION_CODES.M:
                sdkStr = "Android M";
                break;
            default:
                break;
        }
        return sdkStr;
    }

    public static String getPackageName() {
        if (TextUtils.isEmpty(sPackageName)) {
            sPackageName = Global.getContext().getPackageName();
        }
        return sPackageName;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(sVersionName)) {
            try {
                PackageInfo info = Global.getContext().getPackageManager()
                        .getPackageInfo(getPackageName(), 0);
                sVersionName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        }
        return sVersionName;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        if (0 == sVersionCode) {
            try {
                PackageInfo info = Global.getContext().getPackageManager()
                        .getPackageInfo(getPackageName(), 0);
                sVersionCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return sVersionCode;
    }

    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    private static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取屏幕信息
     *
     * @param context
     * @return
     */
    private static String getResolution(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return String.valueOf(metrics.widthPixels) + "*" + String.valueOf(metrics.heightPixels);

//        DisplayMetrics dm = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        return String.valueOf(dm.widthPixels) + "*" + String.valueOf(dm.heightPixels);
    }

//    生成唯一的设备id
//    http://blog.csdn.net/num1394csdn/article/details/53160040
//    1. DEVICE_ID      //getIMEI
//    2. MAC ADDRESS    //getLocalMacAddress
//    3. Serial Number
//    4. ANDROID_ID     //getAndroidID
//    5. Installtion ID : UUID
//    6 Pseudo-Unique ID, 这个在任何Android手机中都有效


    private static String CILIENT_ID = null;

    /**
     * 设备唯一标示
     *
     * @return
     */
    public static final String getClientId() {
        String clientId = "";
        if (!TextUtils.isEmpty(CILIENT_ID)) {
            clientId = CILIENT_ID;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(getIMEI());
            builder.append(getAndroidID());
            builder.append(getMobileModel());
            builder.append(getMacAdd());
            String str = builder.toString();
            clientId = EncryptUtil.md5(str);
            CILIENT_ID = clientId;
        }
        return clientId;
    }

    /***
     * 获得IMEI
     *
     * @return
     */
    public static final String getIMEI() {
        String str = "";
        TelephonyManager telManager = (TelephonyManager) Global.getContext().getSystemService(TELEPHONY_SERVICE);
        if (telManager != null) {
            try {
                str = telManager.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //过滤漏洞垃圾和无效IMEI
        if ("zeros".equals(str) || "asterisks".equals(str) || "0000000000000".equals(str)) {
            str = "";
        }
        return str;
    }

    /***
     * 获取android ID
     *
     * @return
     */
    public static final String getAndroidID() {
        String androidID = "";
        androidID = android.os.Build.ID;
        return androidID;
    }

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

//    public static String getUniqueID() {
////        String m_szDevIDShort = getIMEI();h'x
////        if (TextUtils.isEmpty(m_szDevIDShort)){
//        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
//                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
//                + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
//                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
//                + Build.TYPE.length() % 10 + Build.USER.length() % 10;
////        }
//        return EncryptUtil.md5(m_szDevIDShort);
//    }

    /***
     * 获取mac地址
     *
     * @return
     */
    public static final String getMacAdd() {
// 获取mac地址：
        String command = "cat /sys/class/net/wlan0/address ";
        return getStringInfo(command);
    }


    private static final String getStringInfo(String command) {
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(command);
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    str = str.trim();// 去空格
                    break;
                }
            }
            input.close();
            ir.close();
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
            str = "00000000";
        }
        return str;
    }


//    private static final String TAG = DeviceUtils.class.getSimpleName();
    private static final String CMCC_ISP = "46000";//中国移动
    private static final String CMCC2_ISP = "46002";//中国移动
    private static final String CU_ISP = "46001";//中国联通
    private static final String CT_ISP = "46003";//中国电信

    /**
     * 获取设备的系统版本号
     */
    public static int getDeviceSDK() {
        int sdk = Build.VERSION.SDK_INT;
        return sdk;
    }

    /**
     * 获取设备的型号
     */
    public static String getDeviceName() {
        String model = Build.MODEL;
        return model;
    }

    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telephonyManager.getSubscriberId();
        return IMSI;
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

    /**
     * 获取手机网络运营商类型
     *
     * @param context
     * @return
     */
    public static String getPhoneISP(Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String teleCompany = "";
        String np = manager.getNetworkOperator();

        if (np != null) {
            if (np.equals(CMCC_ISP) || np.equals(CMCC2_ISP)) {
                teleCompany = "中国移动";
            } else if (np.startsWith(CU_ISP)) {
                teleCompany = "中国联通";
            } else if (np.startsWith(CT_ISP)) {
                teleCompany = "中国电信";
            }
        }
        return teleCompany;
    }

    /**
     * 获取屏幕信息
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * 获取/打印屏幕信息
     *
     * @param context
     * @return
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        StringBuilder sb = new StringBuilder();
        sb.append("\ndensity         :").append(dm.density);
        sb.append("\ndensityDpi      :").append(dm.densityDpi);
        sb.append("\nheightPixels    :").append(dm.heightPixels);
        sb.append("\nwidthPixels     :").append(dm.widthPixels);
        sb.append("\nscaledDensity   :").append(dm.scaledDensity);
        sb.append("\nxdpi            :").append(dm.xdpi);
        sb.append("\nydpi            :").append(dm.ydpi);
//        LogUtils.i(TAG, sb.toString());
        return dm;
    }

    /**
     * 获取系统当前可用内存大小
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }


    /**
     * 获取 MAC 地址
     * 须配置android.permission.ACCESS_WIFI_STATE权限
     */
    public static String getMacAddress(Context context) {
        //wifi mac地址
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
//        LogUtils.i(TAG, " MAC：" + mac);
        return mac;
    }

    /**
     * 获取 开机时间
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);
//        LogUtils.i(TAG, h + ":" + m);
        return h + ":" + m;
    }
}
