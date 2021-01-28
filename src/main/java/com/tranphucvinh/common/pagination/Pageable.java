package com.tranphucvinh.common.pagination;

import java.util.List;
import java.util.Map;

public interface Pageable {
	
	public int getTotalList(Map<String,Object> params);
	
	public List<Map<String,Object>> getList(Map<String,Object> params);
	
	public Map<String,Object> getListPaginated(Map<String,Object> params);
	
	public void customizeList(List<Map<String,Object>> list);
}
