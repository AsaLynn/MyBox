package com.example.mybox.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.adapter.FragmentTabAdapter;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.fragment.Fragment1;
import com.example.mybox.fragment.Fragment2;
import com.example.mybox.fragment.Fragment3;
import com.example.mybox.fragment.Fragment4;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 另类方式实现RadioGroup+Fragmnet来实现底部导航切换界面.
 */
public class FragmentTabActivity extends BaseActivity {
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();

   /* @Override
    protected void showTitle() {
        initToolbar("底部导航切换页面");
    }*/

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_tab_rg);
        rgs = (RadioGroup) view.findViewById(R.id.tabs_rg);
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                super.OnRgsExtraCheckedChanged(radioGroup, checkedId, index);
                Toast.makeText(FragmentTabActivity.this, fragments.get(index).getClass().getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
