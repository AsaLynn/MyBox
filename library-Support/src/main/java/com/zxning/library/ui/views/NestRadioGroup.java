package com.zxning.library.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * 支持嵌套CompoundButton和ViewGroup的NestRadioGroup
 * @author 农民伯伯 http://www.cnblogs.com/over140/
 */
public class NestRadioGroup extends LinearLayout {

	// holds the checked id; the selection is empty by default
	//选中的id，默认选择是空的
	private int mCheckedId = -1;

	// tracks children radio buttons checked state
	//子控件的选中监听器
	private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;

	// when true, mOnCheckedChangeListener discards events
	//当变量为true时，moncheckedchangelistener不相应事件
	private boolean mProtectFromCheckedChange = false;

	//子控件选中变化监听.
	private OnCheckedChangeListener mOnCheckedChangeListener;

	//子控件增加减少变化监听.
	private PassThroughHierarchyChangeListener mPassThroughListener;

	//代码中使用的构造函数.
	public NestRadioGroup(Context context) {
		super(context);
		init();
	}

	//xml布局中使用的构造函数.
	public NestRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	//初始化监听器,以及设置子控件变化监听.
	private void init() {
		mCheckedId = View.NO_ID;
		setOrientation(HORIZONTAL);
		mChildOnCheckedChangeListener = new CheckedStateTracker();
		mPassThroughListener = new PassThroughHierarchyChangeListener();
		super.setOnHierarchyChangeListener(mPassThroughListener);
	}

