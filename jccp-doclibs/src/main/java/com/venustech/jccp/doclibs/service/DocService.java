package com.venustech.jccp.doclibs.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.venustech.jccp.doclibs.model.Doc;

public class DocService {

	public Doc getById(int id) {
		return Doc.me.findById(id);
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
	
	public Page<Doc> find(int pageNumber, int pageSize) {
		return Doc.me.paginate(pageNumber, pageSize, 
				"select * ", "from doc order by create_time desc");
	}
	
}
