package com.tranphucvinh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tranphucvinh.common.Const;
import com.tranphucvinh.common.FileUtils;
import com.tranphucvinh.mybatis.mapper.PageInfoMapper;
import com.tranphucvinh.mybatis.mapper.UserMapper;
import com.tranphucvinh.mybatis.model.SocialVO;
import com.tranphucvinh.payload.SocialForm;

@Service
@Component
public class PageInfoService {
	
	@Autowired
	private FileUtils fileUtils;
	
	@Resource
	private PageInfoMapper pageInfoMapper;
	
	@Resource
	private UserMapper userMapper;
	
	public Map<String,Object> getAboutUs() {
		return pageInfoMapper.selectAboutUs();
	}
	
	public Map<String,Object> getContactUs() {
		return pageInfoMapper.selectContactUs();
	}
	
	public SocialVO getSocials() {
		return pageInfoMapper.selectSocials();
	}
	
	public void saveSocials(SocialForm socialForm) {
		pageInfoMapper.saveSocials(socialForm);
	}
	
	public Map<String,Object> getSupperAdminInfo() {
		return userMapper.selectSupperAdminInfo();
	}
	
	public void editAboutUs(Map<String,Object> params) throws IOException {
		params.put("about_us", convertContentImg(params.get("about_us")+"",Const.ABOUT_US_DIR));
		pageInfoMapper.updateAboutUs(params);
	}
	
	public void editContactUs(Map<String, Object> params) throws IOException {
		params.put("contact_us", convertContentImg(params.get("contact_us")+"",Const.CONTACT_US_DIR));
		pageInfoMapper.updateContactUs(params);
	}
	
	private String convertContentImg(String content, String uploadDir) throws IOException {
		Document doc = Jsoup.parse(content, "UTF-8");
		List<String> filePathList = new ArrayList<String>();
		for (Element element : doc.select("img")) {
			String src = element.attr("src");
			if (StringUtils.isEmpty(src))
				continue;
			String filePath = "";
			if (src.startsWith("data:")) {
				filePath = fileUtils.uploadBase64File(src, "", uploadDir);
			} else {
				filePath = src;
			}
			element.attr("src", filePath);
			filePathList.add(filePath);
		}
		fileUtils.synchronizeFilesToData(true, uploadDir, filePathList);
		return doc.body().children().toString();
	}
}
