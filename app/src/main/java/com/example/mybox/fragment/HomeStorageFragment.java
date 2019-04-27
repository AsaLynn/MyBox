package com.example.mybox.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.activity.DiskLruCacheDemoActivity;
import com.example.mybox.base.BasePageFragment;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *数据存储.
 */
public class HomeStorageFragment extends BasePageFragment {
    @Override
    protected List<PlateInfo> getDatas() {
        List<PlateInfo> infoList = new ArrayList<>();
        String[] items = UIUtils.getStringArray(R.array.storage_plates_page1);
        for (int i = 0; i < items.length; i++) {
            PlateInfo plateInfo = new PlateInfo();
            plateInfo.name = items[i];
            infoList.add(plateInfo);
        }
        return infoList;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        switch (i){
            case 0:
                //startActivityByClazz(AssetsDbActivity.class,getDatas().get(i).name);
                startActivityByClazz(DiskLruCacheDemoActivity.class,getDatas().get(i).name);
                break;
            case 1:
                //startActivityByClazz(AssetsDbActivity.class,getDatas().get(i).name);
                break;
            case 2:
                break;
        }
    }


}
