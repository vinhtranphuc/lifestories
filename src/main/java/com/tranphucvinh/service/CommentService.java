package com.tranphucvinh.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tranphucvinh.mybatis.mapper.CommentMapper;
import com.tranphucvinh.mybatis.model.CommentVO;
import com.tranphucvinh.payload.CommentForm;

@Service
public class CommentService {
	
	@Resource
	private CommentMapper commentMapper;
	
	public List<CommentVO> getTreeCommentsByPostId(String postId) {
		List<CommentVO> comments = commentMapper.selectCommentsByPostId(postId);
		//Collections.sort(comments);
		
		List<CommentVO> rootComments = comments.stream().filter(t->{
			return StringUtils.equals(t.comment_parent_id, "0");
		}).collect(Collectors.toList());
		List<CommentVO> treeComments = rootComments.parallelStream().map(t-> {
			t.setChildComments(getChildCommentsById(t.comment_id, comments));
			return t;
		}).collect(Collectors.toList());
		return treeComments;
	}
	
	private List<CommentVO> getChildCommentsById(String commmentId, List<CommentVO> comments) {
		List<CommentVO> childComments = comments.stream().filter(t-> {
			return StringUtils.equals(t.comment_parent_id, commmentId);
		}).collect(Collectors.toList());
		//Collections.sort(childComments);
		
		for(CommentVO cmt:childComments) {
			cmt.setChildComments(getChildCommentsById(cmt.comment_id,comments));
		}
		return childComments;
	}

	public void saveComment(@Valid CommentForm commentForm) {
		commentMapper.insertComment(commentForm);
	}
}
