package com.venustech.jccp.doclibs.util;

import com.jfinal.kit.StrKit;

/**
 * Html Kit
 * @author yanghdx
 *
 */
public class HtmlKit {

	/**
	 * 编码字符
	 */
	static final String[] ENCODE_FROM = {"&", "\"", "'", "<", ">"};
	static final String[] ENCODE_TO   = {"&amp;", "&quot;", "&#39;", "&lt;", "&gt;"};
	/**
	 * 解码字符
	 */
	static final String[] DECODE_FROM = {"&quot;", "&#39;", "&#039;", "&lt;", "&gt;", "&amp;"};
	static final String[] DECODE_TO   = {"\"", "'", "'", "<", ">", "&"};
	
	
	/**
	 * 对html进行特殊字符编码
	 * @author yang_huidi
	 * @param str
	 * @return  
	 */
	
	public static String encode(String str) {
		if (StrKit.isBlank(str)) {
			return "";
		}
		for (int i=0; i<ENCODE_FROM.length && i<ENCODE_TO.length; i++ ) {
			str = str.replaceAll(ENCODE_FROM[i], ENCODE_TO[i]);
		}
		return str;
	}
	
	/**
	 * 对html进行特殊字符解码
	 * @author yang_huidi
	 * @param str
	 * @return  
	 */
	
	public static String decode(String str) {
		if (StrKit.isBlank(str)) {
			return "";
		}
		for (int i=0; i<DECODE_FROM.length && i<DECODE_TO.length; i++ ) {
			str = str.replaceAll(DECODE_FROM[i], DECODE_TO[i]);
		}
		return str;
	}
}
