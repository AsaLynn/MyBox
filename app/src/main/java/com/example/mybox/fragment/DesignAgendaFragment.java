package com.example.mybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mybox.R;


/**
 *TextInputLayout的使用示范:
 * 包含TextInputLayout布局的控件,只能使用Activity作为上下文参数,
 * 才能进行xml转view的操作.
 * FloatingActionButton的使用示范:
 * Snackbar的简单使用.
 */
public class DesignAgendaFragment extends Fragment {
    private View mParentView;

    private TextInputLayout mTextInputLayout;
    private EditText mEditText;

    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_agenda, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        demoTextInputLayout();

        demomFloatingActionButton();
    }

    private void demoTextInputLayout() {
        mTextInputLayout = (TextInputLayout) mParentView.findViewById(R.id.text_input_layout);
        mEditText = mTextInputLayout.getEditText();
        mTextInputLayout.setHint("请输入4位学号");

        mEditText.addTextChangedListener(new TextWatcher() {
            /*int start开始的位置, int count*//*被改变的旧内容数*//*, int after*//*改变后的内容数量*//*) {
                //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。*/
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 4) {
                    mTextInputLayout.setError("学号输入错误！");
                    mTextInputLayout.setErrorEnabled(true);
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*//这里的s表示改变之后的内容，通常start和count组合，
                可以在s中读取本次改变字段中新的内容。而before表示被改变的内容的数量。*/
            }

            //表示最终内容
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void demomFloatingActionButton() {
        mFloatingActionButton = (FloatingActionButton) mParentView.findViewById(R.id.action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "结束当前Acitivty", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        });
    }
}
