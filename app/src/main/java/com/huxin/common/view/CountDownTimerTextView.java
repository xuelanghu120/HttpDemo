package com.huxin.common.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.huxin.common.application.Global;
import com.huxin.xinpiao.R;


/******************************************
 * author: changfeng (changfeng@51huxin.com)
 * createDate: 2016/10/25
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 点击后可倒计时的textview
 * 正常点击监听setHXOnClickListener即可
 ******************************************/

public class CountDownTimerTextView extends TextView{
//    private CountDownTimerUtil countDown;
    private CountDownTimer countDown;
    private HXOnClickListener listener;
    public CountDownTimerTextView(Context context) {
        super(context);
        init();
    }

    public CountDownTimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownTimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

//         countDown = new CountDownTimerUtil(this,60000,1000);
         countDown = new CountDownTimer(60000,1000) {
             @Override
             public void onTick(long l) {
                 setClickable(false); //设置不可点击
                 setText((l-1) / 1000 + "秒后获取");  //设置倒计时时间
                 setTextColor(Global.getContext().getResources().getColor(R.color.a2));
                 setBackgroundResource(R.drawable.shape_corner_stroke2);
             }

             @Override
             public void onFinish() {
                 setText("重新获取验证码");
                 setClickable(true);//重新获得点击
                 setTextColor(getContext().getResources().getColor(R.color.a0));
                 setBackgroundResource(R.drawable.shape_corner_stroke);
             }
         };
       setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                countDown.start();
                listener.onClick(v);
            }
        });
    }

    public void reSet() {
        cancel();
        setClickable(true);//重新获得点击
        setTextColor(Global.getContext().getResources().getColor(R.color.a0));
        setBackgroundResource(R.drawable.shape_corner_stroke);
        setText("获取验证码");
    }

    public interface HXOnClickListener {
        void onClick(View view);
    }

    public void setHXOnClickListener(HXOnClickListener listener){
        this.listener = listener;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void cancel(){
        countDown.cancel();
    }
}
