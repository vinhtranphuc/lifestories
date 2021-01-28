package com.tranphucvinh.controller.cms.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.common.BaseService;
import com.tranphucvinh.controller.common.BaseController;
import com.tranphucvinh.payload.PostForm;
import com.tranphucvinh.payload.Response;
import com.tranphucvinh.security.UserPrincipal;
import com.tranphucvinh.service.PostService;

@RestController
@RequestMapping("/cms/api/post")
public class PostRestController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(PostRestController.class);
	  
	@Autowired
	private PostService postService;
	
	@GetMapping("/list")
    public ResponseEntity<Response> list() {
        try {
        	UserPrincipal userPrincipal = BaseService.getCurrentUser();
        	Map<String,Object> params = new HashMap<String,Object>();
			params.put("role", userPrincipal.getAuthorities().iterator().next().toString());
            List<Map<String,Object>> list = postService.getPostList(params);
            return ResponseEntity.ok().body(new Response(list, "Get list ok !"));
        } catch (Exception e) {
            logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
	
	@PostMapping("/create")
	public ResponseEntity<Response> createPost(@Valid @RequestBody PostForm postForm) {
		try {
			UserPrincipal userPrincipal = BaseService.getCurrentUser();
			postForm.setUser_id(userPrincipal.getId());
			postForm.level = !isSupperAdmin()?"2":postForm.level;
			String postId = postService.createPost(postForm);
			return ResponseEntity.ok().body(new Response(postId, "Create post successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/edit")
	public ResponseEntity<Response> editPost(@Valid @RequestBody PostForm postForm) {
		try {
			UserPrincipal userPrincipal = BaseService.getCurrentUser();
			postForm.setUser_id(userPrincipal.getId());
			postForm.level = !isSupperAdmin()?"2":postForm.level;
			postService.editPost(postForm);
			return ResponseEntity.ok().body(new Response(postForm.post_id, "Edit post successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deletePosts(@RequestBody Map<String,Object> params) {
		try {
			UserPrincipal userPrincipal = BaseService.getCurrentUser();
			params.put("role", userPrincipal.getAuthorities().iterator().next().toString());
			params.put("user_id", userPrincipal.getId());
			postService.deletePosts(params);
			return ResponseEntity.ok().body(new Response(null, "Delete posts successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
