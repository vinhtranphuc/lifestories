package com.tranphucvinh.controller.cms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cms/tag")
public class TagController {

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        return "cms/tag/list";
    }
}
