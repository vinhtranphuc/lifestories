package com.tranphucvinh.common.pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tranphucvinh.common.Utils;

public abstract class AbstractPagination implements Pageable{
	
	@Override
	public Map<String, Object> getListPaginated(Map<String,Object> params) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		String page = params.get("page")+"";
		String pageSize = params.get("pageSize")+"";
		
		int total = getTotalList(params);
		
		List<Map<String,Object>> list;
		if(Utils.isInteger(page) && Utils.isInteger(pageSize)) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			
			if(pageSizeInt < 1 || pageInt < 1) {
				return result;
			}
			
			int startList = (pageInt-1)*pageSizeInt;
			params.put("startList", startList+"");
			list = getList(params);
			
			int lastPage = list.size()<1?1:(total/pageSizeInt + ((total%pageSizeInt)>0?1:0));
			
			result.put("lastPage", lastPage);
			result.putAll(params);
		} else {
			list = getList(params);
			result.put("startList", 0);
			result.put("lastPage", 1);
			result.put("page", 1);
			result.put("pageSize", total);
		}

		customizeList(list);
		
		result.put("list", list);
		result.put("isLast", Integer.parseInt(result.get("lastPage")+"") == Integer.parseInt(result.get("page")+""));
		result.put("total",total);
		return result;
	}
}
