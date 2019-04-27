package com.example.mybox.activity;

import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.hornet.chart.ArcCircleChart;
import com.hornet.chart.ArcData;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class Chart3Activity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_chart3);
        ArcCircleChart ac = (ArcCircleChart) view.findViewById(R.id.ac);
        List<ArcData> ls = new ArrayList<ArcData>();
        ArcData ad = new ArcData();
        ad.setData(270);
        ad.setKey("掌握好的知识点");
        ArcData ad2 = new ArcData();
        ad2.setData(150);
        ad2.setKey("掌握一般的知识点");
        ArcData ad3 = new ArcData();
        ad3.setData(70);
        ad3.setKey("掌握不好知识点");
        ls.add(ad);
        ls.add(ad2);
        ls.add(ad3);
        ac.setData(ls, 360, "试卷共50个知识点");
        return view;
    }


}
