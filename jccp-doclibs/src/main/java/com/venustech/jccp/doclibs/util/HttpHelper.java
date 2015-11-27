package com.venustech.jccp.doclibs.util;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {

	/**
	 * 页面类型
	 */
	public interface PageType {
		String AJAXHEADERKEY = "X-Requested-With";
		String AJAXHEADERVALUE = "XMLHttpRequest";
		String AJAX = "1";
	}
	/**
	 * 是否为ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(final HttpServletRequest request) {
		if (request != null) {
			String ajaxHeadValue = request.getHeader(PageType.AJAXHEADERKEY);
			if (ajaxHeadValue == null) {
				ajaxHeadValue = request.getHeader("HTTP_X_REQUESTED_WITH");
			}

			if (PageType.AJAXHEADERVALUE.equals(ajaxHeadValue)) {
				return true;
			}
		}
		return false;
	}

}
