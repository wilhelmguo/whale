DROP TABLE IF EXISTS `JY_SYS_NEWS`;
CREATE TABLE `JY_SYS_NEWS` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `title` varchar(150) DEFAULT NULL COMMENT '新闻标题',
  `content` text COMMENT '新闻内容',
  `publisher` varchar(50) DEFAULT NULL COMMENT '发布人',
  `addtime` datetime DEFAULT NULL COMMENT '发布时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `pip` varchar(50) DEFAULT NULL COMMENT '发布IP',
  `hits` int(11) DEFAULT '0' COMMENT '点击数',
  `sequence` int(10) DEFAULT '0' COMMENT '排序',
  `recommand` char(2) DEFAULT '0' COMMENT '推荐  0默认未推荐，1推荐',
  `status` char(2) DEFAULT '0' COMMENT '状态  0默认未发布，1发布',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;