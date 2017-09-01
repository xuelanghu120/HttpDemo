package com.huxin.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxin.xinpiao.R;


/**
 * 公共头部分
 * Created by 56417 on 2016/10/10.
 */

public class ErrorView extends RelativeLayout implements View.OnClickListener {

    public static final String NET_ERROR = "NET_ERROR";
    private View mView;
    private TextView mMiddleView;


    public ErrorView(Context context) {
        super(context);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.stutas_error_with_header, this);
        mView.findViewById(R.id.left).setOnClickListener(this);
        mView.findViewById(R.id.error_retry).setOnClickListener(this);
        mMiddleView = (TextView) mView.findViewById(R.id.middle_text);
    }


    private RetryListener mRetryListener;

    public void setRetryListener(RetryListener retryListener) {
        mRetryListener = retryListener;
    }

    public void setTitle(String title) {
        mMiddleView.setText(title);
    }

    public void show(String errorCode) {
        //网络状态异常显示和系统返回数据但是error同样心事网络错误
//        if (NET_ERROR.equals(errorCode)) {//网络状态异常显示
            setVisibility(View.VISIBLE);
//        }

    }

    public void show(String errorCode, String title) {
//        if (NET_ERROR.equals(errorCode)) {//网络状态异常显示
            setVisibility(View.VISIBLE);
//        }
        mMiddleView.setText(title);
    }


    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                ((Activity) v.getContext()).finish();
                break;
            case R.id.error_retry:
                if (mRetryListener != null) {
                    mRetryListener.retry();
                }
                break;
        }
    }

    public interface RetryListener {
        void retry();
    }
}
