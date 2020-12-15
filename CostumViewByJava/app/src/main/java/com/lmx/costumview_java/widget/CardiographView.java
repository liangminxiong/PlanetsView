package com.lmx.costumview_java.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lmx on 2020/12/12
 * Describe:
 */
public class CardiographView extends View {

    //画笔
    public Paint mPaint;
    //折现的颜色
    public int mLineColor;
    //网格颜色
    public int mGridColor;
    //小网格颜色
    public int mSGridColor;
    //背景颜色
    public int mBackgroundColor;
    //自身的大小
    public int mWidth, mHeight;
    //网格宽度
    public int mGridWidth = 60;
    //小网格的宽度
    public int mSGridWidth = 20;
    //心电图折现
    public Path mPath;

    public CardiographView(Context context) {
        super(context);
    }

    public CardiographView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintOrColor();
    }

    public CardiographView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaintOrColor() {
        mPaint = new Paint();
        mPath = new Path();
        mLineColor = Color.parseColor("#76f112");
        mGridColor = Color.parseColor("#1b4200");
        mSGridColor = Color.parseColor("#092100");
        mBackgroundColor = Color.BLACK;
    }

//    private int measureWidth, widthMode, measureHeight, heightMode;
//    /**
//     * 测量
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
//        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, 200);
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, measureHeight);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(measureWidth, 200);
//        } else {
//            setMeasuredDimension(measureWidth, measureHeight);
//        }
//    }

    /**
     * 不测量直接拿
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initBackground(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //画背景
    private void initBackground(Canvas canvas) {

        canvas.drawColor(mBackgroundColor);
        //画小网格

        //竖线个数
        int vSNum = mWidth / mSGridWidth;

        //横线个数
        int hSNum = mHeight / mSGridWidth;
        mPaint.setColor(mSGridColor);
        mPaint.setStrokeWidth(2);
        //画竖线
        for (int i = 0; i < vSNum + 1; i++) {
            canvas.drawLine(i * mSGridWidth, 0, i * mSGridWidth, mHeight, mPaint);
        }
        //画横线
        for (int i = 0; i < hSNum + 1; i++) {

            canvas.drawLine(0, i * mSGridWidth, mWidth, i * mSGridWidth, mPaint);
        }

        //竖线个数
        int vNum = mWidth / mGridWidth;
        //横线个数
        int hNum = mHeight / mGridWidth;
        mPaint.setColor(mGridColor);
        mPaint.setStrokeWidth(2);
        //画竖线
        for (int i = 0; i < vNum + 1; i++) {
            canvas.drawLine(i * mGridWidth, 0, i * mGridWidth, mHeight, mPaint);
        }
        //画横线
        for (int i = 0; i < hNum + 1; i++) {
            canvas.drawLine(0, i * mGridWidth, mWidth, i * mGridWidth, mPaint);
        }
    }
}
