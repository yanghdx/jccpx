package com.venustech.jccp.doclibs.core.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.venustech.jccp.doclibs.core.WebConst.CacheKey;
import com.venustech.jccp.doclibs.core.online.OnlineUser;
import com.venustech.jccp.doclibs.util.HttpHelper;

/**
 * @author yanghdx
 * 
 */
public class AuthInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		final Controller controller = inv.getController();
		controller.setAttr("_ip", controller.getRequest().getRemoteAddr());
		final HttpServletRequest request = controller.getRequest();
		if (!request.getRequestURI().startsWith("/admin")) {
			inv.invoke();
		} else {
			final HttpSession session = controller.getSession();
			final OnlineUser user = CacheKit.get(CacheKey.ONLINE_USERS, session.getId());
			if (user == null) {
				if (HttpHelper.isAjax(request)) {
					final Map<String, String> result = new HashMap<String, String>(8);
					result.put("result", "error");
					result.put("type", "ajax");
					result.put("msg", "No access");
					controller.renderJson(result);
				} else {
					controller.redirect("/login");
				}
			} else {
				//统一设置username、userId
				controller.setAttr("_username", user.getUsername());
				controller.setAttr("_userId", user.getUserId());
				inv.invoke();
			}
		}
	}
}
