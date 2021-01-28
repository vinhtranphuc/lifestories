package com.tranphucvinh.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactForm {
	
	@NotBlank(message = "Please enter your name !")
	@Size(max=50,message="Name must be less than 50 character !")
	public String name;
	
	@Size(max=50,message="Email must be less than 50 character !")
	public String email;
	
	@Size(max=100,message="Subject must be less than 100 character !")
	public String subject;
	
	@NotBlank(message = "Please enter your message !")
	@Size(max=100,message="Message must be less than 500 character !")
	public String message;
}
