package com.zxning.library.ui.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxning.library.tool.UIUtils;

/**
 * 列表分割.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int itemCount;
    int mSpace ;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(int space) {
        this.mSpace = UIUtils.dip2px(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildPosition(view) != 0)
            outRect.top = mSpace;
    }
}
