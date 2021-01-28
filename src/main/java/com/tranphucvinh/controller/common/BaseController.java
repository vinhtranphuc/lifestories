package com.tranphucvinh.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.security.UserPrincipal;

@Controller
public class BaseController {

    @Value("${server.port}")
    protected String severPost;

//    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
//    public String base(Model model) {
//        return "redirect:/home";
//    }
    
    @RequestMapping(value = {"/cms","/cms/"}, method = RequestMethod.GET)
    public String mainCms(Model model) {
        return "redirect:/cms/overview";
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    
	public boolean isSupperAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			for (GrantedAuthority e : userPrincipal.getAuthorities()) {
				if (StringUtils.equals("ROLE_SUPPER_ADMIN", e.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
}
