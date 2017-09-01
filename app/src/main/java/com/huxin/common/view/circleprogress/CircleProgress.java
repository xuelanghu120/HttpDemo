package com.huxin.common.view.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.huxin.common.view.circleprogress.utils.GeometryUtil;
import com.huxin.xinpiao.R;


/**
 * 圆形的进度条
 */
public class CircleProgress extends View {
    protected float maxAngle = 270; //最大角度
    protected int maxProgress = 20000; //最大进度
    protected int currProgress; //现在的进度

    protected int mWidth;
    protected int mHeight;
    protected PointF mCenter;
    //进度条宽度
    protected float mStrokeWidth;
    protected RectF mPaintRectF;

    //前景色起始颜色
    private int foreStartColor;
    //前景色结束颜色
    private int foreEndColcor;
    private Paint forePaint;

    private int bgPaintColor;
    private Paint bgPaint;
    //进度条初始位置
    protected int progressInitialPosition;
    //是否使用动画
    protected boolean useAnimation;
    //动画的执行时间
    protected int ANIMATION_DURATION = 1000;
    //是否使用渐变
    protected boolean useGradient;
    //边角是否是圆的
    private boolean isCircleCorner;
    //是否是实心的
    protected boolean isSolid;
    //刻度值文本
    private String mScaleValueTxt;
    //刻度值文本画笔
    private Paint mScallValueTextPaint;
    //刻度值文本宽度
    private float mScallValueTxtWidth;
    //刻度文本个数
    private int mScallValueCount;
    //刻度尺文本开始的角度
    private int mScallStarteAngle;

    //==========================瞄点
    private float mPointerRadius;//锚点半径
    protected Paint mPointerPaint;
    protected Paint mPointerSmallPaint;
    //==========================瞄点


    //===================刻度线
    protected Paint tickPaint;
    //刻度线的高度
    protected float tickMarkHeight;
    //刻度线的个数
    protected int tickMarkCount = 20;
    private PointF[] startPoints;
    private PointF[] stopPoints;
    //刻度圆弧的rect
    private RectF mTickPaintRect;
    //父类内弧边界的Rect
    private RectF mParentRect;
    private float tickWidth;
    //刻度颜色
    private int tickColor;
    //刻度文字颜色
    private int mTextColor;
    private float mTextSize;
//    private float middleTextSize;
    private String mCurrProgressTxt;
    private float mCurrProgressTxtWidth;
//    private Paint middleTxtPaint;
//    private LinearGradient mMiddleTextShader;
    //===================刻度线

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVar(context, attrs);
        init();
    }

    protected void initVar(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        bgPaintColor = ta.getColor(R.styleable.CircleProgress_bgColor, Color.GRAY);
        foreStartColor = ta.getColor(R.styleable.CircleProgress_foreStartColor, Color.BLUE);
        foreEndColcor = ta.getColor(R.styleable.CircleProgress_foreEndColor, Color.BLUE);
        maxAngle = ta.getInteger(R.styleable.CircleProgress_maxProgress, 270);
        mStrokeWidth = ta.getDimension(R.styleable.CircleProgress_progressWidth, 12);
        currProgress = ta.getInteger(R.styleable.CircleProgress_currProgress, 0);
        progressInitialPosition = ta.getInteger(R.styleable.CircleProgress_progressInitialPosition, 135);
        useAnimation = ta.getBoolean(R.styleable.CircleProgress_useAnimation, true);
        useGradient = ta.getBoolean(R.styleable.CircleProgress_useGradient, true);
        isCircleCorner = ta.getBoolean(R.styleable.CircleProgress_isCircleCorner, true);
        isSolid = ta.getBoolean(R.styleable.CircleProgress_isSolid, false);
        mPointerRadius = ta.getDimension(R.styleable.CircleProgress_pointer_radius, 20);
        mScallValueCount = ta.getInteger(R.styleable.CircleProgress_mScallValueCount, 5);
        mScallStarteAngle = ta.getInteger(R.styleable.CircleProgress_mScallStarteAngle, -45);
        tickColor = ta.getColor(R.styleable.CircleProgress_tickColor1, Color.WHITE);
        mTextColor = ta.getColor(R.styleable.CircleProgress_textColor1, Color.BLACK);
        mTextSize = ta.getDimension(R.styleable.CircleProgress_textSize1, 40);
        tickWidth = ta.getDimension(R.styleable.CircleProgress_tickWidth1, 2);
        tickMarkHeight = ta.getDimension(R.styleable.CircleProgress_tickMarkHeight1, mStrokeWidth * 1F);
//        middleTextSize = ta.getDimension(R.styleable.CircleProgress_middleTextSize1, 100);

        ta.recycle();

        //检查值是否合理
        maxAngle = maxAngle >= 0 && maxAngle < 360 ? maxAngle : 360;
        currProgress = currProgress <= maxProgress && currProgress >= 0 ? currProgress : maxProgress;
    }

    protected void init() {
        initForePaint();
        initBgPaint();
        initTextPaint();
        initPointPaint();
        initPointPaintSmall();
        initTickPaint();
//        initMidilePaint();
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
            forePaint.setShader(gradient);
        } else {
            forePaint.setColor(foreStartColor);
        }

        //===============================刻度线
        float dx = -mStrokeWidth * 0.5F;
        mParentRect = new RectF(mPaintRectF.left + dx, mPaintRectF.top + dx, mPaintRectF.right - dx, mPaintRectF.bottom - dx);
        mTickPaintRect = new RectF(mParentRect.left + tickMarkHeight, mParentRect.top + tickMarkHeight,
                mParentRect.right - tickMarkHeight, mParentRect.bottom - tickMarkHeight);
        startPoints = GeometryUtil.getIntersectionPoints(mCenter, (mParentRect.right - mParentRect.left) / 2F, 0.0, true);
        stopPoints = GeometryUtil.getIntersectionPoints(mCenter, (mTickPaintRect.right - mTickPaintRect.left) / 2F, 0.0, false);
        //===============================刻度线

