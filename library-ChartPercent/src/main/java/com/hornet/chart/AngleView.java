package com.hornet.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画弧度.
 */
public class AngleView extends View {
    public AngleView(Context context) {
        super(context);
    }

    public AngleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);                       //设置画笔为无锯齿
        paint.setColor(Color.BLACK);                    //设置画笔颜色
        canvas.drawColor(Color.WHITE);                  //白色背景
        paint.setStrokeWidth((float) 3.0);              //线宽
        paint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF();                     //RectF对象
        oval.left = 100;                              //左边
        oval.top = 100;                                   //上边
        oval.right = 400;                             //右边
        oval.bottom = 300;                                //下边
        /*oval：圆弧所在的椭圆对象。
        startAngle：圆弧的起始角度。
        sweepAngle：圆弧的角度。
        useCenter：是否显示半径连线，true表示显示圆弧与圆心的半径连线，false表示不显示。
        paint：绘制时所使用的画笔。*/
        //canvas.drawArc(oval, -90, -225, false, paint);    //绘制圆弧
        canvas.drawArc(oval, 270, -90, false, paint);

        //RectF oval=new RectF();                       //RectF对象
        oval.left = 100;                              //左边
        oval.top = 400;                                   //上边
        oval.right = 400;                             //右边
        oval.bottom = 700;                                //下边
        canvas.drawArc(oval, 200, 135, true, paint);    //绘制圆弧
    }
}
