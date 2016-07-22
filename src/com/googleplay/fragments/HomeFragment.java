package com.googleplay.fragments;


import java.util.ArrayList;
import java.util.List;

import com.googleplay.application.utils.UIUtils;
import com.googleplay.fragments.LoadingPager.LoadResult;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class HomeFragment extends BaseFragment {
	private List<String> mDatas;
	@Override
	protected View createLoadingView() {
		ListView mListView = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter();
		mListView.setAdapter(adapter);
		return mListView;
	}

	@Override
	protected LoadResult load() {
		mDatas = new ArrayList<String>();
		return check(mDatas);
	}
		
	class HomeAdapter extends BaseAdapter{

		private ViewHolder holder;

		@Override
		public int getCount() {
			return mDatas.size();
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
			if(convertView == null){
				convertView = new View(UIUtils.getContext());
				holder = new ViewHolder();
				holder.tv = (TextView) convertView;
				convertView.setTag(holder);
			}
			holder.tv.setText("haha,text!");
			return convertView;
		}
		
		
	}
	private class ViewHolder{
		TextView tv;
	}
}
