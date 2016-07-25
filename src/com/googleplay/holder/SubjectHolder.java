package com.googleplay.holder;

import com.googleplay.application.utils.ImageLoader;
import com.googleplay.application.utils.UIUtils;
import com.googleplay.bean.SubjectInfo;
import com.myprogram.googleplay.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectHolder extends BaseHolder<SubjectInfo> {
	private ImageView item_icon;
	private TextView item_txt;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.subject_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_txt = (TextView) view.findViewById(R.id.item_txt);
		return view;
	}

	@Override
	public void refreshView() {
		SubjectInfo subjectinfo = getData();
		ImageLoader.load(item_icon, subjectinfo.getUrl());
		item_txt.setText(subjectinfo.getDes());
	}

}
