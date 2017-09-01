package com.huxin.common.base.fragment;


import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;


/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/10/31
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: fragement检测你存泄露需要写一些方法
 ******************************************/

public class BaseFragment extends Fragment {

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());    //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
