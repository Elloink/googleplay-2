package com.googleplay.adapter;

import java.util.List;

import com.googleplay.application.utils.UIUtils;
import com.googleplay.holder.BaseHolder;
import com.googleplay.holder.MoreHolder;
import com.googleplay.manager.ThreadManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
	public List<T> mDatas;
	BaseHolder holder;
	public MyBaseAdapter(List<T> mDatas){
		setData(mDatas);
	}
	public void setData(List<T> mDatas){
		this.mDatas = mDatas;
	}
	public List<T> getData(){
		return mDatas;
	}
	//普通数据类型
	private final int ITEM_VIEW_TYPE = 0;
	//加载更多数据类型
	private final int MORE_VIEW_TYPE = 1;
	private BaseHolder<Integer> moreHolder;
	@Override
	public int getItemViewType(int position) {
		//服务器一次返回20条数据，position=20返回加载更多的类型
		if(position  == getCount()-1){
			return MORE_VIEW_TYPE;
		}else{
			//返回普通类型
			return getInnerItemView(position);
		}
	}
	
	public int getInnerItemView(int position){
		return ITEM_VIEW_TYPE;

	}
	@Override
	public int getViewTypeCount() {
		
		return super.getViewTypeCount();
	}
	@Override
	public int getCount() {
		//显示加载更多
		return mDatas.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView != null){
			holder = (BaseHolder) convertView.getTag();
		}else{
			//判断当前position对应item的类型是加载更多则加载该item
			if(ITEM_VIEW_TYPE == getItemViewType(position)){
				holder = getHolder();
			}else if(MORE_VIEW_TYPE == getItemViewType(position)){
				holder = getMoreHolder();
			}
		}
		if(position == getItemViewType(position)){
			holder.setData(mDatas);
		}
		return holder.getRootView();
	}
	public abstract BaseHolder<T> getHolder();
	
	public  BaseHolder getMoreHolder(){
		moreHolder =  new MoreHolder(this,hasMore());
		return (BaseHolder) moreHolder;
	}
	public boolean hasMore(){
		return true;
	}
	/**
	 * 在子线程中执行加载更多
	 */
	public boolean is_loading = false;
	public void loadMore(){
		//开启子线程加载服务器发送的数据
		if(!is_loading){
			is_loading = true;
		
		ThreadManager.getLongPool().execute(new Runnable() {
			
			@Override
			public void run() {
				final List list = onLoadMore();
				UIUtils.runInMainThread(new Runnable() {
					
					@Override
					public void run() {
						if(null == list){
							getMoreHolder().setData(MoreHolder.ERROR);
						}else if(list.size() < 20){
							getMoreHolder().setData(MoreHolder.NOMORE_SUCCESS);
						}else{
							getMoreHolder().setData(MoreHolder.MORE_SUCCESS);
						}
						if(list!= null){
							if(mDatas != null){
								mDatas.addAll(list);
							}else{
								setData(list);
							}
						}
						notifyDataSetChanged();
						is_loading =false;
					}
				});
			}

			
		});
		}
	}
	protected abstract List onLoadMore() ;
}
