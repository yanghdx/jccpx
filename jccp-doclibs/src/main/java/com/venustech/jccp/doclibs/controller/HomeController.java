package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.venustech.jccp.doclibs.controller.interceptor.MenuInterceptor;

/**
 * 主页
 * @author yanghdx
 *
 */
public class HomeController extends Controller {

	@Before(MenuInterceptor.class)
	public void index() {
		render("home-index.html");
	}
	
	public void ex() {
		throw new IllegalArgumentException("exception");
	}
}
