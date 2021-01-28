package com.tranphucvinh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

	List<Map<String, Object>> selectCategories();
}
