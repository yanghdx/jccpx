package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.upload.UploadFile;
import com.venustech.jccp.doclibs.controller.interceptor.AdminMenuInterceptor;
import com.venustech.jccp.doclibs.controller.validator.DocAddValidator;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.core.interceptor.AuthInterceptor;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.service.DocService;
import com.venustech.jccp.doclibs.service.MenuService;
import com.venustech.jccp.doclibs.util.DateHelper;

/**
 * 后台管理员controller
 * @author yanghdx
 *
 */
@Before({AuthInterceptor.class, AdminMenuInterceptor.class})
public class AdminController extends Controller {

	//docService
	private DocService docService = enhance(DocService.class);
	
	/**
	 * admin管理首页
	 */
	public void index() {
		render("admin-index.html");
	}
	
	/**
	 * admin查看doc列表s
	 */
	public void docs() {
		
	}
	
	/**
	 * 添加doc页面
	 */
	public void docAdd() {
		createToken("addToken");
		setAttr("menus", CacheKit.get(WebConst.CacheKey.MENUS, "menuList", new IDataLoader() {
			public Object load() {
				//加载普通menu
				return (new MenuService()).loadMenus();
			}
		}));
		render("doc-add.html");
	}
	
	
	/**
	 * 添加doc
	 */
	@Before(DocAddValidator.class)
	public void doDocAdd() {
		UploadFile file = getFile("docFile");
		Doc doc = getModel(Doc.class);
		doc.set("upload_time", DateHelper.now())
			.set("doc_path", file.getFileName())
			.set("visible", 1);
		boolean result = docService.save(doc);
		if (result) {
			redirect("/admin/docAdd");
		} else {
			setAttr("errmsg", I18n.use().get("admin.add.doc.error"));
			render("doc-add.html");
		}
		
	}
}
