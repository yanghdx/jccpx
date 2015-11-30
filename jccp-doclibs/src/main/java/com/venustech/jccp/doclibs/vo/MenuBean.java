package com.venustech.jccp.doclibs.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单bean
 * @author yanghdx
 *
 */
public class MenuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8913147875385288329L;

	private int menuId;
	
	private String menuName;
	
	private List<MenuBean> children;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<MenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBean> children) {
		this.children = children;
	}
	
	
}
