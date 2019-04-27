package com.example.mybox.activity;

import android.view.View;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

import org.feezu.liuli.timeselector.TimeSelector;


public class TimeSelectorActivity extends BaseActivity {
    private TimeSelector timeSelector;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_time_selector);
        view.findViewById(R.id.show_tv).setOnClickListener(this);
        timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                Toast.makeText(TimeSelectorActivity.this, time, Toast.LENGTH_LONG).show();
            }
        }, "2015-10-27 09:33", "2016-11-29 21:54");

        timeSelector.setScrollUnit(TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_tv:
                timeSelector.show();
                break;
        }
    }

    /*public void show(View v) {
        timeSelector.show();
    }*/
}
