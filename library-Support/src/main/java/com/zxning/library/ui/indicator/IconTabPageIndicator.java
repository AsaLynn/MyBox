package com.zxning.library.ui.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxning.library.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-08-27
 * Time: 09:20
 * FIXME
 */
public class IconTabPageIndicator extends LinearLayout implements PageIndicator {
    /**
     * Title text used when no title is provided by the com.jtoushou.mytooldemo.adapter.
     */
    private static final CharSequence EMPTY_TITLE = "";

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);
    }

    private Runnable mTabSelector;

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected, false);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    private final LinearLayout mTabLayout;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mSelectedTabIndex;

    private OnTabReselectedListener mTabReselectedListener;

    private int mTabWidth;

    public IconTabPageIndicator(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public IconTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        /*mTabLayout = new LinearLayout(context, null, R.attr.tabPageIndicator);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        addView(mTabLayout, params);*/

        // mTabLayout.setVisibility(View.INVISIBLE);
        // LinearLayout.LayoutParams params1 = (LayoutParams) mTabLayout.getLayoutParams();
        // params1.weight = 1;
        // LinearLayout.LayoutParams params = new LayoutParams(params1);
        //LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);

        mTabLayout = new LinearLayout(context, null, R.attr.tabPageIndicator);
        mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
        //mTabLayout.setBackgroundColor(Color.RED);
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        addView(mTabLayout, params);
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;

        final int childCount = mTabLayout.getChildCount();

        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            mTabWidth = MeasureSpec.getSize(widthMeasureSpec) / childCount;
        } else {
            mTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            //setCurrentItem(mSelectedTabIndex);
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void addTab(int index, CharSequence text, int iconResId) {
        final TabView tabView = new TabView(getContext());
        tabView.mIndex = index;
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(text);

        if (iconResId > 0) {
            tabView.setIcon(iconResId);
        }

        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, MATCH_PARENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;//Gravity.CENTER_VERTICAL;*/
        LayoutParams params = new LayoutParams(0, MATCH_PARENT, 1);
        mTabLayout.addView(tabView, params);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have com.jtoushou.mytooldemo.adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item, false);

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    private class TabView extends LinearLayout {
        private int mIndex;
        private ImageView mImageView;
        private TextView mTextView;

        /*@TargetApi(Build.VERSION_CODES.HONEYCOMB)*/
        public TabView(Context context) {
            super(context, null, R.attr.tabView);
            this.setOrientation(LinearLayout.HORIZONTAL);
            View view = View.inflate(context, R.layout.tab_view, null);
            view.setBackgroundColor(Color.WHITE);
            mImageView = (ImageView) view.findViewById(R.id.tab_image);
            mTextView = (TextView) view.findViewById(R.id.tab_text);
            this.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //MATCH_PARENT,WRAP_CONTENT
            params.gravity = Gravity.CENTER_VERTICAL;//Gravity.CENTER_VERTICAL;
            this.addView(view,params);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mTabWidth > 0) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        public void setIcon(int resId) {
            if (resId > 0) {
                mImageView.setImageResource(resId);
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }
}

