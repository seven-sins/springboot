/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.6.30 : Database - forum
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`forum` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `forum`;

/*Table structure for table `QRTZ_BLOB_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;

CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_BLOB_TRIGGERS` */

/*Table structure for table `QRTZ_CALENDARS` */

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;

CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_CALENDARS` */

/*Table structure for table `QRTZ_CRON_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;

CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_CRON_TRIGGERS` */

/*Table structure for table `QRTZ_FIRED_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;

CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_FIRED_TRIGGERS` */

/*Table structure for table `QRTZ_JOB_DETAILS` */

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;

CREATE TABLE `QRTZ_JOB_DETAILS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_JOB_DETAILS` */

/*Table structure for table `QRTZ_LOCKS` */

DROP TABLE IF EXISTS `QRTZ_LOCKS`;

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_LOCKS` */

insert  into `QRTZ_LOCKS`(`SCHED_NAME`,`LOCK_NAME`) values ('schedulerFactoryBean','TRIGGER_ACCESS');

/*Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS` */

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_PAUSED_TRIGGER_GRPS` */

/*Table structure for table `QRTZ_SCHEDULER_STATE` */

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;

CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(19) NOT NULL,
  `CHECKIN_INTERVAL` bigint(19) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_SCHEDULER_STATE` */

/*Table structure for table `QRTZ_SIMPLE_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_SIMPLE_TRIGGERS` */

/*Table structure for table `QRTZ_SIMPROP_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
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
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_SIMPROP_TRIGGERS` */

/*Table structure for table `QRTZ_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;

CREATE TABLE `QRTZ_TRIGGERS` (
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
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `QRTZ_TRIGGERS` */

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('client_id','rest_api','client_secret','bar,read,write','password,authorization_code,refresh_token',NULL,NULL,3600,2600,NULL,1);

/*Table structure for table `oauth_client_token` */

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_fired_triggers` */

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

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

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

/*Data for the table `qrtz_job_details` */

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

insert  into `qrtz_locks`(`SCHED_NAME`,`LOCK_NAME`) values ('schedulerFactoryBean','TRIGGER_ACCESS');

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(19) NOT NULL,
  `CHECKIN_INTERVAL` bigint(19) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

/*Table structure for table `sys_privilege` */

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

/*Data for the table `sys_privilege` */

insert  into `sys_privilege`(`id`,`name`,`url`,`method`,`type`,`parent_id`,`menu_category_id`,`position`,`status`) values (1,'用户管理','/user','get',1,0,25,2,0),(2,'列表','/api/user','get',2,1,0,NULL,0),(3,'添加','/api/user','post',2,1,0,NULL,0),(4,'修改','/api/user/{id}','put',2,1,0,NULL,0),(5,'删除','/api/user/{id}','delete',2,1,0,NULL,0),(6,'角色管理','/role','get',1,0,25,1,0),(7,'列表','/api/role','get',2,6,0,1,0),(8,'添加','/api/role','post',2,6,0,2,0),(9,'修改','/api/role/{id}','put',2,6,0,3,0),(10,'删除','/api/role/{id}','delete',2,6,0,4,0),(13,'权限管理','/privilege','get',1,0,25,3,0),(15,'列表','/api/privilege','get',2,13,0,NULL,0),(17,'添加','/api/privilege','post',2,13,0,NULL,0),(18,'修改','/api/privilege/{id}','put',2,13,0,NULL,0),(19,'删除','/api/privilege/{id}','delete',2,13,0,NULL,0),(22,'模块列表','/api/privilege/list','get',2,13,0,NULL,0),(25,'系统设置','/api/sysSettings','get',0,-1,0,1,0),(26,'主题管理','/api/topicManage','get',0,-1,0,2,0),(27,'菜单分类','/menuCategory','get',1,0,25,4,0),(28,'列表','/api/menuCategory','get',2,27,NULL,1,0),(29,'添加','/api/menuCategory','post',2,27,NULL,2,0),(30,'修改','/api/menuCategory/{id}','put',2,27,NULL,3,0),(31,'删除','/api/menuCategory/{id}','delete',2,27,NULL,4,0),(32,'设置模块','/api/menuCategory/{id}/modules','put',2,27,NULL,5,0),(33,'权限列表','/api/privilege/all','get',2,13,NULL,NULL,0),(34,'设置权限','/api/rolePrivilege/{roleId}','put',2,6,NULL,5,0),(35,'获取权限','/api/rolePrivilege/{roleId}','get',2,6,NULL,6,0),(37,'主题分类','/topicCategory','get',1,0,26,5,0),(38,'列表','/api/topicCategory','get',2,37,NULL,1,0),(39,'添加','/api/topicCategory','post',2,37,NULL,2,0),(40,'修改','/api/topicCategory/{id}','put',2,37,NULL,3,0),(41,'删除','/api/topicCategory/{id}','delete',2,37,NULL,4,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`status`) values (1,'圣骑士长',0),(2,'枢机卿',0),(3,'圣骑士',0),(4,'见习圣骑士',0),(5,'骑士',0),(9,'asdf',1),(10,'xxxx',1),(11,'zzzz',1),(12,'sdfsdf',1),(13,'fff',1),(14,'zzzz',1),(15,'zzzzz',1),(16,'zzzzzzzz',1),(17,'jjjjjjj',1),(18,'hgfhgh',1),(19,'ghtry',1),(20,'ertert',1),(21,'ertertert',1),(23,'7777',1),(24,'lllllyyyyyyy',1),(25,'ssssssssssss',1),(27,'yyy',1),(28,'123',0),(29,'ttt',1);

/*Table structure for table `sys_role_privilege` */

DROP TABLE IF EXISTS `sys_role_privilege`;

CREATE TABLE `sys_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_privilege` */

insert  into `sys_role_privilege`(`role_id`,`privilege_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,13),(1,15),(1,17),(1,18),(1,19),(1,22),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,37),(1,38),(1,39),(1,40),(1,41),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,13),(2,15),(2,17),(2,18),(2,19),(2,22),(2,25),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32),(2,33),(2,34),(2,35),(4,1),(4,2),(4,3),(4,4),(4,5),(4,6),(4,7),(4,8),(4,9),(4,10),(4,13),(4,15),(4,17),(4,18),(4,19),(4,22),(4,25),(4,26),(4,27),(4,28),(4,29),(4,30),(4,31),(4,32),(4,33),(4,34),(4,35),(4,36);

