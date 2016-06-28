package com.venustech.jccp.doclibs.controller.validator;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.service.MenuService;

public class DocEditValidator extends Validator {

	private static final Logger logger = Logger.getLogger(DocEditValidator.class);
	
	@Override
	protected void handleError(Controller c) {
		c.createToken("editToken");
		c.keepModel(Doc.class);
		c.setAttr("menus", CacheKit.get(WebConst.CacheKey.MENUS, "menuList", new IDataLoader() {
			public Object load() {
				//加载普通menu
				return (new MenuService()).loadMenus();
			}
		}));
		c.render("doc-edit.html");
		
	}

	@Override
	protected void validate(Controller c) {
		Res res = I18n.use();
		//验证文件
		UploadFile file = c.getFile("docFile");//必须先调用getFile才能解析参数
		if (file == null) {
			logger.info("Edit doc, file is empty, use old!");
		} else {
			logger.info("Edit doc, upload new doc, use new!");
		}
		
		validateInteger("doc.id", 0, Integer.MAX_VALUE, "errmsg", "ID is wrong.");
			
		//验证参数
		validateToken("editToken", "errmsg",  res.get("common.token.error"));
		validateString("doc.doc_name", 1, 100, "errmsg", 
				res.format("error.length.range", res.get("doc.name"), 1, 100));
		validateRegex("type", "[0-9]+-[0-9]+", false, "errmsg", 
				res.format("error.has.error", res.get("doc.type")));
		
	}

}
