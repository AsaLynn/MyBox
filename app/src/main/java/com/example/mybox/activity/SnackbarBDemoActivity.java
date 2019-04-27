package com.example.mybox.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

/**
 *Snackbar挂在CoordinatorLayout上和其他布局上的不同效果.
 *
 SnackbarDemoActivity的布局结构:
 父类根布局是LinearLayout,子类内容根布局是LinearLayout并且包含一个子布局CoordinatorLayout.

 结论:
 Snackbar逐级向所在的view父布局挂载,如果view的父布局是CoordinatorLayout,
 则挂在CoordinatorLayout,否则会循环到最后挂在view所在xml布局的根布局上.

 */
public class SnackbarBDemoActivity extends BaseActivity {

    private RelativeLayout ts_rl;
    private View view;
    private LinearLayout content_root_ll;
    private CoordinatorLayout content_child_coordinator;
    private LinearLayout content_child_ll;

    @Override
    protected View initContentView() {
        view = UIUtils.inflate(this,R.layout.activity_design_snackbar_demo);
        int[] ids = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,R.id.btn5,R.id.btn6};
        for (int i = 0; i < ids.length; i++) {
            view.findViewById(ids[i]).setOnClickListener(this);
        }
        content_root_ll = (LinearLayout) view.findViewById(R.id.content_root_ll);
        content_child_ll = (LinearLayout) view.findViewById(R.id.content_child_ll);
        content_child_coordinator = (CoordinatorLayout) view.findViewById(R.id.content_child_coordinator);
        ts_rl = (RelativeLayout) view.findViewById(R.id.ts_rl);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //挂在父类根布局LinearLayout,最终还是挂到屏幕外!(屏幕外弹出)
                Snackbar.make(root_ll, "挂载到父类根布局LinearLayout的效果", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn2:
                //挂在父类根布局LinearLayout中toolbar,最终还是挂到屏幕外!(屏幕外弹出)
                Snackbar.make(toolbar, "挂载到父类根布局的toolbar效果", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn3:
                //挂在内容根布局的LinearLayout,最终是挂到是挂到屏幕外!((屏幕外弹出)
                Snackbar.make(content_root_ll, "挂载到内容根布局LinearLayout效果", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn4:
                //挂在内容根布局中的LinearLayout,最终是挂到是挂到屏幕外!((屏幕外弹出)
                Snackbar.make(content_child_ll, "挂载到内容根布局中的LinearLayout效果", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn5:
                //挂在内容根布局中的CoordinatLayout,最终是挂到是挂到CoordinatLayout!((屏幕外内弹出)
                Snackbar.make(content_child_coordinator, "挂载到内容根布局中的CoordinatLayout效果", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn6:
                //挂在内容根布局中的CoordinatLayout中的view,最终是挂到是挂到CoordinatLayout!((屏幕外内弹出)
                Snackbar.make(view.findViewById(R.id.btn6), "挂载到内容根布局中的CoordinatLayout中的view效果", Snackbar.LENGTH_LONG).show();
                break;

        }
    }
}
