package com.tranphucvinh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Resource
public interface TagMapper {

	List<Map<String, Object>> selectTags();
	
	List<Map<String, Object>> selectTagsByPostId(String postId);
	
	Map<String,Object> selectTagById(String tagId);
	
	void insertIgnoreTag(Map<String, Object> tagPrm);

	void deleteTagsUnused();
}
