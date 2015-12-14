package com.venustech.jccp.doclibs.controller.interceptor;

import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.service.MenuService;
import com.venustech.jccp.doclibs.vo.MenuBean;

/**
 * 用于加载menu列表
 * @author yanghdx
 *
 */
public class MenuInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		final List<MenuBean> menuList = CacheKit.get(WebConst.CacheKey.MENUS, "menuList", new IDataLoader() {
			public Object load() {
				//加载普通menu
				return (new MenuService()).loadMenus();
			}
		});
		inv.getController().setAttr("leftMenuList", menuList);
		inv.invoke();
		
	}

}
