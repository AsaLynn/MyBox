package com.example.mybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.adapter.DesignRecyclerViewAdapter;

/**
 * fragement的生命周期
 * onAttach()->created   当fragment被加入到activity时调用（在这个方法中可以获得所在的activity）。
 * onCreate()->created
 * onCreateView()->created   当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)。
 * onActivityCreated()->created  当activity的onCreated()方法返回后调用此方法。
 *onStart() ->Started
 * onResume()->Resumed
 * onPause()->Paused
 * onStop()->Stoped
 * onDestroyView() ->Destroyed 当fragment的layout被销毁时被调用。
 * onDestroy()->Destroyed
 * onDetach()->Destroyed   当fragment被从activity中删掉时被调用。
 * 一旦activity进入resumed状态（也就是running状态），你就可以自由地添加和删除fragment了。
 * 因此，只有当activity在resumed状态时，fragment的生命周期才能独立的运转，其它时候是依赖于activity的生命周期变化的。
 * 纵向条目的RecyclerView并且向上滑动具有挤出顶部视图的动画.
 */
public class DesignInfoDetailsFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_info_details, container, false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new DesignRecyclerViewAdapter(getActivity()));
    }
}
