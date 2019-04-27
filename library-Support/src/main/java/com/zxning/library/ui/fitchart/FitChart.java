/**
 * 自定义View的步骤:
 * 1.自定义View的属性
 * 2.在View的构造方法中获得我们自定义的属性
 * 3.重写onMesure(不是必须的,但是大多数情况下须重写)
 * 4.重写onDraw
 * <p/>
 * 自定义View的属性
 * 1.为自定义View自定义属性:在res/values/下建立attrs.xml 在里面的declare-styleable声明需要的属性.
 * 属性取值类型:string,color,demension,integer,enum,reference,float,boolean,fraction,flag;
 * 2.在Theme设置View的默认样式:在attr.xml中定义一个format为reference的属性att_a，在定义Theme时,
 * 为这个attribute指定一个Style，并在View的第二个构造函数中将R.attr.attr_a 作为第三个参数调用第三个构造函数.
 * 3.可以定义一个Style作为Theme中没有定义attr_a时View属性的默认值。
 * 4.可以在Theme中直接为属性赋值，但优先级最低
 * 5.当defStyleAttr（即View的构造函数的第三个参数）不为0且在Theme中有为这个attr赋值时，defStyleRes不起作用
 * 6.属性值定义的优先级(一个attribute在多个位置都定义了值,究竟哪一个起作用?)：
 * 布局xml定义>style定义>Theme中的Sytle定义>默认Style（通过obtainStyledAttributes的第四个参数指定）>
 * 在Theme中直接指定属性值.
 * 7.然后在布局中声明我们的自定义View(全类名),引入命名空间xmlns:app="http://schemas.android.com/apk/res-auto".
 * 8.在View的构造方法中，获得我们的自定义的样式
 */
