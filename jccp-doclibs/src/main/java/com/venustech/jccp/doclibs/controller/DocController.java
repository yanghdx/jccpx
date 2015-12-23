package com.venustech.jccp.doclibs.controller;

import java.io.File;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.venustech.jccp.doclibs.controller.interceptor.MenuInterceptor;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.core.WebConst.PageConst;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.service.DocService;
import com.venustech.jccp.doclibs.service.MenuService;
import com.venustech.jccp.doclibs.util.DataTableHelper;
import com.venustech.jccp.doclibs.util.HtmlKit;

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
		//查询关键字
		String kw = HtmlKit.encode(getPara("headerSearch"));
		setAttr("kw", kw);
		render("doc-index.html");
	}
	
	public void page() {
		int length = getParaToInt("length", PageConst.PAGE_SIZE);
		Page<Record> page = docService.find(getPara("kw"), getParaToInt("menu_id",1), getParaToInt("doc_type_id",1),
				getParaToInt("start", 0) / length + 1, length);
		renderJson(DataTableHelper.toMap(getParaToInt("draw", 1), page));
	}
	
	public void download() {
		Doc doc = docService.getById(getParaToInt());
		if (doc != null) {
			String filePath = PathKit.getWebRootPath() + 
					File.separator + WebConst.Upload.PATH + File.separator + doc.getStr("doc_path");
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				//update upload count
				doc.set("download_count", doc.getInt("download_count") + 1);
				docService.update(doc);
				//download file
				renderFile(file);
				return;
			} 
		} 
		renderText(I18n.use().get("error.file.not.found"));
		
	}
	
	public void view() {
		Doc doc = docService.getById(getParaToInt());
		if (doc != null) {
			String filePath = PathKit.getWebRootPath() + 
					File.separator + WebConst.Upload.PATH + File.separator + doc.getStr("doc_path");
			if (!filePath.toLowerCase().endsWith(".pdf")) {
				renderText(I18n.use().get("error.file.not.pdf"));
				return;
			} else {
				File file = new File(filePath);
				if (file.exists() && file.isFile()) {
					setAttr("fileName", doc.getStr("doc_name"));
					setAttr("path", "/" + WebConst.Upload.PATH + "/" + doc.getStr("doc_path"));
					render("doc-view.html");
					return;
				} 
			}
			
		} 
		renderText(I18n.use().get("error.file.not.found"));
		
	}
	
//	private void viewWord(String filePath) {
//		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
//		String bodyText = null;
//		WordExtractor ex = new WordExtractor(bis);
//		bodyText = ex.getText();
//		getResponse().getWriter().write(bodyText);
//	}
}
