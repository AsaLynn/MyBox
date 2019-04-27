package com.example.mybox.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.mybox.R;

/**
 CoordinatorLayout
 AppBarLayout
 CollapsingToolbarLayout
 FloatingActionButton
 NestedScrollView,使用app:layout_behavior="@string/appbar_scrolling_view_behavior"可以配合CollapsingToolbarLayout产生动画.
 NestedScrollView的setNestedScrollingEnabled(false)方法可以使AppBarLayout不响应滚动事件。
 CardView
 带圆角的FrameLayout,
 Toolbar
向上滑动,CollapsingToolbarLayout折叠效果,
CollapsingToolbarLayout是通过实现AppBarLayout的OnOffsetChangedListener接口，
 根据AppBarLayout的偏移来实现子布局和title的视差移动以及ContentScrim和StatusBarScrim的显示。
 通过调用AppBarLayout的addOnOffsetChangedListener方法监听AppBarLayout的位移，判断CollapsingToolbarLayout的状态。

 */
public class DesignSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tb_design_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //设置左侧图标.
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_back);
        //设置图标是否显示,true显示,false不显示.
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情界面");
    }


}
