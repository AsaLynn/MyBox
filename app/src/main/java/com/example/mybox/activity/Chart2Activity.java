package com.example.mybox.activity;

import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.hornet.chart.CounterClockWiseChart;
import com.hornet.chart.MagnificentChart;
import com.zxning.library.tool.UIUtils;

public class Chart2Activity extends BaseActivity {

    CounterClockWiseChart magnificentChart;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.animationButton:
                if (magnificentChart.getAnimationState()) {
                    magnificentChart.setAnimationState(false);
                } else {
                    magnificentChart.setAnimationState(true);
                }
                break;

            case R.id.roundButton:
                if (magnificentChart.getRound()) {
                    magnificentChart.setRound(false);
                } else {
                    magnificentChart.setRound(true);
                }
                break;

            case R.id.shadowButton:
                if (magnificentChart.getShadowShowingState()) {
                    magnificentChart.setShadowShowingState(false);
                } else {
                    magnificentChart.setShadowShowingState(true);
                }
                break;

            case R.id.animationSpeedDefault:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_DEFAULT);
                break;

            case R.id.animationSpeedSlow:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_SLOW);
                break;

            case R.id.animationSpeedFast:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_FAST);
                break;

            case R.id.animationSpeedNormal:
                magnificentChart.setAnimationSpeed(MagnificentChart.ANIMATION_SPEED_NORMAL);
                break;
        }
    }


    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_chart2);
        magnificentChart = (CounterClockWiseChart) view.findViewById(R.id.circle_chart);
        magnificentChart.setItemPercent(70f, 30f);
        return view;
    }

}
