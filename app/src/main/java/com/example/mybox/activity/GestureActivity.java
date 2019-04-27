package com.example.mybox.activity;

import android.content.Intent;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.constant.Constant;
import com.zxning.library.tool.UIUtils;

public class GestureActivity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_gesture);
        view.findViewById(R.id.set_btn).setOnClickListener(this);
        view.findViewById(R.id.verify_btn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn:
                Intent intent1 = new Intent();
                intent1.setClass(this,GestureLockSettingAty.class);
                intent1.putExtra(Constant.Intent.ExtraName.TITLE,"设置手势密码");
                startActivity(intent1);
                break;
            case R.id.verify_btn:
                Intent intent2 = new Intent();
                intent2.setClass(this,GestureLoginLockActivity.class);
                intent2.putExtra(Constant.Intent.ExtraName.TITLE,"验证手势密码");
                startActivity(intent2);
                break;
        }
    }
}
