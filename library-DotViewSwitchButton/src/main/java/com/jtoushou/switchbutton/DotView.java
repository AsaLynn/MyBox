package com.jtoushou.switchbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 开发自定义控件的步骤:
 * 1、了解View的工作原理
 * 2、 编写继承自View的子类
 * 3、 为自定义View类增加属性
 * 4、 绘制控件
 * 5、 响应用户消息
 * 6 、自定义回调函数
 * 一、View结构原理
 * Android系统的视图结构的设计也采用了组合模式，即View作为所有图形的基类，
 * Viewgroup对View继承扩展为视图容器类。
 * View定义了绘图的基本操作
 * 基本操作由三个函数完成：measure()、layout()、draw()，
 * 其内部又分别包含了onMeasure()、onLayout()、onDraw()三个子方法。具体操作如下：
 * 1、measure操作
 * measure操作主要用于计算视图的大小，即视图的宽度和长度。
 * 在view中定义为final类型，要求子类不能修改。measure()函数中又会调用下面的函数：
 * （1）onMeasure()，视图大小的将在这里最终确定，也就是说measure只是对onMeasure的一个包装，
 * 子类可以覆写onMeasure()方法实现自己的计算视图大小的方式，
 * 并通过setMeasuredDimension(width, height)保存计算结果。
 * 2、layout操作
 * layout操作用于设置视图在屏幕中显示的位置。
 * 在view中定义为final类型，要求子类不能修改。layout()函数中有两个基本操作：
 * （1）setFrame（l,t,r,b），l,t,r,b即子视图在父视图中的具体位置，该函数用于将这些参数保存起来；
 * （2）onLayout()，在View中这个函数什么都不会做，提供该函数主要是为viewGroup类型布局子视图用的；
 * 3、draw操作
 * draw操作利用前两部得到的参数，将视图显示在屏幕上，到这里也就完成了整个的视图绘制工作。
 * 子类也不应该修改该方法，因为其内部定义了绘图的基本操作：
 * （1）绘制背景；
 * （2）如果要视图显示渐变框，这里会做一些准备工作；
 * （3）绘制视图本身，即调用onDraw()函数。
 * 在view中onDraw()是个空函数，
 * 也就是说具体的视图都要覆写该函数来实现自己的显示（比如TextView在这里实现了绘制文字的过程）。
 * 而对于ViewGroup则不需要实现该函数，因为作为容器是“没有内容“的，
 * 其包含了多个子view，而子View已经实现了自己的绘制方法，
 * 因此只需要告诉子view绘制自己就可以了，也就是下面的dispatchDraw()方法;
 * （4）绘制子视图，即dispatchDraw()函数。
 * 在view中这是个空函数，具体的视图不需要实现该方法，它是专门为容器类准备的，也就是容器类必须实现该方法；
 * （5）如果需要（应用程序调用了setVerticalFadingEdge或者setHorizontalFadingEdge），开始绘制渐变框；
 * （6）绘制滚动条；
 * 从上面可以看出自定义View需要最少覆写onMeasure()和onDraw()两个方法。
 * 二、View类的构造方法
 * 创建自定义控件的3种主要实现方式:
 * 1）继承已有的控件来实现自定义控件: 主要是当要实现的控件和已有的控件在很多方面比较类似, 通过对已有控件的扩展来满足要求。
 * 2）通过继承一个布局文件实现自定义控件，一般来说做组合控件时可以通过这个方式来实现。
 * 注意此时不用onDraw方法，在构造广告中通过inflater加载自定义控件的布局文件，再addView(view)，自定义控件的图形界面就加载进来了。
 * 3）通过继承view类来实现自定义控件，使用GDI绘制出组件界面，一般无法通过上述两种方式来实现时用该方式。
 * 三、自定义View增加属性的两种方法：
 * 1）在View类中定义。通过构造函数中引入的AttributeSet 去查找XML布局的属性名称，然后找到它对应引用的资源ID去找值。
 * 案例：实现一个带文字的图片（图片、文字是onDraw方法重绘实现）
 * public MyView(Context context, AttributeSet attrs) {
 * super(context, attrs);
 * int textId = attrs.getAttributeResourceValue(null, "Text",0);
 * }
 * 2）通过XML为View注册属性。与Android提供的标准属性写法一样。
 * attrs.xml进行属性声明， 文件放在values目录下
 * TypedArray typedArray = context.obtainStyledAttributes(attrs,
 * R.styleable.MyImageView);
 * int N = typedArray.getIndexCount();
 * 四、控件绘制 onDraw()
 * 五,自定义View的方法
 * onFinishInflate() 回调方法，当应用从XML加载该组件并用它构建界面之后调用的方法
 * onMeasure() 检测View组件及其子组件的大小
 * onLayout() 当该组件需要分配其子组件的位置、大小时
 * onSizeChange() 当该组件的大小被改变时
 * onDraw() 当组件将要绘制它的内容时
 * onKeyDown 当按下某个键盘时
 * onKeyUp  当松开某个键盘时
 * onTrackballEvent 当发生轨迹球事件时
 * onTouchEvent 当发生触屏事件时
 * onWindowFocusChanged(boolean)  当该组件得到、失去焦点时
 * onAtrrachedToWindow() 当把该组件放入到某个窗口时
 * onDetachedFromWindow() 当把该组件从某个窗口上分离时触发的方法
 * onWindowVisibilityChanged(int): 当包含该组件的窗口的可见性发生改变时触发的方法
 */
