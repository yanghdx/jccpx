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
 * 授权拦截器
 * @author yanghdx
 * 
 */
public class AuthInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		final Controller controller = inv.getController();
		controller.setAttr("_ip", controller.getRequest().getRemoteAddr());
		HttpServletRequest request = controller.getRequest();
		HttpSession session = controller.getSession();
		OnlineUser user = CacheKit.get(CacheKey.ONLINE_USERS, session.getId());
		if (user != null) {
			//统一设置username、userId
			controller.setAttr("_username", user.getUsername());
			controller.setAttr("_userId", user.getUserId());
			inv.invoke();
		} else {
			if (!request.getRequestURI().startsWith("/admin")) {
				inv.invoke();
			} else {
				if (HttpHelper.isAjax(request)) {
					final Map<String, String> result = new HashMap<String, String>(8);
					result.put("result", "error");
					result.put("type", "ajax");
					result.put("msg", "Auth failed!");
					controller.renderJson(result);
				} else {
					controller.redirect("/login");
				}
			}
		}
	}
}
