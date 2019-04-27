package com.example.mybox.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.constant.Constant;
import com.zxning.library.tool.SPUtil;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.gesturelock.CustomLockView;
import com.zxning.library.ui.gesturelock.LockUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码引导设置!
 * CustomLockView的使用:
 * 1,xml中布局,代码中实例化.
 * 2,setOnCompleteListener
 *
 */
public class GestureLockSettingAty extends BaseActivity {

    private List<ImageView> list = new ArrayList<ImageView>();
    private TextView tvWarn;
    private int times = 0;
    private int[] mIndexs = null;
    private CustomLockView cl;
    private int toGesturelock;
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_settings:
                    resetGestureLock();
                    break;
            }
            return true;
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //关闭手势密码设置提示,进入首页.
            ignoreGesturePrompt(false);
            SPUtil.saveData("set_gesturel_locked", false);
        }
    };

    private void ignoreGesturePrompt(boolean flag) {
        LockUtil.setPwdStatus(flag);
        goHome();
    }

    private void goHome() {//判断是否为首次登陆
        finish();
    }

    @Override
    protected void showTitle() {
        super.showTitle();
        initToolbar(false, "跳过", listener,
                this.getIntent().getStringExtra(Constant.Intent.ExtraName.TITLE)
        );
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.content_gesture_lock);
        cl = (CustomLockView) view.findViewById(R.id.cl);
        cl.setShow(false);//设置是否显示箭头.
        tvWarn = (TextView) view.findViewById(R.id.tvWarn);
        //初始化9个小圆
        int[] ivIds = {R.id.iva, R.id.ivb, R.id.ivc, R.id.ivd, R.id.ive, R.id.ivf, R.id.ivg, R.id.ivh, R.id.ivi};
        for (int i = 0; i < ivIds.length; i++) {
            ImageView iv = (ImageView) view.findViewById(ivIds[i]);
            list.add(iv);
        }

        cl.setOnCompleteListener(new CustomLockView.OnCompleteListener() {
            @Override
            public void onComplete(int[] indexs) {
                mIndexs = indexs;
                //显示次数
                if (times == 0) {
                    for (int i = 0; i < indexs.length; i++) {
                        list.get(indexs[i]).setImageDrawable(getResources().getDrawable(R.mipmap.gesturecirlebrownsmall));
                    }
                    GestureLockSettingAty.this.tvWarn.setText("再次绘制解锁图案");
                    GestureLockSettingAty.this.tvWarn.setTextColor(getResources().getColor(R.color.c_474747));
                    times++;
                    showRightBtn("重新设置", onMenuItemClick);
                } else if (times == 1) {
                    //将密码设置在本地.
                    LockUtil.setPwdToDisk(GestureLockSettingAty.this, mIndexs);
                    //开启密码.
                    LockUtil.setPwdStatus(true);
                    //设置密码状态保存在本地.
                    finish();
                }
            }

            @Override
            public void onError() {
                GestureLockSettingAty.this.tvWarn.setText("与上一次绘制不一致，请重新绘制");
                GestureLockSettingAty.this.tvWarn.setTextColor(getResources().getColor(R.color.c_ff4444));
            }
        });

        return view;
    }


    private void resetGestureLock() {
        times = 0;
        for (int i = 0; i < mIndexs.length; i++) {//unselected
            list.get(mIndexs[i]).setImageDrawable(getResources().getDrawable(R.mipmap.unselected));
        }
        cl.clearCurrent();
        tvWarn.setText("绘制解锁图案");
        tvWarn.setTextColor(UIUtils.getColor(R.color.c_474747));
    }
}
