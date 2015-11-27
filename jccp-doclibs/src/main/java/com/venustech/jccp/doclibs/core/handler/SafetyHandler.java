package com.venustech.jccp.doclibs.core.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.venustech.jccp.doclibs.util.HttpHelper;

public class SafetyHandler extends Handler {

	
	@Override
	public void handle(String target,
			HttpServletRequest request,
			HttpServletResponse response,
			boolean[] isHandled) {
		
		if (target.startsWith("/logs/") 
				|| target.startsWith("/views/")) {
			isHandled[0] = true;
			try {
				if (HttpHelper.isAjax(request)) {
					response.setContentType("application/json;charset=utf-8");
					response.getWriter().write("No access");
				} else {
					response.sendRedirect("/index");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			nextHandler.handle(target, request, response, isHandled);
		}
			
		
	}

}
