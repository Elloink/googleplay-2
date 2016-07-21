package com.myprogram.googleplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.googleplay.application.utils.UIUtils;
import com.googleplay.fragments.AppFragment;
import com.googleplay.fragments.BaseFragment;
import com.googleplay.fragments.CategoryFragment;
import com.googleplay.fragments.GameFragment;
import com.googleplay.fragments.HomeFragment;
import com.googleplay.fragments.HotFragment;
import com.googleplay.fragments.RecommendFragment;
import com.googleplay.fragments.SubjectFragment;
import com.googleplay.widget.PagerTab;

public class MainActivity extends BaseActivity {

    private PagerTab tabs;
	private ViewPager pager;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initActionBar();
    }


	@Override
	protected void initActionBar() {
	}


	@Override
	protected void initView() {
		setContentView(R.layout.activity_main); 
		//侧滑菜单
//		DrawerLayout drawerlayout = new DrawerLayout(UIUtils.getContext());
		//初始化横着滚动的title
		tabs = (PagerTab) findViewById(R.id.tabs);
		//初始化viewpager
		pager = (ViewPager) findViewById(R.id.pager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		//将pagertab与viewpager绑定在一起
//		tabs.setViewPager(pager);
	}
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{
		private String[] tab_names;
		BaseFragment fragment = null;
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}

		@Override
		public Fragment getItem(int position) {
			
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return tab_names.length ;
		}
		
	}
	

	@Override
	protected void init() {
	}
    
}
