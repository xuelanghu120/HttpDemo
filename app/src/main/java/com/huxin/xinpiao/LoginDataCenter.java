package com.huxin.xinpiao;


import android.net.Uri;

import com.huxin.common.application.Global;
import com.huxin.common.http.cookie.CookieUtils;
import com.huxin.common.http.cookie.MyPersistentCookieStore;
import com.huxin.common.utils.EntityUtils;
import com.huxin.common.utils.Logger;
import com.huxin.common.utils.SPUtil;
import com.huxin.xinpiao.deviceid.DeviceIdUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 登陆或者全局相关的数据
 * Created by 56417 on 2016/9/20.
 */

public class LoginDataCenter {

    private User user = new User();
    //服务器时间key值
    public final String TIME = "time";
    //服务器时间差值key值
    public final String DURING_TIME = "during_time";
    public static final String DEVICEID = "deviceId";

    private volatile static LoginDataCenter instance;

    public static LoginDataCenter getInstance() {
        if (instance == null) {
            synchronized (LoginDataCenter.class) {
                if (instance == null) {
                    instance = new LoginDataCenter();
                }
            }
        }
        return instance;
    }

    public void setTime(String remoteTime) {
        SPUtil.putString(TIME, remoteTime);
        long during_time = System.currentTimeMillis() / 1000 - Long.parseLong(remoteTime);
        SPUtil.putLong(DURING_TIME, during_time);
    }

    public String getRemoteTime() {
        return String.valueOf(System.currentTimeMillis() / 1000 - SPUtil.getLong(DURING_TIME, 0));
    }

    public long getDuringTime(){
        return SPUtil.getLong(DURING_TIME, 0);
    }

    public void setDeviceId(String deviceId) {
        SPUtil.putString(DEVICEID, deviceId);
    }

    public String getDeviceId() {
//        String deviceId = SPUtil.getString(DEVICEID, "");
        //本地生成设备id的时候打开就ok
        return DeviceIdUtils.getDeiceIdMd5(Global.getContext());
    }

    public boolean isBindBank() {
//        return user.bank != null;
        return false;
    }

    public void saveUser(User user) {
        setUser(user);

    }


    public void clearCookie(){
        MyPersistentCookieStore.getInstance(Global.getContext()).removeAll();
        CookieUtils.removeCookies();
    }

    public void setUser(User user) {
        if (user != null) {
            EntityUtils.resolveAllFieldsSet(this.user, user);
        }
    }


    public String getUserId() {
        return user != null ? user.uid : null;
    }

    public User getUser() {
        return user;
    }

    public void synCookies(String apiHost,String h5Host) {
        List<String> domains = new ArrayList<String>();
        domains.add(Uri.parse(apiHost).getHost());
        domains.add(".sinosig.com");

        domains.add("fqapp.ittun.com");
        domains.add("app-node.local.com");
//        domains.add("xp.local.com");//测试node本地
        domains.add(Uri.parse(h5Host).getHost());
        Map<String, String> cookieMap = MyPersistentCookieStore.getInstance(Global.getContext()).getCookieMap();
        cookieMap.put(LoginDataCenter.DEVICEID, getDeviceId());
        Logger.d("httpword", "cookie数量" + cookieMap.size());
        Logger.i("HttpWork","host--->"+domains.toString()+"\n"+"cookies--->"+cookieMap.toString());
        CookieUtils.sysnCookies(domains, cookieMap);
    }
}
