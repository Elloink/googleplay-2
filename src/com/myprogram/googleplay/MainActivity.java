package com.myprogram.googleplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;

import com.googleplay.application.utils.UIUtils;
import com.googleplay.fragments.BaseFragment;
import com.googleplay.fragments.FragmentFactory;
import com.googleplay.widget.PagerTab;

public class MainActivity extends BaseActivity implements OnPageChangeListener {

    private PagerTab tabs;
	private ViewPager pager;
	private DrawerLayout drawerlayout;
	private ViewPagerAdapter adapter;

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
//		drawerlayout = new DrawerLayout(UIUtils.getContext());
		//初始化横着滚动的title
		tabs = (PagerTab) findViewById(R.id.tabs);
		//初始化viewpager
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		//将pagertab与viewpager绑定在一起
		tabs.setViewPager(pager);
		tabs.setOnPageChangeListener(this);
	}
	
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{
		private String[] tab_names;
		
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
			
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tab_names.length ;
		}
		
	}
	

	@Override
	protected void init() {
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}


	@Override
	public void onPageSelected(int position) {
		BaseFragment fragment = (BaseFragment) FragmentFactory.createFragment(position);
		fragment.show();
	}
    
}
