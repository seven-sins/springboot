/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-02-18 14:30:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(19) NOT NULL,
  `SCHED_TIME` bigint(19) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` tinyint(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` tinyint(1) NOT NULL,
  `IS_NONCONCURRENT` tinyint(1) NOT NULL,
  `IS_UPDATE_DATA` tinyint(1) NOT NULL,
  `REQUESTS_RECOVERY` tinyint(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(19) NOT NULL,
  `CHECKIN_INTERVAL` bigint(19) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` tinyint(1) DEFAULT NULL,
  `BOOL_PROP_2` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(19) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(19) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(19) NOT NULL,
  `END_TIME` bigint(19) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `url` varchar(1000) NOT NULL,
  `method` varchar(30) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0: 菜单分类，1:模块，2:接口',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '根节点0',
  `menu_category_id` int(11) DEFAULT '0',
  `position` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id_index` (`parent_id`) USING BTREE,
  KEY `menu_category_id_index` (`menu_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_privilege
-- ----------------------------
INSERT INTO `sys_privilege` VALUES ('1', '用户管理', '/user', 'get', '1', '0', '25', '2', '0');
INSERT INTO `sys_privilege` VALUES ('2', '列表', '/user', 'get', '2', '1', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('3', '添加', '/user', 'post', '2', '1', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('4', '修改', '/user/{id}', 'put', '2', '1', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('5', '删除', '/user/{id}', 'delete', '2', '1', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('6', '角色管理', '/role', 'get', '1', '0', '25', '1', '0');
INSERT INTO `sys_privilege` VALUES ('7', '列表', '/role', 'get', '2', '6', '0', '1', '0');
INSERT INTO `sys_privilege` VALUES ('8', '添加', '/role', 'post', '2', '6', '0', '2', '0');
INSERT INTO `sys_privilege` VALUES ('9', '修改', '/role/{id}', 'put', '2', '6', '0', '3', '0');
INSERT INTO `sys_privilege` VALUES ('10', '删除', '/role/{id}', 'delete', '2', '6', '0', '4', '0');
INSERT INTO `sys_privilege` VALUES ('13', '权限管理', '/privilege', 'get', '1', '0', '25', '3', '0');
INSERT INTO `sys_privilege` VALUES ('15', '列表', '/privilege', 'get', '2', '13', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('17', '添加', '/privilege', 'post', '2', '13', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('18', '修改', '/privilege/{id}', 'put', '2', '13', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('19', '删除', '/privilege/{id}', 'delete', '2', '13', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('22', '模块列表', '/privilege/list', 'get', '2', '13', '0', null, '0');
INSERT INTO `sys_privilege` VALUES ('25', '系统设置', '/sysSettings', 'get', '0', '-1', '0', '1', '0');
INSERT INTO `sys_privilege` VALUES ('26', '主题管理', '/topicManage', 'get', '0', '-1', '0', '2', '0');
INSERT INTO `sys_privilege` VALUES ('27', '菜单分类', '/menuCategory', 'get', '1', '0', '25', '4', '0');
INSERT INTO `sys_privilege` VALUES ('28', '列表', '/menuCategory', 'get', '2', '27', null, '1', '0');
INSERT INTO `sys_privilege` VALUES ('29', '添加', '/menuCategory', 'post', '2', '27', null, '2', '0');
INSERT INTO `sys_privilege` VALUES ('30', '修改', '/menuCategory/{id}', 'put', '2', '27', null, '3', '0');
INSERT INTO `sys_privilege` VALUES ('31', '删除', '/menuCategory/{id}', 'delete', '2', '27', null, '4', '0');
INSERT INTO `sys_privilege` VALUES ('32', '设置模块', '/menuCategory/{id}/modules', 'put', '2', '27', null, '5', '0');
INSERT INTO `sys_privilege` VALUES ('33', '权限列表', '/privilege/all', 'get', '2', '13', null, null, '0');
INSERT INTO `sys_privilege` VALUES ('34', '设置权限', '/rolePrivilege/{roleId}', 'put', '2', '6', null, '5', '0');
INSERT INTO `sys_privilege` VALUES ('35', '获取权限', '/rolePrivilege/{roleId}', 'get', '2', '6', null, '6', '0');
INSERT INTO `sys_privilege` VALUES ('37', '主题分类', '/topicCategory', 'get', '1', '0', '26', '5', '0');
INSERT INTO `sys_privilege` VALUES ('38', '列表', '/topicCategory', 'get', '2', '37', null, '1', '0');
INSERT INTO `sys_privilege` VALUES ('39', '添加', '/topicCategory', 'post', '2', '37', null, '2', '0');
INSERT INTO `sys_privilege` VALUES ('40', '修改', '/topicCategory/{id}', 'put', '2', '37', null, '3', '0');
INSERT INTO `sys_privilege` VALUES ('41', '删除', '/topicCategory/{id}', 'delete', '2', '37', null, '4', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '圣骑士长', '0');
INSERT INTO `sys_role` VALUES ('2', '枢机卿', '0');
INSERT INTO `sys_role` VALUES ('3', '圣骑士', '0');
INSERT INTO `sys_role` VALUES ('4', '见习圣骑士', '0');
INSERT INTO `sys_role` VALUES ('5', '骑士', '0');
INSERT INTO `sys_role` VALUES ('9', 'asdf', '1');
INSERT INTO `sys_role` VALUES ('10', 'xxxx', '1');
INSERT INTO `sys_role` VALUES ('11', 'zzzz', '1');
INSERT INTO `sys_role` VALUES ('12', 'sdfsdf', '1');
INSERT INTO `sys_role` VALUES ('13', 'fff', '1');
INSERT INTO `sys_role` VALUES ('14', 'zzzz', '1');
INSERT INTO `sys_role` VALUES ('15', 'zzzzz', '1');
INSERT INTO `sys_role` VALUES ('16', 'zzzzzzzz', '1');
INSERT INTO `sys_role` VALUES ('17', 'jjjjjjj', '1');
INSERT INTO `sys_role` VALUES ('18', 'hgfhgh', '1');
INSERT INTO `sys_role` VALUES ('19', 'ghtry', '1');
INSERT INTO `sys_role` VALUES ('20', 'ertert', '1');
INSERT INTO `sys_role` VALUES ('21', 'ertertert', '1');
INSERT INTO `sys_role` VALUES ('23', '7777', '1');
INSERT INTO `sys_role` VALUES ('24', 'lllllyyyyyyy', '1');
INSERT INTO `sys_role` VALUES ('25', 'ssssssssssss', '1');
INSERT INTO `sys_role` VALUES ('27', 'yyy', '1');
INSERT INTO `sys_role` VALUES ('28', '123', '0');
INSERT INTO `sys_role` VALUES ('29', 'ttt', '1');

-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_privilege
-- ----------------------------
INSERT INTO `sys_role_privilege` VALUES ('1', '1');
INSERT INTO `sys_role_privilege` VALUES ('1', '2');
INSERT INTO `sys_role_privilege` VALUES ('1', '3');
INSERT INTO `sys_role_privilege` VALUES ('1', '4');
INSERT INTO `sys_role_privilege` VALUES ('1', '5');
INSERT INTO `sys_role_privilege` VALUES ('1', '6');
INSERT INTO `sys_role_privilege` VALUES ('1', '7');
INSERT INTO `sys_role_privilege` VALUES ('1', '8');
INSERT INTO `sys_role_privilege` VALUES ('1', '9');
INSERT INTO `sys_role_privilege` VALUES ('1', '10');
INSERT INTO `sys_role_privilege` VALUES ('1', '13');
INSERT INTO `sys_role_privilege` VALUES ('1', '15');
INSERT INTO `sys_role_privilege` VALUES ('1', '17');
INSERT INTO `sys_role_privilege` VALUES ('1', '18');
INSERT INTO `sys_role_privilege` VALUES ('1', '19');
INSERT INTO `sys_role_privilege` VALUES ('1', '22');
INSERT INTO `sys_role_privilege` VALUES ('1', '25');
INSERT INTO `sys_role_privilege` VALUES ('1', '26');
INSERT INTO `sys_role_privilege` VALUES ('1', '27');
INSERT INTO `sys_role_privilege` VALUES ('1', '28');
INSERT INTO `sys_role_privilege` VALUES ('1', '29');
INSERT INTO `sys_role_privilege` VALUES ('1', '30');
INSERT INTO `sys_role_privilege` VALUES ('1', '31');
INSERT INTO `sys_role_privilege` VALUES ('1', '32');
INSERT INTO `sys_role_privilege` VALUES ('1', '33');
INSERT INTO `sys_role_privilege` VALUES ('1', '34');
INSERT INTO `sys_role_privilege` VALUES ('1', '35');
INSERT INTO `sys_role_privilege` VALUES ('1', '37');
INSERT INTO `sys_role_privilege` VALUES ('1', '38');
INSERT INTO `sys_role_privilege` VALUES ('1', '39');
INSERT INTO `sys_role_privilege` VALUES ('1', '40');
INSERT INTO `sys_role_privilege` VALUES ('1', '41');
INSERT INTO `sys_role_privilege` VALUES ('2', '1');
INSERT INTO `sys_role_privilege` VALUES ('2', '2');
INSERT INTO `sys_role_privilege` VALUES ('2', '3');
INSERT INTO `sys_role_privilege` VALUES ('2', '4');
INSERT INTO `sys_role_privilege` VALUES ('2', '5');
INSERT INTO `sys_role_privilege` VALUES ('2', '6');
INSERT INTO `sys_role_privilege` VALUES ('2', '7');
INSERT INTO `sys_role_privilege` VALUES ('2', '8');
INSERT INTO `sys_role_privilege` VALUES ('2', '9');
INSERT INTO `sys_role_privilege` VALUES ('2', '10');
INSERT INTO `sys_role_privilege` VALUES ('2', '13');
INSERT INTO `sys_role_privilege` VALUES ('2', '15');
INSERT INTO `sys_role_privilege` VALUES ('2', '17');
INSERT INTO `sys_role_privilege` VALUES ('2', '18');
INSERT INTO `sys_role_privilege` VALUES ('2', '19');
INSERT INTO `sys_role_privilege` VALUES ('2', '22');
INSERT INTO `sys_role_privilege` VALUES ('2', '25');
INSERT INTO `sys_role_privilege` VALUES ('2', '26');
INSERT INTO `sys_role_privilege` VALUES ('2', '27');
INSERT INTO `sys_role_privilege` VALUES ('2', '28');
INSERT INTO `sys_role_privilege` VALUES ('2', '29');
INSERT INTO `sys_role_privilege` VALUES ('2', '30');
INSERT INTO `sys_role_privilege` VALUES ('2', '31');
INSERT INTO `sys_role_privilege` VALUES ('2', '32');
INSERT INTO `sys_role_privilege` VALUES ('2', '33');
INSERT INTO `sys_role_privilege` VALUES ('2', '34');
INSERT INTO `sys_role_privilege` VALUES ('2', '35');
INSERT INTO `sys_role_privilege` VALUES ('4', '1');
INSERT INTO `sys_role_privilege` VALUES ('4', '2');
INSERT INTO `sys_role_privilege` VALUES ('4', '3');
INSERT INTO `sys_role_privilege` VALUES ('4', '4');
INSERT INTO `sys_role_privilege` VALUES ('4', '5');
INSERT INTO `sys_role_privilege` VALUES ('4', '6');
INSERT INTO `sys_role_privilege` VALUES ('4', '7');
INSERT INTO `sys_role_privilege` VALUES ('4', '8');
INSERT INTO `sys_role_privilege` VALUES ('4', '9');
INSERT INTO `sys_role_privilege` VALUES ('4', '10');
INSERT INTO `sys_role_privilege` VALUES ('4', '13');
INSERT INTO `sys_role_privilege` VALUES ('4', '15');
INSERT INTO `sys_role_privilege` VALUES ('4', '17');
INSERT INTO `sys_role_privilege` VALUES ('4', '18');
INSERT INTO `sys_role_privilege` VALUES ('4', '19');
INSERT INTO `sys_role_privilege` VALUES ('4', '22');
INSERT INTO `sys_role_privilege` VALUES ('4', '25');
INSERT INTO `sys_role_privilege` VALUES ('4', '26');
INSERT INTO `sys_role_privilege` VALUES ('4', '27');
INSERT INTO `sys_role_privilege` VALUES ('4', '28');
INSERT INTO `sys_role_privilege` VALUES ('4', '29');
INSERT INTO `sys_role_privilege` VALUES ('4', '30');
INSERT INTO `sys_role_privilege` VALUES ('4', '31');
INSERT INTO `sys_role_privilege` VALUES ('4', '32');
INSERT INTO `sys_role_privilege` VALUES ('4', '33');
INSERT INTO `sys_role_privilege` VALUES ('4', '34');
INSERT INTO `sys_role_privilege` VALUES ('4', '35');
INSERT INTO `sys_role_privilege` VALUES ('4', '36');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(200) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `role_id` int(11) DEFAULT NULL,
  `birthday` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `role_id_index` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'Meliodas', '123', '100000@sevensins.com', '梅利奥达斯', null, '18866669999', '0', '1', '2017-02-11 14:18:49');
INSERT INTO `sys_user` VALUES ('2', 'Elizabeth Liones', '123', '100000@sevensins.com', '伊丽莎白·里昂妮丝', null, '18866669999', '0', '1', null);
INSERT INTO `sys_user` VALUES ('16', 'Diane', '123', '100000@sevensins.com', '黛安', null, '18866669999', '0', '1', null);
INSERT INTO `sys_user` VALUES ('17', 'Ban', '123', '100000@sevensins.com', '班', null, '18866669999', '0', '1', null);
INSERT INTO `sys_user` VALUES ('18', 'King', '', '100000@sevensins.com', '金', null, '18866669999', '0', '1', '2015-11-26 08:00:00');
INSERT INTO `sys_user` VALUES ('19', 'Gowther', '', '100000@sevensins.com', '高瑟', null, '18866669999', '1', '1', null);
INSERT INTO `sys_user` VALUES ('20', 'Merlin', '', '100000@sevensins.com', '玛琳', null, '18866669999', '0', '1', null);
INSERT INTO `sys_user` VALUES ('21', 'Escanor', '', '100000@sevensins.com', '艾斯卡诺', null, '18866669999', '1', '4', '2017-02-09 08:00:00');
INSERT INTO `sys_user` VALUES ('23', 'adf', '', 'asf@qq.com', 'asdf111', null, '11111111111', '1', '2', null);
INSERT INTO `sys_user` VALUES ('37', '123yyy', '', 'add@163.com', '123', null, '123123123123', '0', '3', '2017-02-03 08:00:00');
INSERT INTO `sys_user` VALUES ('64', '123xxx', '', 'ax@yeah.net', '123', null, '123321321321', '1', '3', '2017-02-03 08:00:00');
INSERT INTO `sys_user` VALUES ('65', '123q', '', '123@qq.com', '123', null, '123', '0', '3', '2017-02-12 23:44:53');
INSERT INTO `sys_user` VALUES ('66', '123w', '123', '123', '123', null, '123', '0', '4', '2017-02-12 23:44:55');
INSERT INTO `sys_user` VALUES ('67', '123e', '123', '123', '13', null, '13', '1', '2', '2017-02-12 23:44:56');
INSERT INTO `sys_user` VALUES ('68', '1r', '1', '1', '1', null, '1', '0', '4', '2017-02-12 23:44:57');
INSERT INTO `sys_user` VALUES ('69', '1t', '1', '1', '1', null, '1', '0', '1', '2017-02-12 23:44:59');
INSERT INTO `sys_user` VALUES ('70', '1111116666666666', '', '1', '1', null, '1', '0', '4', null);
INSERT INTO `sys_user` VALUES ('71', '2y', '', '2111', '211111111', null, '2', '1', '5', '2017-02-12 23:45:00');
INSERT INTO `sys_user` VALUES ('75', '111u', '', '1', '1177777777', null, '1', '0', '3', '2017-02-12 23:45:01');
INSERT INTO `sys_user` VALUES ('76', '123i', '123', '123', '123', null, '123', '1', '3', '2017-02-12 23:45:02');
INSERT INTO `sys_user` VALUES ('77', '1o', '1', '1', '1', null, '1', '0', '3', '2017-02-12 23:45:03');
INSERT INTO `sys_user` VALUES ('78', '1p', '', '1', '1123', null, '1', '1', '4', '2017-02-12 23:45:04');
INSERT INTO `sys_user` VALUES ('79', '1l', '', '1', '1', null, '1', '1', '4', '2017-02-12 23:45:06');
INSERT INTO `sys_user` VALUES ('80', '1k', '', 'abc@qq.com', '11111', null, '1', '1', '3', '2017-02-12 23:45:09');
INSERT INTO `sys_user` VALUES ('84', '1j', '', '1', '1', null, '1', '0', '2', '2017-02-12 23:45:11');
INSERT INTO `sys_user` VALUES ('86', '1m', '', '1', '111', null, '1', '0', '4', '2017-02-12 23:45:16');
INSERT INTO `sys_user` VALUES ('87', '1n', '', 'seven-sins@163.com', '1', null, '1', '1', '3', '2017-02-12 23:45:17');
INSERT INTO `sys_user` VALUES ('88', '1b', '', 'seven@163.com', '1', null, '1', '0', '2', '2017-02-12 23:45:18');
INSERT INTO `sys_user` VALUES ('89', '2v', '', 'qwe@qq.com', '3', null, '2', '0', '2', '2017-02-12 23:45:19');
INSERT INTO `sys_user` VALUES ('90', '1c', '', '', '1', null, '1', '0', '3', '2017-02-12 23:45:22');
INSERT INTO `sys_user` VALUES ('92', 'mmmmmm', '', '', 'nuc', null, '', '0', '2', '1980-01-01 07:30:00');
INSERT INTO `sys_user` VALUES ('93', 'test111', '', '', 'test111', null, '', '1', '2', '2017-02-21 08:00:00');
INSERT INTO `sys_user` VALUES ('95', 'test1112', '1', '1', '1', null, '1', '0', '2', '2017-02-12 23:47:27');

-- ----------------------------
-- Table structure for task_execution
-- ----------------------------
DROP TABLE IF EXISTS `task_execution`;
CREATE TABLE `task_execution` (
  `TASK_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `TASK_NAME` varchar(100) DEFAULT NULL,
  `EXIT_CODE` int(11) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `ERROR_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EXTERNAL_EXECUTION_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_execution
-- ----------------------------
INSERT INTO `task_execution` VALUES ('1', '2016-12-25 19:58:08', '2016-12-25 19:58:09', 'application:dev:8443', '0', null, null, '2016-12-25 19:58:09', null);
INSERT INTO `task_execution` VALUES ('2', '2016-12-25 20:00:19', '2016-12-25 20:00:20', 'application:dev:8443', '0', null, null, '2016-12-25 20:00:20', null);
INSERT INTO `task_execution` VALUES ('3', '2016-12-25 20:01:13', '2016-12-25 20:01:13', 'application:dev:8443', '0', null, null, '2016-12-25 20:01:13', null);
INSERT INTO `task_execution` VALUES ('4', '2016-12-25 20:05:06', '2016-12-25 20:05:07', 'application:dev:8443', '0', null, null, '2016-12-25 20:05:07', null);
INSERT INTO `task_execution` VALUES ('5', '2016-12-25 20:13:00', '2016-12-25 20:13:01', 'application:dev:8443', '0', null, null, '2016-12-25 20:13:01', null);
INSERT INTO `task_execution` VALUES ('6', '2016-12-25 20:43:29', '2016-12-25 20:43:30', 'application:dev:8443', '0', null, null, '2016-12-25 20:43:30', null);
INSERT INTO `task_execution` VALUES ('7', '2016-12-25 21:05:08', '2016-12-25 21:05:47', 'application:dev:8443', '0', null, null, '2016-12-25 21:06:06', null);

-- ----------------------------
-- Table structure for task_execution_params
-- ----------------------------
DROP TABLE IF EXISTS `task_execution_params`;
CREATE TABLE `task_execution_params` (
  `TASK_EXECUTION_ID` bigint(20) NOT NULL,
  `TASK_PARAM` varchar(2500) DEFAULT NULL,
  KEY `TASK_EXEC_PARAMS_FK` (`TASK_EXECUTION_ID`),
  CONSTRAINT `TASK_EXEC_PARAMS_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_execution_params
-- ----------------------------
INSERT INTO `task_execution_params` VALUES ('3', 'some');
INSERT INTO `task_execution_params` VALUES ('3', 'parameters');

-- ----------------------------
-- Table structure for task_seq
-- ----------------------------
DROP TABLE IF EXISTS `task_seq`;
CREATE TABLE `task_seq` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_seq
-- ----------------------------
INSERT INTO `task_seq` VALUES ('7', '0');

-- ----------------------------
-- Table structure for task_task_batch
-- ----------------------------
DROP TABLE IF EXISTS `task_task_batch`;
CREATE TABLE `task_task_batch` (
  `TASK_EXECUTION_ID` bigint(20) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  KEY `TASK_EXEC_BATCH_FK` (`TASK_EXECUTION_ID`),
  CONSTRAINT `TASK_EXEC_BATCH_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_task_batch
-- ----------------------------

-- ----------------------------
-- Table structure for topic_category
-- ----------------------------
DROP TABLE IF EXISTS `topic_category`;
CREATE TABLE `topic_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `position` int(10) unsigned zerofill DEFAULT '0000000000',
  `status` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of topic_category
-- ----------------------------
INSERT INTO `topic_category` VALUES ('1', 'java', '0000000001', '0000000000');
INSERT INTO `topic_category` VALUES ('2', 'html/css', '0000000002', '0000000000');
INSERT INTO `topic_category` VALUES ('3', 'javascript', '0000000003', '0000000000');
INSERT INTO `topic_category` VALUES ('4', 'cocos2dx', '0000000004', '0000000000');
INSERT INTO `topic_category` VALUES ('5', 'c/c++', '0000000005', '0000000000');
INSERT INTO `topic_category` VALUES ('7', '其他', '0000000006', '0000000000');
