/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : oa

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-07-30 22:06:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for TASK_INFO
-- ----------------------------
DROP TABLE IF EXISTS `TASK_INFO`;
CREATE TABLE `TASK_INFO` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `businessKey` varchar(32) DEFAULT NULL COMMENT '业务外键',
  `code` varchar(100) DEFAULT NULL COMMENT '当前流程 ID',
  `name` varchar(200) DEFAULT NULL COMMENT '流程名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 完成 1激活',
  `presentationSubject` varchar(200) DEFAULT NULL COMMENT '流程标题',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `completeTime` datetime DEFAULT NULL COMMENT '完成时间',
  `expirationTime` datetime DEFAULT NULL COMMENT '超期时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `assignee` varchar(32) DEFAULT NULL COMMENT '任务分配人',
  `taskId` varchar(200) DEFAULT NULL COMMENT '流程TASK ID',
  `executionId` varchar(200) DEFAULT NULL COMMENT '流程执行 ID',
  `processInstanceid` varchar(200) DEFAULT NULL COMMENT '流程初始化 ID',
  `processDefinitionid` varchar(200) DEFAULT NULL COMMENT '流程定义 ID',
  `attr1` varchar(100) DEFAULT NULL COMMENT '预留字段',
  `attr2` varchar(100) DEFAULT NULL COMMENT '预留字段',
  `attr3` varchar(100) DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
