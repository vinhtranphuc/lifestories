package com.tranphucvinh.mybatis.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.tranphucvinh.mybatis.model.CommentVO;
import com.tranphucvinh.payload.CommentForm;

@Mapper
public interface CommentMapper {

	List<CommentVO> selectCommentsByPostId(String postId);

	void insertComment(@Valid CommentForm commentForm);
}
