package com.googleplay.holder;

import com.googleplay.application.utils.ImageLoader;
import com.googleplay.application.utils.StringUtils;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.AppInfo;
import com.myprogram.googleplay.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HomeHolder extends BaseHolder<AppInfo> {
	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_size;
	private TextView item_bottom;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_size = (TextView) view.findViewById(R.id.item_size);
		item_bottom = (TextView) view.findViewById(R.id.item_bottom);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appinfo = getData();
		ImageLoader.load(item_icon, appinfo.getIconUrl());
		item_title.setText(appinfo.getName());
		item_rating.setRating(appinfo.getStars());
		item_size.setText(StringUtils.formatFileSize(appinfo.getSize()));
		item_bottom.setText(appinfo.getDes());
	}

}
