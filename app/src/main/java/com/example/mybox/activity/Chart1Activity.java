package com.example.mybox.activity;

import android.graphics.Color;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.hornet.chart.MagnificentChart;
import com.hornet.chart.MagnificentChartItem;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class Chart1Activity extends BaseActivity {

    MagnificentChart magnificentChart;

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
        View view = UIUtils.inflate(this, R.layout.activity_chart1);
        MagnificentChartItem firstItem = new MagnificentChartItem("first", 30, Color.parseColor("#BAF0A2"));
        MagnificentChartItem secondItem = new MagnificentChartItem("second", 12, Color.parseColor("#2F6994"));
        MagnificentChartItem thirdItem = new MagnificentChartItem("third", 3, Color.parseColor("#FF6600"));
        MagnificentChartItem fourthItem = new MagnificentChartItem("fourth", 41, Color.parseColor("#800080"));
        MagnificentChartItem fifthItem = new MagnificentChartItem("fifth", 14, Color.parseColor("#708090"));

        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        chartItemsList.add(firstItem);
        chartItemsList.add(secondItem);
        chartItemsList.add(thirdItem);
        chartItemsList.add(fourthItem);
        chartItemsList.add(fifthItem);

        magnificentChart = (MagnificentChart) view.findViewById(R.id.magnificentChart);

        magnificentChart.setChartItemsList(chartItemsList);
        magnificentChart.setMaxValue(100);

        //magnificentChart.setAnimationState(true);//开启动画.
        return view;
    }

}
