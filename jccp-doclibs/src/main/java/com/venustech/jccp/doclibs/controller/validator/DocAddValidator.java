package com.venustech.jccp.doclibs.controller.validator;

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

public class DocAddValidator extends Validator {

	@Override
	protected void handleError(Controller c) {
		c.createToken("addToken");
		c.keepModel(Doc.class);
		c.setAttr("menus", CacheKit.get(WebConst.CacheKey.MENUS, "menuList", new IDataLoader() {
			public Object load() {
				//加载普通menu
				return (new MenuService()).loadMenus();
			}
		}));
		c.render("doc-add.html");
		
	}

	@Override
	protected void validate(Controller c) {
		Res res = I18n.use();
		//验证文件
		UploadFile file = c.getFile("docFile");//必须先调用getFile才能解析参数
		if (file == null) {
			this.addError("errmsg", res.format("error.empty",  res.get("file.upload")));
			return;
		}
		//验证参数
		validateToken("addToken", "errmsg",  res.get("common.token.error"));
		validateString("doc.doc_name", 1, 100, "errmsg", 
				res.format("error.length.range", res.get("doc.name"), 1, 100));
		validateInteger("doc.menu_id", "errmsg", 
				res.format("error.has.error", res.get("doc.type")));
		validateInteger("doc.type_id", "errmsg",
				res.format("error.has.error", res.get("doc.type")));
		
	}

}
