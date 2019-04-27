package com.example.mybox.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

/**
 * 从上往下的平移动画.
 * TranslateAnimation是移动的动画效果。它有三个构造函数，分别是：
 * 　　1.public　　TranslateAnimation(Context context,AttributeSet attrs)   略过
 * 　　2.public　　TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
 * 　　这个是我们最常用的一个构造方法，
 * 　　float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；
 * 　　float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；
 * 　　float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；
 * 　　float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；
 * 　　如果view在A(x,y)点 那么动画就是从B点(x+fromXDelta, y+fromYDelta)点移动到C 点(x+toXDelta,y+toYDelta)点.
 * 　　3.public　　TranslateAnimation (int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)
 * 　　fromXType:第一个参数是x轴方向的值的参照(Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF,or Animation.RELATIVE_TO_PARENT);
 * 　　fromXValue:第二个参数是第一个参数类型的起始值；
 * 　　toXType,toXValue:第三个参数与第四个参数是x轴方向的终点参照与对应值；
 * <p/>
 * 　　后面四个参数就不用解释了。如果全部选择Animation.ABSOLUTE，其实就是第二个构造函数。
 * 以x轴为例介绍参照与对应值的关系:
 * 如果选择参照为Animation.ABSOLUTE，那么对应的值应该是具体的坐标值，比如100到300，指绝对的屏幕像素单位
 * 如果选择参照为Animation.RELATIVE_TO_SELF或者 Animation.RELATIVE_TO_PARENT指的是相对于自身或父控件，对应值应该理解为相对于自身或者父控件的几倍或百分之多少。多试参数就明白了。
 */
public class Translate1Activity extends BaseActivity {
    private Button mButton;
    private TranslateAnimation mShowAnimation;
    private TranslateAnimation mHideAnimation;
    private boolean isShow;
    private TextView mMenu;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_translate1);
        mMenu = (TextView) view.findViewById(R.id.menu);
        mButton = (Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        //mMenu.setVisibility(View.GONE);
        isShow = false;
        initAnimation();
        return view;
    }

    private void initAnimation() {
        // 从自已-1倍的位置移到自己原来的位置
        mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        // 从自己原来的位置,移动到自已-1倍的位置.
        mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mShowAnimation.setDuration(500);
        mHideAnimation.setDuration(500);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            if (isShow) {
                isShow = false;
                mMenu.startAnimation(mHideAnimation);
                mMenu.setVisibility(View.GONE);
            } else {
                isShow = true;
                mMenu.startAnimation(mShowAnimation);
                mMenu.setVisibility(View.VISIBLE);
            }
        }
    }

}

