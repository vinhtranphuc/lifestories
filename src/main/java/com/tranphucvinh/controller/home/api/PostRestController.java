package com.tranphucvinh.controller.home.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.mybatis.model.CommentVO;
import com.tranphucvinh.payload.CommentForm;
import com.tranphucvinh.payload.Response;
import com.tranphucvinh.service.CommentService;
import com.tranphucvinh.service.PostService;

@RestController("homePostApi")
@RequestMapping("/home/api/post")
public class PostRestController {
	
	private Logger logger = LoggerFactory.getLogger(PostRestController.class);
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/comments/{postId}", method = RequestMethod.GET)
    public ResponseEntity<Response> posts(@PathVariable String postId) {
        try {
        	Map<String,Object> result = new HashMap<String,Object>();
        	List<CommentVO> comments = commentService.getTreeCommentsByPostId(postId);
        	result.put("commentSession", httpSession.getAttribute("commentSession"));
        	result.put("comments", comments);
            return ResponseEntity.ok().body(new Response(result, "Get comments ok !"));
        } catch (Exception e) {
            logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
	
	@PostMapping("/save-comment")
	public ResponseEntity<Response> saveComment(@Valid @RequestBody CommentForm commentForm) {
		try {
			if(commentForm.isSaveInfo) {
				Map<String,Object> commentSession = new HashMap<String,Object>();
				commentSession.put("commentName", commentForm.name);
				commentSession.put("commentEmail", commentForm.email);
				
				httpSession.setAttribute("commentSession", commentSession);
			} else {
				httpSession.removeAttribute("commentSession");
			}
			
			commentService.saveComment(commentForm);
			return ResponseEntity.ok().body(new Response(null, "Save comment successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/handle-like")
	public ResponseEntity<Response> handleLike(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		try {
			int likeCnt = postService.handleLike(params,request);
			return ResponseEntity.ok().body(new Response(likeCnt, "Save comment successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