package com.zxning.library.ui.fitchart;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.zxning.library.R;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class FitChart extends View {

    //默认圆的半径.
    static final int DEFAULT_VIEW_RADIUS = 0;

    //默认最小比值.
    static final int DEFAULT_MIN_VALUE = 0;

    //默认最大比值.
    static final int DEFAULT_MAX_VALUE = 100;

    //圆的起始角度.
    static final int START_ANGLE = -92;//-90

    //笔默认线宽.
    static final int DEFAULT_STROKE_WIDTH = 20;

    //笔的文字尺寸.
    static final int DEFAULT_TITLE_SIZE = 20;

    //动画时长.
    static final int ANIMATION_DURATION = 1000;

    //动画所占据初始弧度.
    static final float INITIAL_ANIMATION_PROGRESS = 0.0f;

    //最大划过弧度.
    static final float MAXIMUM_SWEEP_ANGLE = 360f;


    static final int DESIGN_MODE_SWEEP_ANGLE = 216;

    //控件中的可以绘制图形的矩形区域
    private RectF drawingArea;

    //圆的默认线条画笔(即无比例时画圆的画笔)default
    private Paint defaultStrokePaint;

    //
    private Paint valueDesignPaint;

    //圆的默认线条颜色(即无比例时圆的颜色)
    private int defaultStrokeColor;

    //圆环进度的线条颜色.
    private int valueStrokeColor;

    //圆环宽度即线条的粗度.
    private float strokeWidth;

    //默认最小比值.
    private float minValue = DEFAULT_MIN_VALUE;

    //默认最大比值.
    private float maxValue = DEFAULT_MAX_VALUE;

    //数据比例对象.
    private List<FitChartValue> chartValues;

    //动画所占弧度.
    private float animationProgress = INITIAL_ANIMATION_PROGRESS;

    //划过的最大弧度
    private float maxSweepAngle = MAXIMUM_SWEEP_ANGLE;

    //动画模式,默认(一次性的.)
    private AnimationMode animationMode = AnimationMode.LINEAR;

    //标题内容,标题笔.
    //private String title = "";
    //private Paint titlePaint;

    //标题文字大小.
    private float titleSize;

    //数字文字大小.
    private float numberSize;

    //数字内容.
    // private String number = "";

    //数字笔.
    //private Paint numberPaint;

    //代码中实例化时,调用该构造函数.
    public FitChart(Context context) {
        //调用第2个构造函数.
        this(context, null);
    }

    //xml布局中定义调用该构造函数
    public FitChart(Context context, AttributeSet attrs) {
        //调用第3个构造函数,传入defStyleAttr = 0表示不向Theme中搜索默认值.
        this(context, attrs, 0);
    }

    //系统不调用，要由View（我们自定义的或系统预定义的View）显式调用
    public FitChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartValues = new ArrayList<>();
        initializeBackground();
        readAttributes(attrs, defStyleAttr);
        preparePaints();
    }

    /*//api21版本及其以上才有此构造函数.
    public FitChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    /*
     当我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果，
     当我们设置为MATCH_PARENT,系统帮我们测量的结果就是MATCH_PARENT的长度。
    当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法：
    MeasureSpec的specMode,一共三种类型：
    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
    UNSPECIFIED：表示子布局想要多大就多大，很少使用
    */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);*/

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //获取宽和高中的最大值.
        int size = Math.max(width, height);
        setMeasuredDimension(size, size);//设置测量尺寸,也就是定义view的宽和高.
    }

    //布局发生变化时的回调函数，间接回去调用onMeasure, onLayout函数重新布局
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //每当布局变化变化的时候,就计算可以绘图的区域.
        calculateDrawableArea();
    }

    //绘制view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderbackgroundGraphical(canvas);
        //renderTitle(canvas);
        renderProportionArcs(canvas);
    }

    /**
     * ObjectAnimator是ValueAnimator的子类，他本身就已经包含了时间引擎和值计算，
     * 所以它拥有为对象的某个属性设置动画的功能。不再需要实现 ValueAnimator.AnimatorUpdateListener接口，
     * 因为ObjectAnimator动画自己会自动更新相应的属性值。
     * ObjectAnimator需要描叙该对象，需要设置动画的属性的名字(一个字符串)，以及动画属性值的变化范围:
     * ObjectAnimator anim = ObjectAnimator.ofFloat(foo, "alpha", 0f, 1f);
     * anim.setDuration(1000);
     * anim.start();
     * 为了使ObjectAnimator正确的更新属性值，你需要：
     * 1、你要设置动画的对象的属性必须有一个set该值的方法。因为ObjectAnimator在动画的过程中自动更新属性值，
     * 这是通过调用该属性的set方法来实现的。例如，如果属性的名字是foo，你需要有一个setFoo()的方法，
     * 如果不存在set方法，你可以有下面三个选择：
     * 1)、如果你有权限，你可以为该类添加一个set方法;
     * 2)、使用一个包裹类，通过该包裹类你可以去修改和获取属性值的变化，然后把它向前定向到原来的值
     * 3)、使用ValueAnimator类替换
     * 2、如果你在一个ObjectAnimator中只为属性值设置一个值，这个值被任务是动画的结束值。 这样的话，
     * 该对象必须有一个get方法来获取该属性值作为动画的起始值.get方法必须类似于get<属性名>.
     * 例如，如果属性的名字叫foo，你需要有一个getFoo()，方法。
     * 3、动画的属性值的gettter()方法(如果需要)和setter方法必须作用跟ObjectAnimator中的起始值是一个类型，
     * 例如如果你构造ObjectAnimator的方式是如下这样的，
     * 则该属性值的getter和setter方法必须如targetObject.setPropName(float) 和targetObject.getPropName(float)，即都是浮点型
     * ObjectAnimator.ofFloat(targetObject, "propName", 1f)
     * 4、依赖于你设置动画的对象和属性，你可能需要调用View的invalidate来强制屏幕重现绘制以及更新动画值。
     * 你可以在 onAnimationUpdate()中做这个工作。
     * 例如，为一个Drawable对象的颜色属性设置动画，你仅仅需要在该对象重绘的时候更新屏幕。
     * 所有View属性的set方法，例如setAlpha()和setTranslationX()自己会调用invalid方法，
     * 所以当这些属性值有更新时，你不需要再次调用invalid方法。要获取更多关于监听器的信息。你可以查看监听器章节。
     */
    private void playAnimation() {
        //1:被释放动画的对象.2:动画名字.3:
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animationSeek", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        /**
         * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
         AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速
         AnticipateInterpolator 开始的时候向后然后向前甩
         AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
         BounceInterpolator 动画结束的时候弹起
         CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
         DecelerateInterpolator 在动画开始的地方快然后慢
         LinearInterpolator 以常量速率改变
         OvershootInterpolator 向前甩一定值后再回到原来位置
         */
        //设置动画速度.
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setTarget(this);
        animatorSet.play(animator);
        animatorSet.start();
    }

    //设置动画属性值:animationSeek
    void setAnimationSeek(float value) {
        animationProgress = value;
        Log.i(this.getClass().getName(), "animationProgress--->" + animationProgress);
        //请求重绘View树，即draw()过程
        //的invalidate()方法中,系统会自动调用 View的onDraw()方法。
        invalidate();
    }

    //初始化控件背景.
    private void initializeBackground() {
        //若是非编辑模式.
        if (!isInEditMode()) {
            //如果当前控件背景为null
            if (getBackground() == null) {
                //设置背景为透明色.
                //setBackgroundColor(UIUtils.getColor(R.color.default_bg_color));
                setBackgroundColor(UIUtils.getColor(R.color.c_ff99cc00));
            }
        }

    }

    //读取xml定义的属性.
    private void readAttributes(AttributeSet attrs, int defStyleAttr) {

        //1:xml属性集,2:属性id数组,3:主题中指定的样式属性.4:默认的样式属性.
        TypedArray attributes = getContext()
                .getTheme().obtainStyledAttributes(attrs, R.styleable.FitChart, defStyleAttr, 0);
        strokeWidth = attributes
                .getDimensionPixelSize(R.styleable.FitChart_strokeWidth, DEFAULT_STROKE_WIDTH);
        titleSize = attributes.getDimension(R.styleable.FitChart_titleSize, DEFAULT_TITLE_SIZE);
        numberSize = titleSize * 1.5f;
        defaultStrokeColor = attributes
                .getColor(R.styleable.FitChart_backStrokeColor, UIUtils.getColor(R.color.c_transparent));
        valueStrokeColor = attributes
                .getColor(R.styleable.FitChart_valueStrokeColor, UIUtils.getColor(R.color.c_ff3d00));

        int attrAnimationMode = attributes.getInteger(R.styleable.FitChart_animationMode, AnimationCode.LINEAR.code);
        if (attrAnimationMode == AnimationCode.LINEAR.code) {
            animationMode = AnimationMode.LINEAR;
        } else {
            animationMode = AnimationMode.OVERDRAW;
        }
        attributes.recycle();
    }

    //准备画笔.
    private void preparePaints() {
        defaultStrokePaint = getPaint();
        //设置笔色和笔样式和笔画线宽.
        defaultStrokePaint.setColor(defaultStrokeColor);
        //Paint.Style.STROKE(空心圆),FILL(实心圆),FILL_AND_STROKE(实心圆).
        defaultStrokePaint.setStyle(Paint.Style.STROKE);
        defaultStrokePaint.setStrokeWidth(strokeWidth);

        //画标题的笔
//        titlePaint = getPaint();
//        //设置笔的线宽,颜色,字体大小,字体形状.
//        titlePaint.setStrokeWidth(0);
//        titlePaint.setColor(Color.BLACK);
//        titlePaint.setTextSize(titleSize);
//        titlePaint.setTypeface(Typeface.DEFAULT_BOLD);

        //数字.
//        numberPaint = getPaint();
//        numberPaint.setStrokeWidth(0);
//        numberPaint.setColor(Color.BLACK);
//        numberPaint.setTextSize(numberSize);
//        numberPaint.setTypeface(Typeface.DEFAULT);


        //画进度的笔.
        valueDesignPaint = getPaint();
        //设置笔色,笔样式,笔的结尾形状样式,笔宽度.
        valueDesignPaint.setColor(valueStrokeColor);
        valueDesignPaint.setStyle(Paint.Style.STROKE);
        //Paint.Cap.SQUARE(平口超出),BUTT(平齐),ROUND(圆形超出).
        //valueDesignPaint.setStrokeCap(Paint.Cap.BUTT);
        valueDesignPaint.setStrokeWidth(strokeWidth);
    }

    //设置最小进度值.
    public void setMinValue(float value) {
        minValue = value;
    }

    //设置最大进度值.
    public void setMaxValue(float value) {
        maxValue = value;
    }

    //获取最小进度值得.
    public float getMinValue() {
        return minValue;
    }

    //获取最大比例值.
    public float getMaxValue() {
        return maxValue;
    }

    //设置一条数据比例.
    public void setValue(float value) {
        chartValues.clear();
        FitChartValue chartValue = new FitChartValue(value, valueStrokeColor);
        chartValue.setPaint(buildPaintForValue(strokeWidth));
        chartValue.setStartAngle(START_ANGLE);
        chartValue.setSweepAngle(calculateSweepAngle(value));
        chartValues.add(chartValue);
        maxSweepAngle = chartValue.getSweepAngle();
        playAnimation();
    }

    public void setAnimationMode(AnimationMode mode) {
        this.animationMode = mode;
    }

    //计算绘画区域.
    private void calculateDrawableArea() {
        float drawPadding = (strokeWidth / 2);
        float left = drawPadding;
        float top = drawPadding;
        float right = getWidth() - drawPadding;
        float bottom = getHeight() - drawPadding;
        //通过坐标,创建出可绘制的矩形区域.
        drawingArea = new RectF(left, top, right, bottom);
    }

    //获取画笔.
    private Paint getPaint() {
        if (!isInEditMode()) {
            //非编辑模式下,创建图形保真的画笔.
            return new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            //编辑模式下,创建普通画笔.
            return new Paint();
        }
    }

    //获取圆形的半径.
    private float getViewRadius() {
        if (drawingArea != null) {
            return (drawingArea.width() / 2);
        } else {
            return DEFAULT_VIEW_RADIUS;
        }
    }

    //画出默认颜色的背景圆形.
    private void renderbackgroundGraphical(Canvas canvas) {    //backgroundGraphical
        //创建路径轨迹.
        Path path = new Path();
        float viewRadius = getViewRadius();
        //路径添加图形轨迹.Path.Direction.CW( 顺时针),Path.Direction.CCW( 逆时针).
        path.addCircle(drawingArea.centerX(), drawingArea.centerY(), viewRadius, Path.Direction.CW);
        canvas.drawPath(path, defaultStrokePaint);
    }

    //画出所有的比例的弧度.
    private void renderProportionArcs(Canvas canvas) {//ProportionArc
        if (!isInEditMode()) {
            /*int valuesCounter = (chartValues.size() - 1);
            for (int index = valuesCounter; index >= 0; index--) {
                renderProportionArc(canvas, chartValues.get(index));
            }*/
            for (int i = 0; i < chartValues.size(); i++) {
                renderProportionArc(canvas, chartValues.get(i));
            }
        } else {
            renderProportionArc(canvas, null);
        }
    }

    //画出莫衷比例的弧度.
    private void renderProportionArc(Canvas canvas, FitChartValue value) {
        if (!isInEditMode()) {
            float animationSeek = calculateAnimationSeek();
            Renderer renderer = RendererFactory.getRenderer(animationMode, value, drawingArea);
            Path path = renderer.buildPath(animationProgress, animationSeek);
            if (path != null) {
                canvas.drawPath(path, value.getPaint());
            }
        } else {
            Path path = new Path();
            path.addArc(drawingArea, START_ANGLE, DESIGN_MODE_SWEEP_ANGLE);
            canvas.drawPath(path, valueDesignPaint);
        }
    }

    //计算动画进度弧度
    private float calculateAnimationSeek() {
        return ((maxSweepAngle * animationProgress) + START_ANGLE);
    }

    //根据百分比来计算划过的弧度.
    private float calculateSweepAngle(float value) {
        float chartValuesWindow = Math.max(minValue, maxValue) - Math.min(minValue, maxValue);
        float chartValuesScale = (360f / chartValuesWindow);
        return (value * chartValuesScale);
    }


    /**
     * 比例展示.
     *
     * @param sumAmount    总数量所占比例.
     * @param usableAmount 可用数量所占比例.
     */
    public void showProportionDivisionCircle(float sumAmount, float usableAmount) {
        float usedAmount = sumAmount - usableAmount;
        Resources resources = getResources();
        List<FitChartValue> values = new ArrayList<>();
        //float mouthProgress = 1f;
        float mouthProgress = 10f;
        //float usedAmountProgress = (usedAmount / sumAmount) * 98f;
        float usedAmountProgress = (usedAmount / sumAmount) * 80f;
        if ((sumAmount - usableAmount) > 0 && usableAmount != 0 && usableAmount != -1) {
            values.clear();
            values.add(new FitChartValue(mouthProgress, resources.getColor(R.color.c_f8f8f9)));
            values.add(new FitChartValue(usedAmountProgress, resources.getColor(R.color.c_ff3d00)));
            values.add(new FitChartValue(mouthProgress, resources.getColor(R.color.c_3183c9)));
        } else if ((sumAmount - usableAmount) == 0 && usableAmount != 0) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.c_3b8ed4)));
        } else if (usableAmount == 0) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.c_ff3d00)));
        } else if (usableAmount == -1) {
            values.clear();
            values.add(new FitChartValue(100f, resources.getColor(R.color.c_acacac)));
        }
        this.setValues(values);
    }

    //更新标题文字.
