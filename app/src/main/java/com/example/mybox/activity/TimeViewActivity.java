package com.example.mybox.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.mybox.R;

import myapplication.nomasp.com.clock.LEDView;

/*
时钟控件...
 */
public class TimeViewActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_view);
        ledView = (LEDView) findViewById(R.id.ledview);
    }

    private LEDView ledView;




    @Override
    protected void onResume() {
        super.onResume();
        ledView.start();//调用开始
    }

    @Override
    protected void onStop() {
        super.onStop();
        ledView.stop();//暂停
    }

}
