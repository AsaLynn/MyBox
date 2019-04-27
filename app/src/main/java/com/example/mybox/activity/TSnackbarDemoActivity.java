package com.example.mybox.activity;

import android.view.View;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

/**
 *
TSnackBar
 顶部显示的SnackBar
在build gradle文件里添加
compile 'com.androidadvance:topsnackbar:1.1.1'
语法(和SnackBar用法是一样的)
TSnackbar.make(view, "顶部", Snackbar.LENGTH_LONG).show();
参数1,是CoordinatorLayout的子view,但是最终都会挂在在CoordinatorLayout布局上面.
 TSnackbar逐级向所在的view父布局挂载,如果view的父布局是CoordinatorLayout,
 则挂在CoordinatorLayout,否则会循环到最后挂在view所在xml布局的根布局上.
 挂载到非根布局上面的CoordinatorLayout上面的.
 */
public class TSnackbarDemoActivity extends BaseActivity {

    private View view;

    @Override
    protected View initContentView() {
        view = UIUtils.inflate(this,R.layout.activity_top_pop_up_message);
        int[] ids = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
        for (int i = 0; i < ids.length; i++) {
            if (i == 0 || i == 1) {
                view.findViewById(ids[i]).setOnClickListener(this);
            }else {
                view.findViewById(ids[i]).setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //挂在内容根布局CoordinatLayout,最终还是挂到内容布局CoordinatLayout.(屏幕内弹出)
                TSnackbar.make(view, "在内容根布局CoordinatLayout效果", TSnackbar.LENGTH_LONG).show();
                break;
            case R.id.btn2:
                //挂在内容父类根布局中的FrameLayout,最终还是挂到父布局中的LinearLayout(屏幕外弹出)
                TSnackbar.make(base_fl, "挂在内容父类根布局中的FrameLayout效果", TSnackbar.LENGTH_LONG).show();
                break;
        }
    }
}
