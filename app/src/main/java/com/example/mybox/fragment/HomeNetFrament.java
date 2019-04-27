package com.example.mybox.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.activity.GlideDemoActivity;
import com.example.mybox.activity.HttpJsonDemoActivity;
import com.example.mybox.activity.HttpUrlConnnectionDemoActivity;
import com.example.mybox.activity.ListViewDemoActivity;
import com.example.mybox.activity.ListViewLruCacheActivity;
import com.example.mybox.activity.OkHttp3DemoActivity;
import com.example.mybox.activity.RetrofitDemoActivity;
import com.example.mybox.activity.VolleyDemoActivity;
import com.example.mybox.base.BasePageFragment;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeNetFrament extends BasePageFragment {
    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        switch (i){
            case 0:
                startActivityByClazz(GlideDemoActivity.class, getDatas().get(i).name);
                //startActivityByClazz(Vollay1Activity.class);
                break;
            case 1:
                startActivityByClazz(HttpUrlConnnectionDemoActivity.class, getDatas().get(i).name);
                break;
            case 2:
                startActivityByClazz(HttpJsonDemoActivity.class, getDatas().get(i).name);
                break;
            case 3:
                startActivityByClazz(OkHttp3DemoActivity.class, getDatas().get(i).name);
               break;
            case 4:
                startActivityByClazz(ListViewDemoActivity.class, getDatas().get(i).name);
                break;
            case 5:
                startActivityByClazz(ListViewLruCacheActivity.class, getDatas().get(i).name);
                break;
            case 6:
                startActivityByClazz(VolleyDemoActivity.class, getDatas().get(i).name);
                break;
            case 7:
                startActivityByClazz(RetrofitDemoActivity.class, getDatas().get(i).name);
                break;
        }
    }

    @Override
    protected List<PlateInfo> getDatas() {
        List<PlateInfo> infoList = new ArrayList<>();
        String[] items = UIUtils.getStringArray(R.array.net_plates_page1);
        for (int i = 0; i < items.length; i++) {
            PlateInfo plateInfo = new PlateInfo();
            plateInfo.name = items[i];
            infoList.add(plateInfo);
        }
        return infoList;
    }
}
