package com.tranphucvinh.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tranphucvinh.mybatis.mapper.ContactMapper;
import com.tranphucvinh.payload.ContactForm;

@Service
public class ContactService {
	
	@Resource
	private ContactMapper contactMapper;

	public void saveContact(ContactForm contactForm) {
		contactMapper.insertContact(contactForm);
	}

	public List<Map<String, Object>> getContactList(Map<String, Object> params) {
		return contactMapper.selectContactList(params);
	}
	
	public void deleteContacts(Map<String, Object> params) {
		contactMapper.deleteContacts(params);
	}
}
