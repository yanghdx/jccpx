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
	 * @draw dataTable的绘制次数
	 * @param page
	 * @return
	 */
	public static Map<String, Object> toMap(int draw, Page<?> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page != null) {
			map.put("draw", draw);
			map.put("recordsTotal", page.getTotalRow());
			map.put("recordsFiltered", page.getTotalRow());
			map.put("data", page.getList());
		}
		return map;
	}
}
