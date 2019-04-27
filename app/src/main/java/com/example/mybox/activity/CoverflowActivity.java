package com.example.mybox.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dalong.francyconverflow.FancyCoverFlow;
import com.example.mybox.R;
import com.example.mybox.adapter.MyFancyCoverFlowAdapter;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.entity.ItemInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class CoverflowActivity extends BaseActivity {

    private FancyCoverFlow mfancyCoverFlow;
    private MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_coverflow);

        List<ItemInfo> mFancyCoverFlows = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ItemInfo ItemInfo = new ItemInfo();
            ItemInfo.setName((i + 1) + "天");
            ItemInfo.setSelected(false);
            mFancyCoverFlows.add(ItemInfo);
        }
        mfancyCoverFlow = (FancyCoverFlow) view.findViewById(R.id.fancyCoverFlow);
        mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(this, mFancyCoverFlows);
        mfancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
        mfancyCoverFlow.setUnselectedAlpha(0.5f);//通明度
        mfancyCoverFlow.setUnselectedSaturation(0.5f);//设置选中的饱和度
        mfancyCoverFlow.setUnselectedScale(0.3f);//设置选中的规模
        mfancyCoverFlow.setSpacing(0);//设置间距
        mfancyCoverFlow.setMaxRotation(0);//设置最大旋转
        mfancyCoverFlow.setScaleDownGravity(0.5f);
        mfancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        int num = Integer.MAX_VALUE / 2 % mFancyCoverFlows.size();
        int selectPosition = Integer.MAX_VALUE / 2 - num;
        mfancyCoverFlow.setSelection(selectPosition);
        mfancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo homeFancyCoverFlow = (ItemInfo) mfancyCoverFlow.getSelectedItem();
                if (homeFancyCoverFlow != null) {
                    Toast.makeText(CoverflowActivity.this, homeFancyCoverFlow.getName(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
}
