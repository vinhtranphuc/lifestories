-- --------------------------------------------------------
-- Host:                         149.28.146.86
-- Server version:               8.0.22 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for life_stories
CREATE DATABASE IF NOT EXISTS `lifecode` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lifecode`;

-- Dumping structure for function life_stories.fnStripTags
DELIMITER //
CREATE FUNCTION `fnStripTags`( Dirty varchar(4000) ) RETURNS varchar(4000) CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci
    DETERMINISTIC
BEGIN
  DECLARE iStart, iEnd, iLength int;
    WHILE Locate( '<', Dirty ) > 0 And Locate( '>', Dirty, Locate( '<', Dirty )) > 0 DO
      BEGIN
        SET iStart = Locate( '<', Dirty ), iEnd = Locate( '>', Dirty, Locate('<', Dirty ));
        SET iLength = ( iEnd - iStart) + 1;
        IF iLength > 0 THEN
          BEGIN
            SET Dirty = Insert( Dirty, iStart, iLength, '');
          END;
        END IF;
      END;
    END WHILE;
    RETURN Dirty;
END//
DELIMITER ;

-- Dumping structure for table lifecode.tb_categories
CREATE TABLE IF NOT EXISTS `tb_categories` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_img_path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_user` bigint NOT NULL,
  `updated_user` bigint DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table lifecode.tb_categories: ~4 rows (approximately)
/*!40000 ALTER TABLE `tb_categories` DISABLE KEYS */;
INSERT INTO `tb_categories` (`category_id`, `category_name`, `category_img_path`, `created_at`, `updated_at`, `created_user`, `updated_user`) VALUES
	(1, 'Life', NULL, '2020-12-10 11:16:15', NULL, 1, NULL),
	(2, 'Photography', NULL, '2020-12-10 11:16:51', NULL, 1, NULL),
	(3, 'Stories', NULL, '2020-12-10 11:17:06', NULL, 1, NULL),
	(4, 'Travel', NULL, '2020-12-10 11:17:19', NULL, 1, NULL);
/*!40000 ALTER TABLE `tb_categories` ENABLE KEYS */;

-- Dumping structure for table lifecode.tb_comments
CREATE TABLE IF NOT EXISTS `tb_comments` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint DEFAULT NULL,
  `comment_parent_id` bigint DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_tbPosts_tbComment` (`post_id`),
  CONSTRAINT `FK_tbPosts_tbComment` FOREIGN KEY (`post_id`) REFERENCES `tb_posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_comments: ~188 rows (approximately)
/*!40000 ALTER TABLE `tb_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_comments` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_contact
CREATE TABLE IF NOT EXISTS `tb_contact` (
  `contact_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subject` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumping data for table life_stories.tb_contact: ~3 rows (approximately)
/*!40000 ALTER TABLE `tb_contact` DISABLE KEYS */;
INSERT INTO `tb_contact` (`contact_id`, `name`, `email`, `subject`, `message`, `created_at`, `updated_at`) VALUES
	(8, 'Tran Phuc Vinh', 'vinhtranphuc@gmail.com', 'test', 'test test test test test test test test \ntest test test test test test test test', '2021-01-12 11:06:34', NULL);
/*!40000 ALTER TABLE `tb_contact` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_page_info
CREATE TABLE IF NOT EXISTS `tb_page_info` (
  `restriction` enum('') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `about_us` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `contact_us` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `facebook_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `facebook_actived` tinyint(1) DEFAULT NULL,
  `pinterest_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pinterest_actived` tinyint(1) DEFAULT NULL,
  `youtube_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `youtube_actived` tinyint(1) DEFAULT NULL,
  `instagram_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `instagram_actived` tinyint(1) DEFAULT NULL,
  `twitter_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `twitter_actived` tinyint(1) DEFAULT NULL,
  `flickr_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `flickr_actived` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`restriction`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_page_info: ~0 rows (approximately)
/*!40000 ALTER TABLE `tb_page_info` DISABLE KEYS */;
INSERT INTO `tb_page_info` (`restriction`, `about_us`, `contact_us`, `facebook_url`, `facebook_actived`, `pinterest_url`, `pinterest_actived`, `youtube_url`, `youtube_actived`, `instagram_url`, `instagram_actived`, `twitter_url`, `twitter_actived`, `flickr_url`, `flickr_actived`) VALUES
	('', NULL, NULL, 'https://www.facebook.com/tranphucvinh2212', 1, 'https://www.pinterest.com/tranphucvinh2212', 1, 'https://www.youtube.com/user/tpvinh', 1, 'https://www.instagram.com/vinhtranphucqwe', 1, 'Twitter', 0, 'https://www.flickr.com/photos/191849641@N02', 0);
