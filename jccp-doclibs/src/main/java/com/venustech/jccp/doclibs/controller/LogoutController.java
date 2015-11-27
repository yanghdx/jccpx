package com.venustech.jccp.doclibs.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * logout
 * @author yanghdx
 *
 */
public class LogoutController extends Controller {

	@ActionKey("/logout")
	public void logout() {
		final String sessionId = getSession().getId();
		if (CacheKit.get("onlineUser", sessionId) != null) {
			CacheKit.remove("onlineUser", sessionId);
			getSession().invalidate();
		}
		redirect("/login");
	}
}
