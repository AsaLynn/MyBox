package com.zxning.library.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class TestView extends View {

	public TestView(Context context) {
		super(context);
		System.out.println("mDebug---"+ "TestView context");
	}

	
	
	public TestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("mDebug---"+"TestView context,attrs,defStyle attrs="+attrs.getAttributeValue(0));
	}



	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("mDebug---"+ "TestView context, attrs="+attrs.getAttributeValue(0));
	}



	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("mDebug---"+ "onDraw");
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		System.out.println("mDebug---"+ "onFinishInflate");
	}



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		System.out.println("mDebug---"+"onSizeChanged,w="+w+",h="+h+",oldw="+oldw+",oldh="+oldh);
	}

}
