package com.huxin.xinpiao.login;


import com.huxin.common.http.builder.ParamEntity;
import com.huxin.common.http.builder.URLBuilder;
import com.huxin.xinpiao.ApiDefinition;
import com.huxin.xinpiao.HXUrlBuilder;


/**
 * Created by 56417 on 2016/9/20.
 */
@URLBuilder.Path( url = ApiDefinition.URL_LOGIN, builder = HXUrlBuilder.class)
public class ReqLoginParam implements ParamEntity {
    public String mobile;
    public String passwd;
    public String loginIp;
    public String deviceType = "1";//1 android
}
