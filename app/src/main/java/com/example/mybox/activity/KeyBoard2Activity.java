package com.example.mybox.activity;

import android.inputmethodservice.Keyboard;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.keyboard.BasicOnKeyboardActionListener;
import com.zxning.library.ui.keyboard.CustomKeyboardView;

public class KeyBoard2Activity extends BaseActivity {

    private CustomKeyboardView mKeyboardView;
    private View mTargetView;
    private Keyboard mKeyboard;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_custom_input);
        mKeyboard = new Keyboard(this, R.xml.keyboard);
        mTargetView = (EditText) view.findViewById(R.id.target);

        /*mTargetView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showKeyboardWithAnimation();
                return true;
            }
        });*/

        mKeyboardView = (CustomKeyboardView) view.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView
                .setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
                        this));


        //showKeyboardWithAnimation();
        return view;
    }

    /***
     * Mostra la tastiera a schermo con una animazione di slide dal basso
     */
    private void showKeyboardWithAnimation() {
        if (mKeyboardView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(KeyBoard2Activity.this,
                            R.anim.slide_in_bottom);
            mKeyboardView.showWithAnimation(animation);
        }
    }
}

