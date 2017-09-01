package com.huxin.xinpiao;

import android.text.TextUtils;

import com.huxin.common.http.builder.ParamEntity;
import com.huxin.common.http.builder.URLBuilder;
import com.huxin.common.utils.Logger;
import com.huxin.common.utils.SignUtils;
import com.huxin.common.utils.device.DeviceInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 互信请求builder
 * Created by 56417 on 2016/9/14.
 */

public class HXUrlBuilder implements URLBuilder {
    private final static String TAG = HXUrlBuilder.class.getSimpleName();
    public final String TIME = "time";
    public final String DEVICE_ID = "device_id";
    //请求url
    private String url;
    //请求的参数map
    private Map<String,Object> paramsMap;
    //缓存拼接忽略的参数
    private String[] cacheIgoneParam;
    @Override
    public void parse(Path path, Map<String, Field> fields, ParamEntity entity) throws IllegalAccessException {
        if (TextUtils.isEmpty(path.host())){
            url = HXConfig.API_HOST + path.url();
        }else {
            url = path.host() + path.url();
        }
        cacheIgoneParam = path.cacheIgoneParam();
        if (fields != null) {
            paramsMap = new HashMap<>();
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                Object value = entry.getValue().get(entity);
                if (value != null) {
                    paramsMap.put(entry.getKey(), value);
                }
            }

            paramsMap.put(TIME, LoginDataCenter.getInstance().getRemoteTime());
//            paramsMap.put(DEVICE_ID, LoginDataCenter.getInstance().getDeviceId());
            paramsMap.put(DEVICE_ID, LoginDataCenter.getInstance().getDeviceId());
            //}
            paramsMap.put("os","android");
            paramsMap.put("channelid", "2017011");
//            paramsMap.put("channelid", ChannelUtil.getChannelName(Global.getContext()));
            paramsMap.put("appversion", DeviceInfo.getVersionCode()+"");

            paramsMap.put(SignUtils.APPID_KEY, SignUtils.APPID_VALUE);//2016073100129845
            paramsMap.put(SignUtils.SIGN_KEY, SignUtils.encryptHmac(paramsMap));
        }
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, Object> getParams() {
        return paramsMap;
    }
    //缓存参数，一般只保存业务参数
    @Override
    public Map<String, Object> getCacheKeyParams() {
        Map<String, Object> cahceMap = new HashMap<>();
        cahceMap.putAll(paramsMap);
        cahceMap.remove(SignUtils.SIGN_KEY);
        cahceMap.remove(SignUtils.APPID_KEY);
        for (String paramname : cacheIgoneParam) {
            cahceMap.remove(paramname);
            Logger.d(TAG, "getCacheKeyParams: " + paramname);
        }
        return null;
    }

    @Override
    public byte getReqType() {
        return REQ_TYPE_KV;
    }


}
