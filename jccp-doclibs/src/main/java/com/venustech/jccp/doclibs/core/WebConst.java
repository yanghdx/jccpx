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
		String ADMIN  = "admin";
		String USER   = "user";
		String LOGIN  = "login";
		String ONLINE = "online";
	}
	
	
	interface ReturnConst {
		String RESULT  = "result";
		String TYPE    = "type";
		String CODE    = "code";
		String MSG     = "msg";
		String SUCCESS = "success";
		String ERROR   = "error";
		
	}
	
	interface ConfigConst {
		String ON  = "1";
		String OFF = "0";
	}
	
	interface MenuType {
		int COMMON_MENU = 0;
		int ADMIN_MENU  = 1;
	}
	
	interface CacheKey {
		String ONLINE_USERS = "onlineUsers";
		String USERS        = "users";
		String MENUS        = "menus";
		String DOCS         = "docs";
	}
	
	interface Upload {
		String PATH = "upload";
		int FILE_MAX_SIZE = 100 * 1024 * 1024;
		
	}
}
