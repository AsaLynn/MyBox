package com.example.mybox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mybox.bean.StuClzInfo;

import java.util.List;

/**
 *
 */

public class JsonListAdapter extends BaseAdapter{

    private List<StuClzInfo> mList;

    public JsonListAdapter(List<StuClzInfo> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(parent.getContext());
        view.setText(mList.get(position).getCode()+"人数是"+mList.get(position).getCount());
        return view;
    }
}
