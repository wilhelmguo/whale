SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for JY_BASE_ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ACCOUNT`;
CREATE TABLE `JY_BASE_ACCOUNT` (
  `id`          VARCHAR(32)
                CHARACTER SET utf8 NOT NULL
  COMMENT '用户Id',
  `loginName`   VARCHAR(32)
                CHARACTER SET utf8 NOT NULL
  COMMENT '用户登录名',
  `password`    VARCHAR(32)
                CHARACTER SET utf8 NOT NULL
  COMMENT '密码',
  `salt`        VARCHAR(32)        NOT NULL,
  `roleId`      VARCHAR(32)        NOT NULL DEFAULT '0'
  COMMENT '用户类型(对应jy_base_role的Id)',
  `picUrl`      VARCHAR(255)                DEFAULT NULL,
  `name`        VARCHAR(64)
                CHARACTER SET utf8          DEFAULT ''
  COMMENT '用户名字',
  `email`       VARCHAR(255)
                CHARACTER SET utf8          DEFAULT NULL
  COMMENT '电子邮箱',
  `skin`        VARCHAR(64)                 DEFAULT '',
  `isValid`     INT(2)             NOT NULL DEFAULT '0'
  COMMENT '是否有效(1:有效，0:无效)',
  `createTime`  DATETIME                    DEFAULT NULL
  COMMENT '创建时间',
  `updateTime`  DATETIME                    DEFAULT NULL,
  `description` VARCHAR(100)
                CHARACTER SET utf8          DEFAULT ''
  COMMENT '描述',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- ----------------------------
-- Table structure for JY_BASE_ACCOUNT_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ACCOUNT_POSITION`;
CREATE TABLE `JY_BASE_ACCOUNT_POSITION` (
  `accountId` VARCHAR(32) NOT NULL,
  `posId`     VARCHAR(32) NOT NULL,
  PRIMARY KEY (`accountId`, `posId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_login_log
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_LOGIN_LOG`;
CREATE TABLE `JY_BASE_LOGIN_LOG` (
  `id`        VARCHAR(32) DEFAULT NULL,
  `accountId` VARCHAR(32) DEFAULT NULL,
  `loginTime` DATETIME    DEFAULT NULL,
  `loginIP`   VARCHAR(40) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_org
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ORG`;
CREATE TABLE `JY_BASE_ORG` (
  `id`          VARCHAR(32) NOT NULL,
  `pId`         VARCHAR(32) DEFAULT NULL,
  `name`        VARCHAR(50) DEFAULT NULL,
  `isValid`     INT(2)      DEFAULT NULL,
  `description` VARCHAR(50) DEFAULT NULL,
  `createTime`  DATETIME    DEFAULT NULL,
  `updateTime`  DATETIME    DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_org_resources
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ORG_RESOURCES`;
CREATE TABLE `JY_BASE_ORG_RESOURCES` (
  `org_Id`       VARCHAR(32) DEFAULT NULL,
  `resources_id` VARCHAR(32) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_position
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_POSITION`;
CREATE TABLE `JY_BASE_POSITION` (
  `id`          VARCHAR(32) NOT NULL,
  `name`        VARCHAR(32) NOT NULL,
  `orgId`       VARCHAR(32) NOT NULL,
  `type`        VARCHAR(32) NOT NULL,
  `description` VARCHAR(200) DEFAULT NULL,
  `createTime`  DATETIME     DEFAULT NULL,
  `updateTime`  DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_resources
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_RESOURCES`;
CREATE TABLE `JY_BASE_RESOURCES` (
  `id`          VARCHAR(32)
                CHARACTER SET utf8 NOT NULL
  COMMENT 'Id',
  `name`        VARCHAR(50)
                CHARACTER SET utf8 NOT NULL
  COMMENT '菜单名字',
  `parentId`    VARCHAR(32)
                CHARACTER SET utf8 NOT NULL DEFAULT '0'
  COMMENT '父Id',
  `layer`       INT(11)            NOT NULL,
  `type`        INT(4)             NOT NULL DEFAULT '1'
  COMMENT '资源类型(1:为菜单，2:功能，3:按钮)',
  `resUrl`      VARCHAR(512)
                CHARACTER SET utf8          DEFAULT NULL
  COMMENT '菜单链接',
  `btnId`       VARCHAR(32)                 DEFAULT NULL,
  `btnFun`      VARCHAR(64)                 DEFAULT NULL,
  `icon`        VARCHAR(100)
                CHARACTER SET utf8          DEFAULT NULL
  COMMENT '菜单Icon',
  `sort`        INT(10)                     DEFAULT NULL
  COMMENT '菜单顺序(由小到大排列)',
  `isValid`     INT(2)                      DEFAULT NULL
  COMMENT '是否有效(1:有效,0:无效)',
  `description` VARCHAR(200)
                CHARACTER SET utf8          DEFAULT NULL
  COMMENT '描述',
  `createTime`  DATETIME                    DEFAULT NULL
  COMMENT '创建时间',
  `updateTime`  DATETIME                    DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- ----------------------------
-- Table structure for jy_base_role
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ROLE`;
CREATE TABLE `JY_BASE_ROLE` (
  `id`          VARCHAR(32) NOT NULL,
  `orgId`       VARCHAR(32) DEFAULT NULL,
  `name`        VARCHAR(50) DEFAULT NULL,
  `isValid`     INT(2)      DEFAULT NULL,
  `description` VARCHAR(50) DEFAULT NULL,
  `createTime`  DATETIME    DEFAULT NULL,
  `updateTime`  DATETIME    DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_base_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `JY_BASE_ROLE_RESOURCES`;
CREATE TABLE `JY_BASE_ROLE_RESOURCES` (
  `role_id`      VARCHAR(32) NOT NULL,
  `resources_id` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`role_id`, `resources_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_data_dict
-- ----------------------------
DROP TABLE IF EXISTS `JY_DATA_DICT`;
CREATE TABLE `JY_DATA_DICT` (
  `id`          VARCHAR(32) NOT NULL,
  `dataKey`     VARCHAR(32) NOT NULL,
  `name`        VARCHAR(64)          DEFAULT NULL,
  `isValid`     INT(2)      NOT NULL DEFAULT '0',
  `description` VARCHAR(128)         DEFAULT NULL,
  `createTime`  DATETIME             DEFAULT NULL,
  `updateTime`  DATETIME             DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_data_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `JY_DATA_DICT_ITEM`;
CREATE TABLE `JY_DATA_DICT_ITEM` (
  `dictId` VARCHAR(32) NOT NULL,
  `value`  VARCHAR(64) NOT NULL,
  `name`   VARCHAR(32) NOT NULL,
  `sort`   INT(10)     NOT NULL,
  PRIMARY KEY (`dictId`, `value`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `JY_SYS_DICT`;
CREATE TABLE `JY_SYS_DICT` (
  `id`          VARCHAR(32) NOT NULL,
  `paramKey`    VARCHAR(64)  DEFAULT NULL,
  `paramName`   VARCHAR(128) DEFAULT NULL,
  `paramValue`  VARCHAR(128) DEFAULT NULL,
  `isValid`     INT(2)       DEFAULT NULL,
  `description` VARCHAR(500) DEFAULT NULL,
  `createTime`  DATETIME     DEFAULT NULL,
  `updateTime`  DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_task_log
-- ----------------------------
DROP TABLE IF EXISTS `JY_TASK_LOG`;
CREATE TABLE `JY_TASK_LOG` (
  `id`          VARCHAR(32) NOT NULL,
  `name`        VARCHAR(128) DEFAULT NULL,
  `className`   VARCHAR(128) DEFAULT NULL,
  `type`        INT(2)       DEFAULT NULL,
  `description` VARCHAR(256) DEFAULT NULL,
  `createTime`  DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_task_schedule
-- ----------------------------
DROP TABLE IF EXISTS `JY_TASK_SCHEDULE`;
CREATE TABLE `JY_TASK_SCHEDULE` (
  `scheduleJobId`  VARCHAR(32) NOT NULL,
  `jobName`        VARCHAR(64)  DEFAULT NULL,
  `jobGroup`       VARCHAR(64)  DEFAULT NULL,
  `aliasName`      VARCHAR(64)  DEFAULT NULL,
  `jobClass`       VARCHAR(128) DEFAULT NULL,
  `status`         INT(2)       DEFAULT NULL,
  `cronExpression` VARCHAR(64)  DEFAULT NULL,
  `description`    VARCHAR(256) DEFAULT NULL,
  `createTime`     DATETIME     DEFAULT NULL,
  `updateTime`     DATETIME     DEFAULT NULL,
  PRIMARY KEY (`scheduleJobId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for jy_tool_email
-- ----------------------------
DROP TABLE IF EXISTS `JY_TOOL_EMAIL`;
CREATE TABLE `JY_TOOL_EMAIL` (
  `id`         VARCHAR(32) NOT NULL,
  `subject`    VARCHAR(200) DEFAULT NULL,
  `body`       TEXT,
  `toList`     TEXT,
  `ccList`     TEXT,
  `createTime` DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for oa_leave
-- ----------------------------
DROP TABLE IF EXISTS `OA_LEAVE`;
CREATE TABLE `OA_LEAVE` (
  `id`           VARCHAR(32) NOT NULL,
  `org`          VARCHAR(64)  DEFAULT NULL,
  `name`         VARCHAR(32)  DEFAULT NULL,
  `approver`     VARCHAR(32)  DEFAULT NULL,
  `type`         VARCHAR(4)   DEFAULT NULL,
  `beginTime`    DATETIME     DEFAULT NULL,
  `endTime`      DATETIME     DEFAULT NULL,
  `description`  VARCHAR(256) DEFAULT NULL,
  `rejectReason` VARCHAR(256) DEFAULT NULL,
  `pId`          VARCHAR(32)  DEFAULT NULL,
  `account_id`   VARCHAR(32)  DEFAULT NULL,
  `createTime`   DATETIME     DEFAULT NULL,
  `updateTime`   DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for wx_event_click
-- ----------------------------
DROP TABLE IF EXISTS `WX_EVENT_CLICK`;
CREATE TABLE `WX_EVENT_CLICK` (
  `id`         VARCHAR(32) NOT NULL,
  `keyId`      VARCHAR(32)   DEFAULT NULL,
  `title`      VARCHAR(64)   DEFAULT NULL,
  `content`    VARCHAR(128)  DEFAULT NULL,
  `picUrl`     VARCHAR(1024) DEFAULT NULL,
  `url`        VARCHAR(1024) DEFAULT NULL,
  `createTime` DATETIME      DEFAULT NULL,
  `updateTime` DATETIME      DEFAULT NULL,
  `sort`       INT(10)       DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for wx_follower
-- ----------------------------
DROP TABLE IF EXISTS `WX_FOLLOWER`;
CREATE TABLE `WX_FOLLOWER` (
  `openid`        VARCHAR(32) NOT NULL,
  `subscribe`     INT(4)       DEFAULT NULL,
  `nickname`      VARCHAR(32)  DEFAULT NULL,
  `sex`           INT(4)       DEFAULT NULL,
  `city`          VARCHAR(64)  DEFAULT NULL,
  `country`       VARCHAR(64)  DEFAULT NULL,
  `province`      VARCHAR(64)  DEFAULT NULL,
  `language`      VARCHAR(32)  DEFAULT NULL,
  `headimgurl`    VARCHAR(512) DEFAULT NULL,
  `subscribeTime` DATETIME     DEFAULT NULL,
  `unionid`       VARCHAR(32)  DEFAULT NULL,
  `remark`        VARCHAR(128) DEFAULT NULL,
  `groupid`       VARCHAR(32)  DEFAULT '',
  PRIMARY KEY (`openid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for wx_menu
-- ----------------------------
DROP TABLE IF EXISTS `WX_MENU`;
CREATE TABLE `WX_MENU` (
  `id`         VARCHAR(32) NOT NULL,
  `pId`        VARCHAR(32)   DEFAULT NULL,
  `name`       VARCHAR(40)   DEFAULT NULL,
  `type`       VARCHAR(32)   DEFAULT NULL,
  `keyId`      VARCHAR(32)   DEFAULT NULL,
  `url`        VARCHAR(1024) DEFAULT NULL,
  `sort`       INT(10)       DEFAULT NULL,
  `remark`     VARCHAR(100)  DEFAULT NULL,
  `createTime` DATETIME      DEFAULT NULL,
  `updateTime` DATETIME      DEFAULT NULL,
  `selectType` VARCHAR(32)   DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