//        initMidilePaint();
    }


    /**
     * 初始化刻度画笔
     */
    private void initTickPaint() {
        tickPaint = new Paint();
        tickPaint.setColor(tickColor);
        setCommonPaint(tickPaint, false, false);
        tickPaint.setStrokeWidth(tickWidth);
    }

//    /**
//     * 初始化中间文字
//     */
//    private void initMidilePaint() {
//        Paint middleTxtPaint = new Paint();
//        middleTxtPaint.setTextSize(middleTextSize);
//        mMiddleTextShader = new LinearGradient(0, 0, 0, String.valueOf(currProgress).length(),
//                new int[]{getResources().getColor(R.color.color_bg_small_start), getResources().getColor(R.color.color_bg_small_end)},
//                null, Shader.TileMode.MIRROR);
//        Matrix matrix = new Matrix();
//        matrix.setRotate(180);
//        mMiddleTextShader.setLocalMatrix(matrix);
//        middleTxtPaint.setShader(mMiddleTextShader);
//    }

    /**
     * 初始化瞄点的画笔
     */
    private void initPointPaint() {
        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setColor(Color.WHITE);
        mPointerPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 初始化瞄点的画笔
     */
    private void initPointPaintSmall() {
        mPointerSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerSmallPaint.setColor(Color.parseColor("#80ff4157"));
        mPointerSmallPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 初始化刻度文字信息
     */
    private void initTextPaint() {
        mScallValueTextPaint = new Paint();
        mScallValueTextPaint.setTextSize(mTextSize);
        mScallValueTextPaint.setColor(mTextColor);
    }

    protected void initForePaint() {
        forePaint = new Paint();
        setCommonPaint(forePaint, isSolid, isCircleCorner);
    }


    protected void initBgPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(bgPaintColor);
        setCommonPaint(bgPaint, isSolid, isCircleCorner);
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

    protected void valueAnimator(final float originProgress, final float endProgress) {
        ValueAnimator mValueAnim = ValueAnimator.ofInt(1);
        mValueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator mAnim) {
                float fraction = mAnim.getAnimatedFraction();
                Integer newProgress = evaluate(fraction, (int) originProgress, (int) endProgress);
                currProgress = newProgress;
                //防止超越最大值
                if (currProgress >= 0 && currProgress <= maxProgress) {
//                    invalidate();
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        invalidate();
                    } else {
                        postInvalidate();
                    }
                }
            }
        });

        mValueAnim.setInterpolator(new OvershootInterpolator());
        int duration = (int) (ANIMATION_DURATION * (Math.abs((endProgress - originProgress)) / maxProgress));
        mValueAnim.setDuration(duration);
        mValueAnim.start();
    }

    protected Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    private float angle;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(progressInitialPosition, mCenter.x, mCenter.y);//旋转开始的角度
        canvas.drawArc(mPaintRectF, 0, maxAngle, isSolid, bgPaint);//画一个背景色
        //画一个前景色//根据当前进度和总进度诚意总的角度
        angle = (float) currProgress / maxProgress * (maxAngle);
        canvas.drawArc(mPaintRectF, 0, angle, isSolid, forePaint);
        //已经旋转progressInitialPosition 135，所以文字的旋转要在加90
        //===========================刻度值
        for (int i = 0; i < mScallValueCount; i++) {
            canvas.save();// 保存当前画布
//            canvas.rotate(-progressInitialPosition - mScallStarteAngle + (maxAngle / (mScallValueCount - 1) * i ), mCenter.x, mCenter.y);
            canvas.rotate((360 - maxAngle) + (maxAngle / (mScallValueCount - 1) * i), mCenter.x, mCenter.y);
            mScaleValueTxt = String.valueOf(i * (maxProgress) / (mScallValueCount - 1));
            mScallValueTxtWidth = mScallValueTextPaint.measureText(mScaleValueTxt, 0, mScaleValueTxt.length());
//            canvas.drawText(mScaleValueTxt + "", mCenter.x - mScallValueTxtWidth / 2, mCenter.y + mHeight / 2 - getDpValue(15) - mStrokeWidth, mScallValueTextPaint);
            canvas.drawText(mScaleValueTxt + "", mCenter.x - mScallValueTxtWidth / 2, mCenter.y - mHeight / 2 + getDpValue(20) + mStrokeWidth, mScallValueTextPaint);
            canvas.restore();//
        }
        //===========================刻度值

        //===========================刻度线
        float routeDegress = maxAngle / (tickMarkCount);
        for (int i = 0; i < tickMarkCount + 1; i++) {
            if (i != 0) {
                canvas.rotate(-routeDegress, mCenter.x, mCenter.y);
            }
            if (i % 5 == 0) {
                canvas.drawLine(startPoints[1].x, startPoints[1].y, stopPoints[1].x, stopPoints[1].y, tickPaint);
            } else {
                canvas.drawLine(startPoints[0].x, startPoints[0].y, stopPoints[0].x, stopPoints[0].y, tickPaint);
            }
        }
        //===========================刻度线
        canvas.rotate(-360 + maxAngle, mCenter.x, mCenter.y);
