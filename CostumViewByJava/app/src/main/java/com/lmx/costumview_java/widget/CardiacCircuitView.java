package com.lmx.costumview_java.widget;//package com.lmx.mvvm;
//
//import android.animation.Animator;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//import android.view.View;
//import android.graphics.RectF;
//import android.animation.ValueAnimator;
//import android.view.animation.DecelerateInterpolator;
//
//import androidx.annotation.Nullable;
//
///**
// * Created by lmx on 2020/12/12
// * Describe: 人 心脏跳动
// */
//public class CardiacCircuitView extends View {
//
//    //xy坐标轴颜色
//    private int xylinecolor;
//    //xy坐标轴宽度
//    private float xylinewidth;
//    //xy坐标轴文字颜色
//    private int xytextcolor;
//    //xy坐标轴文字大小
//    private float xytextsize;
//    //折线图中折线的颜色
//    private int linecolor;
//    //x轴各个坐标点水平间距
//    private float interval;
//    //背景颜色
//    private int bgcolor;
//    //是否在ACTION_UP时，根据速度进行自滑动
//    private boolean isScroll;
//
//    //xy 箭头线画笔
//    private Paint xyPaint;
//    //xy 红色画笔
//    private Paint xyRedPaint;
//    //线画笔
//    private Paint linePaint;
//    //xy 坐标文本画笔
//    private Paint xyTextPaint;
//    // 绘制正常绿色区域画笔
//    private Paint okAreaPaint;
//
//    public CardiacCircuitView(Context context) {
//        super(context);
//    }
//
//    public CardiacCircuitView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public CardiacCircuitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initAttrs(context, attrs, defStyleAttr);
//    }
//
//    //1 使用自定义属性
//    @SuppressLint("NonConstantResourceId")
//    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.chartView, defStyleAttr, 0);
//        int count = array.getIndexCount();
//        for (int i = 0; i < count; i++) {
//            int attr = array.getIndex(i);
//            switch (attr) {
//                case R.styleable.chartView_xylinecolor://xy坐标轴颜色
//                    xylinecolor = array.getColor(attr, xylinecolor);
//                    break;
//                case R.styleable.chartView_xylinewidth://xy坐标轴宽度
//                    xylinewidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xylinewidth, getResources().getDisplayMetrics()));
//                    break;
//                case R.styleable.chartView_xytextcolor://xy坐标轴文字颜色
//                    xytextcolor = array.getColor(attr, xytextcolor);
//                    break;
//                case R.styleable.chartView_xytextsize://xy坐标轴文字大小
//                    xytextsize = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xytextsize, getResources().getDisplayMetrics()));
//                    break;
//                case R.styleable.chartView_linecolor://折线图中折线的颜色
//                    linecolor = array.getColor(attr, linecolor);
//                    break;
//                case R.styleable.chartView_interval://x轴各个坐标点水平间距
//                    interval = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, interval, getResources().getDisplayMetrics()));
//                    break;
//                case R.styleable.chartView_bgcolor: //背景颜色
//                    bgcolor = array.getColor(attr, bgcolor);
//                    break;
//                case R.styleable.chartView_isScroll://是否在ACTION_UP时，根据速度进行自滑动
//                    isScroll = array.getBoolean(attr, isScroll);
//                    break;
//            }
//        }
//        array.recycle();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvasX(canvas);
//        canvasY(canvas);
//        drawOkArea(canvas);
//    }
//
//
//    //2.1 绘制X轴以及上方的水平虚线，水平轴的时间文字及描点
//    private void canvasX(Canvas canvas) {
//        //绘制X轴坐标
//        canvas.drawLine(xOri, yOri + xylinewidth / 2, width, yOri + xylinewidth / 2, xyPaint);
//        //绘制x轴刻度
//        for (int i = 0; i < xValue.size(); i++) {
//            float x = xInit + interval * i;
//            if (i == 0 && x - interval >= xOri) {
//                String text = TimeUtil.getChartTime(0);
//                Rect rect = getTextBounds(text, xyTextPaint);
//                canvas.drawText(text, 0, text.length(), x - interval - rect.width() / 2, yOri + xylinewidth + dpToPx(4) + rect.height(), xyTextPaint);
//                canvas.drawCircle(xOri, yOri, radiu, linePaint);
//            }
//            if (x >= xOri) {//只绘制从原点开始的区域
//                xyTextPaint.setColor(xytextcolor);
//                canvas.drawLine(x, yOri - yLength * (yValue.size() - 1) + xylinewidth / 2, x, yOri, (i + 1) % 3 == 0 ? xyPaint : xyRedPaint);
//                //绘制X轴文本
//                if ((i + 1) % 3 == 0) {
//                    String text = TimeUtil.getChartTime((i + 1) * perLengTime);
//                    Rect rect = getTextBounds(text, xyTextPaint);
//                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2 - dpToPx(2), yOri + xylinewidth + dpToPx(4) + rect.height(), xyTextPaint);
//                    canvas.drawCircle(x, yOri, radiu, linePaint);
//                }
//
//            }
//        }
//    }
//
//
//    //2.2 绘制Y轴以及上方的水平虚线，水平轴的心率数值文字及描点。
//    private void canvasY(Canvas canvas) {
//        canvas.drawLine(xOri - xylinewidth / 2, yOri - yLength * (yValue.size() - 1), xOri - xylinewidth / 2, yOri, xyPaint);
//        for (int i = 0; i < yValue.size(); i++) {
//            //绘制Y轴刻度
//            canvas.drawLine(xOri, yOri - yLength * i + xylinewidth / 2, width, yOri - yLength * i + xylinewidth / 2, xyPaint);
//            canvas.drawCircle(xOri, yOri - yLength * i, radiu, linePaint);
//            if (i > 0) {
//                canvas.drawLine(xOri, yOri - yLength * i + xylinewidth / 2 + 1.0f / 3 * yLength, width, yOri - yLength * i + xylinewidth / 2 + 1.0f / 3 * yLength, xyRedPaint);
//                canvas.drawLine(xOri, yOri - yLength * i + xylinewidth / 2 + 2.0f / 3 * yLength, width, yOri - yLength * i + xylinewidth / 2 + 2.0f / 3 * yLength, xyRedPaint);
//            }
//            xyTextPaint.setColor(xytextcolor);
//            //绘制Y轴文本
//            String text = yValue.get(i) + "";
//            Rect rect = getTextBounds(text, xyTextPaint);
//            canvas.drawText(text, 0, text.length(), xOri - xylinewidth - dpToPx(3) - rect.width(), yOri - yLength * i + rect.height() / 2, xyTextPaint);
//            int length = xValue.size() / 12;
//            if (length > 0) {
//                for (int j = 1; j <= length; j++) {
//                    float x1 = xInit + interval * j * 12;
//                    if (x1 > xOri + interval * 6)
//                        canvas.drawText(text, 0, text.length(), x1 - interval - rect.width() - dpToPx(3), yOri - yLength * i + rect.height() / 2, xyTextPaint);
//                }
//            }
//        }
//    }
//
//
//    //3 绘制心率单位
//    private void drawUnit(Canvas canvas, int yLength) {
//        xyTextPaint.setTextSize(dpToPx(6));
//        String text = "FHR/bpm";
//        Rect rect = getTextBounds(text, xyTextPaint);
//        //第一竖排的单位
//        canvas.drawText("FHR/bpm", 0, text.length(), xOri - xylinewidth - dpToPx(3) - rect.width(), yOri - yLength * (1.0f * (yValue.size() - 1) / 2) + rect.height() / 2, xyTextPaint);
//        //后面竖排的单位
//        int length = xValue.size() / 12;
//        if (length > 0) {
//            for (int j = 1; j <= length; j++) {
//                float x1 = xInit + interval * j * 12;
//                if (x1 > xOri + interval * 6)
//                    canvas.drawText(text, 0, text.length(), x1 - interval - rect.width() - dpToPx(3), yOri - yLength * (1.0f * (yValue.size() - 1) / 2) + rect.height() / 2, xyTextPaint);
//            }
//        }
//    }
//
//    //4 绘制正常绿色区域（120-160心率)
//    private void drawOkArea(Canvas canvas) {
//        RectF f = new RectF();
//        f.top = yOri - yLength * (4 + 1.0f / 3);
//        f.bottom = yOri - yLength * 3;
//        f.left = xOri;
//        f.right = width;
//        canvas.drawRect(f, okAreaPaint);
//    }
//
//
//    //5 绘制折线图
//    private void drawBrokenLine(Canvas canvas) {//perLengTime初始为5s 如果value的长度超过一分钟 那么需要改变perlengTime为20s
//        if (value.size() < 2) return;
//        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setColor(linecolor);
//        //绘制折线
//        Path path = new Path();
//        float x = xInit - interval + interval * (betweemTime + base) / perLengTime;
//        float y = yOri - yOri * (1 - 0.1f) * (value.get(0) - yValue.get(0)) / (yValue.get(yValue.size() - 1) - yValue.get(0));
//        path.moveTo(x, y);
//        for (int i = 1; i < value.size(); i += 3) {
//            x = getXLength(i);
//            y = getYLength(i);
//            if (isContinuous) {
//                setContinuousPath(i, path, x, y);
//            } else {
//
//                setNoContinuousPath(i, path, x, y);
//            }
//        }
//        linePaint.setStrokeWidth(dpToPx(1));
//        canvas.drawPath(path, linePaint);
//    }
//
//
//    //6 最后一个心率动态打点
//    private void drawSelectPointLinePoint(Canvas canvas) {
//        if (value.size() == 0) return;
//        int position = value.size() - 1;
//        float dp7 = dpToPx(5);
//        float x = xInit - interval + 1.0f * interval * ((position + 1) * betweemTime + base) / perLengTime;
//        float y = yOri - yOri * (1 - 0.1f) * (value.get(position) - yValue.get(0)) / (yValue.get(yValue.size() - 1) - yValue.get(0));
//        linePaint.setStyle(Paint.Style.FILL);
//        linePaint.setColor(markColor);
//        linePaint.setStrokeWidth(dpToPx(1));
//        canvas.drawCircle(x, y, dp7, linePaint);
//        linePaint.setStrokeWidth(dpToPx(2));
//        canvas.drawLine(x, 0, x, getHeight(), linePaint);
//    }
//
//
//    //7 处理滑动事件和点击事件
//    private float startX, x1, y1, x2, y2;
//    boolean isTouch;
//    boolean isClick;
//    //是否正在滑动
//    private boolean isScrolling = false;
//    private VelocityTracker velocityTracker;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (isScrolling)
//            return super.onTouchEvent(event);
//        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
//        obtainVelocityTracker(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = event.getX();
//                isClick = true;
//                x1 = event.getX();
//                y1 = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (interval * xValue.size() > width - xOri) {//当期的宽度不足以呈现全部数据
//                    float dis = event.getX() - startX;
//                    startX = event.getX();
//                    if (xInit + dis < minXInit) {
//                        xInit = minXInit;
//                    } else if (xInit + dis > maxXInit) {
//                        xInit = maxXInit;
//                    } else {
//                        xInit = xInit + dis;
//                    }
//
//
//                    invalidate();
//                }
//                isClick = false;
//                break;
//            case MotionEvent.ACTION_UP:
//                if (event.getX() + getLeft() < getRight() && event.getY() + getTop() < getBottom()) {
//                    x2 = event.getX();
//                    y2 = event.getY();
//                    if (Math.abs(x1 - x2) < 6 && Math.abs(y1 - y2) < 6) {
//                        if (onChartClickListener != null)
//                            onChartClickListener.onChartClick();
//                        return false;// 距离较小，当作click事件来处理
//                    }
//                }
//                scrollAfterActionUp();
//                this.getParent().requestDisallowInterceptTouchEvent(false);
//                recycleVelocityTracker();
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                this.getParent().requestDisallowInterceptTouchEvent(false);
//                recycleVelocityTracker();
//                break;
//        }
//        return true;
//    }
//
//    private void obtainVelocityTracker(MotionEvent event) {
//        if (null == velocityTracker) {
//            velocityTracker = VelocityTracker.obtain();
//        }
//        velocityTracker.addMovement(event);
//    }
//
//    private void recycleVelocityTracker() {
//        if (null != velocityTracker) {
//            velocityTracker.recycle();
//            velocityTracker = null;
//        }
//    }
//
//    /**
//     * 手指抬起后的滑动处理
//     */
//    private void scrollAfterActionUp() {
//        if (!isScroll)
//            return;
//        final float velocity = getVelocity();
//        float scrollLength = maxXInit - minXInit;
//        if (Math.abs(velocity) < 10000)//10000是一个速度临界值，如果速度达到10000，最大可以滑动(maxXInit - minXInit)
//            scrollLength = (maxXInit - minXInit) * Math.abs(velocity) / 10000;
//        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
//        animator.setDuration((long) (scrollLength / (maxXInit - minXInit) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float value = (float) valueAnimator.getAnimatedValue();
//                if (velocity < 0 && xInit > minXInit) {//向左滑动
//                    if (xInit - value <= minXInit)
//                        xInit = minXInit;
//                    else
//                        xInit = xInit - value;
//                } else if (velocity > 0 && xInit < maxXInit) {//向右滑动
//                    if (xInit + value >= maxXInit)
//                        xInit = maxXInit;
//                    else
//                        xInit = xInit + value;
//                }
//                invalidate();
//            }
//        });
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                isScrolling = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                isScrolling = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                isScrolling = false;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//            }
//        });
//        animator.start();
//    }
//
//    /**
//     * 获取速度
//     *
//     * @return
//     */
//    private float getVelocity() {
//        if (velocityTracker != null) {
//            velocityTracker.computeCurrentVelocity(1000);
//            return velocityTracker.getXVelocity();
//        }
//        return 0;
//    }
//
//
//    private int dpToPx(int dp) {
//        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()) + 0.5f);
//    }
//
//    private Rect getTextBounds(String text, Paint paint) {
//        Rect rect = new Rect();
//// 获取字体的显示范围，这个显示范围是只跟字体大小和字符长度有关的属性
//// 不管字符是否被画出，这个显示范围的属性都存在
//// 可以看到getTextBounds在drawText之前调用的，还是可以获得数据
//        paint.getTextBounds(text, 0, text.length(), rect);
//        return rect;
//    }
//
//}
