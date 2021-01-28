package com.tranphucvinh.mybatis.model;

import java.util.List;

public class CommentVO implements Comparable<CommentVO> {
	
    public String comment_id;
    public String post_id;
    public String name;
    public String email;
    public String created_at;
    public String updated_at;
    public String comment;
    public String comment_parent_id;
    
    private List<CommentVO> childComments;

	public List<CommentVO> getChildComments() {
		return childComments;
	}
	public void setChildComments(List<CommentVO> childComments) {
		this.childComments = childComments;
	}
	@Override
	public int compareTo(CommentVO o) {
		return this.comment_id.compareTo(o.comment_id);
	}
}
