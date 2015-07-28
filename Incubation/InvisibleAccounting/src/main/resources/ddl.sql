CREATE DATABASE  IF NOT EXISTS `incubation`;
USE `incubation`;

DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50),
  `seller_id` varchar(50) NOT NULL UNIQUE,
  `company_name` varchar(50),
  `first_name` varchar(50),
  `last_name` varchar(50),
  `address1` varchar(200),
  `address2` varchar(200),
  `state` VARCHAR(30),
  `city` varchar(30),
  `post_code` varchar(20),
  `country` varchar(30),
  `tin_no` varchar(30),
  `mobile` varchar(15),
  `qb_company_id` VARCHAR(30),
  `qb_username` varchar(50) NOT NULL,
  `qb_password` varchar(50) NOT NULL,
  `created_dt` timestamp NOT NULL DEFAULT 0,
  `updated_dt` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

