package com.zxning.library.ui.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zxning.library.R;
import com.zxning.library.http.VolleyHelper;
import com.zxning.library.tool.UIUtils;

/**
 * 联网中,联网失败,获取到空数据,联网成功的UI效果.
 */
public abstract class LoadingPage extends FrameLayout implements Response.Listener<byte[] >,Response.ErrorListener {

    // 初始状态
    protected int STATE_UNLOAD = 1;
    // 正在加载状态
    protected int STATE_LOADING = 2;
    // 请求不到数据的状态
    protected int STATE_LOAD_EMPTY = 3;
    // 请求失败的状态
    protected int STATE_LOAD_ERROR = 4;
    // 请求成功的状态
    protected int STATE_LOAD_SUCCESSED = 5;
    // 当前状态
    protected int CURRENT_STATE = STATE_UNLOAD;
    protected LayoutParams layoutParams;
    protected View loadingView;
    // 加载失败的view对象
    protected View loadErrorView;
    protected View loadEmptyView;
    // 未知的成功展示界面效果对应的view对象
    protected View loadSuccessedView;

    public LoadingPage(Context context) {
        super(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        // 先将所有的能够确认展示效果的界面添加到当前的帧布局中
        initDisplayPage();
    }

    private void initDisplayPage() {

        // 进度条显示.
        if (loadingView == null) {
            loadingView = UIUtils.inflate(R.layout.layout_loading);
            addView(loadingView, layoutParams);
        }

        // 获取数据失败的界面展示.
        if (loadErrorView == null) {
            loadErrorView = UIUtils.inflate(R.layout.layout_error);
            addView(loadErrorView, layoutParams);
        }

        // 获取数据为空的界面展示.
        if (loadEmptyView == null) {
            loadEmptyView = UIUtils.inflate(R.layout.layout_empty);
            addView(loadEmptyView, layoutParams);
        }

        // 根据状态在主线程中展示界面的操作
        showSafePage();
    }

    private void showSafePage() {
        // 将根据状态展示UI的操作封装到Runnable中去做
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    // 界面展示.
    public void showPage() {

        // 根据状态展示UI
        if (loadingView != null) {

            // 如果当前状态为未加载或者加载中,则显示进度条.否则不显示进度条.
            loadingView.setVisibility((CURRENT_STATE == STATE_UNLOAD || CURRENT_STATE == STATE_LOADING) ? View.VISIBLE : View.GONE);
        }

        if (loadErrorView != null) {

            // 如果当前状态为加载错误,则显示错误提示界面.否则不显示.
            loadErrorView.setVisibility(CURRENT_STATE == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }

        if (loadEmptyView != null) {

            // 如果当前状态为加载到空,则显示空界面提示.否则不显示.
            loadEmptyView.setVisibility(CURRENT_STATE == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }

        // 成功获取数据的界面如何构建(抽象方法,回调)
        if (loadSuccessedView == null && CURRENT_STATE == STATE_LOAD_SUCCESSED) {

            // onCreateSuccessedView构建的是不同界面的展示效果,所以不能去编写具体的布局结构,不能实现当前方法,抽象
            loadSuccessedView = onCreateSuccessedView();
            if (loadSuccessedView != null) {
                addView(loadSuccessedView, layoutParams);
            }
        }

        if (loadSuccessedView != null) {
            // 如果当前状态加载数据成功则显示成功界面效果,否则不显示.
            loadSuccessedView.setVisibility((CURRENT_STATE == STATE_LOAD_SUCCESSED) ? View.VISIBLE : View.GONE);
        }
    }

    public void show() {
        // 归位状态的操作
        // 若当前状态错误界面或空界面或成功界面,都归位到初始化状态.
        if (CURRENT_STATE == STATE_LOAD_ERROR || CURRENT_STATE == STATE_LOAD_EMPTY || CURRENT_STATE == STATE_LOAD_SUCCESSED) {
            CURRENT_STATE = STATE_UNLOAD;
        }

        if (CURRENT_STATE == STATE_UNLOAD) {
            // 1,将请求网络的操作加入加入请求队列.
            //2,发送请求到网络.(是否自动请求网络未知,故抽象到子类),根据请求网络的状态,来展示内容视图.
            /*AResultState state = onLoad();
            if (state != null) {
                CURRENT_STATE = state.getValue();
                showPage();
            }*/
            VolleyHelper.getInstance(UIUtils.getContext()).addToRequestQueue(createRequest(this,this));
        }
    }

    // 枚举类型(请求成功无数据,请求失败,请求成功)
    public enum AResultState {
        STATE_EMPTY(3), STATE_ERROR(4), STATE_SUCCESSED(5);

        private int value;

        AResultState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public void onResponse(byte[] response) {
        onLoad(response);
        CURRENT_STATE = STATE_LOAD_SUCCESSED;
        showPage();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        CURRENT_STATE = STATE_LOAD_ERROR;
        showPage();
    }

    public abstract View onCreateSuccessedView();

    /*public abstract AResultState onLoad();*/

    public abstract Request createRequest(Response.Listener<byte[]> listener,Response.ErrorListener errorListener);

    public abstract void onLoad(byte[] response);
}
