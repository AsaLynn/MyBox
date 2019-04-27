package com.example.mybox.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.adapter.DesignFragmentAdapter;
import com.example.mybox.fragment.DesignAgendaFragment;
import com.example.mybox.fragment.DesignInfoDetailsFragment;
import com.example.mybox.fragment.DesignShareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Design Support Library新增所有兼容控件!
 *
 * android.support.design.widget.CoordinatorLayout 	超级FrameLayout
 * android.support.design.widget.AppBarLayout 	MD风格的滑动Layout
 * android.support.design.widget.TabLayout 	选项卡
 * android.support.design.widget.NavigationView 	DrawerLayout的SlideMenu(拉出的菜单栏)
 *  android.support.design.widget.TextInputLayout 	带提示的MD风格的EditText
 * FloatingActionButton  MD风格的圆形按钮
 * android.support.design.widget.Snackbar 	类似Toast，添加了简单的单个Action点击
 * CardView
 * CardView继承于Framelayout，同时CardView还有特有属性：
 * 有属性cardBackgroundColor，意为CardView的卡片颜色
 * 有属性cardConerRadius，意为CardView卡片的四角圆角矩形程度
 * android.support.design.widget.CollapsingToolbarLayout 	可折叠MD风格ToolbarLayout
android.support.v4.widget.NestedScrollView-->组合使用AppBarLayout$ScrollingViewBehavior
 menu菜单修改背景色步骤:
 1,style中定义样式,popupBackground修改颜色.
 <style name="OverflowMenu" parent="Widget.AppCompat.PopupMenu.Overflow">
 <item name="overlapAnchor">false</item>
 <item name="android:dropDownVerticalOffset">4dp</item>
 <item name="android:layout_marginRight">4dp</item>
 <item name="android:popupBackground">@color/c_ff99cc00</item>
 </style>
 2,将定义好的样式添加到主题样式
 <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
 <!-- Customize your theme here. -->
 <item name="colorPrimary">@color/colorPrimary</item>
 <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
 <item name="colorAccent">@color/colorAccent</item>
 <item name="actionOverflowMenuStyle">@style/OverflowMenu</item>
 </style>



 */
public class DesignDemoActivity extends AppCompatActivity {


    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件,抽屉效果控件.
    private DrawerLayout mDrawerLayout;
    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;
    //v4中的ViewPager控件
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
    }

    //初始化控件及布局
    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);

        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //设置拉出盒子的按钮图标.
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        //打开DrawerLayout
//        mDrawerLayout.openDrawer(mNavigationView);

        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("details");
        titles.add("share");
        titles.add("agenda");
        //设置tab默认的颜色,和选中时的颜色.参数1:默认颜色,参数2:选中时的颜色
        mTabLayout.setTabTextColors(Color.RED, Color.GREEN);
        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DesignInfoDetailsFragment());
        fragments.add(new DesignShareFragment());
        fragments.add(new DesignAgendaFragment());
        //创建ViewPager的adapter
        DesignFragmentAdapter adapter = new DesignFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        //设置tab模式:MODE_FIXED为不滑动,MODE_SCROLLABLE可滑动.
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            //回到主界面选中的tab的fragment页
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu_share:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.menu_agenda:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            //关闭DrawerLayout
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_design, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.action2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.action3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.action4:
                Toast.makeText(DesignDemoActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.action5:
                Toast.makeText(DesignDemoActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                //主界面左上角的icon点击事件
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
