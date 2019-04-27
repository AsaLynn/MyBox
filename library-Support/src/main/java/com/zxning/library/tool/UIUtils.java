package com.zxning.library.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxning.library.application.BaseApplication;

/**
 * 页面展示工具类.
 */
public class UIUtils {

    /**
     * 根据id返回dimens文件中的值.
     *
     * @param id
     * @return
     */
    public static float getDimension(int id) {
        return UIUtils.getResources().getDimension(id);
    }

    //将Bitmap转换成圆形的,用于展示圆形的头像.
    public static Bitmap toFullRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    //private static DisplayImageOptions options;

    /**
     * 获取全局上下文.
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getmContext();
    }

    /**
     * 获取全局Handler
     *
     * @return
     */
    public static Handler getHandler() {
        return BaseApplication.getmHandler();
    }

    /**
     * 获取主线程对象.
     *
     * @return
     */
    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 获取资源文件夹操作
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * //string文件中的字符串
     *
     * @param stringId
     * @return
     */
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    /**
     * //integers.xml文件中的整数
     *
     * @param intId
     * @return
     */
    public static Integer getInteger(int intId) {
        return getResources().getInteger(intId);
    }

    /**
     * //返回drawable操作
     *
     * @param drawableId
     * @return
     */
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    /**
     * 获取颜色.
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * //string-array
     *
     * @param arrayId
     * @return
     */
    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }

    //颜色选择通过资源文件获取

    //dip--->px, 1dp = 2px,定义一个控件的宽高  layoutParams(w,h)
    public static int dip2px(int dip) {
        //获取dp和px的转换关系的变量
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    //px---->dp
    public static int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * //判断当前线程是否在主线程中
     *
     * @return
     */
    public static boolean isRunInMainThread() {
        return getMainThreadId() == android.os.Process.myTid();
    }

    /**
     * //区分是否在 主线程中,做UI处理
     *
     * @param runnable
     */
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            //如果当前任务就在主线程中,直接运行
            runnable.run();
        } else {
            //如果当任务在子线程中,则将其传递到主线程中去执行
            getHandler().post(runnable);
        }
    }

    /**
     * //根据id获取颜色选择器xml
     *
     * @param mTabTextColorResId
     * @return
     */
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getResources().getColorStateList(mTabTextColorResId);
    }

    /**
     * 通过activity来进行xml转view.
     *
     * @param layoutId
     * @return
     */
    public static View inflate(Activity activity, int layoutId) {
        return View.inflate(activity, layoutId, null);
    }

    /**
     * xml转view.
     *
     * @param layoutId
     * @return
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * xml转view,挂载到父布局.
     *
     * @param layoutId
     * @return
     */
    public static void inflate(int layoutId, ViewGroup group) {
        View.inflate(getContext(), layoutId, group);
    }

    /**
     * //将dimens中的dp转换成像素值
     *
     * @param dimensId
     * @return
     */
    public static int getDimens(int dimensId) {
        return getResources().getDimensionPixelSize(dimensId);
    }

    /**
     * //延时做任务方法
     *
     * @param runnable
     * @param delayed
     */
    public static void postDelayed(Runnable runnable, int delayed) {

        getHandler().postDelayed(runnable, delayed);
        System.out.print("hhh");
        System.out.print("h2");
        System.out.print("h3");
    }

    /**
     * //移除指定任务的操作
     *
     * @param runnable
     */
    public static void removeCallBack(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * Toast的封装
     *
     * @param msg 你希望显示的值。
     */
    public static void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static int[] getIntArray(int arrayId) {
        return getResources().getIntArray(arrayId);
    }

    /**
     * 展示图片时候的配置选项.
     */
    /*public static DisplayImageOptions getDisplayImageOptions() {

        if (options == null) {
            options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_loading) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.ic_loading) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.ic_loading) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    // .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                    .build();
        }
        return options;
    }*/
}
