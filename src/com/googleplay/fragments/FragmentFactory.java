package com.googleplay.fragments;

import java.util.HashMap;

import android.support.v4.app.Fragment;
/**
 * fragment 工厂类
 * @author feng
 *
 */
public class FragmentFactory {
	
	//首页
	private static final int TAB_HOME = 0;
	//应用
	private static final int TAB_APP = 1;
	//游戏
	private static final int TAB_GAME = 2;
	//专题
	private static final int TAB_SUBJECT = 3;
	//推荐
	private static final int TAB_RECOMMEND = 4;
	//分类
	private static final int TAB_CATEGORY = 5;
	//热门
	private static final int TAB_HOT = 6;
	
	private static HashMap<Integer,BaseFragment> mFragments = new HashMap<Integer,BaseFragment>();
	
	public static Fragment createFragment(int position){
		//从缓存中读取fragment
		BaseFragment fragment = mFragments.get(position);
		if( null == fragment){
			switch (position) {
			case TAB_HOME:
				fragment = new HomeFragment();
				break;
			case TAB_APP:
				fragment = new AppFragment();
				break;
			case TAB_GAME:
				fragment = new GameFragment();
				break;
			case TAB_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case TAB_RECOMMEND:
				fragment = new RecommendFragment();
				break;
			case TAB_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case TAB_HOT:
				fragment = new HotFragment();
				break;
			}
			//将fragment放入到缓存中
			mFragments.put(position, fragment);
		}
		return fragment;
	}
}
