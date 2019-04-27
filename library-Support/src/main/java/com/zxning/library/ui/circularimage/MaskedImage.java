package com.zxning.library.ui.circularimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 圆形头像控件的基类.
 * * 静态代码块:
 * 在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。
 * 每个静态代码块只会执行一次。由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。
 * 如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
 * 注意：
 * 1 静态代码块不能存在于任何方法体内。
 * 2 静态代码块不能直接访问静态实例变量和实例方法，需要通过类的实例对象来访问。
 * 1.Xfermode有三个子类 :
 * AvoidXfermode  指定了一个颜色和容差，强制Paint避免在它上面绘图(或者只在它上面绘图)。
 * PixelXorXfermode  当覆盖已有的颜色时，应用一个简单的像素异或操作。
 * PorterDuffXfermode  这是一个非常强大的转换模式，使用它，
 * 可以使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas图像进行交互。
 * 要应用转换模式，可以使用setXferMode方法，如下所示：
 * AvoidXfermode avoid = new AvoidXfermode(Color.BLUE, 10, AvoidXfermode.Mode. AVOID);
 * borderPen.setXfermode(avoid);
 * <p/>
 * 2.PorterDuff:
 * 从上面我们可以看到PorterDuff.Mode为枚举类,一共有16个枚举值:
 * 1.PorterDuff.Mode.CLEAR
 * 所绘制不会提交到画布上。
 * 2.PorterDuff.Mode.SRC
 * 显示上层绘制图片
 * 3.PorterDuff.Mode.DST
 * 显示下层绘制图片
 * 4.PorterDuff.Mode.SRC_OVER
 * 正常绘制显示，上下层绘制叠盖。
 * 5.PorterDuff.Mode.DST_OVER
 * 上下层都显示。下层居上显示。
 * 6.PorterDuff.Mode.SRC_IN
 * 取两层绘制交集。显示上层。
 * 7.PorterDuff.Mode.DST_IN
 * 取两层绘制交集。显示下层。
 * 8.PorterDuff.Mode.SRC_OUT
 * 取上层绘制非交集部分。
 * 9.PorterDuff.Mode.DST_OUT
 * 取下层绘制非交集部分。
 * 10.PorterDuff.Mode.SRC_ATOP
 * 取下层非交集部分与上层交集部分
 * 11.PorterDuff.Mode.DST_ATOP
 * 取上层非交集部分与下层交集部分
 * 12.PorterDuff.Mode.XOR
 * 异或：去除两图层交集部分
 * 13.PorterDuff.Mode.DARKEN
 * 取两图层全部区域，交集部分颜色加深
 * 14.PorterDuff.Mode.LIGHTEN
 * 取两图层全部，点亮交集部分颜色
 * 15.PorterDuff.Mode.MULTIPLY
 * 取两图层交集部分叠加后颜色
 * 16.PorterDuff.Mode.SCREEN
 * 取两图层全部区域，交集部分变为透明色
 * <p/>
 * 关于@Override:@Override是伪代码,表示重写(当然不写也可以)
 * 不过写上有如下好处:
 * 1、可以当注释用,方便阅读；
 * 2、编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。
 * 例如，你如果没写@Override，而你下面的方法名又写错了，
 * 这时你的编译器是可以编译通过的，因为编译器以为这个方法是你的子类中自己增加的方法。
 * <p/>
 * java native关键字
 * 一. 什么是Native Method
 * 一个Native Method就是一个java调用非java代码的接口。该方法的实现由非java语言实现.在定义
 * native method时，不提供实现体（像定义java interface），因为实现体是由非java语言在外面实现的,
 * 标识符native可以与所有其它的java标识符连用，但是abstract除外。因为native暗示方法有实现体的，只不
 * 过这些实现体是非java的，但abstract却指明方法无实现体。 一个native method方法可以返回任何java类型，
 * 而且可以进行异常控制。这些方法的实现体可以自制一个异常并且将其抛出,native method的存在并不会对其
 * 他类调用这些本地方法产生影响有了本地方法，java程序可以做任何应用层次的任务。
 * 二.为什么要使用Native Method
 * java应用需要与java外面的环境交互
 */
public abstract class MaskedImage extends ImageView {

    //这是一个非常强大的转换模式
    private static final Xfermode MASK_XFERMODE;
    private Bitmap mask;
    private Paint paint;

    static {
        //取两层绘制交集。显示下层。
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        //这是一个非常强大的转换模式
        MASK_XFERMODE = new PorterDuffXfermode(localMode);
    }

    //第一个构造函数.代码中获取实例使用.
    public MaskedImage(Context paramContext) {
        super(paramContext);
    }

    //第二个构造函数,xml布局文件中声明使用.
    public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    //第三个构造函数被第2个显式调用使用.
    public MaskedImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    //创建位图.
    public abstract Bitmap createMask();

    //视图重新绘制的操作.
    @Override
    protected void onDraw(Canvas paramCanvas) {
        Drawable localDrawable = getDrawable();
        if (localDrawable == null) {
            return;
        }

        try {
            if (this.paint == null) {
                this.paint = new Paint();
                //是否用来对位图进行滤波处理。
                this.paint.setFilterBitmap(false);
                this.paint.setXfermode(MASK_XFERMODE);
            }
            float f1 = getWidth();
            float f2 = getHeight();
            //按层次来绘图
            int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, Canvas.MATRIX_SAVE_FLAG);
            int j = getWidth();
            int k = getHeight();
            localDrawable.setBounds(0, 0, j, k);
            localDrawable.draw(paramCanvas);
            if ((this.mask == null) || (this.mask.isRecycled())) {
                Bitmap localBitmap1 = createMask();
                this.mask = localBitmap1;
            }
            Bitmap localBitmap2 = this.mask;
            Paint localPaint3 = this.paint;
            paramCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint3);
            //回到任何一个save()方法调用之前的状态
            paramCanvas.restoreToCount(i);
            return;
        } catch (Exception localException) {
            StringBuilder localStringBuilder = new StringBuilder()
                    .append("Attempting to draw with recycled bitmap. View ID = ");
            Log.i(this.getClass().getName(), localStringBuilder.toString());
        }
    }

}
