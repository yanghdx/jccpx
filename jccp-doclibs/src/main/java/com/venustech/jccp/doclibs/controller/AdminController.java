package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.venustech.jccp.doclibs.controller.interceptor.AdminMenuInterceptor;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.core.interceptor.AuthInterceptor;
import com.venustech.jccp.doclibs.service.MenuService;

/**
 * 后台管理员controller
 * @author yanghdx
 *
 */
@Before({AuthInterceptor.class, AdminMenuInterceptor.class})
public class AdminController extends Controller {


	/**
	 * admin管理首页
	 */
	public void index() {
		render("admin-index.html");
	}
	
	/**
	 * admin查看doc列表s
	 */
	public void docs() {
		
	}
	
	/**
	 * admin添加doc页面
	 */
	public void docAdd() {
		createToken("addToken");
		setAttr("menus", CacheKit.get(WebConst.CacheKey.MENUS, "menuList", new IDataLoader() {
			public Object load() {
				//加载普通menu
				return (new MenuService()).loadMenus();
			}
		}));
		render("doc-add.html");
	}
}
