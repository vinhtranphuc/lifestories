package com.tranphucvinh.payload;

import java.util.List;

import javax.validation.constraints.Size;

public class PostForm {
	
	public String post_id;

	@Size(min=0,max=100,message = "Please enter at least 100 characters for the title!")
	public String title;
	
	public String category_id;
	public String content;
	public String level;
	public String summary;
	public String published_at;
	public List<String> thumbnailList;
	public List<String> tagList;
	public String hasImagesOntop;
	
	private Long user_id;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
}
