package com.lmx.costumview_java.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lmx on 2020/12/12
 * Describe: 网格
 */
public class GridView extends View {

    private static final String TAG = "GridView";

    //画笔
    public Paint mPaint;
    //网格颜色
    public int mGridColor;

    //背景颜色
    public int mBackgroundColor;
    //自身的大小
    public int mWidth, mWidthMode, mHeight, mHeightMode;
    //网格横向数量
    public int mHGridCount = 12;
    //网格纵向数量
    public int mVGridCount = 4;

    public int mGridWidth = 80;

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintOrColor();
    }

    public GridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaintOrColor() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStrokeWidth(2);//画笔宽度
        mPaint.setTextSize(60f);//文字大小
        mPaint.setColor(Color.RED);//画笔颜色
        mPaint.setStyle(Paint.Style.FILL);//画笔样式
        mGridColor = Color.parseColor("#ffffff");
        mBackgroundColor = Color.BLACK;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public void setGridColor(int gridColor) {
        this.mGridColor = gridColor;
    }

    public void setHGridCount(int gridCount) {
        this.mHGridCount = gridCount;
    }

    public void setVGridCount(int gridCount) {
        this.mVGridCount = gridCount;
    }

    public int getVGridCount() {
        return mVGridCount;
    }

    public int getViewHeight() {
        return mGridWidth * mVGridCount;
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mGridWidth = mWidth / mHGridCount;
        Log.i(TAG, "mWidthMode =" + mWidthMode + " mHeightMode =" + mHeightMode + " mVGridCount = " + mVGridCount);
//        if (mWidthMode == MeasureSpec.AT_MOST && mHeightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, 200);
//        } else if (mWidthMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, mHeight);
//        } else if (mHeightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(mWidth, 200);
//        } else {
//            setMeasuredDimension(mWidth, mHeight);
//        }
        setMeasuredDimension(mWidth, mGridWidth * mVGridCount);
    }

    /**
     * 不测量直接拿
     */
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        mWidth = w;
//        mHeight = h;
//        super.onSizeChanged(w, h, oldw, oldh);
//    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initBackground(canvas);
    }

    public void setData() {
        invalidate();
    }

    //画背景
    private void initBackground(Canvas canvas) {
        canvas.drawColor(mBackgroundColor);
        //竖线个数
        int vNum = mHGridCount + 2;
        mGridWidth = mWidth / mHGridCount;
        //横线个数
        int hNum = mVGridCount + 2;
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
        for (int i = 0; i < mHGridCount; i++) {
            for (int j = 0; j < mVGridCount; j++) {
                if (i % 2 == 0) {
                    mPaint.setColor(Color.WHITE);
                    setTxt(canvas, "小", mPaint, i, j);
                } else {
                    if (i % 5 == 0 || i % 3 == 0) {
                        mPaint.setColor(Color.RED);
                        setTxt(canvas, "中", mPaint, i, j);
                    } else {
                        mPaint.setColor(Color.YELLOW);
                        setTxt(canvas, "大", mPaint, i, j);
                    }
                }
            }
        }
    }

    private void setTxt(Canvas canvas, String text, Paint paint, int indexX, int indexY) {
        Rect rect = getTextBounds(text, paint);
        float x = ((mGridWidth >> 1) - (rect.width() >> 1) - 2) + (indexX * mGridWidth);
        float y = (mGridWidth - (rect.height() >> 1)) + (indexY * mGridWidth);
        Log.i(TAG, "mWidth =" + mGridWidth + " rect.width() =" + rect.width() + "  rect.height()=" + rect.height() + " x==" + x + " y==" + y);
        canvas.drawText(text, 0, text.length(), x, y, paint);
    }

    // 获取字体的显示范围，这个显示范围是只跟字体大小和字符长度有关的属性
    // 不管字符是否被画出，这个显示范围的属性都存在
    // 可以看到getTextBounds在drawText之前调用的，还是可以获得数据
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

}
