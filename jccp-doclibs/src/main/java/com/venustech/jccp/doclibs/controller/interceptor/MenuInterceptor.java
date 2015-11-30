package com.venustech.jccp.doclibs.controller.interceptor;

import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
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
		final List<MenuBean> menus = CacheKit.get("menuList", "menuList", new IDataLoader() {
			public Object load() {
				return (new MenuService()).getMenuBeanList();
			}
		});
		inv.getController().setAttr("menuList", menus);
		inv.invoke();
		
	}

}
