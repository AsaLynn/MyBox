package com.example.mybox.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.entity.HelpCenterInfo;
import com.zxning.library.entity.HelpInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewDemo extends BaseActivity implements ExpandableListView.OnChildClickListener {
    private ExpandableListView help_lv;
    private List<HelpCenterInfo> parentList;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_help_center);
        help_lv = (ExpandableListView) view.findViewById(R.id.helpCenter_lv);
        help_lv.setDivider(null);
        help_lv.setGroupIndicator(null);
        help_lv.setOnChildClickListener(this);
        initData();
        help_lv.setAdapter(new HelpAdapter());
        return view;
    }

    private void initData() {
        parentList = new ArrayList<HelpCenterInfo>();
        String[] help_center_infos = UIUtils.getStringArray(R.array.help_center_infos);
        int[] ids = {R.array.help_infos1, R.array.help_infos2, R.array.help_infos3};
        for (int i = 0; i < help_center_infos.length; i++) {
            HelpCenterInfo helpCenterInfo = new HelpCenterInfo();
            helpCenterInfo.name = help_center_infos[i];
            String[] helpInfos = UIUtils.getStringArray(ids[i]);
            List<HelpInfo> list = new ArrayList<>();
            for (int j = 0; j < helpInfos.length; j++) {
                HelpInfo helpInfo = new HelpInfo();
                helpInfo.setTitle(helpInfos[j]);
                list.add(helpInfo);
            }
            helpCenterInfo.infoList = list;
            parentList.add(helpCenterInfo);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        UIUtils.showMsg(parentList.get(groupPosition).infoList.get(childPosition).getTitle());
        return true;
    }

    class HelpAdapter extends BaseExpandableListAdapter {//

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return parentList.get(groupPosition).infoList.get(childPosition);
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = UIUtils.inflate(R.layout.item_children_help);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.second_textview);
            tv.setText(parentList.get(groupPosition).infoList.get(childPosition).getTitle());
            return convertView;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return parentList.get(groupPosition).infoList.size();
        }

        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return parentList.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parentList.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = UIUtils.inflate(R.layout.item_parent_help);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.parent_textview);
            ImageView arrow_iv = (ImageView) convertView
                    .findViewById(R.id.arrow_iv);
            tv.setText(ExpandableListViewDemo.this.parentList.get(groupPosition).name);
            if (isExpanded) {
                arrow_iv.setImageResource(R.mipmap.iv_m_img_elv_indicator_normal);
            } else {
                arrow_iv.setImageResource(R.mipmap.iv_m_img_elv_indicator_down);
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}
