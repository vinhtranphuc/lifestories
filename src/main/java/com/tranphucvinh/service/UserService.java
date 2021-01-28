package com.tranphucvinh.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tranphucvinh.common.Const;
import com.tranphucvinh.common.FileUtils;
import com.tranphucvinh.exception.BusinessException;
import com.tranphucvinh.jpa.entity.AuthProvider;
import com.tranphucvinh.jpa.repository.UserRepository;
import com.tranphucvinh.mybatis.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileUtils fileUtils;

    @Resource
    UserMapper userMapper;
    
    public void changeProfile(Map<String, Object> params, String userNameCrr) throws BusinessException, UnsupportedEncodingException {
    	
    	validateAccountInfo(params,userNameCrr);
        
    	String userName = (String) params.get("username");
        params.put("username", StringUtils.lowerCase(userName));
        
        String avatarImg = (String) params.get("avatarImg");
        String avatarImgName = (String) params.get("avatarImgName");

        String avatarFullPath = "";
        if (StringUtils.isNotEmpty(avatarImg)) {
            if (avatarImg.startsWith("data:")) {
                avatarFullPath = fileUtils.uploadBase64File(avatarImg, avatarImgName, Const.getUserAvatarDir(userName));
            } else {
                avatarFullPath = avatarImg;
            }
        }
        params.put("avatar_img", avatarFullPath);

        userMapper.updateProfile(params);
    }

    public void changePassword(Map<String, Object> params, String username) throws BusinessException {
    	String newPassword = validatePassword(params);
        params.put("new_password", passwordEncoder.encode(newPassword));
        params.put("username", username);
        userMapper.updatePasswordByUsername(params);
    }

    public void createAccount(Map<String, Object> params) throws BusinessException {
    	
    	validateAccountInfo(params,"");
    	
    	String newPassword = validateNewPassword(params,"");

        params.put("new_password", passwordEncoder.encode(newPassword));
        params.put("provider", AuthProvider.local);
        userMapper.insertUser(params);

        params.put("role_id", 2); // admin
        userMapper.insertUserRoles(params);
    }

    public <T> List<T> getUserList() {
        return userMapper.selectUserList();
    }

    public <T> void editAccount(Map<String, Object> params) throws BusinessException {

    	validateFullName(params);
    	validateUsername(params,(String) params.get("userNameSaved"));

        if (StringUtils.isNotEmpty((String) params.get("new_password"))) {
            if (!StringUtils.equals((String) params.get("new_password"), (String) params.get("confirm_password"))) {
                throw new BusinessException("Passwords do not match, please try again!");
            }
            params.put("new_password", passwordEncoder.encode((String) params.get("new_password")));
            userMapper.updatePasswordByUsername(params);
        }
        params.put("enabled", Integer.parseInt((String) params.get("enabled")));
        userMapper.updateUser(params);
    }

    public void deleteUsers(Map<String, Object> params) {
        userMapper.deleteUserRoles(params);
        userMapper.deleteUsers(params);
    }
    
    private void validateAccountInfo(Map<String,Object> params, String userNameCrr) throws BusinessException {
    	validateFullName(params);
    	validateUsername(params,userNameCrr);
    }
    
    private void validateFullName(Map<String,Object> params) throws BusinessException {
    	String fullName = (String) params.get("full_name");
    	if(fullName.length() > 32) 
    		throw new BusinessException("Please enter a name of 32 characters or less!");
    	if(isContainSpecialChar(fullName)) {
    		throw new BusinessException("Please enter only letters for the name!");
    	}
    }
    
    private void validateUsername(Map<String,Object> params, String userNameCrr) throws BusinessException {
    	String userName = (String) params.get("username");
    	if(StringUtils.isEmpty(userName)) 
    		throw new BusinessException("Please enter your username");
        if(userName.contains(" "))
        	throw new BusinessException("Please enter your username without spaces!");
        if((userName.length() > 16 || userName.length() < 8) || isAlphaNumberic(userName,true))
        	throw new BusinessException("Please enter your username within 8~16 characters! (Letters, numbers, ‘-’/ ‘_’ can be used)");
        if ((!StringUtils.equals(userName, userNameCrr)) && userRepository.findByUsername(userName).isPresent()) {
            throw new BusinessException("Your username already exists!");
        }
    }
    
    private String validatePassword(Map<String,Object> params) throws BusinessException {
    	String crrPassword = (String) params.get("crrPassword");
        String checkPassword = (String) params.get("password");
        if (!passwordEncoder.matches(checkPassword, crrPassword)) {
            throw new BusinessException("The current password is not correct! Please try again!");
        }
        String newPassword = validateNewPassword(params, crrPassword);
        return newPassword;
    }
    
    private String validateNewPassword(Map<String,Object> params, String crrPassword) throws BusinessException {
    	String newPassword = (String) params.get("new_password");
        if (!StringUtils.equals(newPassword, (String) params.get("confirm_password"))) {
            throw new BusinessException("Passwords do not match, please try again!");
        }
        if (passwordEncoder.matches(newPassword, crrPassword)) {
            throw new BusinessException("That password has already been used!");
        }
        if(!((newPassword.length() <= 16 && newPassword.length() >= 8) && isContainsAlphaNumberic(newPassword) && isAlphaNumberic(newPassword,false))) {
        	throw new BusinessException("Please enter your password within 8 to 16 characters including letters, numbers, and special characters!");
        }
        return newPassword;
    }
    
    private static boolean isContainsAlphaNumberic(String str) {
    	if(str==null)
    		return false;
    	str = str.toLowerCase();
    	String n = ".*[0-9].*";
        String a = ".*[a-z].*";
        return str.matches(n) && str.matches(a);
    }
    
    private static boolean isAlphaNumberic(String str,boolean isUsername) {
    	if(isUsername) {
    		str = str.replace("-", "").replace("_", "");
    	}
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
    
    public boolean isContainSpecialChar(String str){
    	Pattern pattern = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    	Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
