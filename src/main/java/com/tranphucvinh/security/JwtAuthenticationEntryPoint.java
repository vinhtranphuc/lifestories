package com.tranphucvinh.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("Responding with unauthorized error. Message - {}", "(" + httpServletRequest.getRequestURL() + ") " + e.getMessage());
        
        StringBuilder requestURL = new StringBuilder(httpServletRequest.getRequestURL().toString());
        if(!Pattern.matches(".*/intro.*", requestURL) && !Pattern.matches(".*/cms.*", requestURL)) {
    		httpServletResponse.sendRedirect("/intro");
    		return;
    	}
        httpServletResponse.sendRedirect("/cms/auth/login");
    }
}