	/**
	 * 设置子控件增加或删除监听.
	 */
	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		// the user listener is delegated to our pass-through listener
		mPassThroughListener.mOnHierarchyChangeListener = listener;
	}

	/**
	 * xml转化成view完成后的回调.
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// checks the appropriate radio button as requested in the XML file
		//在xml文件中按要求检查适当的radio button
		if (mCheckedId != View.NO_ID) {
			mProtectFromCheckedChange = true;
			setCheckedStateForView(mCheckedId, true);
			mProtectFromCheckedChange = false;
			setCheckedId(mCheckedId);
		}
	}

	/** 递归查找具有选中属性的子控件 */
	private static CompoundButton findCheckedView(View child) {
		if (child instanceof CompoundButton)
			return (CompoundButton) child;
		if (child instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) child;
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				CompoundButton check = findCheckedView(group.getChildAt(i));
				if (check != null)
					return check;
			}
		}
		return null;// 没有找到
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		final CompoundButton view = findCheckedView(child);
		if (view != null) {
			if (view.isChecked()) {
				mProtectFromCheckedChange = true;
				if (mCheckedId != -1) {
					setCheckedStateForView(mCheckedId, false);
				}
				mProtectFromCheckedChange = false;
				setCheckedId(view.getId());
			}
		}
		super.addView(child, index, params);
	}

	/**
	 * <p>
	 * Sets the selection to the radio button whose identifier is passed in
	 * parameter. Using -1 as the selection identifier clears the selection;
	 * such an operation is equivalent to invoking {@link #clearCheck()}.
	 * 根据id设置选中radio button
	 * </p>
	 *
	 * @param id
	 *            the unique id of the radio button to select in this group
	 *
	 * @see #getCheckedRadioButtonId()
	 * @see #clearCheck()
	 */
	public void check(int id) {
		// don't even bother
		if (id != -1 && (id == mCheckedId)) {
			return;
		}

		if (mCheckedId != -1) {
			setCheckedStateForView(mCheckedId, false);
		}

		if (id != -1) {
			setCheckedStateForView(id, true);
		}

		setCheckedId(id);
	}

	//设置选中的id,相应变化监听.
	private void setCheckedId(int id) {
		mCheckedId = id;
		if (mOnCheckedChangeListener != null) {
			mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
		}
	}

	//设置CompoundButton的选中状态.
	private void setCheckedStateForView(int viewId, boolean checked) {
		View checkedView = findViewById(viewId);
		if (checkedView != null && checkedView instanceof CompoundButton) {
			((CompoundButton) checkedView).setChecked(checked);
		}
	}

	/**
	 * <p>
	 * Returns the identifier of the selected radio button in this group. Upon
	 * empty selection, the returned value is -1.
	 * 获取选中的radiobutton的id.
	 * </p>
	 *
	 * @return the unique id of the selected radio button in this group
	 *
	 * @see #check(int)
	 * @see #clearCheck()
	 *
	 * @attr ref android.R.styleable#NestRadioGroup_checkedButton
	 */
	public int getCheckedRadioButtonId() {
		return mCheckedId;
	}

	/**
	 * <p>
	 * Clears the selection. When the selection is cleared, no radio button in
	 * this group is selected and {@link #getCheckedRadioButtonId()} returns
	 * null.
	 * 清空所有radiobutton的选中状态.
	 * </p>
	 *
	 * @see #check(int)
	 * @see #getCheckedRadioButtonId()
	 */
	public void clearCheck() {
		check(-1);
	}

	/**
	 * <p>
	 * Register a callback to be invoked when the checked radio button changes
	 * in this group.
	 * 设置radiobutton的选中变化监听.
	 * </p>
	 *
	 * @param listener
	 *            the callback to call on checked state change
	 */
	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}

	/**
	 * {产生布局参数.
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	/**
	 * 检验布局参数.
	 */
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams;
	}

	//产生默认布局参数.
	@Override
	protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	/**
	 * <p>
	 * This set of layout parameters defaults the width and the height of the
	 * children to {@link #WRAP_CONTENT} when they are not specified in the XML
	 * file. Otherwise, this class ussed the value read from the XML file.
	 * </p>
	 *
	 * 布局参数的抽象描述.
	 *
	 */
	public static class LayoutParams extends LinearLayout.LayoutParams {
		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(int w, int h) {
			super(w, h);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(int w, int h, float initWeight) {
			super(w, h, initWeight);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}

		/**
		 * <p>
		 * Fixes the child's width to
		 * {@link ViewGroup.LayoutParams#WRAP_CONTENT} and the
		 * child's height to
		 * {@link ViewGroup.LayoutParams#WRAP_CONTENT} when not
		 * specified in the XML file.
		 * </p>
		 *
		 * @param a
		 *            the styled attributes set
		 * @param widthAttr
		 *            the width attribute to fetch
		 * @param heightAttr
		 *            the height attribute to fetch
		 */
		@Override
		protected void setBaseAttributes(TypedArray a, int widthAttr,
				int heightAttr) {

			if (a.hasValue(widthAttr)) {
				width = a.getLayoutDimension(widthAttr, "layout_width");
			} else {
				width = WRAP_CONTENT;
			}

			if (a.hasValue(heightAttr)) {
				height = a.getLayoutDimension(heightAttr, "layout_height");
			} else {
				height = WRAP_CONTENT;
			}
		}
	}

	/**
	 * <p>
	 * Interface definition for a callback to be invoked when the checked radio
	 *  changed in this group.
	 * 一个回调函数的接口定义，当被选中的radiobutton改变的时候回调函数.
	 *
	 * </p>
	 */
	public interface OnCheckedChangeListener {
		/**
		 * <p>
		 * Called when the checked radio button has changed. When the selection
		 * is cleared, checkedId is -1.
		 * 当被选中的radiobutton发生变化回调该函数.当没有任何选中,选中的id就是-1.
		 * </p>
		 *
		 * @param group
		 *            the group in which the checked radio button has changed
		 *			已更改了选中radiobutton的父控件.
		 * @param checkedId
		 *            the unique identifier of the newly checked radio button
		 *            最新选中的radiobutton的id.
		 */
		public void onCheckedChanged(NestRadioGroup group, int checkedId);
	}

	//选中监听描述.
	private class CheckedStateTracker implements
			CompoundButton.OnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// prevents from infinite recursion
			//防止无限递归
			if (mProtectFromCheckedChange) {
				return;
			}

			mProtectFromCheckedChange = true;
			if (mCheckedId != -1) {
				setCheckedStateForView(mCheckedId, false);
			}
			mProtectFromCheckedChange = false;

			int id = buttonView.getId();
			setCheckedId(id);
		}
	}

	/**
	 * <p>
	 * A pass-through listener acts upon the events and dispatches them to
	 * another listener. This allows the table layout to set its own internal
	 * hierarchy change listener without preventing the user to setup his.
	 * 行为事件监听和分配给其他监听器的事件。这允许表布局来设置它自己的内部
	 *层次结构变化的侦听器，而不妨碍用户设置
	 * 实现该回调接口,当view中的层次结构变化的时候进行回调.
	 * 每当一个子控件从该控件增加或删除的时候，结构发生变化
	 * </p>
	 */
	private class PassThroughHierarchyChangeListener implements
			OnHierarchyChangeListener {
		private OnHierarchyChangeListener mOnHierarchyChangeListener;

		/**
		 * {@inheritDoc}
		 */
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
		public void onChildViewAdded(View parent, View child) {
			if (parent == NestRadioGroup.this) {
				CompoundButton view = findCheckedView(child);// 查找子控件
				if (view != null) {
					int id = view.getId();
					// generates an id if it's missing
					if (id == View.NO_ID
							&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
						id = View.generateViewId();
						view.setId(id);
					}
					view.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
				}
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewAdded(parent, child);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewRemoved(View parent, View child) {
			if (parent == NestRadioGroup.this) {
				CompoundButton view = findCheckedView(child);// 查找子控件
				if (view != null) {
					view.setOnCheckedChangeListener(null);
				}
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
			}
		}
	}
}