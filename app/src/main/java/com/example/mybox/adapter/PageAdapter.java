package com.example.mybox.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.mybox.R;
import com.zxning.library.entity.PlateInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 页面模块的适配器.
 */
public class PageAdapter extends BGARecyclerViewAdapter<PlateInfo> {

    public PageAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_plate);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int position, PlateInfo o) {
        bgaViewHolderHelper.setText(R.id.tv, position+","+o.name);
    }
}
