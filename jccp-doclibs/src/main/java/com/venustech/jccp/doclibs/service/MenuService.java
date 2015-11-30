package com.venustech.jccp.doclibs.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.venustech.jccp.doclibs.model.Menu;
import com.venustech.jccp.doclibs.vo.MenuBean;

/**
 * MenuService
 * @author yanghdx
 *
 */
public class MenuService {

	public List<Menu> findAll() {
		return Menu.me.find("select * from menu order by menu_order asc ");
	}
	
	/**
	 * 根据菜单、文档类型生成前台的菜单
	 * @return
	 */
	public List<MenuBean> getMenuBeanList() {
		final List<Menu> menus = findAll();
		final List<MenuBean> result = new ArrayList<MenuBean>();
		if (menus != null && !menus.isEmpty()) {
			final String sql = "select dt.id, dt.type_name from doc_type dt, menu_doc_type mdt "
					+ "where dt.id=mdt.type_id and mdt.menu_id=? "
					+ "order by mdt.menu_id asc, mdt.type_order asc ";
			for (Menu m : menus) {
				MenuBean mb = new MenuBean();
				mb.setMenuId(m.getInt("id"));
				mb.setMenuName(m.getStr("menu_name"));
				mb.setChildren(new ArrayList<MenuBean>());
				
				final List<Record> docTypes = Db.find(sql, mb.getMenuId());
				if (docTypes != null && !docTypes.isEmpty()) {
					for (Record r : docTypes) {
						MenuBean c = new MenuBean();
						c.setMenuId(r.getInt("id"));
						c.setMenuName(r.getStr("type_name"));
						mb.getChildren().add(c);
					}
				}
				result.add(mb);
			}
			
		}
		return result;
	}
}
