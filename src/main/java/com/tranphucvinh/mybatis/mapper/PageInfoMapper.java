package com.tranphucvinh.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tranphucvinh.mybatis.model.SocialVO;
import com.tranphucvinh.payload.SocialForm;

@Mapper
public interface PageInfoMapper {
	
	public SocialVO selectSocials();
	
	void saveSocials(SocialForm socialForm);
	
	public Map<String,Object> selectAboutUs();
	
	public Map<String,Object> selectContactUs();

	public void updateAboutUs(Map<String, Object> params);
	
	public void updateContactUs(Map<String, Object> params);
}
