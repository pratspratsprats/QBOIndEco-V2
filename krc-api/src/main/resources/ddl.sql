CREATE DATABASE  IF NOT EXISTS `krconnect`;
USE `krconnect`;

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50),
  `shop_domain` varchar(50) NOT NULL UNIQUE,
  `web_api_key` varchar(50) NOT NULL,
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
  `sync_status` TINYINT(1) NOT NULL DEFAULT 0,
  `qb_company_id` VARCHAR(30),
  `qb_username` varchar(50),
  `qb_password` varchar(50),
  `created_dt` timestamp NOT NULL DEFAULT 0,
  `updated_dt` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table customers add last_order_sync_dt timestamp null default null;
alter table customers add last_product_sync_dt timestamp null default null;

