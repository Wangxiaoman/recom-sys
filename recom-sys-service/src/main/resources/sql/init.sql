CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `source` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '来源',
  `title` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标题',
  `auther` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '作者',
  `coverUrl` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '封面图',
  `tags` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签，逗号分割',
  `type` tinyint(4) DEFAULT '1' COMMENT '分类，1-文章',
  `description` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publish_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `item_id` (`item_id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB;

CREATE TABLE `item_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '物料名称',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `access_token` varchar(64) NOT NULL COMMENT '物料上传等接口的token',
  `status` tinyint(4) DEFAULT '1' COMMENT '1-有效，0-无效',
  `item_numbers` bigint(20) DEFAULT '0' COMMENT '物料集合的数量',
  `terminal_type` tinyint(4) DEFAULT '1' COMMENT '1-PC,2-ANDRIOID,3-iOS,4-小程序,5-WAP,6-DISCUZ,7-WORDPRESS',
  `main_url` varchar(256) DEFAULT '' COMMENT '主站域名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;