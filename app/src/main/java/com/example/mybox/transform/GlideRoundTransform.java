package com.example.mybox.transform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


/**
 *实现圆角效果.
 */

public class GlideRoundTransform extends BitmapTransformation {
    private float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 20);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        //density,屏幕密度.1.5,1,0.75
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        //Bitmap.Config.ARGB_8888,图片质量.
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        //REPEAT,MIRROR,CLAMP{重复,镜像,拉伸}
        //设置着色器
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        //设置抗锯齿.
        paint.setAntiAlias(true);
        //矩阵,(float left, float top, float right, float bottom)
        //矩形的左边的x坐标,矩形的顶部的Y坐标,对右侧的矩形的x坐标,矩形的底部的Y坐标
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        //画指定的圆矩形。
        //drawRoundRect(RectF rect, float rx, float ry, @NonNull Paint paint)
        //矩阵,圆角,椭圆的x半径,y半径.画笔,
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;

    }
}
