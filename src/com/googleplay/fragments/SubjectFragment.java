package com.googleplay.fragments;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.SubjectInfo;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.BaseHolder;
import com.googleplay.holder.SubjectHolder;
import com.googleplay.protocol.SubjectProtocol;


public class SubjectFragment extends BaseFragment {
	List<SubjectInfo> mDatas;
	@Override
	protected LoadResult load() {
		SubjectProtocol protocol = new SubjectProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	protected View createLoadedView() {
		ListView mListView = new ListView(UIUtils.getContext());
		SubjectAdapter adapter = new SubjectAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}
	private class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(List<SubjectInfo> mDatas) {
			super(mDatas);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder() {
			return new SubjectHolder();
		}

		@Override
		protected List onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			return protocol.load(mDatas.size());
		}
		
	}
}
