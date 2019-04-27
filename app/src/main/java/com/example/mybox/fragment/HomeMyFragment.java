package com.example.mybox.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.adapter.MyAdapter;
import com.example.mybox.base.BaseFragment;
import com.zxning.library.constant.SPName;
import com.zxning.library.db.dphelper.MyDBHelper;
import com.zxning.library.db.dphelper.UserDBHelper;
import com.zxning.library.entity.MeInfo;
import com.zxning.library.entity.MeItemInfo;
import com.zxning.library.tool.SPUtil;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.views.GridDecoration;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的.
 */
public class HomeMyFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener {

    private UserDBHelper userDBHelper;
    private TextView credit_num_tv, current_account_num_tv, name_tv, phone_tv, email_tv, qq_num_tv;
    private MyAdapter mAdapter;
    private RecyclerView rv;
    private TextView wx_num_tv;
    private List<MeItemInfo> infos;

    @Override
    protected View onCreateSuccessedView() {
        //View view = UIUtils.inflate(R.layout.fragment_me2);
        View view = UIUtils.inflate(R.layout.fragment_page1);
        BGARefreshLayout bga_rl = (BGARefreshLayout) view.findViewById(R.id.bga_rl);
        bga_rl.setDelegate(this);
        bga_rl.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_rl.setIsShowLoadingMoreView(false);
        bga_rl.setPullDownRefreshEnable(true);
        View headerView = UIUtils.inflate(getActivity(), R.layout.layout_me_header);
        name_tv = (TextView) headerView.findViewById(R.id.name_tv);
        phone_tv = (TextView) headerView.findViewById(R.id.phone_tv);
        email_tv = (TextView) headerView.findViewById(R.id.email_tv);
        qq_num_tv = (TextView) headerView.findViewById(R.id.qq_num_tv);
        wx_num_tv = (TextView) headerView.findViewById(R.id.wx_num_tv);
        bga_rl.setCustomHeaderView(headerView, true);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new GridDecoration(getActivity()));
        mAdapter = new MyAdapter(rv);
        mAdapter.setOnRVItemClickListener(this);
        setShowNum(headerView);
        //refreshViewFormLocal();
        return view;
    }

    private void setShowNum(View view) {
        CheckBox show_cb = (CheckBox) view.findViewById(R.id.show_cb);
        show_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.saveData(SPName.CURRENT_ACCOUNT_NUM, isChecked);
                mAdapter.notifyDataSetChanged();
            }
        });
        boolean showWealthNum = (boolean) SPUtil.getData(SPName.CURRENT_ACCOUNT_NUM, true);
        show_cb.setChecked(showWealthNum);
    }

    private void refreshViewFormLocal() {
        MeInfo meInfo = MyDBHelper.selectMeInfoAlone(getActivity());
        name_tv.setText(meInfo.name);
        phone_tv.setText(meInfo.phone);
        email_tv.setText(meInfo.email);
        qq_num_tv.setText(meInfo.qq);
        wx_num_tv.setText(meInfo.wx);
        infos = MyDBHelper.selectAllMeItemInfo(getActivity());
        mAdapter.setDatas(infos);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                //refreshViewFormLocal();
                //current_account_num_tv.setText(data.getStringExtra(Name.Intent.NUMBER));
                break;
        }
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        Intent intent = new Intent();
        /*switch (i) {
            case 0:
                intent.setClass(getActivity(), MyAccountAssortmentActivity.class);
                break;
            case 1:
                break;
            case 2:
                intent.setClass(getActivity(), MyCreditCardActivity.class);
                break;
            case 3:
                break;
            case 4:
                intent.setClass(getActivity(), MyBillActivity.class);
                intent.putExtra(Name.Intent.BILL_SUM, infos.get(i).content);
                intent.putExtra(Name.Intent.TOTAL_CURRENT_SUM, infos.get(0).content);
                break;
            case 5:
                intent.setClass(getActivity(), MyLendActivity.class);
                break;
            case 6:
                intent.setClass(getActivity(), MyCreditCardInstallmentActivity.class);
                break;
        }*/
        if (i == 1 || i == 3) {
            return;
        }else {
            startActivity(intent);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
        //OutlayDBHelper.copyDbFromAssetsToSdCard(getActivity(), Name.DB.ZXN_APP);
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.endRefreshing();
                refreshViewFormLocal();
                rv.smoothScrollToPosition(0);
            }
        }, 3000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //mAdapter.addNewDatas(MyDBHelper.selectAllMeItemInfo(getActivity()));
        return false;
    }
}