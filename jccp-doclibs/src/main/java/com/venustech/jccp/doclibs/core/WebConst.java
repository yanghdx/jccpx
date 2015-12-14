package com.venustech.jccp.doclibs.core;
/**
 * 常量
 * @author  yang_huidi
 * @version 1.00
 * @date    2015年6月26日
 * @see        
 * 
 */
public interface WebConst {

	interface PageConst {
		int PAGE_SIZE = 15;
	}
	
	interface UserConst {
		final String ADMIN  = "admin";
		final String USER   = "user";
		final String LOGIN  = "login";
		final String ONLINE = "online";
	}
	
	
	interface ReturnConst {
		final String RESULT  = "result";
		final String TYPE    = "type";
		final String CODE    = "code";
		final String MSG     = "msg";
		final String SUCCESS = "success";
		final String ERROR   = "error";
		
	}
	
	interface ConfigConst {
		final String ON  = "1";
		final String OFF = "0";
	}
	
	interface MenuType {
		int COMMON_MENU = 0;
		int ADMIN_MENU  = 1;
	}
}
