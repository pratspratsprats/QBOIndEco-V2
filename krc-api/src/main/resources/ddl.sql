CREATE DATABASE  IF NOT EXISTS `krconnect`;
USE `krconnect`;

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `shop_domain` varchar(50) NOT NULL,
  `web_api_key` varchar(50) NOT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `address1` varchar(200) DEFAULT NULL,
  `address2` varchar(200) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `post_code` varchar(20) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `tin_no` varchar(30) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `sync_status` tinyint(1) NOT NULL DEFAULT '0',
  `qb_company_id` varchar(30) DEFAULT NULL,
  `qb_username` varchar(50) DEFAULT NULL,
  `qb_password` varchar(50) DEFAULT NULL,
  `created_dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_order_sync_dt` timestamp NULL DEFAULT NULL,
  `last_product_sync_dt` timestamp NULL DEFAULT NULL,
  `access_token` varchar(50) DEFAULT NULL,
  `access_token_secret` varchar(50) DEFAULT NULL,
  `access_token_expiration_date` timestamp NULL DEFAULT NULL,
  `sync_frequency` bigint(20) DEFAULT NULL,
  `sync_start_delay` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shop_domain` (`shop_domain`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

alter table customers add last_order_sync_dt timestamp null default null;
alter table customers add last_product_sync_dt timestamp null default null;

