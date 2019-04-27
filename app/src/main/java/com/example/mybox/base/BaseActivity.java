package com.example.mybox.base;

import android.view.View;
import android.widget.LinearLayout;

import com.example.mybox.R;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 基类,标题公用.
 */
public abstract class BaseActivity extends BaseContentActivity implements  BGARefreshLayout.BGARefreshLayoutDelegate {

    @Override
    protected View initRootView() {
//        return inflate(this,R.layout.activity_base);
        return View.inflate(this,R.layout.activity_base,null);
    }

    protected LinearLayout root_ll;

    protected void initLocal() {
        initCommonView();
        root_ll = (LinearLayout) findViewById(R.id.root_ll);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

}