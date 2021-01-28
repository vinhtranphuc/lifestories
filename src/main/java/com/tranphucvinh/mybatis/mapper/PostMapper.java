package com.tranphucvinh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.tranphucvinh.payload.PostForm;

@Mapper
public interface PostMapper {

	void insertPost(PostForm postForm);

	void insertIgnorePostsTags(Map<String, Object> postTagPrm);

	void insertPostImages(Map<String, Object> postImagesPrm);

	Map<String, Object> selectPostById(Map<String, Object> postPrm);
	
	List<Map<String, Object>> selectPostList(Map<String,Object> params);

	List<Map<String, Object>> selectPostImagesById(String postId);

	void updatePost(@Valid PostForm postForm);

	void updatePostContentById(PostForm postForm);
	
	void deletePostImagesUnused(Map<String, Object> params);

	void deletePostTagsUnused(Map<String, Object> params);
	
	void deletePostTagsByPostId(Map<String, Object> params);
	
	void deletePostImagesByPostId(Map<String, Object> params);
	
	void deleteCommentsByPostId(Map<String, Object> params);
	
	void deletePosts(Map<String, Object> params);
	
	int selectHomePostCnt(Map<String, Object> params);
	
	List<Map<String, Object>> selectHomePostList(Map<String, Object> params);

	Map<String, Object> selectHomePostById(Map<String, Object> params);
	
	List<Map<String, Object>>  selectSuggestPosts(Map<String, Object> params);
	
	List<Map<String, Object>>  selectPorpularPosts();

	void updatePostLike(Map<String, Object> params);
}
