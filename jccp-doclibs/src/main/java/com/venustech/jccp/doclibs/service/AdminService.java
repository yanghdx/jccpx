package com.venustech.jccp.doclibs.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.venustech.jccp.doclibs.model.Admin;

public class AdminService {

	public Admin findByName(String name) {
		List<Admin> list = Admin.me.find("select * from admin where user=? limit 1", name);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Admin getById(int id) {
		return Admin.me.findById(id);
	}
	
	@Before(Tx.class)
	public boolean update(Admin admin) {
		return admin.update();
	}
}
