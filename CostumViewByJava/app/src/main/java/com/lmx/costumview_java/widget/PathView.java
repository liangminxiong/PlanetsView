package com.lmx.costumview_java.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Created by lmx on 2020/12/12
 * Describe: 折线
 */
public class PathView extends CardiographView {


    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        // 重置path
        mPath.reset();

        //用path模拟一个心电图样式
        mPath.moveTo(0, mHeight / 2);
        int tmp = 0;
        for (int i = 0; i < 100; i++) {
            mPath.lineTo(tmp + 20, 100);
            mPath.lineTo(tmp + 70, mHeight / 2 + 50);
            mPath.lineTo(tmp + 80, mHeight / 2);

            mPath.lineTo(tmp + 200, mHeight / 2);
            tmp = tmp + 200;
        }
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath, mPaint);

        scrollBy(1, 0);
        postInvalidateDelayed(10);
    }
}
