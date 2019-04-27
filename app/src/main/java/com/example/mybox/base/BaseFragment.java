package com.example.mybox.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxning.library.constant.Constant;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private String title;
    private int iconId;

    public void startActivityByClazz(Class<?> cls, String title) {
        Intent intent = new Intent(this.getActivity(), cls);
        intent.putExtra(Constant.Intent.ExtraName.TITLE, title);
        this.startActivity(intent);
    }

    public void startActivityByClazz(Class<?> cls) {
        BaseActivity baseActivity = (BaseActivity) this.getActivity();
        baseActivity.startActivityByClazz(cls);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return BaseFragment.this.onCreateSuccessedView();
    }

    //BaseFragment中依然是不知道子界面的展示效果,所以抽象,让其放到子类中去处理
    protected abstract View onCreateSuccessedView();

    @Override
    public void setMenuVisibility(boolean menuVisible) {

        //如果当前view是可见的,则menuVisible就是true
        //每一个子类fragment对应view是否存在的操作
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
        super.setMenuVisibility(menuVisible);
    }

    public void startAty(Class<?> cls, boolean isFinish) {
        BaseActivity activity = (BaseActivity) BaseFragment.this.getActivity();
        activity.startAty(cls, isFinish);
    }

    public void startAty(Intent intent, boolean isFinish) {
        this.startActivity(intent);
        if (isFinish) {
            this.getActivity().finish();
        }
    }


    @Override
    public void onClick(View v) {
    }

}
