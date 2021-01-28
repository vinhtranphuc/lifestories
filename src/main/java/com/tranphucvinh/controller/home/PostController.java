package com.tranphucvinh.controller.home;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.service.PostService;

@Controller("homePost")
@RequestMapping("/home/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String post(@PathVariable Map<String,Object> params,Model model) {
		Map<String,Object> post = postService.getPostById(params, true);
		if(post == null || post.isEmpty()) {
			return "/home/error/404";
		}
		String userAvatar = post.get("post_user_avatar")+"";
		if(userAvatar.equals("null") || StringUtils.isEmpty(userAvatar)) {
			post.put("post_user_avatar", "/store/common/avatar-default.png");
		}
		model.addAllAttributes(post);
		model.addAttribute("isPost", true);
		
		Object likeSession = httpSession.getAttribute("likeSession");
		model.addAttribute("likeSession", likeSession == null?"like":likeSession);
		
		List<Map<String,Object>> suggestPosts = postService.getSuggestPosts(post);
		model.addAttribute("suggestPosts",suggestPosts);
		
        return "home/post/index";
    }
}
