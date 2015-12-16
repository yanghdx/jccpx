package com.venustech.jccp.doclibs.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.model.Doc;
import com.venustech.jccp.doclibs.model.DocType;

public class DocService {

	public Doc getById(int id) {
		return Doc.me.findById(id);
	}
	
	public DocType getDocTypeById(int typeId) {
		return DocType.me.findFirstByCache(WebConst.CacheKey.DOCS, "docType-"+typeId, 
				"select * from doc_type where id="+typeId);
	}
	
	@Before(Tx.class)
	public boolean save(Doc doc) {
		return doc.save();
	}
	
	@Before(Tx.class)
	public boolean update(Doc doc) {
		return doc.update();
	}
	
	@Before(Tx.class)
	public boolean deleteById(int id) {
		return Db.deleteById("doc", id);
	}
	
	public Page<Doc> find(String docName, int menuId, int docTypeId, int pageNumber, int pageSize) {
		return Doc.me.paginate(pageNumber, pageSize, 
				"select * ", "from doc d where d.menu_id=? and d.type_id=? order by upload_time desc", menuId, docTypeId);
	}
	
}
