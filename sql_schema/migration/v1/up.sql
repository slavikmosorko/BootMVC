ALTER TABLE messages ADD COLUMN `valid` bit(1) NOT NULL;

DROP TABLE IF EXISTS `web_info`;
CREATE TABLE IF NOT EXISTS `web_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id_to` varchar(36) NOT NULL,
  `user_id_from` varchar(36) NOT NULL,
  `title` varchar(36) NOT NULL,
  `message` varchar(255) NOT NULL,
  `received` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;