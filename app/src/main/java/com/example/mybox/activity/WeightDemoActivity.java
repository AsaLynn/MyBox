package com.example.mybox.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

/**
 * 代码中动态的设置weight属性.
 */
public class WeightDemoActivity extends BaseActivity {

    protected ImageView itemIv;
    protected TextView nameTv;
    protected TextView stateTv;
    protected ImageView enterIv;
    protected LinearLayout itemLl;

    @Override
    protected View initContentView() {

        View view = UIUtils.inflate(R.layout.activity_weight);
        itemIv = (ImageView) view.findViewById(R.id.item_iv);
        nameTv = (TextView) view.findViewById(R.id.name_tv);
        stateTv = (TextView) view.findViewById(R.id.state_tv);
        enterIv = (ImageView) view.findViewById(R.id.enter_iv);
        itemLl = (LinearLayout) view.findViewById(R.id.item_ll);
        changeViewLayout();
        return view;
    }

    public void changeViewLayout() {
        LinearLayout.LayoutParams param
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        nameTv.setLayoutParams(param);
        nameTv.setPadding(0, 0, 100, 0);
        LinearLayout.LayoutParams param2
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        stateTv.setLayoutParams(param2);
        stateTv.setText("内容222");
    }

}
