package com.tranphucvinh.common;

public class Const {
	
	// max captcha tries
    public static final Integer MAX_CAPTCHA_TRIES = 3;
    
    // store
    public static final String STORE_ROOT = "store";
    public static final String FILE_COMMON_DIR = "store/common";
    public static final String FILE_UPLOAD_DIR = "store/upload";
    
    public static final String ABOUT_US_DIR = "/about-us";
    public static final String CONTACT_US_DIR = "/contact-us";
    
    private static final String USER_AVATAR_DIR = "/user/%s/avatar";
    private static final String POST_DIR = "/post/%s";
    private static final String POST_THUMBNAIL_DIR = "/post/%s/thumbnail";
    private static final String POST_CONTENT_IMAGES_DIR = "/post/%s/content_images";
    
    public static String getUserAvatarDir(String userName) {
    	return String.format(USER_AVATAR_DIR, userName);
    }
    
    public static String getPostDir(String postId) {
    	return String.format(POST_DIR, postId);
    }
    
    public static String getPostThumbnailDir(String postId) {
    	return String.format(POST_THUMBNAIL_DIR, postId);
    }
    
    public static String getPostContentImagesDir(String postId) {
    	return String.format(POST_CONTENT_IMAGES_DIR, postId);
    }
}
