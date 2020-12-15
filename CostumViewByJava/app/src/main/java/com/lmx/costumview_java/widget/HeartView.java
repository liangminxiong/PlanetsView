package com.lmx.costumview_java.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lmx on 2020/12/12
 * Describe: 心
 */
public class HeartView extends View {

    private int measureWidth;
    private int widthMode;
    private int measureHeight;
    private int heightMode;
    private Paint paint;


    public HeartView(Context context) {
        super(context);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化 画笔
    private void initPaint() {
        paint = new Paint();//实例画笔
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeWidth(2);//画笔宽度
        paint.setTextSize(40f);//文字大小
        paint.setColor(Color.RED);//画笔颜色
        paint.setStyle(Paint.Style.FILL);//画笔样式
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, measureHeight);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidth, 200);
        } else {
            setMeasuredDimension(measureWidth, measureHeight);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();//获取屏幕宽
        int height = getHeight();//获取屏幕高

        /**
         *  绘制心形
         */
        //左半面
        @SuppressLint("DrawAllocation") Path path = new Path();
        path.moveTo(width / 2, height / 4);
        path.cubicTo((width * 6) / 7, height / 9, (width * 12) / 13, (height * 2) / 5, width / 2, (height * 7) / 12);
        canvas.drawPath(path, paint);
        //右半面
        Path path2 = new Path();
        path2.moveTo(width / 2, height / 4);
        path2.cubicTo(width / 7, height / 9, width / 13, (height * 2) / 5, width / 2, (height * 7) / 12);
        canvas.drawPath(path2, paint);
        String text = "红心";
        Rect rect = getTextBounds(text, paint);
        canvas.drawText(text, 0, text.length(), width / 2 - rect.width() / 2, (height * 7) / 12 + rect.height() + 5, paint);
    }

    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
// 获取字体的显示范围，这个显示范围是只跟字体大小和字符长度有关的属性
// 不管字符是否被画出，这个显示范围的属性都存在
// 可以看到getTextBounds在drawText之前调用的，还是可以获得数据
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
