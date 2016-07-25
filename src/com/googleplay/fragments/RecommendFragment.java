package com.googleplay.fragments;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.googleplay.adapter.MyBaseAdapter;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.fragments.LoadingPager.LoadResult;
import com.googleplay.holder.BaseHolder;
import com.googleplay.protocol.RecommendProtocol;
import com.googleplay.random.StellarMap;
import com.googleplay.random.StellarMap.Adapter;


public class RecommendFragment extends BaseFragment {

	private List<String> mDatas;
	private StellarMap stellmap;
	@Override
	protected LoadResult load() {
		RecommendProtocol protocol = new RecommendProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	protected View createLoadedView() {
		stellmap = new StellarMap(UIUtils.getContext());
		stellmap.setInnerPadding(20, 20, 20, 20);
		//设置x,y的规则
		stellmap.setRegularity(6, 9);
		StellarAdapter adapter = new StellarAdapter();
		//设置数据适配器
		stellmap.setAdapter(adapter);
		//设置从第0组开始，开启动画
		stellmap.setGroup(0, true);
		return stellmap;
	}
	private class StellarAdapter implements Adapter{
		Random random;
		public StellarAdapter() {
			super();
			random = new Random();
			
		}
		//设置动画规则为2组
		@Override
		public int getGroupCount() {
			return 2;
		}
		//每一组的数量
		@Override
		public int getCount(int group) {
			return 15;
		}
		//返回TextView
		@Override
		public View getView(int group, int position, View convertView) {
			TextView textview = new TextView(UIUtils.getContext());
			//随机生成文本颜色
			int red = 20 + random.nextInt(230);
			int green = 20 + random.nextInt(230);
			int blue = 20 + random.nextInt(230);
			int color = Color.rgb(red, green, blue);
			textview.setTextColor(color);
			textview.setText(mDatas.get(position));
			return textview;
		}
		
		@Override
		public int getNextGroupOnPan(int group, float degree) {
			return (group +1)%2;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			return 0;
		}

		
	}
}
