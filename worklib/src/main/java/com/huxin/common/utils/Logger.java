package com.huxin.common.utils;

import android.util.Log;



/**
 * log工具类
 *
 */
public class Logger {
	public static boolean isDebug = true;

	public static void setIsDebug(boolean isDebug) {
		Logger.isDebug = isDebug;
	}

	/***
	 * 是否处于调试模式
	 * @return
	 */
	public static boolean isDebug(){
		return Logger.isDebug;
	}
	
	public static void d(String tag, String msg) {
		if (isDebug()) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, String msg, Throwable e) {
		if (isDebug()) {
			Log.d(tag, msg,e);
		}
	}
	public static void i(String tag, String msg) {
		if (isDebug()) {
			Log.i(tag, msg);
		}
	}
	public static void v(String tag, String msg) {
		if (isDebug()) {
			Log.v(tag, msg);
		}
	}
	public static void e(String tag, String msg) {
		if (isDebug()) {
			Log.e(tag, msg);
		}
	}
	public static void e(String tag, String msg, Throwable e) {
		if (isDebug()) {
			Log.e(tag, msg,e);
		}
	}
	
	public static void e(String tag, Throwable e){
		if(isDebug()){
			Log.e(tag,"",e);
		}
	}
	
	public static void w(String tag, String msg) {
		if (isDebug()) {
			Log.w(tag, msg);
		}
	}
	public static void w(String tag, String msg, Throwable e) {
		if (isDebug()) {
			Log.w(tag, msg,e);
		}
	}
	
}
