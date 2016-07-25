package com.googleplay.fragments;

import java.util.List;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.AppInfo;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.BaseHolder;
import com.googleplay.holder.GameHolder;
import com.googleplay.protocol.GameProtocol;


public class GameFragment extends BaseFragment {

	List<AppInfo> mDatas ;
	@Override
	protected LoadResult load() {
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	protected View createLoadedView() {
		ListView mListView = new ListView(UIUtils.getContext());
		GameAdapter adapter = new GameAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}
	private class GameAdapter extends MyBaseAdapter<AppInfo>{

		public GameAdapter(List<AppInfo> mDatas) {
			super(mDatas);
		}

		@Override
		public BaseHolder getHolder() {
			return new GameHolder();
		}

		@Override
		protected List<AppInfo> onLoadMore() {
			GameProtocol protocol = new GameProtocol();
			return  protocol.load(mDatas.size());
		}
		
	}
}
