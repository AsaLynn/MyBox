package com.example.mybox.activity;

import android.view.View;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.hzy.paykeyboard.IPasswordCallback;
import com.hzy.paykeyboard.PayKeyboard;
import com.zxning.library.tool.UIUtils;

public class KeyBoard1Activity extends BaseActivity implements IPasswordCallback {

    private PayKeyboard mPayKeyboard;

    @Override
    protected void showTitle() {
        initToolbar(true, "仿支付宝弹出密码输入");
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_pay_ali_key_board);
        view.findViewById(R.id.show_keyboard).setOnClickListener(this);
        mPayKeyboard = new PayKeyboard(this);
        mPayKeyboard.setPasswordCallback(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_keyboard) {
            mPayKeyboard.show();
        }
    }

    @Override
    public void onInputComplete(Object encrypted) {
        String password = mPayKeyboard.getDecryptedPassword(encrypted);
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInputCancel() {
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordForget() {
        Toast.makeText(this, "forget", Toast.LENGTH_SHORT).show();
    }
}
