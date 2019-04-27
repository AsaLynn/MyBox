package com.example.mybox.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.mybox.R;
import com.example.mybox.activity.AnimatorActivity;
import com.example.mybox.activity.BottomDialogAActivity;
import com.example.mybox.activity.BottomDialogActivity;
import com.example.mybox.activity.BottomDialogBActivity;
import com.example.mybox.activity.BottomDialogCActivity;
import com.example.mybox.activity.Chart1Activity;
import com.example.mybox.activity.Chart2Activity;
import com.example.mybox.activity.Chart3Activity;
import com.example.mybox.activity.ClipLoadActivity;
import com.example.mybox.activity.ContactsListAActivity;
import com.example.mybox.activity.ContactsListActivity;
import com.example.mybox.activity.ContactsOptDemoActivity;
import com.example.mybox.activity.Coverflow2Activity;
import com.example.mybox.activity.CoverflowActivity;
import com.example.mybox.activity.DesignDemoActivity;
import com.example.mybox.activity.DialogActivity;
import com.example.mybox.activity.DialogBoxActivity;
import com.example.mybox.activity.ExpandableListViewDemo;
import com.example.mybox.activity.FileOptionActivity;
import com.example.mybox.activity.FragmentTabActivity;
import com.example.mybox.activity.GestureActivity;
import com.example.mybox.activity.HanZiToPinYinActivity;
import com.example.mybox.activity.IndexRecyclerActivity;
import com.example.mybox.activity.JsonActivity;
import com.example.mybox.activity.KeyBoard1Activity;
import com.example.mybox.activity.KeyBoard2Activity;
import com.example.mybox.activity.KeyBoard3Activity;
import com.example.mybox.activity.ListViewLoadDemoActivity;
import com.example.mybox.activity.MaskedImageActivity;
import com.example.mybox.activity.RippleImageActivity;
import com.example.mybox.activity.SalaryCheckAty;
import com.example.mybox.activity.SnackbarADemoActivity;
import com.example.mybox.activity.SnackbarBDemoActivity;
import com.example.mybox.activity.SwipeRefreshLayoutDemoActivity;
import com.example.mybox.activity.SwitchDemoActivity;
import com.example.mybox.activity.TSnackbarDemoActivity;
import com.example.mybox.activity.TabLayout2Activity;
import com.example.mybox.activity.TextShakeActivity;
import com.example.mybox.activity.TimeCheck2Activity;
import com.example.mybox.activity.TimeSelectorActivity;
import com.example.mybox.activity.TimeViewActivity;
import com.example.mybox.activity.TopSnackbarDemoActivity;
import com.example.mybox.activity.Translate1Activity;
import com.example.mybox.activity.WeightDemoActivity;
import com.example.mybox.base.BasePageFragment;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面模块第一页
 */
