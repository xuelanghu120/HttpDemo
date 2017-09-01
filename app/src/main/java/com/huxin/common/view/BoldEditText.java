package com.huxin.common.view;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.huxin.common.application.Global;
import com.huxin.xinpiao.R;


/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/10/25
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: hint不加粗，不为空的时候加粗
 ******************************************/

public class BoldEditText extends EditText {
//    public boolean isEmpty;
    public ObservableBoolean isEmpty;
    private BoldTextWatcher mBoldTextWatcher;
    private Paint mPaint;
    private static final int Color_nofocus = Global.getContext().getResources().getColor(R.color.c0);
    private static final int Color_focus = Global.getContext().getResources().getColor(R.color.a0);

    public BoldEditText(Context context) {
        super(context);
        init();
    }

    public BoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setTextBold(s);
                if (mBoldTextWatcher!=null){
                    mBoldTextWatcher.beforeTextChanged(s,start,count,after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextBold(s);
                if (mBoldTextWatcher!=null){
                    mBoldTextWatcher.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextBold(s);
                if (mBoldTextWatcher!=null){
                    mBoldTextWatcher.afterTextChanged(s);
                }
            }
        });
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color_nofocus);
        
        this.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mPaint.setColor(Color_focus);
                    
                } else {
                    // 此处为失去焦点时的处理内容
                    mPaint.setColor(Color_nofocus);
                }
                //invalidate();
            }
        });

    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

//      画底线  
        canvas.drawLine(0,this.getHeight()-1,  this.getWidth()-1, this.getHeight()-1, mPaint);
    }


    public void addBoldTextChangedListener(BoldTextWatcher textWatcher) {
        this.mBoldTextWatcher = textWatcher;
    }

    public void setTextBold(CharSequence s){
        if(isEmpty==null){
            isEmpty = new ObservableBoolean(false);
        }
        if (TextUtils.isEmpty(s.toString())){
            setTextColor(getResources().getColor(R.color.b3));
            TextPaint tp = BoldEditText.this.getPaint();
            tp.setFakeBoldText(false);
            isEmpty.set(true);
        }else {
            setTextColor(getResources().getColor(R.color.b0));
            TextPaint tp = BoldEditText.this.getPaint();
            tp.setFakeBoldText(true);
            isEmpty.set(false);
        }
    }

    public interface BoldTextWatcher{

        void beforeTextChanged(CharSequence s, int start, int count, int after) ;

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

//    public boolean isEmpty() {
//        return isEmpty;
//    }

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(ObservableBoolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
