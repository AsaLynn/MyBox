package com.hornet.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

import java.util.Collections;
import java.util.List;

//圆弧比较图  

/** 
 * 	
 *
 */
public class ArcCircleChart  extends View{
	private int height;
	private int width;	
	Paint p=new Paint();
	//半径
	private float radius;
	//圆环宽度
	private float pW=30;
	//中心点
	private Point center;
	//中心文字
	private Paint cp;
	//图例
	private Paint tlp;
	public ArcCircleChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ArcCircleChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ArcCircleChart(Context context) {
		super(context);
		init();
	}
	private List<ArcData> dlist; 
	private double max;
	private String centerT="";
	public void setData(List<ArcData> dlist,double max,String center){
		Collections.sort(dlist, new Compare());  
		this.dlist=dlist;
		this.max=max;
		centerT=center;
	}
	
	private void init() {
		ls = ColorUtils.getInstance().getColorList();
		ls.add(0,Color.BLUE);
		ls.add(1,Color.rgb(56 ,200 ,219));
		ls.add(2,Color.rgb(81, 213 ,192 )); 
		jg = Dp2Px(getContext(), 5);
		p.setColor(Color.BLUE);
		p.setStrokeWidth(pW);
		p.setAntiAlias(true);
		p.setStyle(Style.STROKE);
		cp=new Paint();
		cp.setAntiAlias(true);
		cp.setTextSize(Dp2Px(getContext(), 15));
		cp.setColor(Color.BLACK);
		
		tlp=new Paint();
		tlp.setAntiAlias(true);
		
		ViewTreeObserver vto=this.getViewTreeObserver();
		vto.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				ArcCircleChart.this.getViewTreeObserver().removeOnPreDrawListener(this);
				height = ArcCircleChart.this.getMeasuredHeight();
				width = ArcCircleChart.this.getMeasuredWidth();
				
				return false;
			}
		});
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			drawCircle(canvas);
			drawCenter(canvas);
	}
	
	private void drawCenter(Canvas canvas) {
		canvas.drawText(centerT, center.x-cp.measureText(centerT)/2,center.y-cp.measureText("测")/2, cp);
	}
	private float jj=pW+5;
	private int jg;
	private List<Integer> ls;
	private void drawCircle(Canvas canvas) {
		center = new Point();
		center.x=width/2;
		center.y=height/2;
		radius=(float) (width/3);
		if(dlist==null){
			return;
		}
		int size = dlist.size();
		
		if(radius<=5*(size-1)+size*pW  ){
			pW=(radius-5*(size-1))/size;
			jj=pW+5;
			p.setStrokeWidth(pW);
		}
		if(dlist==null||size<=0){
			return;
		}
		RectF rf=new RectF();
		double per=max/360;
		
		float degree=(float) (dlist.get(0).getData()*per);
		for(int i=0;i<size;i++){
			rf.set(center.x-radius+jj*i, center.y-radius+jj*i, center.x+radius-jj*i, center.y+radius-jj*i);
//			Log.i("2022", rf.left+"[][]"+rf.top+"[][]"+rf.right+"[][]"+rf.bottom);
			ArcData arcData = dlist.get(i);
			double data = arcData.getData();
			p.setColor(ls.get(i));
			tlp.setColor(ls.get(i));
			if(i==0){
				canvas.drawArc(rf,270-degree,degree,false,p);
			}else{
				canvas.drawArc(rf,270-(float) (data*per), (float) (data*per), false, p);
			}
			RectF rect=new RectF(center.x+jg,center.y-radius-pW/2+jj*i,
					center.x+jg+40,
					center.y-radius-pW/2+jj*i+pW);
			canvas.drawRect(rect, tlp);
			
			canvas.drawText(arcData.getKey(), center.x+jg+40,center.y-radius-pW/2+jj*i+cp.measureText("测"),cp);
		}
		
	}
	public static  int Dp2Px(Context context, float dp) {
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	} 
	public static  int Px2Dp(Context context, float px) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (px / scale + 0.5f); 
	} 
}

