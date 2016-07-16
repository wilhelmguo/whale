DROP TABLE IF EXISTS `JY_WORK_RULE`;
CREATE TABLE `JY_WORK_RULE` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `year` int(11) DEFAULT '0' COMMENT '年度',
  `workDate` datetime DEFAULT NULL COMMENT '日期',
  `status` char(2) DEFAULT '0' COMMENT '推状态0节假日1调休',
  `name` varchar(50) DEFAULT NULL COMMENT '规则名称比如:春节调休,国庆调休等等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JY_WORK_RECORD`;
CREATE TABLE `JY_WORK_RECORD` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `type` char(2) DEFAULT '0' COMMENT '0内勤1外勤',
  `employee` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `location` varchar(200) DEFAULT NULL COMMENT '地理位置',
  `picture` varchar(300) DEFAULT NULL COMMENT '考勤图片路径',
  `date` date DEFAULT NULL COMMENT '考勤日期',
  `week` varchar(10) DEFAULT NULL COMMENT '周一,周二等',
  `morning` varchar(5) DEFAULT NULL COMMENT '早上签到时间',
  `beforenoon` varchar(5) DEFAULT NULL COMMENT '中午签退时间',
  `afternoon` varchar(5) DEFAULT NULL COMMENT '中午签到时间',
  `night` varchar(5) DEFAULT NULL COMMENT '晚上考勤时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JY_WORK_DEVICE`;
CREATE TABLE `JY_WORK_DEVICE` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `type` char(2) DEFAULT '0' COMMENT '设备类型:0.wifi 1.mac地址',
  `name` varchar(100) DEFAULT NULL COMMENT '设备名称:可以是wifi名称或者mac地址',
  `belongto` varchar(32) DEFAULT NULL COMMENT '所属员工,0表示所有员工',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JY_WORK_TIME`;
CREATE TABLE `JY_WORK_TIME` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `morning` varchar(5) DEFAULT '09:00' COMMENT '上班时间',
  `beforenoon` varchar(5) DEFAULT NULL COMMENT '中午签退时间,不设置表示不要求中午打卡,必须与beforenoon同时设置生效',
  `afternoon` varchar(5) DEFAULT NULL COMMENT '中午签到时间,不设置表示不要求中午打卡,必须与afternoon同时设置生效',
  `night` varchar(5) DEFAULT NULL COMMENT '下班时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;