package com.example.mybox.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import com.example.mybox.R;
import com.zxning.library.constant.SPName;
import com.zxning.library.entity.MeItemInfo;
import com.zxning.library.tool.SPUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 我的模块的适配器.
 */
public class MyAdapter extends BGARecyclerViewAdapter<MeItemInfo> {

    public MyAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_me);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, MeItemInfo meItemInfo) {
        bgaViewHolderHelper.setText(R.id.credit_tv, meItemInfo.name).setText(R.id.credit_num_tv,meItemInfo.content);
        boolean showWealthNum = (boolean) SPUtil.getData(SPName.CURRENT_ACCOUNT_NUM, true);
        if (showWealthNum) {
            bgaViewHolderHelper.getTextView(R.id.credit_num_tv).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            bgaViewHolderHelper.getTextView(R.id.credit_num_tv).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
