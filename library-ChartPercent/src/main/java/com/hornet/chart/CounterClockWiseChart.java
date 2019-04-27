package com.hornet.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改版本环形比例展示.
 */
public class CounterClockWiseChart extends View {


    //默认动画速度.
    public static final float ANIMATION_SPEED_DEFAULT = 4.0f;
    //慢速.
    public static final float ANIMATION_SPEED_SLOW = 1.0f;
    //快速.
    public static final float ANIMATION_SPEED_FAST = 10.0f;
    //正常速度.
    public static final float ANIMATION_SPEED_NORMAL = 3.5f;

    //数据容器.
    private List<MagnificentChartItem> chartItemsList;

    //各个数值总和.
    private float maxValue;

    //无用.
    private boolean isTitleShowing;

    //是否开启动画.
    private boolean isAnimated;

    //是否为扇形:true为扇形,false为环形.
    private boolean isRound;

    //是否显示阴影:true显示,false不显示.
    private boolean isShadowShowing;

    //阴影的颜色.
    private int shadowBackgroundColor;

    //控件的背景色.
    private int chartBackgroundColor;

    //上下文.
    private Context context;

    //无用.
    private Typeface typeface = null;

    //控件宽度.
    private int width;

    //控件高度.
    private int height;

    //动画速度.
    private float animationSpeed = 4f;

    //--当前所画出全部弧度.
    private float globalCurrentAngle = 0.0f;
    private int[] valueColors = {
            R.color.chart_item0,
            R.color.chart_item1,
            R.color.chart_item2,
            R.color.chart_item3,
            R.color.chart_item4,
            R.color.chart_item5,
            R.color.chart_item6,
            R.color.chart_item7,
            R.color.chart_item8,
            R.color.chart_item9,
            R.color.chart_item10,
            R.color.chart_item11
    };
    ;


