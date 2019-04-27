package com.example.mybox.activity;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseCoordinatorActivity;
import com.zxning.library.tool.UIUtils;

/**
 * Snackbar
 * Snackbar 是 Android design support library 中的组件
 * 就是屏幕底部快速的显示一条消息，大体与 Toast类似
 * 使用场景:
 * 一小段时间之后、或者用户与屏幕触发交互，Snackbar 会自动消失；
 * 可以包含一个可选的操作；
 * 把 Snackbar 划出屏幕，可以弃用；
 * 作为一条上下文敏感的消息，也是 UI 的一部分，并在屏幕内所有元素的上层 ；
 * 一个时刻只能有唯一一个 Snackbar 显示
 * 使用SnackBar:
 * 1,首先要导入support design 库，找到你app的build gradle文件
 * 2,然后增加一个compile语句:compile 'com.android.support:design:23.4.0'(版本号同本地)
 * 3,编写xml文件以及java文件
 * snackbar需要有一个父控件,如果用design库里的CoordinatorLayout来做他的父控件,则snackbar会依附在CoordinatorLayout上面,
 * 如果用其他布局例如LinearLayout等作为其父控件则snackbar会依附在屏幕上面.
 * Snackbar.make(mLayout, "提示消息", Snackbar.LENGTH_LONG).setAction("按钮", new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    //点击右侧的按钮之后的操作
    }
    }).show();
 方法:
 *public static Snackbar.make(View view, CharSequence text, int duration){}
 * 方法的第1个参数是一个 view，snackbar 会找到一个父 view，以寄存所赋的 snackbar 值。
 * Snackbar 会沿着 view 的树状路径，找到第一个合适的布局或窗口视图，作为父 view。
 * 方法的第2个参数:提示的消息文字.
 * 方法的第3个参数:持续时间,LENG_SHORT 或者 LENGTH_LONG。
 *
 *public Snackbar setAction(CharSequence text, final View.OnClickListener listener) {}
 * 第1个参数:按钮的文字内容.
 * 第2个参数:按钮的点击事件.
 *public void show() {}
 * 弹出提示,同Toast.
 * 布局:
 *该demo挂载到CoordinatorLayout上面
 * 介绍了Snackbar的几种用法.
 */
public class SnackbarADemoActivity extends BaseCoordinatorActivity {


    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_top_snackbar_demo_a);
        int[] ids = {R.id.m_btn0, R.id.m_btn, R.id.m_btn1, R.id.m_btn2, R.id.m_btn3};
        for (int i = 0; i < ids.length; i++) {
            view.findViewById(ids[i]).setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_btn:
                //普通用法.
                fab.setVisibility(View.GONE);
                Snackbar.make(fab, "我是普通的Snackbar", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.m_btn0:
                //设置右侧一个按钮.
                Snackbar.make(v, "过年了，过年了", Snackbar.LENGTH_LONG)
                        .setAction("去过年", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(SnackbarADemoActivity.this, "你点击了右边的按钮", Toast.LENGTH_LONG).show();
                            }
                        }).show();
                break;
            case R.id.m_btn1:
                //自定义背景和文字颜色.
                Snackbar snackbar =
                        Snackbar.make(fab, "过年了，过年了", Snackbar.LENGTH_LONG)
                                .setAction("去过年", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(SnackbarADemoActivity.this, "你点击了右边的按钮", Toast.LENGTH_LONG).show();
                                    }
                                });
                Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) snackbar.getView();
                ve.setBackgroundColor(0xffff0000);//设置背景Se
                ve.setAlpha(0.9f);//设置透明度
                ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
                ((Button) ve.findViewById(R.id.snackbar_action)).setTextColor(Color.parseColor("#c0d9d9"));
                snackbar.show();
                break;
            case R.id.m_btn2:
                //添加一个view
                Snackbar snackbar2 =
                        Snackbar.make(fab, "过年了，过年了", Snackbar.LENGTH_LONG)
                                .setAction("去过年", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(SnackbarADemoActivity.this, "你点击了右边的按钮", Toast.LENGTH_LONG).show();
                                    }
                                });
                View snackbarview = snackbar2.getView();
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;
                snackbarview.setBackgroundColor(0xffffffff);//设置背景Se
                View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(R.layout.snackbar_main, null);
                ((TextView) snackbarview.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF0000"));
                ((Button) snackbarview.findViewById(R.id.snackbar_action)).setTextColor(Color.parseColor("#ff0000"));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.gravity = Gravity.CENTER_VERTICAL;
                snackbarLayout.addView(add_view, 0, p);
                snackbar2.show();
                break;
        }
    }
}
