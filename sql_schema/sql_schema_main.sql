DROP DATABASE IF EXISTS `bootmvcdb`;
CREATE DATABASE IF NOT EXISTS `bootmvcdb`;
USE `bootmvcdb`;

DROP TABLE IF EXISTS `app_logs`;
CREATE TABLE IF NOT EXISTS `app_logs` (
  `DATE` datetime NOT NULL,
  `LOGGER` varchar(50) NOT NULL,
  `LEVEL` varchar(10) NOT NULL,
  `MESSAGE` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `groups`;
CREATE TABLE IF NOT EXISTS `groups` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `groups`;
INSERT INTO `groups` (`id`, `group_name`) VALUES
	(1, 'administrators'),
	(2, 'users');

DROP TABLE IF EXISTS `group_authorities`;
CREATE TABLE IF NOT EXISTS `group_authorities` (
  `group_id` bigint(20) unsigned NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `group_authorities`;
INSERT INTO `group_authorities` (`group_id`, `authority`) VALUES
	(1, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(2, 'ROLE_USER');

DROP TABLE IF EXISTS `group_members`;
CREATE TABLE IF NOT EXISTS `group_members` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `group_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `matchers`;
CREATE TABLE IF NOT EXISTS `matchers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_role` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `matchers`;
INSERT INTO `matchers` (`id`, `access_role`, `url`) VALUES
	(1, 'ROLE_USER', '/'),
	(2, 'ROLE_USER', '/messages/**');

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `id` varchar(36) NOT NULL,
  `content` text NOT NULL,
  `addressee` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `sending_date` datetime NOT NULL,
  `sent` bit(1) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `message_parameters`;
CREATE TABLE IF NOT EXISTS `message_parameters` (
  `message_id` varchar(36) NOT NULL,
  `value` varchar(255) NOT NULL,
  `key` varchar(255) NOT NULL,
  PRIMARY KEY (`message_id`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(36) NOT NULL,
  `int_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `activation_code` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INT_ID` (`int_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `worker_logs`;
CREATE TABLE IF NOT EXISTS `worker_logs` (
  `DATE` datetime NOT NULL,
  `LOGGER` varchar(50) NOT NULL,
  `LEVEL` varchar(10) NOT NULL,
  `MESSAGE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;