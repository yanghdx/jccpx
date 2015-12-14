package com.venustech.jccp.doclibs.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.model.Menu;
import com.venustech.jccp.doclibs.vo.MenuBean;

/**
 * MenuService
 * @author yanghdx
 *
 */
public class MenuService {

	public List<Menu> findByMenuType(int menuType) {
		return Menu.me.find("select * from menu m where m.menu_type=? order by menu_order asc", menuType);
	}
	
	/**
	 * 根据菜单、文档类型生成前台的菜单
	 * @return
	 */
	public List<MenuBean> loadMenus() {
		final List<Menu> menus = findByMenuType(WebConst.MenuType.COMMON_MENU);
		final List<MenuBean> result = new ArrayList<MenuBean>();
		if (menus != null && !menus.isEmpty()) {
			final String sql = "select dt.id, dt.type_name from doc_type dt, menu_doc_type mdt "
					+ "where dt.id=mdt.type_id and mdt.menu_id=? "
					+ "order by mdt.menu_id asc, mdt.type_order asc ";
			for (Menu m : menus) {
				MenuBean mb = new MenuBean();
				mb.setMenuId(m.getInt("id"));
				mb.setMenuName(m.getStr("menu_name"));
				mb.setFuncUrl("");
				mb.setChildren(new ArrayList<MenuBean>());
				
				final List<Record> docTypes = Db.find(sql, mb.getMenuId());
				if (docTypes != null && !docTypes.isEmpty()) {
					for (Record r : docTypes) {
						MenuBean c = new MenuBean();
						c.setMenuId(r.getInt("id"));
						c.setMenuName(r.getStr("type_name"));
						mb.setFuncUrl("");
						mb.getChildren().add(c);
					}
				}
				result.add(mb);
			}
			
		}
		return result;
	}
	
	/**
	 * 获取系统管理员的菜单
	 * @return
	 */
	public List<MenuBean> loadAdminMenus() {
		final List<Menu> menus = this.findByMenuType(WebConst.MenuType.ADMIN_MENU);
		final List<MenuBean> result = new ArrayList<MenuBean>();
		//只有两级菜单
		if (menus != null && !menus.isEmpty()) {
			//先添加一级菜单
			for (Menu menu : menus) {
				if (menu.getInt("parent_id") == 0) {
					MenuBean mb = new MenuBean();
					mb.setMenuId(menu.getInt("id"));
					mb.setMenuName(menu.getStr("menu_name"));
					mb.setFuncUrl("");
					mb.setChildren(new ArrayList<MenuBean>());
					result.add(mb);
				}
			}
			//再添加二级
			for (Menu menu2 : menus) {
				int parentId = menu2.getInt("parent_id");
				if (parentId > 0) {
					MenuBean mb = new MenuBean();
					mb.setMenuId(menu2.getInt("id"));
					mb.setMenuName(menu2.getStr("menu_name"));
					mb.setFuncUrl(menu2.getStr("func_url"));
					for (MenuBean _mb : result) {
						if (_mb.getMenuId() == parentId) {
							_mb.getChildren().add(mb);
							break;
						}
					}
				}
			}
		}
		
		return result;
	}
}
