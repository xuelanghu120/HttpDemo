package com.huxin.common.utils;

import android.view.View;

/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/12/8
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public abstract class OnSingleClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (!ClickUtil.isFastDoubleClick()) {
            onClickListener(v);
        }
    }

    public abstract void onClickListener(View v);
}

