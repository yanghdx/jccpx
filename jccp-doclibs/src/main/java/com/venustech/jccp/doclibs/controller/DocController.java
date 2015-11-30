package com.venustech.jccp.doclibs.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.venustech.jccp.doclibs.core.WebConst.PageConst;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.service.DocService;

/**
 * 文档资料
 * @author yanghdx
 *
 */
public class DocController extends Controller {

	private DocService docService = enhance(DocService.class);
	
	public void index() {
		render("doc-index.html");
	}
	
	
	public void page() {
		Page<Doc> page = docService.find("", getParaToInt(0,1), getParaToInt(1,1),
				getParaToInt(2, 1), getParaToInt(3, PageConst.PAGE_SIZE));
		renderJson(page);
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
