package com.zxning.library.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.zxning.library.R;

public class MyRadioButton extends RadioButton {

    private int mDrawableSize;// xml文件中设置的大小

    public MyRadioButton(Context context) {
        this(context, null, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyRadioButton);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
//            switch (attr) {
//            case R.styleable.MyRadioButton_drawableHeightSize:
//                mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_drawableHeightSize, 30);
//                break;
//            case R.styleable.MyRadioButton_drawableTop:
//                drawableTop = a.getDrawable(attr);
//                break;
//            case R.styleable.MyRadioButton_drawableBottom:
//                drawableRight = a.getDrawable(attr);
//                break;
//            case R.styleable.MyRadioButton_drawableRight:
//                drawableBottom = a.getDrawable(attr);
//                break;
//            case R.styleable.MyRadioButton_drawableLeft:
//                drawableLeft = a.getDrawable(attr);
//                break;
//            default :
//                    break;
//            }

            if (attr == R.styleable.MyRadioButton_drawableHeightSize) {
                mDrawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_drawableHeightSize, 30);

            } else if (attr == R.styleable.MyRadioButton_drawableTop) {
                drawableTop = a.getDrawable(attr);

            } else if (attr == R.styleable.MyRadioButton_drawableBottom) {
                drawableRight = a.getDrawable(attr);
            } else if (attr == R.styleable.MyRadioButton_drawableRight) {
                drawableBottom = a.getDrawable(attr);
            } else if (attr == R.styleable.MyRadioButton_drawableLeft) {
                drawableLeft = a.getDrawable(attr);
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

    //设置按钮图片内部边界.
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

}