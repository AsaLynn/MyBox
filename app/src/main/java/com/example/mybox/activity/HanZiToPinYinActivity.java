package com.example.mybox.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.xbc.contacts.ContactsUtils;
import com.xbc.contacts.SortToken;
import com.zxning.library.tool.UIUtils;

/**
 * 页面.
 */
public class HanZiToPinYinActivity extends BaseActivity {

    private EditText content_et;
    private TextView content_tv;

    @Override
    protected void showTitle() {
        initToolbar(true, "汉字转拼音");
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.layout_hanzi_pinyin);
        content_et = (EditText) view.findViewById(R.id.content_et);
        Button ok_btn = (Button) view.findViewById(R.id.ok_btn);
        content_tv = (TextView) view.findViewById(R.id.content_tv);
        ok_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                //String pinYin = ContactsUtils.getPinYin(content_et.getText().toString());
                //String pinYin = ContactsUtils.converterToSpell(content_et.getText().toString());
                //String pinYin = ContactsUtils.converterToFirstSpell(content_et.getText().toString());
                SortToken sortToken = ContactsUtils.getSortTokenByName(content_et.getText().toString());
                String pinYin = sortToken.wholeSpell+ "--"+sortToken.simpleSpell;
                if (TextUtils.isEmpty(pinYin)) {
                    UIUtils.showMsg("转换为空.");
                } else {
                    content_tv.setText(pinYin);
                }
                break;
        }
    }


}
