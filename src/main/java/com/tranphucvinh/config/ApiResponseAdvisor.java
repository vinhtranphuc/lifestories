package com.tranphucvinh.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.tranphucvinh.payload.Response;

@ControllerAdvice(basePackages = {"com.tranphucvinh.controller.home.api","com.tranphucvinh.controller.cms.api"})   // package where it will look for the controllers.
public class ApiResponseAdvisor<T> implements ResponseBodyAdvice<Object> {
	
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // get status from response
        ServletServerHttpResponse sshrs = (ServletServerHttpResponse) response;
        HttpServletResponse hsrs = sshrs.getServletResponse();
        int status = hsrs.getStatus();

        if (status == HttpStatus.INTERNAL_SERVER_ERROR.value() && body == null) {
            Response resBody = new Response();
            resBody.setMessage("A server error has occurred");
            return resBody;
        }

        if (body != null && body.getClass() == Response.class) {
            // set status to body
            ((Response) body).setStatus(hsrs.getStatus());
        }

        return body;
    }
}