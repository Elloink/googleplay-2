package com.googleplay.application.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * drawable工具类
 * @author feng
 *
 */
public class DrawableUtils {
	/**
	 * 创建一个图片
	 * @param contentColor 设置内容区域颜色
	 * @param strokeColor 设置描边颜色
	 * @param radius 设置圆角
	 * @return
	 */
	public static GradientDrawable createDrawable(int contentColor,int strokeColor,int radius){
		GradientDrawable drawable = new GradientDrawable();//生成shape
		drawable.setGradientType(GradientDrawable.RECTANGLE);//设置矩形
		drawable.setColor(contentColor);//设置内容区域颜色
		drawable.setStroke(1, strokeColor);//设置四周描边，描边四周未圆角不会出现黑色阴影
		drawable.setCornerRadius(radius);//设置四角都是圆角
		return drawable;
	}
	/**
	 * 图片状态选择器
	 * @param normalState 普通状态
	 * @param pressedState 按压状态
	 * @return 
	 */
	public static StateListDrawable createSelector(Drawable normalState,Drawable pressedState){
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[]{android.R.attr.state_pressed,android.R.attr.state_enabled}, pressedState);
		bg.addState(new int[]{android.R.attr.state_enabled},normalState);
		bg.addState(new int[]{}, normalState);
		return bg;
	}
	/**
	 * 获取图片大小
	 * @param drawable
	 * @return 
	 */
	public static int getDrawableSize(Drawable drawable){
		if(drawable == null){
			return 0;
		}else{
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
				return bitmap.getByteCount();
			}else{
				return bitmap.getRowBytes()*bitmap.getHeight();
			}
		}
	}

}
