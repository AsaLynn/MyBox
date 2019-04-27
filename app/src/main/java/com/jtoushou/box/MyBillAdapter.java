package com.jtoushou.box;

import android.support.v7.widget.RecyclerView;

import com.example.mybox.R;
import com.zxning.library.entity.BillInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 我的账单.
 */
public class MyBillAdapter extends BGARecyclerViewAdapter<BillInfo> {

    public MyBillAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_bill);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, BillInfo billInfo) {

        bgaViewHolderHelper.setText(R.id.card_id_tv, billInfo.cardId)
                .setText(R.id.bank_name_tv, billInfo.bankName)
                .setText(R.id.bill_data_tv, billInfo.billData)
                .setText(R.id.residual_amount_tv, billInfo.residualAmount)
                .setText(R.id.should_repayment_tv, billInfo.shouldRepaymentAmount);

    }

}