public class HomePageFragment extends BasePageFragment {

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        switch (i) {
            case 43:
                //圆形图片的水波纹效果
                startActivityByClazz(RippleImageActivity.class, getDatas().get(i).name);
                break;
            case 42:
                //时钟控件.
                startActivityByClazz(TimeViewActivity.class, getDatas().get(i).name);
                break;
            case 41:
                startActivityByClazz(SwipeRefreshLayoutDemoActivity.class, getDatas().get(i).name);
                break;
            case 40:
                startActivityByClazz(ListViewLoadDemoActivity.class, getDatas().get(i).name);
                break;
            case 39:
                startActivityByClazz(ContactsOptDemoActivity.class, getDatas().get(i).name);
                break;
            case 38:
                startActivityByClazz(HanZiToPinYinActivity.class, getDatas().get(i).name);
                break;
            case 37:
                startActivityByClazz(FileOptionActivity.class, getDatas().get(i).name);
                break;
            case 36:
                startActivityByClazz(ContactsListAActivity.class, getDatas().get(i).name);
                break;
            case 35:
                startActivityByClazz(ContactsListActivity.class, getDatas().get(i).name);
                break;
            case 34:
                startActivityByClazz(CoverflowActivity.class, getDatas().get(i).name);
                break;
            case 33:
                startActivityByClazz(Coverflow2Activity.class, getDatas().get(i).name);
                break;
            case 32:
                startActivityByClazz(TimeCheck2Activity.class, getDatas().get(i).name);
                break;
            case 31:
                startActivityByClazz(TimeSelectorActivity.class, getDatas().get(i).name);
                break;
            case 30:
                startActivityByClazz(AnimatorActivity.class, getDatas().get(i).name);
                break;
            case 29:
                startActivityByClazz(WeightDemoActivity.class, getDatas().get(i).name);
                break;
            case 28:
                startActivityByClazz(SwitchDemoActivity.class, getDatas().get(i).name);
                break;
            case 27:
                startActivityByClazz(Chart3Activity.class, getDatas().get(i).name);
                break;
            case 26:
                startActivityByClazz(Chart2Activity.class, getDatas().get(i).name);
                break;
            case 25:
                startActivityByClazz(Chart1Activity.class, getDatas().get(i).name);
                break;
            case 24:
                startActivityByClazz(IndexRecyclerActivity.class, getDatas().get(i).name);
                break;
            case 23:
                startActivityByClazz(BottomDialogCActivity.class, getDatas().get(i).name);
                break;
            case 22:
                startActivityByClazz(TabLayout2Activity.class, getDatas().get(i).name);
                break;
            case 21:
                startActivityByClazz(JsonActivity.class, getDatas().get(i).name);
                break;
            case 20:
                startActivityByClazz(ClipLoadActivity.class, getDatas().get(i).name);
                break;
            case 19:
                startActivityByClazz(BottomDialogBActivity.class, getDatas().get(i).name);
                break;
            case 18:
                startActivityByClazz(BottomDialogAActivity.class, getDatas().get(i).name);
                break;
            case 17:
                startActivityByClazz(BottomDialogActivity.class, getDatas().get(i).name);
                break;
            case 16:
                startActivityByClazz(TextShakeActivity.class, getDatas().get(i).name);
                break;
            case 15:
                startActivityByClazz(SalaryCheckAty.class, getDatas().get(i).name);
                break;
            case 14:
                startActivityByClazz(ExpandableListViewDemo.class, getDatas().get(i).name);
                break;
            case 13:
                startActivityByClazz(Translate1Activity.class, getDatas().get(i).name);
                break;
            case 12:
                startActivityByClazz(KeyBoard3Activity.class, getDatas().get(i).name);
                break;
            case 11:
                startActivityByClazz(KeyBoard2Activity.class, getDatas().get(i).name);
                break;
            case 10:
                startActivityByClazz(KeyBoard1Activity.class, getDatas().get(i).name);
                break;
            case 9:
                startActivityByClazz(DialogBoxActivity.class, getDatas().get(i).name);
                break;
            case 8:
                startActivityByClazz(FragmentTabActivity.class, getDatas().get(i).name);
                break;
            case 7:
                startActivityByClazz(GestureActivity.class, getDatas().get(i).name);
                break;
            case 6:
                //Design控件使用
                startActivityByClazz(DesignDemoActivity.class, getDatas().get(i).name);
                break;
            case 5:
                startActivityByClazz(MaskedImageActivity.class, getDatas().get(i).name);
                break;
            case 4:
                startActivityByClazz(TopSnackbarDemoActivity.class, getDatas().get(i).name);
                break;
            case 3:
                startActivityByClazz(TSnackbarDemoActivity.class, getDatas().get(i).name);
                break;
            case 2:
               startActivityByClazz(SnackbarBDemoActivity.class, getDatas().get(i).name);
                break;
            case 1:
                startActivityByClazz(SnackbarADemoActivity.class, getDatas().get(i).name);
                break;
            case 0:
                startActivityByClazz(DialogActivity.class, getDatas().get(i).name);
                break;
        }
    }

    @Override
    protected List<PlateInfo> getDatas() {

        if (null == infoList) {
            infoList = new ArrayList<PlateInfo>();
            String[] items = UIUtils.getStringArray(R.array.page_plates_page1);
            //int[] numbers = UIUtils.getIntArray(R.array.page1_plate_serial_numbers);
            for (int i = 0; i < items.length; i++) {
                PlateInfo plateInfo = new PlateInfo();
                plateInfo.name = items[i];
                //plateInfo.serialNumber = numbers[i];
                infoList.add(plateInfo);
            }
        }
        return infoList;
    }
}