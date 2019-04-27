package com.example.mybox.base;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.mybox.R;
import com.zxning.library.tool.UIUtils;

/**
 * 总基类,适合不需联网或被动联网继承使用.
 */
public abstract class BaseCoordinatorActivity extends BaseContentActivity {

    protected FloatingActionButton fab;

    @Override
    protected View initRootView() {
        return UIUtils.inflate(this,R.layout.activity_base_coordinator);
    }

    protected CoordinatorLayout root_cl;

    protected void initLocal() {
        initCommonView();
        root_cl = (CoordinatorLayout) findViewById(R.id.root_cl);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}