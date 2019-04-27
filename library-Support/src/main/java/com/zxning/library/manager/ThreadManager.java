package com.zxning.library.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
	//创建一个可以去调用线程池运行的对象
	//(反射,代理,枚举,注解)
	private static ThreadPoolProxy threadPoolProxy;
	private static Object object = new Object();
	
	public static ThreadPoolProxy getThreadPoolProxy(){
		synchronized (object) {
			if(threadPoolProxy == null){
				threadPoolProxy = new ThreadPoolProxy(5,5,5L);
			}
			return threadPoolProxy;
		}
	}
	
	public static class ThreadPoolProxy{
		private int corePoolSize;
		private int maximumPoolSize;
		private long keepAliveTime;
		private ThreadPoolExecutor threadPoolExecutor;
		
		public ThreadPoolProxy(int corePoolSize,int maximumPoolSize,long keepAliveTime) {
			this.corePoolSize =corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}
		//子线程中运行任务
		public void execute(Runnable runnable){
			//开启线程池,执行任务
			if(runnable!=null){
				threadPoolExecutor = new ThreadPoolExecutor(
						//核心线程数
						corePoolSize,
						//最大线程数
						maximumPoolSize,
						//线程存活时间
						keepAliveTime,
						//线程存活时间单位
						TimeUnit.MILLISECONDS, 
						//工作队列
						new LinkedBlockingQueue<Runnable>(),
						//默认线程对象
						Executors.defaultThreadFactory(), 
						//线程异常处理对象
						new AbortPolicy());
				//执行
				threadPoolExecutor.execute(runnable);
			}
		}
	}
}
