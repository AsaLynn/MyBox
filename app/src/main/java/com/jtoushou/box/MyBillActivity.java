package com.jtoushou.box;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.zxning.library.constant.Name;
import com.zxning.library.entity.BillInfo;
import com.zxning.library.tool.ZDateUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * Android CoordinatorLayout + AppBarLayout(向上滚动隐藏指定的View)
 * 1) 首先需要用CoordinatorLayout包住AppBarLayout;
 * 2) 顶部区域的View都放在AppBarLayout里面;
 * 3) AppBarLayout外面,CoordinatorLayout里面,放一个带有可滚动的View.
 * 4) 在AppBarLayout里面的View,通过app:layout_scrollFlags属性来控制,滚动时候的表现.其中有4种Flag的类型.
 * Scroll 表示向下滚动时,这个View会被滚出屏幕范围直到隐藏.
 * enterAlways 表示向上滚动时,这个View会随着滚动手势出现,直到恢复原来的位置.
 * 5) 在可以滚动的View上设置属性 app:layout_behavior.
 * 6) 代码部分,只需要实现RecylerView的逻辑就可以了.
 * <p/>
 * app:layout_scrollFlags属性里面必须至少启用scroll这个flag，这样这个view才会滚动出屏幕，否则它将一直固定在顶部。可以使用的其他flag有：
 * •enterAlways: 一旦向上滚动这个view就可见。
 * •enterAlwaysCollapsed: 顾名思义，这个flag定义的是何时进入（已经消失之后何时再次显示）。假设你定义了一个最小高度（minHeight）同时enterAlways也定义了，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。
 * •exitUntilCollapsed: 同样顾名思义，这个flag时定义何时退出，当你定义了一个minHeight，这个view将在滚动到达这个最小高度的
 */
public class MyBillActivity extends BaseActivity
        implements BGAOnRVItemClickListener, View.OnClickListener {

    protected TextView totalBillTv;
    protected TextView todayTv;
    protected LinearLayout bgaLl;
    private MyBillAdapter mAdapter;
    private RecyclerView rv;
    private TextView total_current_tv;

    @Override
    protected void showTitle() {
        initToolbar(true, "我的账单");
    }

    @Override
    protected View initContentView() {
        View view = View.inflate(this, R.layout.activity_my_bill,null);
        //total_bill_tv ,today_tv
        totalBillTv = (TextView) view.findViewById(R.id.total_bill_tv);
        totalBillTv.setText(getIntent().getStringExtra(Name.Intent.BILL_SUM));
        total_current_tv = (TextView) view.findViewById(R.id.total_current_tv);
        total_current_tv.setText(getIntent().getStringExtra(Name.Intent.TOTAL_CURRENT_SUM));
        todayTv = (TextView) view.findViewById(R.id.today_tv);
        todayTv.setText(ZDateUtil.getCurrentDate());
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyBillAdapter(rv);
        rv.setHasFixedSize(true);
        //rv.addItemDecoration(new SpaceItemDecoration(1));
        mAdapter.setOnRVItemClickListener(this);

        //setScrollFlags(2);
        refreshViewFormLocal();
        return view;
    }

    @Override
    protected void refreshViewFormLocal() {
        List<BillInfo> billInfos = getDatas();
        mAdapter.setDatas(billInfos);
        rv.setAdapter(mAdapter);
    }

    private List<BillInfo> getDatas() {
        List<BillInfo> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BillInfo billInfo = new BillInfo();
            billInfo.bankName = "name" + i;
            billInfo.cardId = "id" + i;
            list.add(billInfo);
        }
        return list;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.total_bill_tv) {

        } else if (view.getId() == R.id.today_tv) {

        }
    }

}
