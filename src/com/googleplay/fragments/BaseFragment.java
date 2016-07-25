package com.googleplay.fragments;

import java.util.List;

import com.googleplay.application.utils.UIUtils;
import com.googleplay.application.utils.ViewUtils;
import com.googleplay.fragments.LoadingPager.LoadResult;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


@SuppressLint("NewApi") 
public abstract class BaseFragment extends Fragment {
	
	LoadingPager mContentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(null == mContentView){
			
			LoadingPager mContentView = new LoadingPager(UIUtils.getContext()){
				
				@Override
				protected View createLoadingView() {
					return BaseFragment.this.createLoadedView();
				}
				
				@Override
				protected LoadResult load() {
					return BaseFragment.this.load();
				}
				
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentView);
		}
		
		return mContentView;
	}
	
	protected abstract View createLoadedView();

	protected abstract LoadResult load();

	public void show(){
		if(null != mContentView){
			mContentView.show();
		}
	}
	/**
	 * 检查服务器数据情况
	 * @param obj
	 * @return
	 */
	public LoadResult check(Object obj){
		if(obj == null){
			return LoadResult.ERROR;
		}
		if(obj instanceof List){
			List list = (List) obj;
			if(list.size() == 0){
				return LoadResult.EMPTY;
			}
		}
		return LoadResult.SUCESS;
		}
	
}
