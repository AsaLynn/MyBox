package com.example.mybox.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.factory.FragmentFactory;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.views.NestRadioGroup;

/**
 * 底部采用自定义的RadioGroup和RadioButton
 * 实现即可以嵌套ViewGroup又可以嵌套RadioButton等子控件,从而实现了底部具有小红点的功能.
 */
public class HomeActivity extends BaseActivity {

    private NestRadioGroup mGroup;
    private FrameLayout content_fl;
    private TextView msg_dot_tv;
    private int index;
    //private final String[] tabNames = new String[]{"页面", "存储", "网络", "我的"};
    private final String[] tabNames = UIUtils.getStringArray(R.array.bottom_tabs);
    private NestRadioGroup.OnCheckedChangeListener myCheckChangeListener = new NestRadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(NestRadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.rb_home_page:
                    index = 0;
                    break;
                case R.id.rb_net:
                    index = 1;
                    break;
                case R.id.rb_comprehensive:
                    index = 2;
                    break;
                case R.id.rb_monney:
                    index = 3;
                    break;
                case R.id.rb_me:
                    index = 4;
                    break;
            }
            initToolbar(tabNames[index]);
            Fragment fragment = (Fragment) fragmentStatePagerAdapter.instantiateItem(content_fl, index);
            //替换方法3,需要去替换的fragment对象
            fragmentStatePagerAdapter.setPrimaryItem(null, 0, fragment);
            //提交操作
            fragmentStatePagerAdapter.finishUpdate(null);

        }
    };

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_home);
        content_fl = (FrameLayout) view.findViewById(R.id.content_fl);
        mGroup = (NestRadioGroup) view.findViewById(R.id.radiogroup);

        mGroup.setOnCheckedChangeListener(myCheckChangeListener);
        mGroup.check(R.id.rb_home_page);//选中首页.
        msg_dot_tv = (TextView) view.findViewById(R.id.msg_dot_tv);
        changeMsgState("1");
        return view;
    }

    //创建fragment的数据适配器
    FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public Fragment getItem(int position) {
            int intId = 0;
            switch (position) {
                case 0:
                    intId = R.integer.home_index;
                    break;
                case 1:
                    intId = R.integer.net_index;
                    break;
                case 2:
                    intId = R.integer.comprehensive_index;
                    break;
                case 3:
                    intId = R.integer.wealth_index;
                    break;
                case 4:
                    intId = R.integer.me_index;
                    break;

            }
           Fragment fragment = FragmentFactory.createFragment(intId);
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    };

    @Override
    protected void showTitle() {
        initToolbar(tabNames[0]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这里不需要执行父类的点击事件，所以直接return
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void changeMsgState(String count) {
        if (!TextUtils.isEmpty(count) && !"0".equals(count)) {
            msg_dot_tv.setVisibility(View.VISIBLE);
        } else {
            msg_dot_tv.setVisibility(View.GONE);
        }
    }
}