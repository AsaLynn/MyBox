package com.zxning.library.ui.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.zxning.library.R;
import com.zxning.library.manager.ThreadManager;
import com.zxning.library.tool.UIUtils;

/**
 * 联网前,联网中,联网失败,联网成功的UI效果.
 */
public abstract class DisplayPage extends FrameLayout {

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
    /*private LinearLayout.LayoutParams params;*/
    protected View loadingView;
    // 加载失败的view对象
    protected View loadErrorView;
    protected View loadEmptyView;
    // 未知的成功展示界面效果对应的view对象
    protected View loadSuccessedView;

    public DisplayPage(Context context) {
        super(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        // 先将所有的能够确认展示效果的界面添加到当前的帧布局中
        initDisplayPage();

        //UIUtils.inflate(R.layout.content_display_page, this);
        // params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
            // 通过自定义线程池去维护线程
            ThreadManager.getThreadPoolProxy().execute(new DisplayTask());
        }
    }

    class DisplayTask implements Runnable {
        @Override
        public void run() {

            // 请求网络处理,对应每一个子界面来说,请求网络操作,都不一样,没法实现,抽象
            final AResultState onLoad = onLoad();
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    // 根据状态,处理UI
                    if (onLoad != null) {
                        CURRENT_STATE = onLoad.getValue();
                        showPage();
                    }
                }
            });
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

    public abstract View onCreateSuccessedView();

    public abstract AResultState onLoad();

   /* public DisplayPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }*/
}
