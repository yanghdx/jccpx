package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.venustech.jccp.doclibs.controller.interceptor.AdminMenuInterceptor;
import com.venustech.jccp.doclibs.core.interceptor.AuthInterceptor;

/**
 * 后台管理员controller
 * @author yanghdx
 *
 */
@Before({AuthInterceptor.class, AdminMenuInterceptor.class})
public class AdminController extends Controller {

	public void index() {
		render("admin-index.html");
	}
	
	
}
