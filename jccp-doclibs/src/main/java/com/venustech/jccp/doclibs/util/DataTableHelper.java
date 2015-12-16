package com.venustech.jccp.doclibs.util;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;

/**
 * DataTable Helper
 * @author yanghdx
 *
 */
public class DataTableHelper {

	
	/**
	 * 转化为DataTable需要的格式
	 * @param page
	 * @return
	 */
	public static Map<String, Object> toMap(Page<?> page) {
		Map<String, Object> json = new HashMap<String, Object>();
		if (page != null) {
			json.put("draw", page.getPageNumber());
			json.put("recordsTotal", page.getTotalRow());
			json.put("recordsFiltered", page.getTotalRow());
			json.put("data", page.getList());
		}
		return json;
	}
}
