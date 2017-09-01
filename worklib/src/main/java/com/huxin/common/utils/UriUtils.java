package com.huxin.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/12/6
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class UriUtils {

//    public static void startActivity(Context context, String uri) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        PackageManager packageManager = context.getPackageManager();
//        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
//        boolean isValid = !activities.isEmpty();
//        ToastUtils.showShortToast(context, isValid + "");
//        if (isValid) {
//            context.startActivity(intent);
//        }
//    }

    /**
     * intent是否合法存在
     *
     * @param context
     * @return
     */
    public static boolean isValid(Context context,String uri) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        return isValid;
    }

    /**
     * 完整的url信息
     *
     * @param uri
     * @return
     */
    public static String getAllUriString(Uri uri) {
        String url = null;
        if (uri != null) {
            url = uri.toString();
        }
        return url;
    }

    /**
     * scheme部分
     *
     * @param uri
     * @return
     */
    public static String getScheme(Uri uri) {
        String scheme = null;
        if (uri != null) {
            scheme = uri.getScheme();
        }
        return scheme;
    }

    /**
     * host部分
     *
     * @param uri
     * @return
     */
    public static String getHost(Uri uri) {
        String host = null;
        if (uri != null) {
            host = uri.getHost();
        }
        return host;
    }

    /**
     * port部分端口
     *
     * @param uri
     * @return
     */
    public static int getPort(Uri uri) {
        int port = -1;
        if (uri != null) {
            port = uri.getPort();
        }
        return port;
    }

    /**
     * 访问路劲
     *
     * @param uri
     * @return
     */
    public static String getPath(Uri uri) {
        String path = null;
        if (uri != null) {
            path = uri.getPath();
        }
        return path;
    }

    /**
     * 获取所有的path
     *
     * @param uri
     * @return
     */
    public static List<String> getPaths(Uri uri) {
        List<String> paths = null;
        if (uri != null) {
            paths = uri.getPathSegments();
        }
        return paths;
    }

    /**
     * Query部分 所有的参数string
     *
     * @param uri
     * @return
     */
    public static String getQuery(Uri uri) {
        String query = null;
        if (uri != null) {
            query = uri.getQuery();
        }
        return query;
    }

    /**
     * 获取指定参数值
     *
     * @param uri
     * @param key
     * @return
     */
    public static String getQueryParameter(Uri uri, String key) {
        String parameter = null;
        if (uri != null) {
            parameter = uri.getQueryParameter(key);
        }
        return parameter;
    }

    /**
     * 将file转换为uri。解决7.0是配的问题
     * @param context  the current component.
     * @param file
     * @return
     */
    public static Uri fromFile(Context context,File file){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            return FileProvider.getUriForFile(context, "com.huxin.xinpiao.fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
