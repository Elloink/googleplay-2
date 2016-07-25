package com.googleplay.fragments;


import java.util.List;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.AppInfo;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.BaseHolder;
import com.googleplay.protocol.HomeProtocol;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class HomeFragment extends BaseFragment {
	private List<AppInfo> mDatas;
	@Override
	protected View createLoadedView() {
		ListView mListView = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}

	@Override
	protected LoadResult load() {
		HomeProtocol protocol = new HomeProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}
		
	class HomeAdapter extends MyBaseAdapter<AppInfo>{

		private ViewHolder holder;

		public HomeAdapter(List<AppInfo> mDatas) {
			super(mDatas);
		}

		@Override
		public BaseHolder getHolder() {
			return new ViewHolder();
		}

		

		@Override
		protected List onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			
			return protocol.load(getData().size());
		}
		
		
	}
	private class ViewHolder extends BaseHolder<String>{
		TextView tv;

		@Override
		public View initView() {
			tv = new TextView(UIUtils.getContext());
			return tv;
		}

		@Override
		public void refreshView() {
			tv.setText(getData());
		}
	}
}
