package com.venustech.jccp.doclibs.core.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.core.online.OnlineUser;
import com.venustech.jccp.doclibs.util.HttpHelper;

/**
 * @author yanghdx
 * 
 */
public class AuthInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		final Controller controller = inv.getController();
		final HttpServletRequest request = controller.getRequest();
		if (!request.getRequestURI().startsWith("/admin/")) {
			inv.invoke();
		} else {
			final HttpSession session = controller.getSession();
			final OnlineUser user = (OnlineUser) session
					.getAttribute(WebConst.UserConst.ONLINE);
			if (user == null) {
				if (HttpHelper.isAjax(request)) {
					final Map<String, String> result = new HashMap<String, String>(8);
					result.put("result", "error");
					result.put("type", "ajax");
					result.put("msg", "No access");
					controller.renderJson(result);
				} else {
					controller.redirect("/index");
				}
			} else {
				inv.invoke();
			}
		}
	}
}
