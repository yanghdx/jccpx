package com.venustech.jccp.doclibs.controller;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

/**
 * Index
 * @author yanghdx
 *
 */
public class IndexController extends Controller {
	
	static final Logger logger = Logger.getLogger(IndexController.class);
	
	public void index() {
		logger.info("WebRootPath is : " + PathKit.getWebRootPath());
		redirect("/home");
	}
}
