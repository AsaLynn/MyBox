package com.zxning.library.ui.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxning.library.R;

/**
 * 格子列表的分割线.
 */
public class GridDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public GridDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.dp_px1);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}
