package com.tranphucvinh.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.service.CategoryService;
import com.tranphucvinh.service.ImageService;

@Controller
@RequestMapping("/home/gallery")
public class GalleryController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String gallery(Model model) {
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("images",imageService.getImages());
        return "home/gallery/index";
    }
}
