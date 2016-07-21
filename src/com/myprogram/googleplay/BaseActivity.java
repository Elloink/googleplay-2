package com.myprogram.googleplay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {
	//获取前台进程的activity
	private static Activity mForegroundActivty = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		initView();
		initActionBar();
	}
	
	/**初始化actionBar
	 * 
	 */
	protected abstract void initActionBar();
	/**初始化界面
	 * 
	 */
	protected abstract void initView();
	/**初始化数据
	 * 
	 */
	protected abstract void init();


	@Override
	protected void onResume() {
		super.onResume();
		this.mForegroundActivty = this;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mForegroundActivty = null;
	}
	
	/**
	 * 获取前台进程的activity
	 * @return
	 */
	public static Activity getForegroundActivity(){
		return mForegroundActivty;
	}
}

