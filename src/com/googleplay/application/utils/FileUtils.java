package com.googleplay.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

/**
 * 文件工具类
 * @author feng
 *
 */
public class FileUtils {
	
	public static final String ROOT_DIR = "Googleplay";//应用根目录取名公司名称
	public static final String DOWNLOAD_DIR = "download";//下载目录
	public static final String CACHE_DIR = "cache";//缓存目录,方便清理缓存
	public static final String ICON_DIR = "icon";
	/**
	 * 获取SD卡挂载状态
	 * @return
	 */
	public static boolean isSDCardAvailable(){
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取下载目录
	 * @return
	 */
	public static String getDownLoadDir(){
		return getDir(DOWNLOAD_DIR);
	}
	/**
	 * 获取缓存目录
	 * @return
	 */
	public static String getCacheDir(){
		return getDir(CACHE_DIR);
	}
	/**
	 * 获取图标目录
	 * @return
	 */
	public static String getIconDir(){
		return getDir(ICON_DIR);
	}
	/**
	 * 如果当前sd卡可用，获取sd卡上的目录，如果sd不可用，获取cache目录
	 * @param dir
	 * @return
	 */
	public static String getDir(String dir){
		
		StringBuilder sb = new StringBuilder();
		if(isSDCardAvailable()){
			sb.append(getExternalStoragePath());
		}else{
			sb.append(getCachePath());
		}
		sb.append(dir);
		sb.append(File.separator);
		String path = sb.toString();
		if(createDirs(path)){
			return path;
		}else{
			return null;
				
		}
	}
	/**
	 * 获取sd卡的根目录
	 * @return
	 */
	public static String getExternalStoragePath(){
		StringBuilder sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append(File.separator);
		sb.append(ROOT_DIR);
		sb.append(File.separator);
		return sb.toString();
	}
	/**
	 *获取cache 目录
	 * @return
	 */
	public static String getCachePath(){
		File f = UIUtils.getContext().getCacheDir();
		if(f == null){
			return null;
		}else{
			return f.getAbsolutePath()+"/";
		}
	}
	/**
	 * 创建文件夹
	 * @param dirPath
	 * @return
	 */
	public static boolean createDirs(String dirPath){
		File f = new File(dirPath);
		if(!f.exists()||!f.isDirectory()){//如果不存在或者不是一个文件路径则创建
			return f.mkdirs();
		}else{
			return true;
		}
	}
	/**
	 * 复制文件选择是否删除源文件
	 * @param srcPath
	 * @param destPath
	 * @param deleteSrc
	 * @return
	 */
	public static boolean copyFile(String srcPath,String destPath,boolean deleteSrc){
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		return copyFile(srcFile, destFile, deleteSrc);
	}
	
	public static boolean copyFile(File srcFile,File destFile,boolean deleteSrc){
		if(!srcFile.exists() || !srcFile.isFile()){
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int len;
			while((len = in.read(buffer))!= 0){
				out.write(buffer,0,len);
				out.flush();
			}
			if(deleteSrc){
				srcFile.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.close(in);
			IOUtils.close(out);
		}
		return true;
	}
	/**
	 * 判断文件是否可写
	 * @param path
	 * @return
	 */
	public static boolean isWriteable(String path){
		try{
			if(StringUtils.isEmpty(path)){
				return false;
			}else{
				File f = new File(path);
				 return	f.exists() && f.canWrite();
			}
		}catch (Exception e){
			LogUtils.e(e);
			return false;
		}
		
	}
	/**
	 * 修改文件权限
	 * @param path
	 * @param mode
	 */
	public static void chmod(String path,String mode){
		try {
			String command = "chmod "+mode+" "+path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (Exception e) {
			LogUtils.e(e);
		}
	}
}
