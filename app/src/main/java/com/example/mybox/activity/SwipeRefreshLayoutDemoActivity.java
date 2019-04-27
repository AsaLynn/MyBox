package com.example.mybox.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mybox.R;
import com.example.mybox.adapter.SampleRecyclerAdapter;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/*
1,SwipeRefreshLayout的使用:放在布局的根节点.
    设置下拉监听,
    设置下拉进度条显示不同颜色
    设置关闭进度条的显示.
2,RecyclerView条目添加不同的类型:根据不同的类型创建不同的ViewHolder.
    设置布局结构
    设置条目大小固定
    设置适配器
    设置滑动监听
3,ContentLoadingProgressBar的使用.
<android.support.v4.widget.ContentLoadingProgressBar
        android:id="@+id/progressbar"
        style="?android:attr/progressBarStyleInverse"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />



 */
public class SwipeRefreshLayoutDemoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srl;
    private RecyclerView recyclerView;
    private List<Integer> list;
    private SampleRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager manager;
    private int lastVisibleItemPosition;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_swipe_refresh_layout_demo);
        //初始化布局控件
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        //设置下拉监听
        srl.setOnRefreshListener(this);
        //设置下拉进度圈的多个颜色显示效果.
        srl.setColorSchemeColors(getResources().getColor(R.color.black),getResources().getColor(R.color.holo_red_light),getResources().getColor(R.color.holo_orange_light),getResources().getColor(R.color.c_ff99cc00),getResources().getColor(R.color.blue));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        //设置布局管理
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //设置条目大小固定.
        recyclerView.setHasFixedSize(true);
        //设置条目默认动画.
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置适配器.
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        //过时了,被addOnScrollListener代替.
       /* recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当滚动状态静止的时候,并且滑动到最后一个条目的时候开始加载,
                if (newState == RecyclerView.SCROLL_STATE_IDLE){

                    if (lastVisibleItemPosition + 1 == recyclerAdapter.getItemCount()){
                        UIUtils.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.showMsg("上拉了!");
                                int size = list.size();
                                for (int i = 0; i < 15; i++) {
                                    list.add(size+i);
                                }
//                                recyclerAdapter.setLoadOver(true);
                                recyclerAdapter.notifyDataSetChanged();
                            }
                        },5*1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });*/

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当滚动状态静止的时候,并且滑动到最后一个条目的时候开始加载,
                if (newState == RecyclerView.SCROLL_STATE_IDLE){

                    if (lastVisibleItemPosition + 1 == recyclerAdapter.getItemCount()){
                        UIUtils.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.showMsg("上拉了!");
                                int size = list.size();
                                for (int i = 0; i < 15; i++) {
                                    list.add(size+i);
                                }
//                                recyclerAdapter.setLoadOver(true);
                                recyclerAdapter.notifyDataSetChanged();
                            }
                        },5*1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });

        recyclerAdapter = new SampleRecyclerAdapter(list);
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onRefresh() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 0; i < 15; i++) {
                    list.add(i);
                }
                recyclerAdapter.notifyDataSetChanged();
                UIUtils.showMsg("下拉了!");
                //关闭下拉刷新进度圈的ui显示.
                srl.setRefreshing(false);
            }
        },5*1000);
    }
}
