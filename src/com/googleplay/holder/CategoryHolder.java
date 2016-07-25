package com.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googleplay.application.utils.ImageLoader;
import com.googleplay.application.utils.StringUtils;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.CategoryInfo;
import com.myprogram.googleplay.R;

public class CategoryHolder extends BaseHolder<CategoryInfo> {
	ImageView iv_1;
	ImageView iv_2;
	ImageView iv_3;
	TextView tv_1;
	TextView tv_2;
	TextView tv_3;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item);
		iv_1 = (ImageView) view.findViewById(R.id.iv_1);
		iv_2 = (ImageView) view.findViewById(R.id.iv_2);
		iv_3 = (ImageView) view.findViewById(R.id.iv_3);
		
		tv_1 = (TextView) view.findViewById(R.id.tv_1);
		tv_2 = (TextView) view.findViewById(R.id.tv_2);
		tv_3 = (TextView) view.findViewById(R.id.tv_3);
		return view;
	}

	@Override
	public void refreshView() {
		CategoryInfo categoryinfo = getData();
		String name1 = categoryinfo.getName1();
		String name2 = categoryinfo.getName2();
		String name3 = categoryinfo.getName3();
		
		String imageUrl1 = categoryinfo.getImageUrl1();
		String imageUrl2 = categoryinfo.getImageUrl2();
		String imageUrl3 = categoryinfo.getImageUrl3();
		
		if(StringUtils.isEmpty(name1)){
			tv_1.setText(name1);
			iv_1.setImageDrawable(null);
		}else{
			tv_1.setText(name1);
			ImageLoader.load(iv_1, imageUrl1);
		}
		if(StringUtils.isEmpty(name2)){
			tv_2.setText(name2);
			iv_2.setImageDrawable(null);
		}else{
			tv_2.setText(name2);
			ImageLoader.load(iv_2, imageUrl2);
		}
		if(StringUtils.isEmpty(name3)){
			tv_3.setText(name3);
			iv_3.setImageDrawable(null);
		}else{
			tv_1.setText(name3);
			ImageLoader.load(iv_3, imageUrl3);
		}
		
		
	}
	
}
