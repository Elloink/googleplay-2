package com.googleplay.application.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.googleplay.application.BaseApplication;
import com.myprogram.googleplay.BaseActivity;

public class UIUtils {
	
	public static Context getContext(){
		return BaseApplication.getContext();
	}
	
	public static Handler getHandler(){
		return BaseApplication.getMainThreadHandler();
	}
	
	public static Thread getMainThread(){
		return BaseApplication.getMainThread();
	}
	
	public static int getMainThreadId(){
		return BaseApplication.getMainThreadId();
	}
	
	/**
	 * dip转px
	 */
	
	public static int dip2px(int dip){
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip*scale + 0.5f);
	}
	
	/**
	 * px转dip
	 */
	public static int px2dip(int px){
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px/scale +0.5f);
	}
	
	/**
	 * 延时在主线程执行runnable
	 */
	public static boolean postDelayed(Runnable runnable,long delayMillis){
		return getHandler().postDelayed(runnable, delayMillis);
	}
	
	/**
	 * 在主线程执行runnable
	 */
	
	public static boolean post(Runnable r){
		return getHandler().post(r);
	}
	/**
	 * 从主线程looper里面移除runnable
	 */
	public static void removeCallbacks(Runnable r){
		getHandler().removeCallbacks(r);
	}
	/**
	 * 布局填充器
	 */
	public static View inflate(int resid){
		return LayoutInflater.from(getContext()).inflate(resid, null);
	}
	/**
	 * 获取资源
	 */
	public static Resources getResources(){
		return getContext().getResources();
	}
	
	/**
	 * 获取资源文字
	 */
	public static String getString(int resid){
		return getResources().getString(resid);
	}
	
	/**
	 * 获取文字数组
	 */
	
	public static String[] getStringArray(int resid){
		return getResources().getStringArray(resid);
	}
	/**
	 * 获取dimension
	 */
	public static int getDemins(int resid){
		return getResources().getDimensionPixelSize(resid);
	}
	/**
	 * 获取drawable
	 */
	public static Drawable getDrawable(int resid){
		return getResources().getDrawable(resid);
	}
	/**
	 * 获取颜色
	 */
	public static int getColor(int resid){
		return getResources().getColor(resid);
	}
	
	/**
	 * 获取颜色选择器
	 */
	public static ColorStateList getColorStateList(int resid){
		return getResources().getColorStateList(resid);
	}
	/**
	 * 判断当前线程是否运行在主线程
	 */
	public static boolean isRunInMainThread(){
		return android.os.Process.myPid() == getMainThreadId();
	}
	/**
	 * 在主线程运行
	 */
	public static void runInMainThread(Runnable r){
		if(isRunInMainThread()){
			r.run();//如果在主线程运行，则开启该线程
		}else{
			post(r);//不在主线程将该线程发送到主线程
		}
	}
	/**
	 * 开启一个activity
	 */
	public static void startActivity(Intent intent){
		BaseActivity acitivty  = (BaseActivity) BaseActivity.getForegroundActivity();
		if(acitivty != null){
			startActivity(intent);
		}else{
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(intent);
		}
	}
	/**
	 * 对toast封装，在非UI线程可调用
	 */
	
	public static void showToastSafe(final int resid){
		showToastSafe(getString(resid));
	}

	public static void showToastSafe(final String str) {
		if(isRunInMainThread()){
			showToast(str);//主线程直接打印toast
		}else{
			post(new Runnable() {//非主线程发送到主线程
				
				@Override
				public void run() {
					showToast(str);
				}
			});
		}
	}

	private static void showToast(String str) {
		BaseActivity frontActivity = (BaseActivity) BaseActivity.getForegroundActivity();
		if(frontActivity != null){
			Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
		}
	}
}
