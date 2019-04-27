package com.jtoushou.box;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mybox.R;
import com.zxning.library.tool.UIUtils;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 总基类,适合不需联网或被动联网继承使用.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private static boolean sLastVisiable;
    private boolean menuClickAble;
    public int iconId;
    protected CoordinatorLayout root_cl;
    private CoordinatorLayout.LayoutParams layoutParams;
    //private FrameLayout base_fl;
    private RelativeLayout base_fl;
    private FrameLayout.LayoutParams flParams;
    protected Toolbar toolbar;
    private String text;
    private TextView toolbar_title;
    private TextView left_tv;
    private BaseActivity context;
    private MenuItem rightMenu;
    private long onStopTime;
    private long onRestartTime;
    private static String name;
    protected Bundle mSavedInstanceState;
    private ImageView title_iv;
    private ProgressDialog mLoadingDialog;
    private AppBarLayout root_abl;
    private AppBarLayout.LayoutParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        name = BaseActivity.this.getClass().getName();
        //1,加载整体布局.
        setContentView(R.layout.activity_base);
        // 2,分布填充各个内容布局.
        onCreatingView();
    }


    /**
     * 创建页面 .
     */
    protected void onCreatingView() {
        //0,本地数据初始化.
        //1,展示标题.抽象到子类.
        initLocal();
        showTitle();
        //2,展示内容视图,但内容视图未知抽象到子类.
        base_fl.addView(initContentView(), flParams);
    }

    protected void initLocal() {

        root_cl = (CoordinatorLayout) findViewById(R.id.root_cl);
        root_abl = (AppBarLayout) findViewById(R.id.root_abl);
        mParams = (AppBarLayout.LayoutParams) root_abl.getChildAt(0).getLayoutParams();
        //base_fl = (FrameLayout) findViewById(R.id.base_fl);
        base_fl = (RelativeLayout) findViewById(R.id.base_fl);
        flParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);///com.zxning.library.
        //设置默认的标题为空,再设置自定义标题.
        toolbar.setTitle("");
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        left_tv = (TextView) findViewById(R.id.left_tv);
        title_iv = (ImageView) findViewById(R.id.title_iv);
    }

    //0,不滚动!1,滚动!2,滚动!3,
    protected void setScrollFlags(int flags) {
        if (0 == flags) {
            mParams.setScrollFlags(flags);//bu会随着滚动条折叠
            //root_abl.setExpanded(true);
        } else if (1 == flags) {
            mParams.setScrollFlags
                    (AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                            AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);//的时候AppBarLayout下的toolbar会随着滚动条折叠
        } else if (2 == flags) {
            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
            toolbar.setLayoutParams(mParams);
        } else if (3 == flags) {

        }
    }

    ;

    protected abstract void showTitle();

    //0,初始化头部设置.无返回,标题内容.
    protected void initToolbar(String title) {
        initToolbar(false, title);
    }

    //0,初始化头部设置.显示logo,不显示文字.
    protected void initTitleLogo(boolean showLogo) {
        if (true == showLogo) {
            title_iv.setVisibility(View.VISIBLE);
            toolbar_title.setVisibility(View.GONE);
        } else {
            title_iv.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
        }
    }

    //1,初始化头部设置.有无返回,左上角文字点击,标题内容.
    protected void initToolbar(Boolean isBack, String left, View.OnClickListener leftListener, String title) {
        initToolbar(isBack, title);
        showLeftBtn(left, leftListener);

    }

    //1,初始化头部设置.有无返回和标题内容.
    protected void initToolbar(Boolean isBack, String title) {
        showToolbar(true);
        setToolbarTitle(title);
        setSupportActionBar(toolbar);
        if (isBack) {
            //设置是否显示返回按钮.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //设置返回按钮的图标.
            toolbar.setNavigationIcon(R.mipmap.iv_back);
        }
    }

    //有无返回,标题,右侧点击,右侧文字.
    protected void initToolbar(Boolean isBack, String title, Toolbar.OnMenuItemClickListener listener, String rightText) {
        initToolbar(isBack, title);
        this.text = rightText;
        if (null != listener) {
            toolbar.setOnMenuItemClickListener(listener);
            this.menuClickAble = true;
        } else {
            this.menuClickAble = false;
        }
    }

    /**
     * 初始化头部.
     *
     * @param listener 右侧图标点击监听,没有传null.
     * @param isBack   true显示返回按钮,false不显示.
     * @param title    标题.
     * @param listener
     * @param iconId   右侧图标id,没有传0.
     */
    protected void initToolbar(Boolean isBack, String title, Toolbar.OnMenuItemClickListener listener, int iconId) {
        initToolbar(isBack, title);
        //3,
        this.iconId = iconId;
        if (null != listener) {
            toolbar.setOnMenuItemClickListener(listener);
            this.menuClickAble = false;
        } else {
            this.menuClickAble = false;
        }
    }

    private void setToolbarTitle(String title) {
        toolbar_title.setText(title);
        //代码中设置在父布局中居中.
        Toolbar.LayoutParams params =
                new Toolbar.LayoutParams(toolbar_title.getLayoutParams());
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
    }

    protected void showLeftBtn(String left, View.OnClickListener leftListener) {
        left_tv.setVisibility(View.VISIBLE);
        left_tv.setText(left);
        left_tv.setOnClickListener(leftListener);
    }

    //右侧点击,右侧文字.
    protected void showRightBtn(String rightText, Toolbar.OnMenuItemClickListener rightListener) {
        this.text = rightText;
        showRightMenu(text);
        if (null != rightListener) {
            toolbar.setOnMenuItemClickListener(rightListener);
            this.menuClickAble = true;
        } else {
            this.menuClickAble = false;
        }
    }

    protected void showRightText(String rightText, Toolbar.OnMenuItemClickListener rightListener) {
        this.text = rightText;
        if (null != rightListener) {
            toolbar.setOnMenuItemClickListener(rightListener);
            this.menuClickAble = true;
        } else {
            this.menuClickAble = false;
        }
    }


    /**
     * Toolbar的 右侧Menu菜单.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        rightMenu = menu.findItem(R.id.action_settings);
        showRightMenu(text);

        if (0 != iconId) {
            rightMenu.setIcon(iconId);
            rightMenu.setVisible(true);
        }
        return true;
    }

    private void showRightMenu(String text) {
        if (!TextUtils.isEmpty(text)) {
            rightMenu.setTitle(text);
            rightMenu.setVisible(true);
        } else {
            //rightMenu.setEnabled(false);
            rightMenu.setVisible(false);
        }
    }

    /**
     * 是否显示标题.
     *
     * @param isShow
     */
    protected void showToolbar(boolean isShow) {

        if (isShow) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 点击返回按钮,关闭页面.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            UIUtils.showMsg("action_set");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 未知的展示效果.
     *
     * @return
     */
    protected abstract View initContentView();

    @Override
    public void onClick(View v) {

    }


    /**
     * 无需传送数据时候,调用开启新的ac.
     *
     * @param cls
     * @param isFinish
     */
    public void startAty(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(BaseActivity.this, cls);
        BaseActivity.this.startActivity(intent);
        if (isFinish) {
            BaseActivity.this.finish();//
        }
    }

    /**
     * 跳转页面调用的公共方法
     *
     * @param intent
     * @param bundle   可携带数据.
     * @param isFinish 是否关闭跳转前的.
     */
    public void startAty(Intent intent, Bundle bundle, boolean isFinish) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    public void startAty(Intent intent, boolean isFinish) {
        startAty(intent, null, isFinish);
    }


    /**
     * 用于设置系统状态栏.
     *
     * @param on
     */
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //-------------手势密码----------------
    public <K extends View> K getViewById(int id) {
        return (K) getWindow().findViewById(id);
    }

    //---------------------键盘----------------------

    /**
     * 监听软键盘状态
     *
     * @param activity
     * @param listener
     */
    /*public static void addOnSoftKeyBoardVisibleListener(Activity activity,
                                                        final OnSoftKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHight = rect.bottom - rect.top;
                int hight = decorView.getHeight();
                boolean visible = (double) displayHight / hight < 0.8;
                if (visible != sLastVisiable) {
                    listener.onSoftKeyBoardVisible(visible);
                }
                sLastVisiable = visible;
            }
        });
    }*/

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    //Log.i(name, "---后台" + appProcess.processName);
                    return true;
                } else {
                    //Log.i(name, "---前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }




    /*protected void doTaskOnResume() {
        cancleLockTask();
        //满足以下情况进行手势上锁的操作.
        if (kxdApplication.isReadyLockInBackground()
                && !kxdApplication.isScreenLocked()
                && LockUtil.getPwdStatus()
                && LockUtil.getPwd().length > 0) {//
            this.startAty(GestureLoginLockActivity.class, false);
            kxdApplication.setScreenLocked(true);
        }
    }*/

   /* public void cancleLockTask() {
        UIUtils.getHandler().removeCallbacks(runnable);
    }*/

    /*protected void lockInBackground() {
        KxdApplication.setIsAppInBackground(isApplicationBroughtToBackground(this));
        if (isApplicationBroughtToBackground(this)
                && kxdApplication.isScreenOn()
                ) {
            UIUtils.getHandler().postDelayed(runnable, 3 * 60 * 1000);
            //UIUtils.getHandler().postDelayed(runnable, 10* 1000);
        }
    }*/
    AlertDialog dialog;

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public View fView(View rootView, int resId) {
        return rootView.findViewById(resId);
    }

    public View fViewAddClick(View rootView, int resId) {
        View view = fView(rootView, resId);
        addOnClickListener(view);
        return view;
    }

    public void addOnClickListener(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    public void loadFragment(int resFragContainer, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(resFragContainer, fragment).commit();
    }

    /*public void exitApp() {
        KxdActivityManager.getInstance().finishAllActivity();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }*/


    public interface IButtonClickListener {
        void positiveButtonClick(View v);

        void negativeButtonClick(View v);
    }

    public void loadingDialogShow(String msg) {
        if (null == mLoadingDialog) {
            mLoadingDialog = new ProgressDialog(this);
        }
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }

    public void loadingDialogDismiss() {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 无需传送数据时候,调用开启新的ac.
     *
     * @param cls
     */
    public void startActivityByClazz(Class<?> cls) {
        Intent intent = new Intent(BaseActivity.this, cls);
        BaseActivity.this.startActivity(intent);
    }

    protected abstract void refreshViewFormLocal();

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

}