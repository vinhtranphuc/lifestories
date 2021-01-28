package com.tranphucvinh.controller.cms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cms/photo")
public class PhotoController {

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        return "cms/photo/list";
    }
}