package com.tranphucvinh.controller.home.api;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.payload.Response;
import com.tranphucvinh.service.PostService;

@RestController
@RequestMapping("/home/api/posts")
public class PostsRestController {
	
	protected Logger logger = LoggerFactory.getLogger(PostsRestController.class);
	
	@Autowired
	private PostService postService;

	@GetMapping("")
    public ResponseEntity<Response> getPosts(@RequestParam Map<String,Object> params) {
        try {
            Map<String,Object> result = postService.getHomePostList(params);
            return ResponseEntity.ok().body(new Response(result, "Get list ok !"));
        } catch (Exception e) {
            logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
