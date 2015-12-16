package com.venustech.jccp.doclibs.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.venustech.jccp.doclibs.controller.interceptor.MenuInterceptor;
import com.venustech.jccp.doclibs.core.WebConst.PageConst;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.service.DocService;
import com.venustech.jccp.doclibs.service.MenuService;
import com.venustech.jccp.doclibs.util.DataTableHelper;

/**
 * 文档资料
 * @author yanghdx
 *
 */
public class DocController extends Controller {

	private DocService docService = enhance(DocService.class);
	
	private MenuService menuService = enhance(MenuService.class);
	
	@Before(MenuInterceptor.class)
	public void index() {
		setAttr("menu",    menuService.getById(getParaToInt(0,1)));
		setAttr("docType", docService.getDocTypeById(getParaToInt(1,1)));
		render("doc-index.html");
	}
	
	public void page() {
		Page<Doc> page = docService.find("", getParaToInt("menu_id",1), getParaToInt("doc_type_id",1),
				getParaToInt("draw", 1), getParaToInt("length", PageConst.PAGE_SIZE));
		renderJson(DataTableHelper.toMap(page));
	}
	
	public void download() {
		Doc doc = docService.getById(getParaToInt(0));
		if (doc != null) {
			doc.set("download_times", doc.getInt("download_times") + 1);
			docService.update(doc);
		}
		//TODO download
	}
}
