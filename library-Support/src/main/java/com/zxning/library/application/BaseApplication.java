package com.zxning.library.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {


	
	//全局用户登录数据.
	private static String loginData;
	//全局上下文环境
	private static Context mContext;
	//全局的handler对象
	private static Handler mHandler;
	//主线程对象
	private static Thread mainThread;
	//主线程id
	private static int mainThreadId;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		mHandler = new Handler();
		//获取当前线程为主线程
		mainThread = Thread.currentThread();
		//主线线程id
		mainThreadId = android.os.Process.myTid();
		//加载图片
		//initImageLoader(mContext);

	}
	
	public static Context getmContext() {
		return mContext;
	}
	public static Handler getmHandler() {
		return mHandler;
	}
	public static Thread getMainThread() {
		return mainThread;
	}
	public static int getMainThreadId() {
		return mainThreadId;
	}

	public static String getLoginData() {
		return loginData;
	}
	public static void setLoginData(String loginData) {
		BaseApplication.loginData = loginData;
	}
	
	/***
	 * 异步加载图片的信息
	 * 
	 * @param context
	 */
	/*public static void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		// .writeDebugLogs() // Remove for release app
		// .build();
		ImageLoader.getInstance().init(config);
	}*/
}