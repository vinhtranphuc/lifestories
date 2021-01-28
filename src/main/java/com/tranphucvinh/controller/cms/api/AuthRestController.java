package com.tranphucvinh.controller.cms.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.common.BaseService;
import com.tranphucvinh.common.CookieUtils;
import com.tranphucvinh.exception.BusinessException;
import com.tranphucvinh.payload.LoginRequest;
import com.tranphucvinh.payload.Response;
import com.tranphucvinh.security.JwtTokenProvider;
import com.tranphucvinh.security.UserPrincipal;
import com.tranphucvinh.security.capcha.CaptchaGenerator;
import com.tranphucvinh.security.capcha.CaptchaUtils;
import com.tranphucvinh.service.AuthService;

@RestController
@RequestMapping("/cms/api/auth")
public class AuthRestController {
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    HttpSession httpSession;
    
	@Autowired
    private CaptchaGenerator captchaGenerator;

    protected Logger logger = LoggerFactory.getLogger(AuthRestController.class);
    
	@PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) {

        try {
            int failedLoginCnt = CaptchaUtils.getFaiedLoginCnt(request);
            if (failedLoginCnt > 3) {

                Map<String, Object> result = new HashMap<String, Object>();

                String message = "";

                if (StringUtils.isEmpty(loginRequest.getCaptcha())) {
                    message = "Please enther the captcha!";
                } else if (CaptchaUtils.isInvalidCaptcha(loginRequest.getCaptcha(), request)) {
                    message = "Captcha does not match!";
                }

                if (StringUtils.isNotEmpty(message)) {
                    result.put("signal", "createCaptcha");
                    result.put("captchaEncode", CaptchaUtils.getNewCaptchaImg(captchaGenerator, request));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(result, message));
                }
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CaptchaUtils.clearFaiedLoginCnt(request);

			if (authService.isAccountlDisabled(loginRequest.getUsername())) {
				SecurityContextHolder.clearContext();
				HttpSession session = request.getSession(false);
                if (session != null)
                    session.removeAttribute("Authorization");
                CookieUtils.clear(response, "JSESSIONID");
                CookieUtils.clear(response, "remember-me");
				throw new BusinessException("Your account has been locked, please contact admin !");
			}

            if (loginRequest.isRememberMe() == true) {
                // remember 30 days
                String cookieJwt = tokenProvider.generateTokenExp(authentication, 30);
                CookieUtils.addCookieJwtRememberMe(response, "remember-me", "Bearer " + cookieJwt, 2592000); // 30 days to seconds
            } else {
                CookieUtils.clear(response, "remember-me");
                String jwt = tokenProvider.generateTokenPrivate(authentication);
                httpSession.setAttribute("Authorization", "Bearer " + jwt);
            }

            return ResponseEntity.ok(new Response(null, "You are successfully logged in"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(null, e.getMessage()));
        } catch (Exception e) {

            if (e instanceof BadCredentialsException) {

                int failedLoginCnt = CaptchaUtils.getFaiedLoginCnt(request);
                CaptchaUtils.incrementFaiedLoginCnt(failedLoginCnt, request);

                Map<String, Object> result = new HashMap<String, Object>();
                if (failedLoginCnt >= 3) {
                    result.put("signal", "createCaptcha");
                    result.put("captchaEncode", CaptchaUtils.getNewCaptchaImg(captchaGenerator, request));
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response(result, "Username or password is incorrect. !"));
            } else {
                logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("current-user")
    public ResponseEntity<Response> currentUser() {
        try {
            UserPrincipal userPrincipal = BaseService.getCurrentUser();

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("user_id", userPrincipal.getId());
            result.put("full_name", userPrincipal.getFullName());
            String imageName = StringUtils.isNotEmpty(userPrincipal.getAvatarImg()) ? userPrincipal.getAvatarImg() : "DEFAULT.png";
            result.put("avatar_url", "/image/user-avatar/" + imageName + "/private");

            return ResponseEntity.ok().body(new Response(result, "Get current user successfully !"));
        } catch (Exception e) {
            logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
