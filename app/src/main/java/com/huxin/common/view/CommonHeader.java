package com.huxin.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxin.xinpiao.R;


/**
 * 公共头部分
 * Created by 56417 on 2016/10/10.
 */

public class CommonHeader extends RelativeLayout implements View.OnClickListener {

    private View mLeftView;
    private TextView mRightText;
    private View mRightView;
    private TextView mMiddleText;
    private View mView;
    private ImageView mLeftImageView;
    private TextView mLeftTextView;
    private View mBgView;

    public CommonHeader(Context context) {
        super(context);
        init();
    }

    public CommonHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_header, this);

        mBgView = mView.findViewById(R.id.view);

        mLeftView = mView.findViewById(R.id.left);
        mLeftImageView = (ImageView) mView.findViewById(R.id.left_image);
        mRightView = mView.findViewById(R.id.right);
        mLeftView.setOnClickListener(this);

        mRightText = (TextView) mView.findViewById(R.id.right_txt);
        mMiddleText = (TextView) mView.findViewById(R.id.middle_text);

        mLeftTextView = (TextView) mView.findViewById(R.id.left_text);
    }

    public void setMiddleText(String titile) {
        mMiddleText.setText(titile);
    }

    public void setMiddleText(int titileRid) {
        mMiddleText.setText(titileRid);
    }

    public void setRightText(String titile) {
        //mRightText.setVisibility(View.VISIBLE);
        mRightText.setText(titile);
    }
    public void setRightTextColor(int color) {
        //mRightText.setVisibility(View.VISIBLE);
        mRightText.setTextColor(color);
    }

    public void setRightText(int titileRid) {
        //mRightText.setVisibility(View.VISIBLE);
        mRightText.setText(titileRid);
    }
    public void setRightListener(OnClickListener listener){
        mRightText.setOnClickListener(listener);
    }

    public void setRightVisible(boolean isVisible) {
        if (isVisible) {
            mRightText.setVisibility(VISIBLE);
        } else {
            mRightText.setVisibility(GONE);
        }
    }

    public void setBackGroundMode(int type) {
        if (type == 1){//白色背景
            mView.findViewById(R.id.view).setBackgroundColor(Color.WHITE);
            int color = getResources().getColor(R.color.b0);
            mMiddleText.setTextColor(color);
            mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_left_back_b0));
        }else if (type == 2){//左上角只有一个×
            mView.findViewById(R.id.view).setBackgroundColor(Color.WHITE);
            mMiddleText.setVisibility(View.GONE);
            mLeftImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        }else if (type == 3){//webView左上角显示 关闭
            mBgView.setBackgroundColor(Color.BLACK);
            mLeftTextView.setVisibility(View.VISIBLE);
            mLeftTextView.setTextColor(Color.WHITE);
        }
    }
    public void setLeftListener(OnClickListener listener ){
        mLeftView.setOnClickListener(listener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                ((Activity) v.getContext()).finish();
                break;
        }
    }
}
