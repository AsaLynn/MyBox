package com.example.mybox.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.constant.Constant;
import com.zxning.library.tool.UIUtils;


public class SwitchDemoActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_switch_demo);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        return view;
    }


    private void jumpToStyle() {
        Intent intent = new Intent(this, SwitchStyleActivity.class);
        intent.putExtra(Constant.Intent.ExtraName.TITLE,"xml布局中使用");
        startActivity(intent);
    }

    private void jumpToStyleInCode() {
        Intent intent = new Intent(this, SwitchStyleInCodeActivity.class);
        intent.putExtra(Constant.Intent.ExtraName.TITLE,"代码中使用");
        startActivity(intent);
    }

    private void jumpToUse() {
        Intent intent = new Intent(this, SwitchUseActivity.class);
        intent.putExtra(Constant.Intent.ExtraName.TITLE,"简单使用");
        startActivity(intent);
    }

    private void jumpToRecycler() {
        Intent intent = new Intent(this, SwitchRecyclerActivity.class);
        intent.putExtra(Constant.Intent.ExtraName.TITLE,"RecyclerView中使用");
        startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                jumpToStyle();
                break;
            case 1:
                jumpToStyleInCode();
                break;
            case 2:
                jumpToUse();
                break;
            case 3:
                jumpToRecycler();
                break;
            default:
                break;
        }
    }

}
