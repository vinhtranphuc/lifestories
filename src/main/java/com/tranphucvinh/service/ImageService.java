package com.tranphucvinh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tranphucvinh.common.pagination.AbstractPagination;
import com.tranphucvinh.mybatis.mapper.ImageMapper;

@Service
public class ImageService {
	
	@Resource
	private ImageMapper imageMapper;

	public List<Map<String,Object>> getGallery() {
		return imageMapper.selectDecoratorImages();
	}
	
	public List<Map<String,Object>> getImages() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startList", 0+"");
		params.put("pageSize", 20+"");
		return imageMapper.selectImages(params);
	}
	
	public Map<String,Object> getImages(Map<String,Object> params) {
		AbstractPagination paginator = new AbstractPagination() {
			@Override
			public int getTotalList(Map<String, Object> params) {
				return imageMapper.selectImagesCnt(params);
			}
			@Override
			public List<Map<String, Object>> getList(Map<String, Object> params) {
				return imageMapper.selectImages(params);
			}
			@Override
			public void customizeList(List<Map<String, Object>> list) {
			}
		};
		return paginator.getListPaginated(params);
	}
}
