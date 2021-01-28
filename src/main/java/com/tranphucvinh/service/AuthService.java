package com.tranphucvinh.service;

import nl.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tranphucvinh.common.BaseService;
import com.tranphucvinh.jpa.entity.Role;
import com.tranphucvinh.jpa.entity.RoleName;
import com.tranphucvinh.jpa.entity.User;
import com.tranphucvinh.jpa.repository.RoleRepository;
import com.tranphucvinh.jpa.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthService extends BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession httpSession;

//	public Optional<User> getUserByEmail(String email) {
//		return userRepository.findByEmailIgnoreCase(email);
//	}
//	
//	public boolean isExistsByEmail(String email) {
//		return userRepository.existsByEmail(email);
//	}

    public Optional<Role> getRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

	public boolean isAccountlDisabled(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.isPresent() && !user.get().isEnabled();
	}
//	
//	public boolean iAccountEnabled(String email) {
//		Optional<User> user = userRepository.findByEmail(email);
//		return user.isPresent() && user.get().isEnabled();
//	}

    public boolean isInvalidCaptcha(String captchaAnswer) {
        Object object = httpSession.getAttribute("captcha");
        Captcha captcha = (object instanceof Captcha) ? (Captcha) object : null;
        return !captcha.getAnswer().equals(captchaAnswer);
    }
}
