package com.example.mybox.activity;

import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

public class TextShakeActivity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_text_shake);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setAnimation(shakeAnimation(5));
        return view;
    }
}
