package com.googleplay.holder;

import android.view.View;

public abstract class BaseHolder<T> {
	private View view;
	private T data;
	public BaseHolder(){
		View view = initView();
		view.setTag(this);
	}
	
	public abstract View initView();
	public abstract void refreshView();
	public View getRootView(){
		return view;
	}
	
	public void setData(T data){
		this.data = data;
		refreshView();
	}
	
	public T getData(){
		return data;
	}
}
