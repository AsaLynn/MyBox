package com.example.mybox.activity;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.jtoushou.demo.SVProgressHUD;
import com.zxning.library.tool.UIUtils;

public class DialogBoxActivity extends BaseActivity {

    int progress = 0;


    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_dialog_box);

        return view;
    }

    public void show(View view) {
        SVProgressHUD.show(this);
    }

    public void showWithMaskType(View view) {
        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.None);
//        SVProgressHUD.showWithMaskType(this,SVProgressHUD.SVProgressHUDMaskType.Black);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.Clear);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.Gradient);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    public void showWithStatus(View view) {
        SVProgressHUD.showWithStatus(this, "加载中...");
    }

    public void showInfoWithStatus(View view) {
        SVProgressHUD.showInfoWithStatus(this, "这是提示", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    public void showSuccessWithStatus(View view) {
        SVProgressHUD.showSuccessWithStatus(this, "恭喜，提交成功！");
    }

    public void showErrorWithStatus(View view) {
        SVProgressHUD.showErrorWithStatus(this, "不约，叔叔我们不约～", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress = progress + 5;
            if (SVProgressHUD.getProgressBar(DialogBoxActivity.this).getMax() != SVProgressHUD.getProgressBar(DialogBoxActivity.this).getProgress()) {
                SVProgressHUD.getProgressBar(DialogBoxActivity.this).setProgress(progress);
                SVProgressHUD.setText(DialogBoxActivity.this, "进度 " + progress + "%");

                mHandler.sendEmptyMessageDelayed(0, 500);
            } else {
                SVProgressHUD.dismiss(DialogBoxActivity.this);
                SVProgressHUD.getProgressBar(DialogBoxActivity.this).setProgress(0);
            }

        }
    };

    public void showWithProgress(View view) {
        SVProgressHUD.showWithProgress(this, "进度 " + progress + "%", SVProgressHUD.SVProgressHUDMaskType.Black);
        progress = 0;
        mHandler.sendEmptyMessageDelayed(0, 500);
    }

    public void showBoxLikeAliPay(View view) {
        //SVProgressHUD.showWithStatus(this, "支付宝...",R.drawable.bg_black);
        SVProgressHUD.showZhiWithStatus(this, "支付宝", SVProgressHUD.SVProgressHUDMaskType.None, R.drawable.bg_black);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (SVProgressHUD.isShowing(this)) {
                SVProgressHUD.dismiss(this);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
