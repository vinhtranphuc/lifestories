package com.tranphucvinh.controller.cms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.common.BaseService;
import com.tranphucvinh.security.UserPrincipal;

@Controller
@RequestMapping("/cms/user")
public class UserController {

    protected Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String setting(Model model) {
        UserPrincipal userPrincipal = BaseService.getCurrentUser();
        model.addAttribute("user_id", userPrincipal.getId());
        model.addAttribute("username", userPrincipal.getUsername());
        model.addAttribute("full_name", userPrincipal.getFullName());
        model.addAttribute("avatar_img", userPrincipal.getAvatarImg());
        model.addAttribute("summary", userPrincipal.getSummary());
        
        boolean isSupperAdmin = false;
        for (GrantedAuthority e : userPrincipal.getAuthorities()) {
            if (StringUtils.equals("ROLE_SUPPER_ADMIN", e.getAuthority())) {
                isSupperAdmin = true;
                break;
            }
        }
        model.addAttribute("isSupperAdmin", isSupperAdmin);
        return "/cms/user/setting";
    }

}
