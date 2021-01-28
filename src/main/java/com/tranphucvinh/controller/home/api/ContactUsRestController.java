package com.tranphucvinh.controller.home.api;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.controller.common.BaseController;
import com.tranphucvinh.payload.ContactForm;
import com.tranphucvinh.payload.Response;
import com.tranphucvinh.service.ContactService;

@RestController
@RequestMapping("/home/api/contact-us/")
public class ContactUsRestController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(ContactUsRestController.class);
	
	@Autowired
	private ContactService contactService;
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveContact(@Valid @RequestBody ContactForm contactForm) {
		try {
			contactService.saveContact(contactForm);
			return ResponseEntity.ok().body(new Response(null, "Save contact successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
