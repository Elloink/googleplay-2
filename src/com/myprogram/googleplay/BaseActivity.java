package com.myprogram.googleplay;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	//获取前台进程的activity
	private static Activity mForegroundActivty = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
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