/*Table structure for table `sys_user` */

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

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`pass_word`,`email`,`nick_name`,`avatar`,`phone_number`,`status`,`role_id`,`birthday`) values (1,'Meliodas','123','100000@sevensins.com','梅利奥达斯',NULL,'18866669999',0,1,'2017-02-11 14:18:49'),(2,'Elizabeth Liones','123','100000@sevensins.com','伊丽莎白·里昂妮丝',NULL,'18866669999',0,1,NULL),(16,'Diane','123','100000@sevensins.com','黛安',NULL,'18866669999',0,1,NULL),(17,'Ban','123','100000@sevensins.com','班',NULL,'18866669999',0,1,NULL),(18,'King','','100000@sevensins.com','金',NULL,'18866669999',0,1,'2015-11-26 08:00:00'),(19,'Gowther','','100000@sevensins.com','高瑟',NULL,'18866669999',1,1,NULL),(20,'Merlin','','100000@sevensins.com','玛琳',NULL,'18866669999',0,1,NULL),(21,'Escanor','','100000@sevensins.com','艾斯卡诺',NULL,'18866669999',1,4,'2017-02-09 08:00:00'),(23,'adf','','asf@qq.com','asdf111',NULL,'11111111111',1,2,NULL),(37,'123yyy','','add@163.com','123',NULL,'123123123123',0,3,'2017-02-03 08:00:00'),(64,'123xxx','','ax@yeah.net','123',NULL,'123321321321',1,3,'2017-02-03 08:00:00'),(65,'123q','','123@qq.com','123',NULL,'123',0,3,'2017-02-12 23:44:53'),(66,'123w','123','123','123',NULL,'123',0,4,'2017-02-12 23:44:55'),(67,'123e','123','123','13',NULL,'13',1,2,'2017-02-12 23:44:56'),(68,'1r','1','1','1',NULL,'1',0,4,'2017-02-12 23:44:57'),(69,'1t','1','1','1',NULL,'1',0,1,'2017-02-12 23:44:59'),(70,'1111116666666666','','1','1',NULL,'1',0,4,NULL),(71,'2y','','2111','211111111',NULL,'2',1,5,'2017-02-12 23:45:00'),(75,'111u','','1','1177777777',NULL,'1',0,3,'2017-02-12 23:45:01'),(76,'123i','123','123','123',NULL,'123',1,3,'2017-02-12 23:45:02'),(77,'1o','1','1','1',NULL,'1',0,3,'2017-02-12 23:45:03'),(78,'1p','','1','1123',NULL,'1',1,4,'2017-02-12 23:45:04'),(79,'1l','','1','1',NULL,'1',1,4,'2017-02-12 23:45:06'),(80,'1k','','abc@qq.com','11111',NULL,'1',1,3,'2017-02-12 23:45:09'),(84,'1j','','1','1',NULL,'1',0,2,'2017-02-12 23:45:11'),(86,'1m','','1','111',NULL,'1',0,4,'2017-02-12 23:45:16'),(87,'1n','','seven-sins@163.com','1',NULL,'1',1,3,'2017-02-12 23:45:17'),(88,'1b','','seven@163.com','1',NULL,'1',0,2,'2017-02-12 23:45:18'),(89,'2v','','qwe@qq.com','3',NULL,'2',0,2,'2017-02-12 23:45:19'),(90,'1c','','','1',NULL,'1',0,3,'2017-02-12 23:45:22'),(92,'mmmmmm1','','','nuc',NULL,'',0,2,'1980-01-01 08:00:00'),(95,'test1112','1','1','1',NULL,'1',0,2,'2017-02-12 23:47:27');

