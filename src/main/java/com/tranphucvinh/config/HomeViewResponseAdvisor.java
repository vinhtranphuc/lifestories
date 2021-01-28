package com.tranphucvinh.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tranphucvinh.controller.common.AppErrorController;
import com.tranphucvinh.controller.home.AboutUsController;
import com.tranphucvinh.controller.home.ContactUsController;
import com.tranphucvinh.controller.home.GalleryController;
import com.tranphucvinh.controller.home.IndexController;
import com.tranphucvinh.controller.home.PostController;
import com.tranphucvinh.controller.home.PostsController;
import com.tranphucvinh.mybatis.model.SocialVO;
import com.tranphucvinh.service.CategoryService;
import com.tranphucvinh.service.ImageService;
import com.tranphucvinh.service.PageInfoService;
import com.tranphucvinh.service.PostService;

@ControllerAdvice(assignableTypes = { IndexController.class, PostController.class, GalleryController.class,
		AboutUsController.class, ContactUsController.class, PostsController.class, AppErrorController.class })
public class HomeViewResponseAdvisor {
	
	@Resource
	private PageInfoService pageInfoService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ImageService imageService;
	
	@ModelAttribute("socials")
	public SocialVO socials() {
		return pageInfoService.getSocials();
	}
	
	@ModelAttribute("postsInit")
	public Map<String,Object> postsInit() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 4);
		return postService.getHomePostList(params);
	}
	
	@ModelAttribute("lifePostsInit")
	public Map<String,Object> lifePostsInit() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 4);
		params.put("categoryId", 1);
		return postService.getHomePostList(params);
	}
	
	@ModelAttribute("photographyPostsInit")
	public Map<String,Object> photographyPostsInit() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 4);
		params.put("categoryId", 2);
		return postService.getHomePostList(params);
	}

	@ModelAttribute("storiesPostsInit")
	public Map<String,Object> storiesPostsInit() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 4);
		params.put("categoryId", 3);
		return postService.getHomePostList(params);
	}
	
	@ModelAttribute("travelPostsInit")
	public Map<String,Object> travelPostsInit() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 5);
		params.put("categoryId", 4);
		Map<String,Object> result = postService.getHomePostList(params);
		return result;
	}
	
	@ModelAttribute("recentPosts")
	public Map<String,Object> recentPost() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", 1);
		params.put("pageSize", 6);
		Map<String,Object> result = postService.getHomePostList(params);
		return result;
	}
	
	@ModelAttribute("categories")
	public List<Map<String,Object>> categories() {
		return categoryService.getCategories();
	}
	
	@ModelAttribute("popularPosts")
	public List<Map<String,Object>> popularPosts() {
		return postService.getPopularPosts();
	}
	
	@ModelAttribute("supperAdminInfo")
	public Map<String,Object> supperAdminInfo() {
		return pageInfoService.getSupperAdminInfo();
	}
	
	@ModelAttribute("gallery")
	public List<Map<String,Object>> gallery() {
		return imageService.getGallery();
	}
}