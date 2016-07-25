package com.googleplay.fragments;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.CategoryInfo;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.BaseHolder;
import com.googleplay.holder.CategoryHolder;
import com.googleplay.holder.CategoryTitleHolder;
import com.googleplay.protocol.CategoryProtocol;


public class CategoryFragment extends BaseFragment {

		private List<CategoryInfo> mDatas;
	@Override
	protected LoadResult load() {
		CategoryProtocol protocol = new CategoryProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	protected View createLoadedView() {
		ListView mListView= new ListView(UIUtils.getContext());
		CategoryAdapter adapter = new CategoryAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}
	private class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

		public CategoryAdapter(List mDatas) {
			super(mDatas);
			
		}
		private BaseHolder holder;
		private int postion;
		
		
		
		@Override
		public BaseHolder getHolder() {
			CategoryInfo categoryInfo = getData().get(postion);
			if(categoryInfo.isTitle()){
				holder  = new CategoryTitleHolder();
			}else{
				holder = new CategoryHolder();
			}
			return holder;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.postion = position;
			return super.getView(position, convertView, parent);
		}
		@Override
		protected List onLoadMore() {
			return null;
		}
		@Override
		public boolean hasMore() {
			return false;
		}
		
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount()+1;
		}
		@Override
		public int getInnerItemView(int position) {
			CategoryInfo categoryinfo =  getData().get(position);
			if(categoryinfo.isTitle()){
				return super.getInnerItemView(position)+1;
			}else{
				return super.getInnerItemView(position);
			}
		}
		
		
	}
}
