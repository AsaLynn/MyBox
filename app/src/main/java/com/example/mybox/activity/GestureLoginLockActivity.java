package com.example.mybox.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.gesturelock.CustomLockView;
import com.zxning.library.ui.gesturelock.LockUtil;

/**
 * 登陆验证手势密码页面
 */
public class GestureLoginLockActivity extends BaseActivity {
    private TextView tvWarn;
    private int[] mIndexs;
    private View login_other_tv;
    private View forget_pw_tv;

    @Override
    protected void showTitle() {
        showToolbar(false);
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_loginlock);
        login_other_tv = view.findViewById(R.id.login_other_tv);
        forget_pw_tv = view.findViewById(R.id.forget_pw_tv);
        login_other_tv.setOnClickListener(this);
        forget_pw_tv.setOnClickListener(this);
        tvWarn = (TextView) view.findViewById(R.id.tvWarn);
        mIndexs = LockUtil.getPwd();
        //判断当前是否设置过密码
        if (mIndexs.length > 1) {
            final CustomLockView cl = (CustomLockView) view.findViewById(R.id.cl);
            cl.setmIndexs(mIndexs);
            cl.setErrorTimes(5);
            cl.setStatus(1);
            cl.setShow(false);
            cl.setOnCompleteListener(new CustomLockView.OnCompleteListener() {
                @Override
                public void onComplete(int[] indexs) {
                    UIUtils.showMsg("成功");
                    finish();
                }

                @Override
                public void onError() {
                    if (cl.getErrorTimes() > 0) {
                        tvWarn.setText("密码错误，还可以再输入" + cl.getErrorTimes() + "次");
                        tvWarn.setTextColor(getResources().getColor(R.color.c_ff4444));
                        tvWarn.setAnimation(shakeAnimation(5));
                    } else {
                        UIUtils.showMsg("请重新设置");
                        LockUtil.clearPwd(GestureLoginLockActivity.this);
                        LockUtil.setPwdStatus(false);
                        finish();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这里不需要执行父类的点击事件，所以直接return
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
