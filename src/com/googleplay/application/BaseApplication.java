package com.googleplay.application;
/**
 * @author feng
 * @version 1.0
 * @date 2016年6月25日
 *
 */
import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class BaseApplication extends Application {
	//获取应用上下文
	private static BaseApplication  mContext = null;
	//获取主线程Handler
	private static Handler mMainThreadHandler = null;
	//获取主线程
	private static Thread mMainThread = null;
	//获取主线程ID
	private static int mMainThreadID;
	//获取主线程Looper
	private static Looper mMainThreadLooper = null;
	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext = mContext;
		this.mMainThreadHandler = new Handler();
		this.mMainThread = Thread.currentThread();
		this.mMainThreadID = android.os.Process.myPid();
		mMainThreadLooper = getMainLooper();
	}
	public static BaseApplication getContext(){
		return mContext;
	}
	public static Handler getMainThreadHandler(){
		return mMainThreadHandler;
	}
	public static Thread getMainThread(){
		return mMainThread;
	}
	
	public static int getMainThreadId(){
		return mMainThreadID;
	}
	
	public static Looper getMainThreadLooper(){
		return mMainThreadLooper;
	}
	
}
