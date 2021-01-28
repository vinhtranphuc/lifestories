package com.tranphucvinh.payload;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public boolean isRememberMe;

    private String captcha;

    public String getUsername() {
        return StringUtils.lowerCase(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}