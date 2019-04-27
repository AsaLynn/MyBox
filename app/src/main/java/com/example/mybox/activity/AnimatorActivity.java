package com.example.mybox.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

/**
 * 常用的几种动画方式.
 */
public class AnimatorActivity extends BaseActivity implements Animation.AnimationListener {

    private View tv;
    private View view1;
    private View view2;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_animator);
        tv = view.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateyAnimRun(view);
            }
        });
        view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.btn3).setOnClickListener(this);
        view.findViewById(R.id.btn4).setOnClickListener(this);
        view.findViewById(R.id.btn5).setOnClickListener(this);
        view.findViewById(R.id.btn6).setOnClickListener(this);
        view.findViewById(R.id.btn7).setOnClickListener(this);
        view.findViewById(R.id.btn8).setOnClickListener(this);

        view1 = view.findViewById(R.id.linear1);
        view2 = view.findViewById(R.id.linear2);
        //view3 = view.findViewById(R.id.button);
        return view;
    }

    public void rotateyAnimRun(final View view) {
//        ObjectAnimator anim = ObjectAnimator
//                .ofFloat(view, "zhy", 1.0F, 0.75F);
//        anim.setDuration(800);
//        anim.start();
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
////                view.setAlpha(cVal);
//                view.setScaleX(cVal);
//                view.setScaleY(cVal);
//            }
//        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                rotateyAnimRun1(tv);
                break;
            case R.id.btn2:
                rotateyAnimRun2(tv);
                break;
            case R.id.btn3:
                rotateyAnimRun3(tv);
                break;
            case R.id.btn4:
                rotateyAnimRun4(tv);
                break;
            case R.id.btn5:
                rotateyAnimRun5(tv);
                break;
            case R.id.btn6:
                rotateyAnimRun6(tv);
                break;
            case R.id.btn7:
                rotateyAnimRun7(tv);
                break;

        }
    }


    private void rotateyAnimRun7(View tv) {

        AnimationSet animationSet = new AnimationSet(true);
        //1.开始角度
        //2.结束角度
        //3.确定x坐标类型.
        RotateAnimation rotateAnimation = new RotateAnimation(0f,
                360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        animationSet.addAnimation(rotateAnimation);
        tv.startAnimation(animationSet);
    }

    //5秒中内从常规变换成全透明，再从全透明变换成常规
    private void rotateyAnimRun1(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    //360度的旋转
    private void rotateyAnimRun2(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
    }

    //先向左移出屏幕，然后再移动回来
    private void rotateyAnimRun3(View view) {
        float curTranslationX = view.getTranslationX();
        ObjectAnimator animator
                = ObjectAnimator
                .ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(5000);
        animator.start();
    }

    //进行缩放操作,比如说将TextView在垂直方向上放大3倍再还原
    private void rotateyAnimRun4(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    //组合动画
    private void rotateyAnimRun5(View view) {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }

    //需求动画
    /*
    * •after(Animator anim)   将现有动画插入到传入的动画之后执行
    •after(long delay)   将现有动画延迟指定毫秒后执行
    •before(Animator anim)   将现有动画插入到传入的动画之前执行
    •with(Animator anim)   将现有动画和传入的动画同时执行
    * */
    private void rotateyAnimRun6(View view) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(view, "rotationY", 0, 180);

        //ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        //ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        //animSet.play(rotate).with(fadeInOut).after(scale);

        animSet.play(scaleY).with(scaleX).before(rotationY);
        animSet.setDuration(1000);
        animSet.start();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}


/*
* ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f);
* 其实这段代码的意思就是ObjectAnimator会帮我们不断地改变textview对象中alpha属性的值，从1f变化到0f。
* 然后textview对象需要根据alpha属性值的改变来不断刷新界面的显示，从而让用户可以看出淡入淡出的动画效果。
*
* anim.addListener(new AnimatorListener() {
	@Override
	public void onAnimationStart(Animator animation) {
	动画开始的时候调用
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
	动画重复执行的时候调用
	}

	@Override
	public void onAnimationEnd(Animator animation) {
	动画结束的时候调用
	}

	@Override
	public void onAnimationCancel(Animator animation) {
	动画被取消的时候调用。
	}
});
*
* */