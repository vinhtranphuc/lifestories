package com.tranphucvinh.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.service.PostService;

@Controller
@RequestMapping(value = {"", "/","/home"})
public class IndexController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(Model model) {
		model.addAttribute("topPosts", postService.getTopPosts());
		model.addAttribute("lifePosts", postService.getLifePosts());
		model.addAttribute("travelPosts", postService.getTravelPosts());
		model.addAttribute("initMorePosts", postService.getInitMorePosts());
        return "home/index";
    }
}
