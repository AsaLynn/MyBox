package com.example.mybox.activity;

import android.view.View;
import android.view.animation.AlphaAnimation;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.base.HomeActivity;
import com.zxning.library.tool.UIUtils;

/**
 * 启动页,渲染页面.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void showTitle() {
        showToolbar(false);
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        //AnalyticsConfig.enableEncrypt(true);
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_splash);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        view.startAnimation(animation);
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAty(HomeActivity.class, true);
            }
        }, 500);
        return view;
    }
}
