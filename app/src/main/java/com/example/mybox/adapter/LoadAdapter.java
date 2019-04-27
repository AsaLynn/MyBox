package com.example.mybox.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.bean.ItemDataInfo;

import java.util.List;

/**
 *
 */

public class LoadAdapter extends BaseAdapter {

    private List<ItemDataInfo> mList;


    public LoadAdapter(List<ItemDataInfo> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_list_load, null);
            holder = new ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
             holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(mList.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
    }
}
