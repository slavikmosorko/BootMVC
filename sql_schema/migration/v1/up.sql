ALTER TABLE messages
  ADD COLUMN deleted BIT;
ALTER TABLE messages
  ADD COLUMN addressee VARCHAR(255);
ALTER TABLE messages
  ADD COLUMN subject VARCHAR(255);
ALTER TABLE users
  ADD COLUMN activation_code VARCHAR(255);


CREATE TABLE IF NOT EXISTS `message_parameters` (
  `message_id` bigint(20) NOT NULL,
  `value` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `key` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`message_id`, `key`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;