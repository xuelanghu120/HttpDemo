package com.huxin.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.ObservableBoolean;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

import com.huxin.xinpiao.R;


/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/10/25
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: hint不加粗，不为空的时候加粗
 ******************************************/

public class BoldTextView extends TextView {

    public ObservableBoolean isEmpty;
    private String mDefaultText;

    public BoldTextView(Context context) {
        this(context, null);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BoldTextView);
        mDefaultText = ta.getString(R.styleable.BoldTextView_defaultText);
        if (mDefaultText == null) {
            mDefaultText = getResources().getString(R.string.please_choose);
        }
        ta.recycle();

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setTextBold(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextBold(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextBold(s);
            }
        });
        setText(mDefaultText);

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mDefaultText == null) {
            mDefaultText = getResources().getString(R.string.please_choose);
        }
        if (text==null) {
            text = mDefaultText;
        }
        super.setText(text, type);
    }

    public void setDefaultText(String text) {
        mDefaultText = text;
        setText(mDefaultText);
    }

    public void setTextBold(CharSequence s) {
        if(isEmpty==null){
            isEmpty = new ObservableBoolean(false);
        }
        if (TextUtils.isEmpty(s.toString()) || mDefaultText.equals(s.toString().trim())) {
            setTextColor(getResources().getColor(R.color.b3));
            TextPaint tp = BoldTextView.this.getPaint();
            tp.setFakeBoldText(false);
            isEmpty.set(true);
        } else {
            setTextColor(getResources().getColor(R.color.b0));
            TextPaint tp = BoldTextView.this.getPaint();
            tp.setFakeBoldText(true);
            isEmpty.set(false);
        }
    }
    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(ObservableBoolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
