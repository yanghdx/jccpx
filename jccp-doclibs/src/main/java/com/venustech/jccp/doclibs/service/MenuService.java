package com.venustech.jccp.doclibs.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.venustech.jccp.doclibs.core.WebConst;
import com.venustech.jccp.doclibs.model.DocType;
import com.venustech.jccp.doclibs.model.Menu;
import com.venustech.jccp.doclibs.model.MenuDocType;
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
	
	public Menu getById(int menuId) {
		return Menu.me.findFirstByCache(WebConst.CacheKey.MENUS, "menu-" + menuId, 
				"select * from menu where id="+menuId);
	}
	
	public boolean add(Menu menu) {
		return menu.save();
	}
	
	public boolean update(Menu menu) {
		return menu.update();
	}
	
	public boolean deleteById(int menuId) {
		return Db.deleteById("menu", menuId);
	}
	
	public boolean hasChildren(int menuId) {
		List<Menu> list = Menu.me.find("select * from menu m where m.parent_id="+menuId);
		return list != null && list.size() > 0;
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
	
	
	/**
	 * @return
	 */
	public List<MenuBean> loadMenusByCache() {
		List<MenuBean> result = CacheKit.get(WebConst.CacheKey.MENUS, "menuList");
		if (result == null) {
			result = this.loadMenus();
			CacheKit.put(WebConst.CacheKey.MENUS, "menuList", result);
		}
		return result;
	}
	
	
	/**
	 * @return
	 */
	public List<MenuBean> loadAdminMenusByCache() {
		List<MenuBean> result = CacheKit.get(WebConst.CacheKey.MENUS, "adminMenuList");
		if (result == null) {
			result = this.loadAdminMenus();
			CacheKit.put(WebConst.CacheKey.MENUS, "adminMenuList", result);
		}
		return result;
	}
	
	public DocType getDocTypeById(int docTypeId) {
		return DocType.me.findById(docTypeId);
	}
	
	@Before(Tx.class)
	public boolean deleteDocTypeById(int docTypeId) {
		Db.deleteById("doc_type", docTypeId);
		Db.update("delete from menu_doc_type where type_id="+docTypeId);
		return true;
	}
	
	public boolean updateDocType(DocType docType) {
		return docType.update();
	}
	
	public boolean addDocType(int menuId, DocType docType) {
		//
		docType.save();
		//
		String re = Db.queryStr("select max(type_order) from menu_doc_type where menu_id="+menuId);
		
		Integer max = re == null ? null : Integer.parseInt(re);
		MenuDocType mdt = new MenuDocType();
		mdt.set("menu_id", menuId);
		mdt.set("type_id", docType.getInt("id"));
		mdt.set("type_order", max == null ? 1 : max+1);
		mdt.save();
		return true;
	}
}
