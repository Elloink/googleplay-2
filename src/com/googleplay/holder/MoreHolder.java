package com.googleplay.holder;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.myprogram.googleplay.R;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHolder extends BaseHolder<Integer> {
	View view;
	RelativeLayout rl_more_loading;
	RelativeLayout rl_more_error;
	/**
	 * 加载失败的数据类型
	 * 加载成功的类型
	 * 加载成功的类型，但没有更多数据了
	 */
	//加载成功还有更多
	public static  int MORE_SUCCESS = 0;
	//加载成功没有更多了
	public static  int NOMORE_SUCCESS = 1;
	//加载失败
	public static  int ERROR = 2;
	//接收适配器
	private MyBaseAdapter adapter;
	public MoreHolder(boolean hasMore) {
		
	}
	
	public MoreHolder(MyBaseAdapter adapter, boolean hasMore) {
		this.adapter = adapter;
	}

	@Override
	public View initView() {
		view = UIUtils.inflate(R.layout.loading_page_loading);
		rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error= (RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}

	@Override
	public void refreshView() {
		Integer data = getData();
		rl_more_loading.setVisibility(data == MORE_SUCCESS ? View.VISIBLE : View.GONE);
		rl_more_error.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
	}
	/**
	 * 如果当前数据有更多，就加载更多
	 */
	@Override
	public View getRootView() {
		if(getData() == MORE_SUCCESS){
			loadMore();
		}
		return super.getRootView();
	}

	private void loadMore() {
		adapter.loadMore();
	}
	
}
