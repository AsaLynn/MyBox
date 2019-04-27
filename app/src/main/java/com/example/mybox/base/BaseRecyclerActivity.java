package com.example.mybox.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.mybox.R;
import com.example.mybox.adapter.PageAdapter;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.views.SpaceItemDecoration;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * 列表基类!
 */
public abstract class BaseRecyclerActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private RecyclerView rv;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.layout_recycler);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PageAdapter mAdapter = new PageAdapter(rv);
        mAdapter.setDatas(getDatas());
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(1));
        mAdapter.setOnRVItemClickListener(this);
        return view;
    }

    protected abstract List<PlateInfo> getDatas();
}