/*!40000 ALTER TABLE `tb_page_info` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_posts
CREATE TABLE IF NOT EXISTS `tb_posts` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `level` int NOT NULL,
  `summary` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `times_of_view` int NOT NULL,
  `like_cnt` int NOT NULL,
  `title` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `published_at` datetime DEFAULT NULL,
  `has_images_ontop` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_user` bigint NOT NULL DEFAULT '0',
  `updated_user` bigint DEFAULT '0',
  PRIMARY KEY (`post_id`),
  KEY `FKijnwr3brs8vaosl80jg9rp7uc` (`category_id`),
  CONSTRAINT `FK_tbCategories_tbPosts` FOREIGN KEY (`category_id`) REFERENCES `tb_categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_posts: ~24 rows (approximately)
/*!40000 ALTER TABLE `tb_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_posts` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_posts_authors
CREATE TABLE IF NOT EXISTS `tb_posts_authors` (
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  KEY `FKet0txk8jynytashy09ehfbpp3` (`user_id`),
  CONSTRAINT `FK_tbPosts_tbPostsAurhors` FOREIGN KEY (`post_id`) REFERENCES `tb_posts` (`post_id`),
  CONSTRAINT `FK_tbUsers_tbPostsAurhors` FOREIGN KEY (`user_id`) REFERENCES `tb_users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_posts_authors: ~0 rows (approximately)
/*!40000 ALTER TABLE `tb_posts_authors` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_posts_authors` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_posts_tags
CREATE TABLE IF NOT EXISTS `tb_posts_tags` (
  `post_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `FK4svsmj4juqu2l8yaw6whr1v4v` (`tag_id`),
  CONSTRAINT `FK_tbPosts_tbPostsTags` FOREIGN KEY (`post_id`) REFERENCES `tb_posts` (`post_id`),
  CONSTRAINT `FK_tbTags_tbPostsTags` FOREIGN KEY (`tag_id`) REFERENCES `tb_tags` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_posts_tags: ~26 rows (approximately)
/*!40000 ALTER TABLE `tb_posts_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_posts_tags` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_post_images
CREATE TABLE IF NOT EXISTS `tb_post_images` (
  `image_id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint DEFAULT NULL,
  `image_path` varchar(1000) DEFAULT NULL,
  `standard_image_path` varchar(1000) DEFAULT NULL,
  `small_image_path` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FK_tbPosts_tbPostImages` (`post_id`),
  CONSTRAINT `FK_tbPosts_tbPostImages` FOREIGN KEY (`post_id`) REFERENCES `tb_posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;

-- Dumping data for table life_stories.tb_post_images: ~33 rows (approximately)
/*!40000 ALTER TABLE `tb_post_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_post_images` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_roles
CREATE TABLE IF NOT EXISTS `tb_roles` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_3mgeodec2ykm307478v8u0352` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_roles: ~3 rows (approximately)
/*!40000 ALTER TABLE `tb_roles` DISABLE KEYS */;
INSERT INTO `tb_roles` (`role_id`, `name`) VALUES
	(2, 'ROLE_ADMIN'),
	(1, 'ROLE_SUPPER_ADMIN'),
	(3, 'ROLE_USER');
/*!40000 ALTER TABLE `tb_roles` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_tags
CREATE TABLE IF NOT EXISTS `tb_tags` (
  `tag_id` bigint NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag` (`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=480 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_tags: ~17 rows (approximately)
/*!40000 ALTER TABLE `tb_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_tags` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_users
CREATE TABLE IF NOT EXISTS `tb_users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `join_date` datetime DEFAULT NULL,
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `occupation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `social_avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `summary` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK8n82lwp7lflhwda2v2v3wckc9` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_users: ~2 rows (approximately)
/*!40000 ALTER TABLE `tb_users` DISABLE KEYS */;
INSERT INTO `tb_users` (`user_id`, `created_at`, `updated_at`, `address`, `avatar_img`, `city`, `company_name`, `country`, `email`, `enabled`, `full_name`, `join_date`, `note`, `occupation`, `password`, `phone`, `provider`, `provider_id`, `social_avatar_url`, `summary`, `type`, `username`) VALUES
	(1, '2020-12-04 15:14:06', '2020-12-04 15:14:07', 'Duy Xuyên, Quảng Nam', '/store/upload/user/tranphucvinh/avatar/2021012412354008327150_87177420_2593437000891830_1889369513808363520_n.jpg', NULL, 'Softone', 'Viet Nam', 'vinhtranphuc@gmail.com', b'1', 'Trần Phúc Vinh', '2020-12-04 15:14:45', NULL, NULL, '$2a$10$A7F8CntAh5lojQCnWil6wOEIF4SRDxVcamp.aMB5uR.a0icZ/BtYa', '0382607172', 'local', NULL, NULL, 'I’m inspired by humanity in all its forms. Photography is an excuse to get closer to people and to hear their stories. That’s how I got started as a photographer, simply meeting people and taking the time to talk to them.', NULL, 'tranphucvinh'),
	(4, '2020-12-21 09:46:47', '2020-12-21 09:46:47', NULL, NULL, NULL, NULL, NULL, NULL, b'1', 'testadmin', '2020-12-21 09:46:47', NULL, NULL, '$2a$10$Fr0bEf46RU676YJ9BkuyvO3c.Qc92YL2sKsJN47hJHzxC4BlwfdwC', NULL, 'local', NULL, NULL, NULL, NULL, 'testadmin');
/*!40000 ALTER TABLE `tb_users` ENABLE KEYS */;

-- Dumping structure for table life_stories.tb_user_roles
CREATE TABLE IF NOT EXISTS `tb_user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK3mxrxqo67aefq0yaufnn84v3e` (`role_id`),
  CONSTRAINT `FK3mxrxqo67aefq0yaufnn84v3e` FOREIGN KEY (`role_id`) REFERENCES `tb_roles` (`role_id`),
  CONSTRAINT `FKugolgxur3og4u4y8od79ubp6` FOREIGN KEY (`user_id`) REFERENCES `tb_users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table life_stories.tb_user_roles: ~3 rows (approximately)
/*!40000 ALTER TABLE `tb_user_roles` DISABLE KEYS */;
INSERT INTO `tb_user_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(4, 2);
/*!40000 ALTER TABLE `tb_user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
