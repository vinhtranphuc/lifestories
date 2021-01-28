package com.tranphucvinh.controller.cms.api;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.payload.Response;
import com.tranphucvinh.payload.SocialForm;
import com.tranphucvinh.service.ContactService;
import com.tranphucvinh.service.PageInfoService;

@RestController
@RequestMapping("/cms/api/page-info")
public class PageInfoRestController {

	private Logger logger = LoggerFactory.getLogger(PageInfoRestController.class);
	
	@Autowired
	private PageInfoService pageInfoService;
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contact-list")
    public ResponseEntity<Response> contactList(@RequestParam Map<String,Object> params) {
        try {
            List<Map<String,Object>> list = contactService.getContactList(params);
            return ResponseEntity.ok().body(new Response(list, "Get contacts ok !"));
        } catch (Exception e) {
            logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

	@PostMapping("/social/save")
	public ResponseEntity<Response> saveSocial(@RequestBody SocialForm socialForm) {
		try {
			pageInfoService.saveSocials(socialForm);
			return ResponseEntity.ok().body(new Response(null, "Save socials successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/edit-aboutus")
	public ResponseEntity<Response> editAboutUs(@RequestBody Map<String,Object> params) {
		try {
			pageInfoService.editAboutUs(params);
			return ResponseEntity.ok().body(new Response(null, "Save about us successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/edit-contactus")
	public ResponseEntity<Response> editContactUs(@RequestBody Map<String,Object> params) {
		try {
			pageInfoService.editContactUs(params);
			return ResponseEntity.ok().body(new Response(null, "Save contact us successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/delete-contacts")
	public ResponseEntity<Response> deleteContacts(@RequestBody Map<String,Object> params) {
		try {
			contactService.deleteContacts(params);
			return ResponseEntity.ok().body(new Response(null, "Save contact us successfully !"));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
