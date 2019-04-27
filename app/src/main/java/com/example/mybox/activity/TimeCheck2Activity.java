/**
 *  riqixuanze.
 */
package com.example.mybox.activity;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.jtoushou.tickertimer.interfaces.OnDateSelected;
import com.jtoushou.tickertimer.utils.LogUtil;
import com.jtoushou.tickertimer.views.DatePicker;
import com.zxning.library.tool.UIUtils;

import java.util.List;

/**
 * Demo应用的主Activity
 */
public class TimeCheck2Activity extends BaseActivity {
    private DatePicker mDatePicker;
    private Button btnPick;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_time_check_2);
        mDatePicker = (DatePicker) view.findViewById(R.id.main_dp);
        mDatePicker.setOnDateSelected(new OnDateSelected() {
            @Override
            public void selected(List<String> date) {
                for (String s : date) {
                    LogUtil.v(s);
                }
            }
        });
//        AseoZdpAseo.initFinalTimer(this, AseoZdpAseo.BOTH_TYPE);
        btnPick = (Button) view.findViewById(R.id.main_btn);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(TimeCheck2Activity.this).create();
                dialog.show();

                DatePicker datePicker = new DatePicker(TimeCheck2Activity.this);
                datePicker.setOnDateSelected(new OnDateSelected() {
                    @Override
                    public void selected(List<String> date) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : date) {
                            sb.append(s).append("\n");
                        }
                        Toast.makeText(TimeCheck2Activity.this, sb.toString(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(datePicker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });

        return view;
    }


   /* @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }*/
}


/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mDatePicker = (DatePicker) findViewById(R.id.main_dp);
        mDatePicker.setOnDateSelected(new OnDateSelected() {
            @Override
            public void selected(List<String> date) {
                for (String s : date) {
                    LogUtil.v(s);
                }
            }
        });
//        AseoZdpAseo.initFinalTimer(this, AseoZdpAseo.BOTH_TYPE);
        btnPick = (Button) findViewById(R.id.main_btn);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();

                DatePicker datePicker = new DatePicker(MainActivity.this);
                datePicker.setOnDateSelected(new OnDateSelected() {
                    @Override
                    public void selected(List<String> date) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : date) {
                            sb.append(s).append("\n");
                        }
                        Toast.makeText(MainActivity.this, sb.toString(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(datePicker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }*/