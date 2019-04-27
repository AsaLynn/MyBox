package com.example.mybox.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.ysdemo.keyboard.view.KeyboardUtil;
import com.ysdemo.keyboard.view.MyKeyboardView;
import com.zxning.library.tool.UIUtils;

public class KeyBoard3Activity extends BaseActivity {

    private Button btn_showKey;
    private Button btn_hideKey;
    private Button btn_price;
    private Button btn_number;
    private int change_type;
    // private XEditText et_amount;
    private EditText et_amount;
    private KeyboardUtil keyboardUtil;
    private RelativeLayout rl_keyboard;
    private MyKeyboardView keyboard_view;

    @Override
    protected View initContentView() {//activity_pay_key
        View view = UIUtils.inflate(this, R.layout.activity_pay_key);
        initTestBtn(view);
        initKeyboard(view);
        showKeyBoard();
        return view;
    }

    private void initTestBtn(View view) {
        btn_showKey = (Button) view.findViewById(com.ysdemo.keyboard.R.id.btn_showKey);
        btn_hideKey = (Button) view.findViewById(com.ysdemo.keyboard.R.id.btn_hideKey);
        btn_price = (Button) view.findViewById(com.ysdemo.keyboard.R.id.btn_price);
        btn_number = (Button) view.findViewById(com.ysdemo.keyboard.R.id.btn_number);
        btn_showKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showKeyBoard();
            }
        });
        btn_hideKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyBoard();
            }
        });
        btn_price.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = KeyboardUtil.TYPE_PRICE;
                showKeyBoard();
            }
        });
        btn_number.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = KeyboardUtil.TYPE_NUMBER;
                showKeyBoard();
            }
        });
    }

    private void initKeyboard(View view) {
        et_amount = (EditText) view.findViewById(R.id.et_amount);
        /*et_amount.setInputType(InputType.TYPE_NULL);*/
        keyboard_view = (MyKeyboardView) view.findViewById(R.id.keyboard_view);
        keyboardUtil = new KeyboardUtil(this, et_amount, keyboard_view);
        keyboardUtil.setType(KeyboardUtil.TYPE_PRICE);
        keyboardUtil.setKeyboardListener(new KeyboardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                String result = et_amount.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case KeyboardUtil.TYPE_NUMBER:
                            msg += "num:" + result;
                            break;
                        case KeyboardUtil.TYPE_PRICE:
                            msg += "price:" + result;
                            break;
                        default:
                            msg += "input:" + result;
                            break;
                    }
                    Toast.makeText(KeyBoard3Activity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard();
                change_type = -1;
            }
        });
    }

    /**
     * 显示键盘
     */
    protected void showKeyBoard() {
        //rl_keyboard.setVisibility(View.VISIBLE);
        et_amount.setText("");
        switch (change_type) {
            case KeyboardUtil.TYPE_NUMBER:
                et_amount.setHint("请输入数量");
                break;

            case KeyboardUtil.TYPE_PRICE:
                et_amount.setHint("请输入价格");
                break;

            default:
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
        showKeyboardWithAnimation();
    }

    private void showKeyboardWithAnimation() {
        if (keyboard_view.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(this,
                            R.anim.slide_in_bottom);
            keyboard_view.showWithAnimation(animation);
        }
    }

    /**
     * 显示键盘
     */
    protected void hideKeyBoard() {
        //rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                hideKeyBoard();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/
}

