package com.example.mybox.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.TransformUtils;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.selectdate.TimeSelector;

/**
 * 年月选择空间，年份或者月份倒序排列。
 */
public class SalaryCheckAty extends BaseActivity {

    private TextView date_tv;
    private TimeSelector timeSelector;
    private View headerView;
    private TextView name_tv;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_check_salary_aty);

        headerView = view.findViewById(R.id.salary_top_includ);
        name_tv = (TextView) headerView.findViewById(R.id.name_tv);
        LinearLayout date_ll = (LinearLayout) headerView.findViewById(R.id.date_ll);
        date_ll.setOnClickListener(this);
        date_tv = (TextView) headerView.findViewById(R.id.date_tv);
        timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                date_tv.setText(time);
            }
        }, "2010-01-01 01:00", TransformUtils.getCurrentTimeByDate());
        timeSelector.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_ll:
                timeSelector.show(date_tv.getText().toString());
                break;
        }
    }
}
