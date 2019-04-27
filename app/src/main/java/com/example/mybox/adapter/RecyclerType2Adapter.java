package com.example.mybox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.xbc.contacts.ContactsInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class RecyclerType2Adapter extends RecyclerView.Adapter<RecyclerType2Adapter.ViewHolder> {
    private List<ContactsInfo> mList;
    //private LayoutInflater mInflater;
    private String[] mTitles = null;
    private boolean isJustContacts;

    public boolean isJustContacts() {
        return isJustContacts;
    }

    public void setJustContacts(boolean justContacts) {
        isJustContacts = justContacts;
    }

    public RecyclerType2Adapter(Context context) {
        //this.mInflater = LayoutInflater.from(context);//ContactsInfo
        this.mTitles = new String[20];
        for (int i = 0; i < 20; i++) {
            int index = i + 1;
            mTitles[i] = "item" + index;
        }
    }

    public RecyclerType2Adapter(Context context, List<ContactsInfo> list) {
        this.mList = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<ContactsInfo> list) {
        if (mList == null) {
            this.mList = new ArrayList<ContactsInfo>();
        } else {
            this.mList = list;
        }
        notifyDataSetChanged();
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view=mInflater.inflate(R.layout.item_department,parent,false);
        /*if (viewType == Type.HEADER.getValue()) {
            View view = UIUtils.inflate(R.layout.item_header);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else */
        if (viewType == Type.NORMAL.getValue()) {
            View view = UIUtils.inflate(R.layout.item_contact_type2);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View view = UIUtils.inflate(R.layout.item_groups);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name_tv.setText(mList.get(position).getName());
        holder.count_tv.setText(mList.get(position).getUseState());

        if (mList.get(position).isPeople()) {
            holder.enter_iv.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(mList.get(position).getJob())) {
                holder.level_tv.setVisibility(View.VISIBLE);
                holder.level_tv.setText(mList.get(position).getJob());
            } else {
                holder.level_tv.setVisibility(View.INVISIBLE);
            }
            holder.sign_tv.setVisibility(View.VISIBLE);
            holder.sign_tv.setText(mList.get(position).getLastName());

            //是否显示索引.
            //根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            if (position == getPositionForSection(section)) {
                holder.catalog.setVisibility(View.VISIBLE);
                holder.catalog.setText(mList.get(position).sortLetters);
            } else {
                holder.catalog.setVisibility(View.GONE);
            }
        } else {
            holder.enter_iv.setVisibility(View.VISIBLE);
            holder.level_tv.setVisibility(View.GONE);
            holder.sign_tv.setVisibility(View.GONE);
            if (0 == position) {
                holder.catalog.setVisibility(View.VISIBLE);
            } else {
                holder.catalog.setVisibility(View.GONE);
            }
        }

        if (isJustContacts) {
            holder.cbChecked.setVisibility(View.GONE);
        } else {
            holder.cbChecked.setVisibility(View.VISIBLE);
        }


        /*if (position == 0) {
            //toubu
        } else {

        }*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isPeople()) {
            return Type.NORMAL.getValue();
        } else {
            return Type.GROUPS.getValue();
        }
        /*if (position == 0) {
            return Type.HEADER.getValue();
        } else {
        }*/
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbChecked;
        private TextView sign_tv;
        private TextView name_tv;
        private TextView level_tv;
        private TextView count_tv;
        private ImageView enter_iv;
        private TextView catalog;
        private TextView header_tv;

        public ViewHolder(View view) {
            super(view);
            cbChecked = (CheckBox) view.findViewById(R.id.cbChecked);
            sign_tv = (TextView) view.findViewById(R.id.sign_tv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            level_tv = (TextView) view.findViewById(R.id.level_tv);
            count_tv = (TextView) view.findViewById(R.id.count_tv);
            enter_iv = (ImageView) view.findViewById(R.id.enter_iv);
            catalog = (TextView) view.findViewById(R.id.catalog);
            //header_tv = (TextView) view.findViewById(R.id.header_tv);
        }
    }


    public enum Type {
        NORMAL(107), GROUPS(105), HEADER(101);
        private int value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).sortLetters.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mList.get(i).sortLetters;
            if (getItemViewType(i) == Type.NORMAL.getValue()) {
                char firstChar = sortStr.toUpperCase(Locale.CHINESE).charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return -1;
    }


}
