package com.example.mybox.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

public class TabLayout2Activity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_tab_layout2);

        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setTabTextColors(Color.BLACK, Color.RED);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        //初始化TabLayout的title数据集
        String[] tabs = getTabs();
        //初始化TabLayout的title
        for (int i = 0; i < tabs.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabs[i]));
        }
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //第一个参数表示当前Tab的位置，第二个参数是偏移值，
        // 从文档中看到该值的取值范围是0到1的一个半开区间，
        // 最后一个参数很好理解表示是否置移动后位置所对应的Tab为选中状态，
        // 打个比方，如果我从0移动到1的位置，如果updateSelectedText为true，
        // 那么1这个位置上的文本就会是一个选中状态。
        //mTabLayout.setScrollPosition(0, 3.0F, true);
       // mTabLayout.scrollTo(calculateScrollXForTab(position, positionOffset), 0);
        return view;
    }

    //@Override
    protected String[] getTabs() {
        return UIUtils.getStringArray(R.array.net_tabs_sty2);
    }

     /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }*/
}
