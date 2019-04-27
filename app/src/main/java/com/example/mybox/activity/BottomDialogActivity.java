package com.example.mybox.activity;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

public class BottomDialogActivity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_bottom_dialog);
        view.findViewById(R.id.btn).setOnClickListener(this);
        //view.findViewById(R.id.btn2).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        View view = UIUtils.inflate(R.layout.photo_choose_dialog);
        View openPhones = view.findViewById(R.id.openPhones);
        openPhones.setOnClickListener(this);
        View openCamera = view.findViewById(R.id.openCamera);
        openCamera.setOnClickListener(this);
        View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = this.getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
