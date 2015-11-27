package com.venustech.jccp.doclibs.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

	public static final String WEB_ROOT_KEY = "webRootPath";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.getProperties().remove(WEB_ROOT_KEY);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.setProperty(WEB_ROOT_KEY, arg0.getServletContext().getRealPath("/"));
	}

}
