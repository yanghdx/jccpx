package com.venustech.jccp.doclibs.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.venustech.jccp.doclibs.core.WebConst;

/**
 * logout
 * @author yanghdx
 *
 */
public class LogoutController extends Controller {

	@ActionKey("/logout")
	public void logout() {
		final String sessionId = getSession().getId();
		if (CacheKit.get(WebConst.CacheKey.ONLINE_USERS, sessionId) != null) {
			CacheKit.remove(WebConst.CacheKey.ONLINE_USERS, sessionId);
			getSession().invalidate();
		}
		redirect("/login");
	}
}
