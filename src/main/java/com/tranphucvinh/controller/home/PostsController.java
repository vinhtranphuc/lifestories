package com.tranphucvinh.controller.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tranphucvinh.service.PostService;
import com.tranphucvinh.service.TagService;

@Controller
@RequestMapping("/home/posts")
public class PostsController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TagService tagService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/category", method = RequestMethod.GET)
    public String categoryPosts(@RequestParam(required = false) String categoryId, Model model, HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("categoryId",categoryId );
		params.put("page", 1);
		params.put("pageSize", 6);
		Map<String,Object> postsResult = postService.getHomePostList(params);
		if(Integer.parseInt(postsResult.get("total")+"") <1) {
			return "/home/error/404";
		}
		List<Map<String,Object>> list = (List<Map<String, Object>>) postsResult.get("list");
		model.addAttribute("categoryName", StringUtils.isEmpty(categoryId)?"All":list.get(0).get("category_name"));
		model.addAttribute("categoryId", StringUtils.isEmpty(categoryId)?"":categoryId);
		model.addAllAttributes(postsResult);
		
		String postFullUrl = "http://" + request.getHeader("host")+"/home/post/";
		model.addAttribute("postFullUrl",postFullUrl);
		
        return "home/posts/category";
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPosts(@RequestParam(required = false) String userId, Model model, HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId",userId);
		params.put("page", 1);
		params.put("pageSize", 6);
		Map<String,Object> postsResult = postService.getHomePostList(params);
		if(Integer.parseInt(postsResult.get("total")+"") <1) {
			return "/home/error/404";
		}
		List<Map<String,Object>> list = (List<Map<String, Object>>) postsResult.get("list");
		model.addAttribute("userName", StringUtils.isEmpty(userId)?"All":list.get(0).get("created_username"));
		model.addAttribute("userId", StringUtils.isEmpty(userId)?"":userId);
		model.addAllAttributes(postsResult);
		
		String postFullUrl = "http://" + request.getHeader("host")+"/home/post/";
		model.addAttribute("postFullUrl",postFullUrl);
		
        return "home/posts/user";
    }
	
	@RequestMapping(value = "/tag", method = RequestMethod.GET)
    public String tagPosts(@RequestParam(required = false) String tagId, Model model, HttpServletRequest request) {
		
		Map<String,Object> tag = tagService.getTagById(tagId);
		if(tag == null || tag.isEmpty()) {
			return "/home/error/404";
		}
		model.addAttribute("tagName", StringUtils.isEmpty(tagId)?"All":tag.get("tag"));
		model.addAttribute("tagId", StringUtils.isEmpty(tagId)?"":tagId);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tagId",tagId);
		params.put("page", 1);
		params.put("pageSize", 6);
		Map<String,Object> postsResult = postService.getHomePostList(params);
		if(Integer.parseInt(postsResult.get("total")+"") <1) {
			return "/home/error/404";
		}
		
		model.addAllAttributes(postsResult);
		String postFullUrl = "http://" + request.getHeader("host")+"/home/post/";
		model.addAttribute("postFullUrl",postFullUrl);
		
        return "home/posts/tag";
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchPosts(@RequestParam String key, Model model, HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("key",key);
		params.put("page", 1);
		params.put("pageSize", 6);
		Map<String,Object> postsResult = postService.getHomePostList(params);
		
		model.addAllAttributes(postsResult);
		String postFullUrl = "http://" + request.getHeader("host")+"/home/post/";
		model.addAttribute("postFullUrl",postFullUrl);
		model.addAttribute("key",key);
		
        return "home/posts/search";
    }
}
