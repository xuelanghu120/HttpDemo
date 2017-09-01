package com.huxin.common.view.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.huxin.xinpiao.R;


/******************************************
 * author: xuzhu (xuzhu@51huxin.com)
 * createDate: 2016/11/9
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 注释写这里
 ******************************************/

public class CircleArc extends View {

    //前景色起始颜色
    private int foreStartColor;
    //前景色结束颜色
    private int foreEndColcor;
    //是否使用渐变
    protected boolean useGradient;
    //进度条初始位置
    protected int progressInitialPosition;

    private Paint bgPaint;
    private int bgPaintColor;
    private float mStrokeWidth;
    private int mWidth;
    private int mHeight;
    private PointF mCenter;
    private RectF mPaintRectF;
    protected float maxAngle; //最大角度
    private boolean isSolid = false;


    public CircleArc(Context context) {
        this(context, null);
    }

    public CircleArc(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVar(context, attrs);
        init();
    }
    private void initVar(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleArc);

        foreStartColor = ta.getColor(R.styleable.CircleArc_circleArcStartColor, Color.BLUE);
        foreEndColcor = ta.getColor(R.styleable.CircleArc_circleArcEndColor, Color.BLUE);
        useGradient = ta.getBoolean(R.styleable.CircleArc_circleArcUseGradient, true);
        bgPaintColor = ta.getColor(R.styleable.CircleArc_circleArcbgColor, Color.GRAY);
        mStrokeWidth = ta.getDimension(R.styleable.CircleArc_circleArcStrokeWidth, 12);
        progressInitialPosition = ta.getInteger(R.styleable.CircleArc_circleArProgressInitialPosition, 150);
        maxAngle = ta.getInteger(R.styleable.CircleArc_circleArMaxAngle, 240);

        ta.recycle();
    }

    private void init() {
        initBgPaint();
    }
    protected void initBgPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(bgPaintColor);
        setCommonPaint(bgPaint, isSolid, true);
    }


    /**
     * @param paint   画笔
     * @param isSolid 是否空心
     * @param isRound 边角是否是圆的
     */
    protected void setCommonPaint(Paint paint, boolean isSolid, boolean isRound) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(mStrokeWidth);
        if (!isSolid) {
            paint.setStyle(Paint.Style.STROKE);
        }
        if (isRound) {
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initViewSize();
    }

    protected void initViewSize() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //防止宽高不一致
        if (mWidth > mHeight) {
            mWidth = mHeight;
        } else {
            mHeight = mWidth;
        }
        mCenter = new PointF(mWidth / 2, mHeight / 2);
        mPaintRectF = new RectF(0 + mStrokeWidth, 0 + mStrokeWidth, mWidth - mStrokeWidth, mHeight - mStrokeWidth);

        if (useGradient) {
            LinearGradient gradient = new LinearGradient(0, 0, mWidth, mHeight, foreEndColcor, foreStartColor, Shader.TileMode.CLAMP);
            bgPaint.setShader(gradient);
        } else {
            bgPaint.setColor(bgPaintColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(progressInitialPosition, mCenter.x, mCenter.y);//旋转开始的角度
        canvas.drawArc(mPaintRectF, 0, maxAngle, isSolid, bgPaint);//画一个背景色
    }
}
