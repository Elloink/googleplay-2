package com.googleplay.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Properties;

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
	/**
	 * 字符数组数据写入文件
	 * @param content 需要写入的字符数组
	 * @param path 文件路径
	 * @param append 是否已添加模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(byte[] content,String path,boolean append){
		boolean res  = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if(f.exists()){
				if(!append){
					f.delete();
					f.createNewFile();
				}
			}else{
				f.createNewFile();
			}
			if(f.canWrite()){
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.write(content);
				res = true;
			}
			
		} catch (Exception e) {
			LogUtils.e(e);
		}finally{
			IOUtils.close(raf);
		}
		return res;
	}
	/**
	 * 字符串写入文件
	 * @param content 写入到字符串
	 * @param path 写入的位置
	 * @param append 是否添加模式写入
	 * @return 是否成功
	 */
	public static boolean writeFile(String content,String path,boolean append){
		return writeFile(content.getBytes(),path,append);
	}
	/**
	 * 把键值对写入文件
	 * @param filePath 文件了路径
	 * @param key 键
	 * @param value 值
	 * @param comment 键值对的注解
	 */
	public static void writeProperties(String filePath,String key,String value,String comment){
		if(StringUtils.isEmpty(key)|| StringUtils.isEmpty(filePath)){
			return ;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if(!f.exists()|| !f.isFile()){
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);//先读取文件，再把键值对添加到后面
			p.setProperty(key, value);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
			
		} catch (Exception e) {
			LogUtils.e(e);
		}finally{
			IOUtils.close(fis);
			IOUtils.close(fos);
		}
	}
	/**
	 * 根据key读取文件
	 * @param filePath
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String readProperties(String filePath,String key,String defaultValue){
		if(StringUtils.isEmpty(key)||StringUtils.isEmpty(filePath)){
			return null;
		}
		String value = null;
		
		FileInputStream fis = null;
		File f = new File(filePath);
		try {
			if(!f.exists() || !f.isFile() ){
				f.createNewFile();
			}
				fis = new FileInputStream(f);
				Properties p = new Properties();
				p.load(fis);
				value = p.getProperty(key, defaultValue);
			
		} catch (Exception e) {
			LogUtils.e(e);
		}finally{
			IOUtils.close(fis);
		}
		return value;
	}
	/**
	 * 字符串键值对的map写入文件
	 * @param filePath
	 * @param map
	 * @param append
	 * @param comment
	 */
	public static void writeMap(String filePath,Map<String,String> map,boolean append,String comment){
		if(map == null || map.size() == 0 || StringUtils.isEmpty(filePath)){
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if(!f.exists()||!f.isFile()){
				f.createNewFile();
			}
			Properties p = new Properties();
			if(append){
				fis = new FileInputStream(f);
				p.load(fis);//先读取文件，把键值对追加到后面
			}
			p.putAll(map);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (Exception e) {
			LogUtils.e(e);
		}finally{
			IOUtils.close(fis);
			IOUtils.close(fos);
		}
	}
}
