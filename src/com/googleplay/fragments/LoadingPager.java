package com.googleplay.fragments;


import com.googleplay.application.utils.UIUtils;
import com.googleplay.manager.ThreadManager;
import com.myprogram.googleplay.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public abstract class LoadingPager extends FrameLayout {
	//默认加载状态
	private static final int START_UNLOADING = 0;
	//正在加载状态
	private static final int START_LOADING = 1;
	//加载错误状态
	private static final int START_ERROR = 2;
	//加载空状态
	private static final int START_EMPTY = 3;
	//加载成功状态
	private static final int START_SUCESS = 4;
	
	private int mState;
	private View loadingView;
	private View emptyView;
	private View errorView;
	private View successView;
	
	public LoadingPager(Context context,AttributeSet attrs,int delStyleAttr) {
		super(context,attrs,delStyleAttr);
		init();
	}
	public LoadingPager(Context context,AttributeSet attrs){
		super(context,attrs);
		init();
	}
	public LoadingPager(Context context){
		super(context);
		init();
	}
	
	private void init(){
		//初始化默认状态
		mState = START_UNLOADING;
		loadingView = createLoadingView();
		if(null != loadingView){
			addView(loadingView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		//初始化错误页面
		errorView  = createErrorView();
		if(null != errorView){
			addView(errorView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		//初始化空页面
		emptyView = createEmptyView();
		if(null != emptyView){
			addView(emptyView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		//显示出界面
		showSafePagerView();
		
	}
	protected abstract View createLoadingView();
	protected abstract LoadResult load();
	
	private void  showSafePagerView(){
		UIUtils.runInMainThread(new Runnable() {
			
			@Override
			public void run() {
				showPagerView();
			}
		});
	}
	
	protected void showPagerView(){
		//正在加载界面
		if(null != loadingView){
			loadingView.setVisibility(mState == START_UNLOADING || 
					mState == START_LOADING ? View.VISIBLE:View.INVISIBLE);
		}
		//错误界面
		if(null != errorView){
			errorView.setVisibility(mState == START_ERROR ? View.VISIBLE : View.INVISIBLE);
		}
		//空界面
		if(null != emptyView){
			emptyView.setVisibility(mState == START_EMPTY ? View .VISIBLE : View.INVISIBLE);
		}
		//成功界面
		if(null == successView && mState == START_SUCESS){
			successView = createLoadedView();
			addView(successView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		//成功界面显示隐藏控制
		if(null != successView ){
			successView.setVisibility(mState == START_SUCESS ? View.VISIBLE : View.INVISIBLE);
		}
		
	}
	public void show(){
		if( mState == START_ERROR || mState == START_EMPTY){
			mState = START_UNLOADING;
		}
		if(mState == START_UNLOADING){
			mState = START_LOADING;
			
			ThreadManager manager = new ThreadManager();
			manager.getLongPool().execute(new TaskRunnable());
//			ExecutorService executor = Executors.newFixedThreadPool(5);
//			TaskRunnable task = new TaskRunnable();
//			executor.execute(task);
		}
		showSafePagerView();
	}
	
	private class TaskRunnable implements Runnable{

		@Override
		public void run() {
			final LoadResult result = load();
			UIUtils.runInMainThread(new Runnable() {
				
				@Override
				public void run() {
					mState = result.getValue();
					showPagerView();
				}
			});
		}
		
	}
	enum LoadResult{
		ERROR(3),EMPTY (4),SUCESS(5);
		int value;
		LoadResult(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	//创建加载中界面
	private View createLoadedView() {
		return UIUtils.inflate(R.layout.loading_page_loading);
	}
	//初始化错误页面
		private View createErrorView(){
			return UIUtils.inflate(R.layout.loading_page_error);
		}
		//初始化空页面
		private View createEmptyView(){
			return UIUtils.inflate(R.layout.loading_page_empty);
		}
	
}
