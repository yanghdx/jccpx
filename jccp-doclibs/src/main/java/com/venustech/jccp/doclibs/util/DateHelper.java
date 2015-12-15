package com.venustech.jccp.doclibs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	
	/**
	 * 获取当前时间戳（距1970-1-1的秒数）
	 * @return
	 */
	public static int now() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	/**
	 * 根据时间戳获取时间字符串
	 * @param seconds 时间戳
	 * @return
	 */
	public static String toStr(int seconds) {
		return toStr(seconds, "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 根据时间戳获取时间字符串
	 * @param seconds 时间戳
	 * @param format  时间格式
	 * @return
	 */
	public static String toStr(int seconds, String format) {
		return new SimpleDateFormat(format).format(new Date(seconds * 1000L));
	}
}
