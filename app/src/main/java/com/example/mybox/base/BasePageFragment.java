package com.example.mybox.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.adapter.PageAdapter;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.views.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 页面点击基类
 */
public abstract class BasePageFragment extends BaseFragment
        implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener {

    private BGARefreshLayout bga_rl;
    private RecyclerView rv;
    protected ArrayList<PlateInfo> infoList;

    @Override
    protected View onCreateSuccessedView() {
        View view = UIUtils.inflate(getActivity(), R.layout.fragment_page1);

        bga_rl = (BGARefreshLayout) view.findViewById(R.id.bga_rl);
        bga_rl.setDelegate(this);
        bga_rl.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_rl.setIsShowLoadingMoreView(false);
        bga_rl.setPullDownRefreshEnable(false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        PageAdapter mAdapter = new PageAdapter(rv);
        mAdapter.setDatas(getDatas());
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(1));
        mAdapter.setOnRVItemClickListener(this);
        return view;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                bga_rl.endRefreshing();
                // mAdapter.addNewDatas(refreshModels);
                rv.smoothScrollToPosition(0);
            }
        }, 3000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                bga_rl.endLoadingMore();
                //mAdapter.addMoreDatas(refreshModels);
            }
        }, 500);
        return true;
    }

    protected abstract List<PlateInfo> getDatas();

    @Override
    public abstract void onRVItemClick(ViewGroup viewGroup, View view, int i);
}
