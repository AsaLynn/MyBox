package com.example.mybox.activity;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bruce.pickerview.popwindow.BankSelectPopWin;
import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;

public class BottomDialogAActivity extends BaseActivity implements BankSelectPopWin.OnDatePickedListener {

    private EditText et_add_bank_select_bank;
    private BankSelectPopWin mBankSelectPopWin;
    private ArrayList<String> listBankName;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_bottom_dialog2);
        if (listBankName == null) {
            listBankName = new ArrayList<>();
        }
        String[] bank_names = UIUtils.getStringArray(R.array.bank_names);
        for (int i = 0; i < bank_names.length; i++) {
            listBankName.add(bank_names[i]);
        }
        et_add_bank_select_bank = (EditText) view.findViewById(R.id.et_add_bank_select_bank);
        et_add_bank_select_bank.setOnClickListener(this);
        mBankSelectPopWin = new BankSelectPopWin.Builder(BottomDialogAActivity.this, this).listItems(/*initBanks(banks)*/listBankName)
                .textConfirm("完成").build();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_add_bank_select_bank:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_add_bank_select_bank.getWindowToken(), 0);
                mBankSelectPopWin.showPopWin(this);
                break;

        }
    }

    @Override
    public void onDatePickCompleted(String result) {
        ///bankSelected = result;
        et_add_bank_select_bank.setText(result);
    }
}

