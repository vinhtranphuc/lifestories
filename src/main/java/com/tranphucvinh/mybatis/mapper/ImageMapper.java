package com.tranphucvinh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

	List<Map<String, Object>> selectDecoratorImages();
	
	List<Map<String, Object>> selectImages(Map<String,Object> params);

	int selectImagesCnt(Map<String, Object> params);
}
