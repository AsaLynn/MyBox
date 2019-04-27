package com.zxning.library.ui.indicator;

public interface IconPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the com.jtoushou.mytooldemo.adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
}