    public CounterClockWiseChart(Context context) {
        super(context);

        init(context, null, 0, false, false, true, false, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public CounterClockWiseChart(Context context, List<MagnificentChartItem> itemsList, int maxValue) {
        super(context);
        init(context, itemsList, maxValue, false, false, true, false, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public CounterClockWiseChart(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow, boolean showTitle) {
        super(context);
        init(context, itemsList, maxValue, isAnimated, isRound, showShadow, showTitle, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public CounterClockWiseChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MagnificentChart, 0, 0);
        try {
            boolean isAnimated = typedArray.getBoolean(R.styleable.MagnificentChart_animation, false);
            boolean isRound = typedArray.getBoolean(R.styleable.MagnificentChart_round, false);
            boolean showShadow = typedArray.getBoolean(R.styleable.MagnificentChart_shadow, true);
            boolean showTitle = typedArray.getBoolean(R.styleable.MagnificentChart_showTitle, false);
            int shadowColor = typedArray.getColor(R.styleable.MagnificentChart_shadowColor, Color.parseColor("#F2F2F2"));
            int backgroundColor = typedArray.getColor(R.styleable.MagnificentChart_backColor, Color.parseColor("#FFFFFF"));

            init(context, null, 0, isAnimated, isRound, showShadow, showTitle, shadowColor, backgroundColor);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (width != height) {
            return;
        }

        if (this.isAnimated) {
            animatedDraw(canvas);
        } else {
            regularDraw(canvas);
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        this.width = width;
        this.height = height;
    }

    //属性初始化.
    private void init(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow, boolean showTitle, int shadowColor, int backgroundColor) {
        this.context = context;
        this.chartItemsList = itemsList;
        this.isAnimated = isAnimated;
        this.isRound = isRound;
        this.isShadowShowing = showShadow;
        this.shadowBackgroundColor = shadowColor;
        this.chartBackgroundColor = backgroundColor;
        this.maxValue = maxValue;
        this.isTitleShowing = showTitle;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = screenWidth;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        this.width = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = screenHeight;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        this.height = result;
        return result;
    }

    //设置是否开启动画.
    public void setAnimationState(boolean state) {
        this.isAnimated = state;
        invalidate();
    }

    //设置动画速度.
    public void setAnimationSpeed(float animationSpeed) { // use just value ANIMATION_SPEED_... from current class
        if (animationSpeed == ANIMATION_SPEED_DEFAULT || animationSpeed == ANIMATION_SPEED_SLOW || animationSpeed == ANIMATION_SPEED_FAST || animationSpeed == ANIMATION_SPEED_NORMAL) {
            this.animationSpeed = animationSpeed;
        } else {
            this.animationSpeed = ANIMATION_SPEED_DEFAULT;
        }
        invalidate();
    }

    //设置形状,true:实体,false:环形.
    public void setRound(boolean state) {
        this.isRound = state;
        invalidate();
    }

    //设置阴影,true:有,false无.
    public void setShadowShowingState(boolean state) {
        this.isShadowShowing = state;
        invalidate();
    }

    public boolean getAnimationState() {
        return this.isAnimated;
    }

    public boolean getRound() {
        return this.isRound;
    }

    public boolean getShadowShowingState() {
        return this.isShadowShowing;
    }

    public void setTitleShowingState(boolean state) {
        this.isTitleShowing = state;
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        invalidate();
    }

    public void setChartItemsList(List<MagnificentChartItem> itemsList) {
        this.chartItemsList = itemsList;
        invalidate();
    }

    public void setShadowBackgroundColor(int color) {
        this.shadowBackgroundColor = color;
        invalidate();
    }

    public void setChartBackgroundColor(int color) {
        this.chartBackgroundColor = color;
        invalidate();
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        invalidate();
    }

    private float getPercent(int value, int maxValue) {
        float result = (float) value / maxValue;
        return result;
    }

    private float getPercent(float value, float maxValue) {
        float result = (float) value / maxValue;
        return result;
    }

    //未开启动画的时候进行绘画.
    private void regularDraw(Canvas canvas) {
        Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);
        RectF rect = new RectF();
        rect.set(10, 10, width - 10, height - 10);
        drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);
        canvas.rotate(-90f, rect.centerX(), rect.centerY());

        if (this.chartItemsList != null && this.maxValue > 0) {
            drawItems(canvas, rect);
            canvas.rotate(90f, rect.centerX(), rect.centerY());
            drawInsideCircle(canvas, insideShadowPaint, insideChartPaint);
        }
    }

    //开启动画的时候进行绘画.
    private void animatedDraw(Canvas canvas) {
        Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);
        RectF rect = new RectF();
        rect.set(10, 10, width - 10, height - 10);
        //drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);
        //矩形绕中心旋转-90f.
        canvas.rotate(-90f, rect.centerX(), rect.centerY());

        if (chartItemsList != null && maxValue > 0) {
            drawItems(canvas, rect);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(chartBackgroundColor);
            RectF oval = new RectF();
            oval.set(8, 8, width - 8, height - 8);
            Path path = new Path();
            path.moveTo(oval.centerX(), oval.centerY());
            path.addRect(oval, Path.Direction.CCW);
            path.addArc(oval, 360.0f - globalCurrentAngle, globalCurrentAngle);
            path.lineTo(oval.centerX(), oval.centerY());
            //动画画出弧度.
            canvas.drawPath(path, paint);
            globalCurrentAngle += animationSpeed; // <-- animation speed
            //Log.i(this.getClass().getName(), "globalCurrentAngle---" + globalCurrentAngle);
            canvas.rotate(90f, rect.centerX(), rect.centerY());
            if (!isRound) {
                //画阴影部分.
                if (isShadowShowing) {
                    canvas.drawCircle(width / 2, height / 2, width / 4 - 10, insideShadowPaint);
                }
                canvas.drawCircle(width / 2, height / 2, width / 4 - 20, insideChartPaint);
            }

            if (globalCurrentAngle > 360f) {
                globalCurrentAngle = 0.0f;
                /*canvas.rotate(-90f, rect.centerX(), rect.centerY());
                drawItems(canvas, rect);
                canvas.rotate(90f, rect.centerX(), rect.centerY());
                drawInsideCircle(canvas, insideShadowPaint, insideChartPaint);*/
                return;
            }
            invalidate();
        }
    }

    private void drawInsideCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint) {
        if (!isRound) {
            if (isShadowShowing) {
                canvas.drawCircle(width / 2, height / 2, width / 4 - 10, insideShadowPaint);
            }
            canvas.drawCircle(width / 2, height / 2, width / 4 - 20, insideChartPaint);
        }
    }

    //画实体圆的背景.
    private void drawMainCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint, RectF mainRectangle) {
        if (isShadowShowing) {
            //画阴影.
            canvas.drawCircle(width / 2, height / 2, width / 2, insideShadowPaint);
        }
        canvas.drawArc(mainRectangle, 0f, 360f, true, insideChartPaint);
    }

    private void drawItems(Canvas canvas, RectF mainRectangle) {
        float startAngle = 0f;
        float anglesSum = 0f;
        Paint currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < chartItemsList.size(); ++i) {
            MagnificentChartItem currentItem = chartItemsList.get(i);
            int color = currentItem.color;
            float value = currentItem.fValue;
            float currentPercentValue = getPercent(value, maxValue);
            float currentAngle = currentPercentValue * -360;
            anglesSum += currentAngle;
            //currentPaint.setColor(color);
            currentPaint.setColor(getResources().getColor(color));
            canvas.drawArc(mainRectangle, startAngle, currentAngle, true, currentPaint);
            /*Log.i(this.getClass().getName(),"startAngle--"+startAngle+"currentAngle:"+(currentAngle));*/

            startAngle += currentAngle;
        }
    }

    /**
     * 依次按照数组比例分配.
     *
     * @param values
     */
    public void setItemPercent(float... values) {
        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        float maxValue = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                MagnificentChartItem item = new MagnificentChartItem(values[i], valueColors[i]);
                chartItemsList.add(item);
                maxValue += values[i];
            }
            Log.i(this.getClass().getName(), "Value--" + values[i]);
        }
        setChartItemsList(chartItemsList);
        setMaxValue(maxValue);
        Log.i(this.getClass().getName(), "maxValue--" + maxValue);
        setAnimationState(true);
    }
}
