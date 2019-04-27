package com.hornet.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

public class ColorUtils {
	   Random rd=new Random();
	   private static ColorUtils c;
	   List<Integer> cList=new ArrayList<Integer>();
	   private ColorUtils(){
		   for(int i=0;i<30;i++){
			   	if(i==0){
			   		cList.add(Color.rgb(255, 127, 80));
			   	}else if(i==1){
			   		cList.add(Color.rgb(135, 206, 250));
			   	}else if(i==2){
			   		cList.add(Color.rgb(218, 112, 213));
			   	}else{
			   		int r=rd.nextInt(255);
					int g=rd.nextInt(255);
					int b=rd.nextInt(255);
					int c=Color.rgb(r, g, b);
					cList.add(c);
			   	}
			  }
	   }
	   public static ColorUtils getInstance(){
		    if(c==null){
		    	c=new ColorUtils();
		    }
		    return c;
	   }
	   public List<Integer> getColorList(){
		  if(cList.size()==0){
			  for(int i=0;i<30;i++){
				    if(i==0){
				   		cList.add(Color.rgb(255, 127, 80));
				   	}else if(i==1){
				   		cList.add(Color.rgb(135, 206, 250));
				   	}else if(i==2){
				   		cList.add(Color.rgb(218, 112, 213));
				   	}else{
				   		int r=rd.nextInt(255);
						int g=rd.nextInt(255);
						int b=rd.nextInt(255);
						int c=Color.rgb(r, g, b);
						cList.add(c);
				   	}
				  }
		  }
		   return cList;
	   }
}
