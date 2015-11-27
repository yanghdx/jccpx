package com.venustech.jccp.doclibs.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.venustech.jccp.doclibs.controller.AdminController;
import com.venustech.jccp.doclibs.controller.DocController;
import com.venustech.jccp.doclibs.controller.HomeController;
import com.venustech.jccp.doclibs.controller.IndexController;
import com.venustech.jccp.doclibs.controller.LoginController;
import com.venustech.jccp.doclibs.controller.LogoutController;
import com.venustech.jccp.doclibs.controller.SysLogController;
import com.venustech.jccp.doclibs.core.handler.SafetyHandler;
import com.venustech.jccp.doclibs.core.interceptor.AuthInterceptor;
import com.venustech.jccp.doclibs.core.interceptor.ExceptionInterceptor;
import com.venustech.jccp.doclibs.model.Admin;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.model.DocTag;
import com.venustech.jccp.doclibs.model.DocType;
import com.venustech.jccp.doclibs.model.Menu;
import com.venustech.jccp.doclibs.model.SysLog;

/**
 * Web configuration
 * @author yanghdx
 *
 */
public class WebConfig extends JFinalConfig {

	static final Logger logger = Logger.getLogger(WebConfig.class);
	
	@Override
	public void afterJFinalStart() {
		logger.info("Web is started!");
		String root = PathKit.getWebRootPath();
		logger.info("Web root path is " + root);
	}
	
	@Override
	public void beforeJFinalStop() {
		logger.info("Web will stop...");
	}
	
	@Override
	public void configConstant(Constants me) {
		Prop pro = PropKit.use("product.ini");
		
		me.setDevMode(pro.getBoolean("devMode", false));
		me.setEncoding("UTF-8");
		me.setViewType(ViewType.FREE_MARKER);
		me.setError404View("/404.html");
		me.setError500View("/500.html");
		me.setBaseViewPath("/views/");
		me.setI18nDefaultBaseName("global");
		me.setI18nDefaultLocale(pro.get("language", "zh-Hans"));
		
	}

	@Override
	public void configHandler(Handlers me) {
		//druid
		me.add(new DruidStatViewHandler("/druid", new IDruidStatViewAuth(){
			public boolean isPermitted(HttpServletRequest request) {
				//登录用户可查看druid统计
				return request.getSession() != null;
			}
		}));
		
		me.add(new SafetyHandler());
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
		me.add(new ExceptionInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		Prop db = PropKit.use("db.properties");
		DruidPlugin druidPlugin = new DruidPlugin(db.get("jdbcUrl"), db.get("user"), db.get("password"));
		druidPlugin.setTestOnBorrow(true);
		druidPlugin.setTestOnReturn(true);
		druidPlugin.setTestWhileIdle(true);
		druidPlugin.addFilter(new StatFilter());
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);
		me.add(druidPlugin);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(db.getBoolean("showSql", false));
		arp.addMapping("doc", Doc.class);
		arp.addMapping("doc_type", DocType.class);
		arp.addMapping("doc_tag", DocTag.class);
		arp.addMapping("sys_log", SysLog.class);
		arp.addMapping("admin", Admin.class);
		arp.addMapping("menu", Menu.class);
		
		me.add(arp);
		
		//ehcache
		me.add(new EhCachePlugin());
		
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/index", IndexController.class);
		me.add("/home", HomeController.class);
		me.add("/doc", DocController.class);
		me.add("/admin", AdminController.class);
		me.add("/syslog", SysLogController.class);
		me.add("/login", LoginController.class);
		me.add("/logout", LogoutController.class);
		
	}

}
