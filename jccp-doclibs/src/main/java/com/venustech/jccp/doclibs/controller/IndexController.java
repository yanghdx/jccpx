package com.venustech.jccp.doclibs.controller;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

public class IndexController extends Controller {
	
	static final Logger logger = Logger.getLogger(IndexController.class);
	
	public void index() {
		logger.warn(System.getProperty("Web.root"));
		logger.warn(PathKit.getWebRootPath());
		logger.info("In index.");
		logger.error(System.getProperty("webRootPath"));
		render("index.html");
	}
}
