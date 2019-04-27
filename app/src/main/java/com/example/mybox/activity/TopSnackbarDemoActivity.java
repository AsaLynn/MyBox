package com.example.mybox.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.topsnackbar.TopSnackbar;
import com.zxning.library.tool.UIUtils;

/**
 * 此处用的自定义TopSnackbar顶部弹出,用法和TSnackbar一模一样,只不过代码单独抽取出来新创建了库module.
 */
public class TopSnackbarDemoActivity extends BaseActivity {

    private RelativeLayout relative_layout_main;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_top_pop_up_message);
        int[] ids = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
        for (int i = 0; i < ids.length; i++) {
            view.findViewById(ids[i]).setOnClickListener(this);
        }
        relative_layout_main = (RelativeLayout) view.findViewById(R.id.ts_rl);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //普通样式.
                TopSnackbar.make(findViewById(R.id.coordinatorLayout),
                        "你好,顶部弹出消息样式1",
                        TopSnackbar.LENGTH_LONG).show();
                break;
            case R.id.btn2:
                //右侧增加一个按钮.
                TopSnackbar snackbar = TopSnackbar
                        .make(relative_layout_main, "你好,顶部弹出消息样式2.", TopSnackbar.LENGTH_LONG)
                        .setAction("ActionButton", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIUtils.showMsg("ActionButton");
                            }
                        });
                snackbar.setActionTextColor(Color.LTGRAY);
                snackbar.addIcon(R.mipmap.ic_launcher, 200);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.parseColor("#555555"));
                TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                break;
            case R.id.btn3:
                //自定义文字颜色和背景颜色.
                TopSnackbar snackbar3 = TopSnackbar
                        .make(relative_layout_main, "Had a snack at Snackbar", TopSnackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("CLICKED Action", "CLIDKED Action");
                            }
                        });
                snackbar3.setActionTextColor(Color.WHITE);
                View snackbarView3 = snackbar3.getView();
                snackbarView3.setBackgroundColor(Color.parseColor("#0000CC"));
                TextView textView3 = (TextView) snackbarView3.findViewById(R.id.snackbar_text);
                textView3.setTextColor(Color.WHITE);
                snackbar3.show();
                break;
            case R.id.btn4:
                //可以添加一个view
                TopSnackbar snackbar4 = TopSnackbar
                        .make(relative_layout_main, "Had a snack at Snackbar  Had a snack at Snackbar  Had a snack at Snackbar Had a snack at Snackbar Had a snack at Snackbar Had a snack at Snackbar", TopSnackbar.LENGTH_LONG);
                snackbar4.setActionTextColor(Color.WHITE);
                View snackbarView4 = snackbar4.getView();
                TopSnackbar.SnackbarLayout snackbarLayout = (TopSnackbar.SnackbarLayout) snackbarView4;
                View add_view = LayoutInflater.from(snackbarView4.getContext()).inflate(R.layout.snackbar_main, null);
                ((TextView) snackbarView4.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF0000"));
                ((Button) snackbarView4.findViewById(R.id.snackbar_action)).setTextColor(Color.parseColor("#ff0000"));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.gravity = Gravity.CENTER_VERTICAL;
                snackbarLayout.addView(add_view, 0, p);
                snackbar4.show();
                break;
        }
    }
}
