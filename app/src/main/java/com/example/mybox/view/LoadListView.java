package com.example.mybox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.mybox.R;

/**
 *
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    //最后一个可见的item
    private int lastVisibleItem;
    ///总的item
    private int mTotaliTemCount;
    ////底布局
    private View footView;
    //是否正在加载数据
    private boolean isLoading;
    ////回调接口，用来加载数据
    private OnLoadMoreListener mLoadMoreListener;

    public LoadListView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        footView = View.inflate(this.getContext(), R.layout.laout_lv_foot, null);
        this.addFooterView(footView);
        footView.setVisibility(View.GONE);
        //千万别忘记设定监听器
        this.setOnScrollListener(this);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滚动的状态为静止的时候,并且总条目数量等于最后一个条目的时候.开始加载.
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleItem == mTotaliTemCount){

            if (!isLoading){
               footView.setVisibility(View.VISIBLE);
                //加载数据的回调.
                mLoadMoreListener.loadMore();
           }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleItem = firstVisibleItem + visibleItemCount;
        mTotaliTemCount = totalItemCount;
    }

    //当加载完毕后,对外调用.
    public void loadFinish(){
        footView.setVisibility(View.GONE);
        isLoading = false;
    }

    //对外设置监听方法.
    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mLoadMoreListener = listener;
    }

    //定义接口用于外部回调
    public interface OnLoadMoreListener{
        void loadMore();
    }


}
