package com.googleplay.fragments;

import java.util.List;
import android.view.View;
import android.widget.ListView;
import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.AppInfo;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.AppHolder;
import com.googleplay.holder.BaseHolder;
import com.googleplay.protocol.AppProtocol;

public class AppFragment extends BaseFragment {
	private List<AppInfo> mDatas;
	@Override
	protected View createLoadedView() {
		ListView mListView = new ListView(UIUtils.getContext());
		AppAdapter adapter = new AppAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}

	@Override
	protected LoadResult load() {
		AppProtocol protocol = new AppProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}
	private class AppAdapter extends MyBaseAdapter<AppInfo>{

		public AppAdapter(List<AppInfo> mDatas) {
			super(mDatas);
			
		}

		@Override
		public BaseHolder<AppInfo> getHolder() {
			return new AppHolder();
		}

		@Override
		protected List onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			return protocol.load(mDatas.size());
		}
		
	}
}
