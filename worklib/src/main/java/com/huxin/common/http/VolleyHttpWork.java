package com.huxin.common.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by 56417 on 2017/9/1.
 */

public class VolleyHttpWork {

    private volatile static VolleyHttpWork instance;
    private final RequestQueue mMQueue;

    public static VolleyHttpWork getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyHttpWork.class) {
                if (instance == null) {
                    instance = new VolleyHttpWork(context);
                }
            }
        }
        return instance;
    }

    public VolleyHttpWork(Context context) {
        mMQueue = Volley.newRequestQueue(context);
    }


    //异步回调
    public void stringReq(StringRequest stringRequest) {
        mMQueue.add(stringRequest);
    }

    public void jsonReq(JsonObjectRequest jsonObjectRequest){
        mMQueue.add(jsonObjectRequest);
    }

}