/*Table structure for table `sys_user_tmp` */

DROP TABLE IF EXISTS `sys_user_tmp`;

CREATE TABLE `sys_user_tmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(200) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sys_user_tmp` */

/*Table structure for table `task_execution` */

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

/*Data for the table `task_execution` */

insert  into `task_execution`(`TASK_EXECUTION_ID`,`START_TIME`,`END_TIME`,`TASK_NAME`,`EXIT_CODE`,`EXIT_MESSAGE`,`ERROR_MESSAGE`,`LAST_UPDATED`,`EXTERNAL_EXECUTION_ID`) values (1,'2016-12-25 19:58:08','2016-12-25 19:58:09','application:dev:8443',0,NULL,NULL,'2016-12-25 19:58:09',NULL),(2,'2016-12-25 20:00:19','2016-12-25 20:00:20','application:dev:8443',0,NULL,NULL,'2016-12-25 20:00:20',NULL),(3,'2016-12-25 20:01:13','2016-12-25 20:01:13','application:dev:8443',0,NULL,NULL,'2016-12-25 20:01:13',NULL),(4,'2016-12-25 20:05:06','2016-12-25 20:05:07','application:dev:8443',0,NULL,NULL,'2016-12-25 20:05:07',NULL),(5,'2016-12-25 20:13:00','2016-12-25 20:13:01','application:dev:8443',0,NULL,NULL,'2016-12-25 20:13:01',NULL),(6,'2016-12-25 20:43:29','2016-12-25 20:43:30','application:dev:8443',0,NULL,NULL,'2016-12-25 20:43:30',NULL),(7,'2016-12-25 21:05:08','2016-12-25 21:05:47','application:dev:8443',0,NULL,NULL,'2016-12-25 21:06:06',NULL);

/*Table structure for table `task_execution_params` */

DROP TABLE IF EXISTS `task_execution_params`;

CREATE TABLE `task_execution_params` (
  `TASK_EXECUTION_ID` bigint(20) NOT NULL,
  `TASK_PARAM` varchar(2500) DEFAULT NULL,
  KEY `TASK_EXEC_PARAMS_FK` (`TASK_EXECUTION_ID`),
  CONSTRAINT `TASK_EXEC_PARAMS_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task_execution_params` */

insert  into `task_execution_params`(`TASK_EXECUTION_ID`,`TASK_PARAM`) values (3,'some'),(3,'parameters');

/*Table structure for table `task_seq` */

DROP TABLE IF EXISTS `task_seq`;

CREATE TABLE `task_seq` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `task_seq` */

insert  into `task_seq`(`ID`,`UNIQUE_KEY`) values (7,'0');

/*Table structure for table `task_task_batch` */

DROP TABLE IF EXISTS `task_task_batch`;

CREATE TABLE `task_task_batch` (
  `TASK_EXECUTION_ID` bigint(20) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  KEY `TASK_EXEC_BATCH_FK` (`TASK_EXECUTION_ID`),
  CONSTRAINT `TASK_EXEC_BATCH_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task_task_batch` */

/*Table structure for table `topic_category` */

DROP TABLE IF EXISTS `topic_category`;

CREATE TABLE `topic_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `position` int(10) unsigned zerofill DEFAULT '0000000000',
  `status` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `topic_category` */

insert  into `topic_category`(`id`,`name`,`position`,`status`) values (1,'java',0000000001,0000000000),(2,'html/css',0000000002,0000000000),(3,'javascript',0000000003,0000000000),(4,'cocos2dx',0000000004,0000000000),(5,'c/c++',0000000005,0000000000),(7,'其他',0000000006,0000000000);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
