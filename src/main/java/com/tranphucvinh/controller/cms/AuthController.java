package com.tranphucvinh.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.controller.common.BaseController;
import com.tranphucvinh.security.capcha.CaptchaGenerator;
import com.tranphucvinh.security.capcha.CaptchaUtils;

@Controller
@RequestMapping("/cms/auth")
public class AuthController extends BaseController {
	
	@Autowired
    private CaptchaGenerator captchaGenerator;
	  
    protected Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String base(Model model) {
        return "redirect:/auth/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        if (isAuthenticated()) {
            return "redirect:/cms/main";
        }
        int failedLoginCnt = CaptchaUtils.getFaiedLoginCnt(request);
        if (failedLoginCnt > 3) {
            model.addAttribute("captchaEncode", CaptchaUtils.getNewCaptchaImg(captchaGenerator, request));
            model.addAttribute("isShowCaptcha", true);
        }
        return "/cms/auth/login";
    }
}