package com.venustech.jccp.doclibs.controller.interceptor;

import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.venustech.jccp.doclibs.service.MenuService;
import com.venustech.jccp.doclibs.vo.MenuBean;

/**
 * 用于加载系统管理员的menu列表
 * @author yanghdx
 *
 */
public class AdminMenuInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		final List<MenuBean> menuList = CacheKit.get("menus", "adminMenuList", new IDataLoader() {
			public Object load() {
				//加载admin menu
				return (new MenuService()).loadAdminMenus();
			}
		});
		inv.getController().setAttr("menuList", menuList);
		inv.invoke();
		
	}

	
}
