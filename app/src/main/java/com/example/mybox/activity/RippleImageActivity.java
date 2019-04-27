package com.example.mybox.activity;

import android.view.View;
import android.widget.Button;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.ripple.RippleImageView;
import com.zxning.library.tool.UIUtils;

public class RippleImageActivity extends BaseActivity {
    private RippleImageView rippleImageView;
    private Button btn_start,btn_stop;


    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_ripple_demo);
        btn_start=(Button)view.findViewById(R.id.btn_start);
        btn_stop=(Button)view.findViewById(R.id.btn_stop);
        rippleImageView=(RippleImageView)view.findViewById(R.id.rippleImageView);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //停止动画
                rippleImageView.stopWaveAnimation();
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始动画
                rippleImageView.startWaveAnimation();
            }
        });

        return view;
    }
}
