package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.venustech.jccp.doclibs.controller.validator.LoginValidator;
import com.venustech.jccp.doclibs.core.online.OnlineUser;
import com.venustech.jccp.doclibs.model.Admin;
import com.venustech.jccp.doclibs.service.AdminService;

public class LoginController extends Controller {

	
	private AdminService adminService = enhance(AdminService.class);
	
	public void index() {
		createToken("loginToken");
		render("login-index.html");
	}
	
	@Before(LoginValidator.class)
	public void login() {
		String user = getPara("user");
		String pass = getPara("pass");//md5(md5(md5(pass) + user) + token)
		//读库
		Admin admin = adminService.findByName(user);
		if (admin == null) {
			setAttr("errmsg", I18n.use().get("login.user.error"));
			createToken("loginToken");
			render("login-index.html");
			return;
		}
		//检查密码
		String dbPass = admin.getStr("pass");//md5(md5(pass) + user)
		String calcPass = HashKit.md5(dbPass + getPara("loginToken"));
		if (!pass.equals(calcPass)) {
			setAttr("errmsg", I18n.use().get("login.pass.error"));
			createToken("loginToken");
			render("login-index.html");
			return;
		}
		//封装在线用户
		final String sessionId = getSession().getId();
		OnlineUser onlineUser = new OnlineUser();
		onlineUser.setClientIP(this.getRequest().getRemoteHost());
		onlineUser.setLoginTime(System.currentTimeMillis());
		onlineUser.setUserId(admin.getInt("id"));
		onlineUser.setUsername(admin.getStr("user"));
		onlineUser.setEmail(admin.getStr("email"));
		onlineUser.setSessionId(sessionId);
		//放入缓存
		CacheKit.put("onlineUser", sessionId, onlineUser);
		
		redirect("/admin");
		
	}
}
