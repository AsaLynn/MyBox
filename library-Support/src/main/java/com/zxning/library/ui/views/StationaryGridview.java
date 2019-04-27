/**
 * 
 */
package com.zxning.library.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 不滚动的格子列表.(Gridview)
 * @author wisdom-z
 *
 */
public class StationaryGridview extends GridView {

	/**
	 * @param context
	 */
	public StationaryGridview(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public StationaryGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public StationaryGridview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
	 * 重写该方法可使GridView嵌套在ScrollView中显示全部行.
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	/**
	 * 重写dispatchTouchEvent方法禁止GridView滑动
	 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
	
	
}
