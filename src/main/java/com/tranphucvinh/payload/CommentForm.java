package com.tranphucvinh.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentForm {
	
	@NotBlank(message = "Invalid operation!")
	public String post_id;
	
	@NotBlank(message = "Invalid operation!")
	public String comment_parent_id;
	
	@NotBlank(message = "Comment not be blank")
	public String comment;
	
	@NotBlank(message = "Name not be blank !")
	@Size(max=50,message="Name must be less than 50 character !")
	public String name;
	
	@Size(max=100,message="Email too long, it's must be less than 50 character!")
	public String email;
	
	public boolean isSaveInfo;
}
