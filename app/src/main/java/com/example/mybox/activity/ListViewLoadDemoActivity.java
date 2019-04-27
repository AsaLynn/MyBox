package com.example.mybox.activity;

import android.view.View;

import com.example.mybox.R;
import com.example.mybox.adapter.LoadAdapter;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.ItemDataInfo;
import com.example.mybox.view.LoadListView;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页加载原理.
 * 1,实现一个普通的Listview.
 * 2，换成自定义的listview.
 */

public class ListViewLoadDemoActivity extends BaseActivity implements LoadListView.OnLoadMoreListener {

    private LoadListView lv;
    private List<ItemDataInfo> list;
    private LoadAdapter adapter;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_list_load);
        lv = (LoadListView) view.findViewById(R.id.lv_load);
        lv.setOnLoadMoreListener(this);
        initData();
        adapter = new LoadAdapter(list);
        lv.setAdapter(adapter);
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemDataInfo info = new ItemDataInfo();
            info.setContent("这是原来数据" + i);
            list.add(info);
        }
    }

    @Override
    public void loadMore() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMoreData();
                lv.loadFinish();
            }
        }, 5 * 1000);

    }

    private void getMoreData() {
        for (int i = 0; i < 5; i++) {
            ItemDataInfo info = new ItemDataInfo();
            info.setContent("这个是刚加载的数据" + i);
            list.add(info);
            //熟悉适配器,会重新调用getview方法.
            adapter.notifyDataSetChanged();
        }
    }
}
