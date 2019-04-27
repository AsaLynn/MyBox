package com.example.mybox.activity;

import android.view.View;
import android.widget.CompoundButton;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.jtoushou.switchbutton.SwitchButton;
import com.zxning.library.tool.UIUtils;

public class SwitchStyleActivity extends BaseActivity {

    private SwitchButton mFlymeSb, mMiuiSb, mCustomSb, mDefaultSb, mSB;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_style);
        SwitchButton disableSb = (SwitchButton) view.findViewById(R.id.sb_disable_control);
        SwitchButton disableNoEventSb = (SwitchButton) view.findViewById(R.id.sb_disable_control_no_event);
        mFlymeSb = (SwitchButton) view.findViewById(R.id.sb_custom_flyme);
        mMiuiSb = (SwitchButton) view.findViewById(R.id.sb_custom_miui);
        mCustomSb = (SwitchButton) view.findViewById(R.id.sb_custom);
        mDefaultSb = (SwitchButton) view.findViewById(R.id.sb_default);
        mSB = (SwitchButton) view.findViewById(R.id.sb_ios);

        disableSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFlymeSb.setEnabled(isChecked);
                mMiuiSb.setEnabled(isChecked);
                mCustomSb.setEnabled(isChecked);
                mDefaultSb.setEnabled(isChecked);
                mSB.setEnabled(isChecked);
            }
        });
        disableNoEventSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFlymeSb.setEnabled(isChecked);
                mMiuiSb.setEnabled(isChecked);
                mCustomSb.setEnabled(isChecked);
                mDefaultSb.setEnabled(isChecked);
                mSB.setEnabled(isChecked);
            }
        });
        disableNoEventSb.setCheckedImmediatelyNoEvent(false);
        return view;
    }

}