public class DotView extends View {

    //均匀间隔的直线.

    public static final int DOT_WIDTH = 10;
    //直线横向.
    public static final int HORIZONTAL = 0;
    //直线纵向
    public static final int VERTICAL = 1;
    //默认点数量.
    private static int DEFAULT_DOT_NUM = 10;

    //默认线宽.
    private static int DEFAULT_LINE_WIDTH = 2;

    //默认线色.
    private static int DEFAULT_LINE_COLOR = Color.RED;

    //线色.
    private int lineColor;

    //线宽.
    private int lineWidth;

    //点数.
    private int dotNum;

    //方向.
    private int lineOriental;

    //小线宽度.
    private int dotWidth;

    public DotView(Context context) {
        super(context);
        initAttributeSet(context, null);
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    //属性初始化.
    private void initAttributeSet(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);
            try {
                lineColor = typedArray.getColor(R.styleable.DotView_lineColor,
                        DEFAULT_LINE_COLOR);
                lineWidth = typedArray.getColor(R.styleable.DotView_lineWidth,
                        DEFAULT_LINE_WIDTH);
                dotNum = typedArray.getColor(R.styleable.DotView_dotNum,
                        DEFAULT_DOT_NUM);
                lineOriental = typedArray.getInt(R.styleable.DotView_lineOriental,
                        HORIZONTAL);
                dotWidth = typedArray.getInt(R.styleable.DotView_dotWidth,
                        DOT_WIDTH);
            } finally {
                typedArray.recycle();
            }
        } else {
            lineColor = DEFAULT_LINE_COLOR;
            lineWidth = DEFAULT_LINE_WIDTH;
            lineOriental = HORIZONTAL;
        }
    }


    public void setDotWidth(int dotWidth) {
        this.dotWidth = dotWidth;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setDotNum(int dotNum) {
        this.dotNum = dotNum;
    }

    public void setLineOriental(int lineOriental) {
        this.lineOriental = lineOriental;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setStyle(Paint.Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStrokeWidth(lineWidth);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
        //设置画布背景颜色
        //canvas.drawRGB(255, 255, 255);
        //drawBackground(canvas,paint);
        if (lineOriental == VERTICAL) {
            drawVerticalLine(canvas, paint);
        } else {//Horizontal
            drawHorizontalLine(canvas, paint);
        }

    }

    private void drawHorizontalLine(Canvas canvas, Paint paint) {
        paint.setColor(lineColor);//设置画笔颜色
        int startY = this.getHeight() / 2;
        int stopY = this.getHeight() / 2;
        int maxX = this.getWidth();
        int spacingX = dotWidth;
        int stopX = dotWidth;

        for (int startX = 0; startX < maxX; startX += (spacingX + dotWidth)) {
            Log.i(this.getClass().getName(), "startX:" + startY + "--" + "stopX:" + stopX);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
            stopX += (spacingX + dotWidth);
            if (stopX > maxX) {
                stopX = maxX;
            }
        }
    }

    private void drawVerticalLine(Canvas canvas, Paint paint) {
        paint.setColor(lineColor);//设置画笔颜色
        int startX = this.getWidth() / 2;
        int stopX = this.getWidth() / 2;
        int maxY = this.getHeight();
        int heightY = this.getHeight() / dotNum;
        int spacingY = heightY;
        int stopY = heightY;
        for (int startY = 0; startY < maxY; startY += (spacingY + heightY)) {
            Log.i(this.getClass().getName(), "startY:" + startY + "--" + "stopY:" + stopY);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
            stopY += (spacingY + heightY);
            if (stopY > maxY) {
                stopY = maxY;
            }
        }
    }
}

//初始化控件背景.
//    private void drawBackground(Canvas canvas,Paint paint) {
//        //setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
//        //若是非编辑模式.
//        /*if (!isInEditMode()) {
//            setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
//            如果当前控件背景为null
//            if (getBackground() == null) {
//                //设置背景为透明色.
//                setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
//            }
//        }*/
//        paint.setColor(Color.RED);//设置画笔颜色
//        float left = 0;
//        float top = 0;
//        float right = getWidth();
//        float bottom = getHeight();
//        RectF drawingArea = new RectF(left, top, right, bottom);
//        Path path = new Path();
//        //canvas.drawPath(path, defaultStrokePaint);
//        canvas.drawRect(left, top, right, bottom, paint);
//    }