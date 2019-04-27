package com.example.mybox.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.base.BasePageFragment;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeComprehensiveFragment extends BasePageFragment {
    @Override
    protected List<PlateInfo> getDatas() {
        if (null == infoList) {
            infoList = new ArrayList<>();
        }
        String[] items = UIUtils.getStringArray(R.array.comprehensive_plates_page1);
        for (int i = 0; i < items.length; i++) {
            PlateInfo plateInfo = new PlateInfo();
            plateInfo.name = items[i];
            infoList.add(plateInfo);
        }
        return infoList;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {

    }
}
