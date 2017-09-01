package com.huxin.xinpiao.test;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.huxin.common.base.activity.BaseActivity;
import com.huxin.common.http.HttpWork;
import com.huxin.common.utils.Logger;
import com.huxin.xinpiao.R;
import com.huxin.xinpiao.databinding.ActivityTestBinding;
import com.huxin.xinpiao.login.ReqLoginParam;
import com.huxin.xinpiao.login.RspLogin;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class TestActivity extends BaseActivity implements View.OnClickListener {

    private ActivityTestBinding mBinding;


    @Override
    protected void bindView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
    }

    @Override
    protected void init() {
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReqLoginParam param = new ReqLoginParam();
                param.mobile = "15222223333";
                param.passwd = "15222223333";
                param.loginIp = "15222223333";
                HttpWork.getInstace(TestActivity.this).post(param, RspLogin.class, null, false)
                        .doOnNext(new Consumer<RspLogin>() {
                            @Override
                            public void accept(@NonNull RspLogin rspLogin) throws Exception {
                                Logger.d("15222223333","15222223333");
                            }
                        }).subscribe();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