//    public void setTitle(String title) {
//        this.title = title;
//    }

    //数字文字.
//    public void setNumber(String number, boolean isRed) {
//        this.number = number;
//        if (isRed) {
//            numberPaint.setColor(Color.parseColor("#ff4444"));
//            numberSize = titleSize * 1.2f;
//            numberPaint.setTextSize(numberSize);
//        }
//    }

    //设置默认圆圈画笔的颜色.
    public void setbackStrokeColor(int backStrokeColor) {
        defaultStrokePaint.setColor(backStrokeColor);
    }

    //构建画白色弧的笔.
    private Paint buildPaintForValue() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.BUTT);
        return paint;
    }

    //构建画笔,用于画数据比例弧度.
    private Paint buildPaintForValue(float width) {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.BUTT);
        return paint;
    }

    //画出标题文字.
//    private void renderTitle(Canvas canvas) {
//        if (!TextUtils.isEmpty(number)) {
////            float titleWidth = titlePaint.measureText(title);
////            canvas.drawText(title,
////                    drawingArea.centerX() - titleWidth / 2,
////                    drawingArea.centerY() / 2 + titleSize / 2,
////                    titlePaint);
//            //画额度
////            float numberWidth = numberPaint.measureText(number);
////            canvas.drawText(number,
////                    drawingArea.centerX() - (numberWidth / 2f),
////                    drawingArea.centerY() + numberSize / 2f,
////                    numberPaint);
//        }
//    }

    //修改.setValues
    public void setValues(List<FitChartValue> values) {
        chartValues.clear();
        maxSweepAngle = 0;
        float offsetSweepAngle = START_ANGLE;
        for (int i = 0; i < values.size(); i++) {
            FitChartValue chartValue = values.get(i);
            float sweepAngle = calculateSweepAngle(chartValue.getValue());
            if (values.size() == 1) {
                chartValue.setPaint(buildPaintForValue());
            } else if (values.size() == 3) {
                if (i == 0 || i == 2) {
                    chartValue.setPaint(buildPaintForValue(strokeWidth + strokeWidth / 4));
                } else {
                    chartValue.setPaint(buildPaintForValue());
                }
            }
            chartValue.setStartAngle(offsetSweepAngle);
            chartValue.setSweepAngle(sweepAngle);
            chartValues.add(chartValue);
            offsetSweepAngle += sweepAngle;
            maxSweepAngle += sweepAngle;
        }
        playAnimation();
    }

    //枚举限定两种动画模式.
    public enum AnimationCode {

        //利用构造函数传参
        LINEAR(0), OVERDRAW(1);

        // 定义私有变量
        private int code;

        //构造函数，枚举类型只能为私有
        AnimationCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }
}