//        //画锚点
        canvas.drawCircle(mWheelCurX, mWheelCurY, 30, mPointerPaint);
        canvas.drawCircle(mWheelCurX, mWheelCurY, 20, mPointerSmallPaint);

//        canvas.save();
//        mCurrProgressTxt = String.valueOf(currProgress);
//        mCurrProgressTxtWidth = middleTxtPaint.measureText(mCurrProgressTxt, 0, mCurrProgressTxt.length());
//        canvas.rotate(-progressInitialPosition, mCenter.x, mCenter.y);//旋转开始的角度
//        canvas.drawText(currProgress + "", mCenter.x - mCurrProgressTxtWidth/2, mCenter.y , middleTxtPaint);
//        canvas.restore();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);

        refershUnreachedWidth();
        refershPosition();

    }

    private float mUnreachedRadius;

    /**
     * 得到圆环的半径
     */
    private void refershUnreachedWidth() {
        mUnreachedRadius = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2 - mStrokeWidth;
    }


    private double mCurAngle;

    /**
     * 更新角度
     */
    private void refershPosition() {
        mCurAngle = (double) currProgress / maxProgress * 270.0 + 90;
        double cos = -Math.cos(Math.toRadians(mCurAngle));
        refershWheelCurPosition(cos);
    }

    //瞄点的x和y
    protected float mWheelCurX, mWheelCurY;

    /**
     * 根据cos获取瞄点的x和y
     *
     * @param cos
     */
    private void refershWheelCurPosition(double cos) {
        mWheelCurX = calcXLocationInWheel(mCurAngle, cos);
        mWheelCurY = calcYLocationInWheel(cos);
    }

    private float calcYLocationInWheel(double cos) {
        return getMeasuredWidth() / 2 + mUnreachedRadius * (float) cos;
    }

    private float calcXLocationInWheel(double angle, double cos) {
        if (angle < 180) {
            return (float) (getMeasuredWidth() / 2 + Math.sqrt(1 - cos * cos) * mUnreachedRadius);
        } else {
            return (float) (getMeasuredWidth() / 2 - Math.sqrt(1 - cos * cos) * mUnreachedRadius);
        }
    }


    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext().getResources().getDisplayMetrics());
    }


    private int current = 0;

    public interface OnProgressChangeListener {
        void onProgressChanger(int progress);
    }

    public OnProgressChangeListener mOnProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    /**
     * 设置进度
     *
     * @param progress 0到maxProgress
     */
    public void setProgress(final int progress) {
        if (progress < 0 || progress > maxProgress) {
            throw new IllegalArgumentException("progress must >=0 && <=" + maxProgress + "，now progress is " + progress);
        }
        final int progressMin = progress / 20;
        current = 0;
        requestLayout();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (current < progress) {
                    current += progressMin;
                    setProgressAction(current);
                    delay();
                }
            }
        }.start();
    }

    private void setProgressAction(int progress) {
        currProgress = progress;
        Message message = Message.obtain();
        message.obj = progress;
        mMainHandler.sendMessage(message);
        refershPosition();
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currProgress = (int) msg.obj;
            if (mOnProgressChangeListener != null) {
                mOnProgressChangeListener.onProgressChanger(currProgress);
            }
        }
    };

    void delay() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
