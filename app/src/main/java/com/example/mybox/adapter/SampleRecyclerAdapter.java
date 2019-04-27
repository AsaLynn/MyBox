package com.example.mybox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mybox.R;

import java.util.List;

/**
 * 创建RecyclerView可用适配器的步骤:
 * 1,定一个带参数构造方法,传入一个list,并且赋值到全局变量.
 * 2,继承RecyclerView.Adapter<Recycler.ViewHolder>,重写其三个方法,分别为,
 * getItemCount,获取条目数量为集合长度.
 * onCreateViewHolder,创建viewholder
 * onBindViewHolder,绑定viewholder
 * 给列表底部添加一个脚布局的操作.
 * 1,定义条目数量,重写getItemCount方法,返回长度应该为集合长度+1.
 * 2,定义条目类型,重写getItemViewType,若是列表最后一个条目则返回脚布局类型,
 * 否则返回普通类型.
 * 3,onCreateViewHolder,根据不同类型创建不同ViewHolder
 * 4,onBindViewHolder中根据holder的不同,来做不同的UI处理.
 */

public class SampleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOT = 2;
    private static final int TYPE_NORMAL = 1;
    private List<Integer> mList;
    private FootViewHolder footViewHolder;

    public SampleRecyclerAdapter(List<Integer> mList) {
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        //当为最后一个条目的时候,条目类型为脚布局,否则为普通条目.
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_NORMAL) {
            View itemView = View.inflate(parent.getContext(), R.layout.item_rv_srl, null);
            holder = new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOT) {
            View footView = View.inflate(parent.getContext(), R.layout.item_rv_foot, null);
            //代码中修改布局属性,是布局居中显示.
            footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            holder = new FootViewHolder(footView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleRecyclerAdapter.ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tv.setText(String.valueOf(mList.get(position)));
        }else if (holder instanceof FootViewHolder){
            if (null == footViewHolder){
                footViewHolder = (FootViewHolder) holder;
            }
        }
    }

    @Override
    public int getItemCount() {
        //因为增加了脚布局,所以条目数量+1
        return mList == null ? 0 : mList.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View footView) {
            super(footView);
        }
    }

    public void setLoadOver(boolean isOver){
        if (isOver){
            if (null != footViewHolder){
                footViewHolder.itemView.setVisibility(View.GONE);
            }
        }else {
            footViewHolder.itemView.setVisibility(View.VISIBLE);
        }
    }
}
