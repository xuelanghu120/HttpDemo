package com.huxin.xinpiao.login;


import com.huxin.common.http.responser.AbstractResponser;

import org.json.JSONObject;

/**
 * Created by 56417 on 2016/9/20.
 */

public class RspLogin extends AbstractResponser {

    @Override
    public void parserBody(String result) {

    }

    @Override
    public String getErrorDesc(JSONObject jsonObject) {
        return null;
    }

    @Override
    public boolean isSuccess(JSONObject dataObject) {
        return false;
    }
}
