package com.tranphucvinh.controller.home.api;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tranphucvinh.payload.Response;
import com.tranphucvinh.service.ImageService;

@RestController
@RequestMapping("/home/api/gallery")
public class GalleryRestController {

	private Logger logger = LoggerFactory.getLogger(GalleryRestController.class);
	
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public ResponseEntity<Response> images(@RequestParam Map<String,Object> params) {
        try {
        	Map<String,Object> images = imageService.getImages(params);
            return ResponseEntity.ok().body(new Response(images, "Get gallery ok !"));
        } catch (Exception e) {
            logger.error("Exception : {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
