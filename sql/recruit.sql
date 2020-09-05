/*
SQLyog Professional v12.5.0 (64 bit)
MySQL - 5.7.26 : Database - recruit
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`recruit` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `recruit`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`password`) values 
(1,'Yuu','e10adc3949ba59abbe56e057f20f883e');

/*Table structure for table `bid` */

DROP TABLE IF EXISTS `bid`;

CREATE TABLE `bid` (
  `id` bigint(20) NOT NULL COMMENT '投标ID',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '职业者ID',
  `bid_price` double DEFAULT NULL COMMENT '投标价格',
  `delivery_desc` varchar(255) DEFAULT NULL COMMENT '交货时间描述，例如 1 天',
  `delivery_time` timestamp NULL DEFAULT NULL COMMENT '交货时间',
  `bid_status` tinyint(4) DEFAULT NULL COMMENT '竞标状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bid` */

insert  into `bid`(`id`,`task_id`,`employee_id`,`bid_price`,`delivery_desc`,`delivery_time`,`bid_status`,`create_time`) values 
(157124285696981,157124090699406,157124225706439,100,'3小时','2019-10-17 03:20:57',0,'2019-10-17 00:20:57'),
(157124347413656,157124009918832,157124288026264,1500,'1小时','2019-10-17 01:31:14',1,'2019-10-17 00:31:14');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL COMMENT '雇员ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `head_img` varchar(255) DEFAULT 'http://recruit1.oss-cn-shenzhen.aliyuncs.com/4aa3d423-b8cf-4520-91ba-3f30b578973f.png' COMMENT '头像地址',
  `tagline` varchar(255) DEFAULT NULL COMMENT '标语',
  `profile` text COMMENT '个人简介',
  `browse_count` int(11) DEFAULT '0' COMMENT '主页被浏览次数',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employee` */

insert  into `employee`(`id`,`username`,`password`,`email`,`phone`,`head_img`,`tagline`,`profile`,`browse_count`,`create_time`) values 
(157124225706439,'Yuu','123','1225459207@qq.com','13055206361','http://recruit1.oss-cn-shenzhen.aliyuncs.com/dc7fa65a-ae93-4938-8f91-01f6c6e7ee4c.jpg','Good Man!','来跟妲己一起玩耍呀~',0,'2019-10-17 00:10:57'),
(157124288026264,'Yuu2','123',NULL,NULL,'http://recruit1.oss-cn-shenzhen.aliyuncs.com/10f65b3a-e73d-4d8b-b95b-3841534ea0dc.png',NULL,NULL,27,'2019-10-17 00:21:20');

/*Table structure for table `employee_bookmarked` */

DROP TABLE IF EXISTS `employee_bookmarked`;

CREATE TABLE `employee_bookmarked` (
  `id` bigint(20) NOT NULL COMMENT '雇员收藏任务ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '雇员ID',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employee_bookmarked` */

insert  into `employee_bookmarked`(`id`,`employee_id`,`task_id`) values 
(157124280249465,157124225706439,157124114265141),
(157124281570732,157124225706439,157124090699406),
(157124282308079,157124225706439,157124009918832),
(157124468269738,157124288026264,157124114265141);

/*Table structure for table `employee_skill` */

DROP TABLE IF EXISTS `employee_skill`;

CREATE TABLE `employee_skill` (
  `id` bigint(20) NOT NULL COMMENT '雇员技能对应ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '雇员ID',
  `skill_name` varchar(255) DEFAULT NULL COMMENT '技能名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employee_skill` */

insert  into `employee_skill`(`id`,`employee_id`,`skill_name`) values 
(157124228454456,157124225706439,'Java'),
(157124228840540,157124225706439,'MySQL'),
(157124229314145,157124225706439,'SSM'),
(157124229869194,157124225706439,'Spring Boot');

/*Table structure for table `employer` */

DROP TABLE IF EXISTS `employer`;

CREATE TABLE `employer` (
  `id` bigint(20) NOT NULL COMMENT '雇主ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employer` */

insert  into `employer`(`id`,`username`,`password`,`head_img`,`phone`,`email`,`create_time`) values 
(157123889925612,'Yuu','123456','http://recruit1.oss-cn-shenzhen.aliyuncs.com/37872ab6-0460-4905-85b1-5350e09df6e4.jpg','13055206361','1225459207@qq.com','2019-10-16 23:14:59');

/*Table structure for table `home_bower` */

DROP TABLE IF EXISTS `home_bower`;

CREATE TABLE `home_bower` (
  `id` bigint(20) NOT NULL COMMENT '主页浏览表',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '雇员ID',
  `employer_id` bigint(20) DEFAULT NULL COMMENT '雇主ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `home_bower` */

insert  into `home_bower`(`id`,`employee_id`,`employer_id`,`create_time`) values 
(157124308317312,157124288026264,157123889925612,'2019-10-17 00:24:43'),
(157124362128255,157124288026264,157123889925612,'2019-10-17 00:33:41'),
(157124394802989,157124288026264,157123889925612,'2019-10-17 00:39:08'),
(157124395181598,157124288026264,157123889925612,'2019-10-17 00:39:12'),
(157124399959641,157124288026264,157123889925612,'2019-10-17 00:40:00'),
(157124400572249,157124288026264,157123889925612,'2019-10-17 00:40:06'),
(157124436645011,157124288026264,157123889925612,'2019-10-17 00:46:06'),
(157124444335881,157124288026264,157123889925612,'2019-10-17 00:47:23'),
(157124444529063,157124288026264,157123889925612,'2019-10-17 00:47:25'),
(157124444718472,157124288026264,157123889925612,'2019-10-17 00:47:27'),
(157124444939622,157124288026264,157123889925612,'2019-10-17 00:47:29'),
(157124445119862,157124288026264,157123889925612,'2019-10-17 00:47:31'),
(157124460085914,157124288026264,157123889925612,'2019-10-17 00:50:01'),
(157124460223002,157124288026264,157123889925612,'2019-10-17 00:50:02'),
(157124460257603,157124288026264,157123889925612,'2019-10-17 00:50:03'),
(157124460283010,157124288026264,157123889925612,'2019-10-17 00:50:03'),
(157124460285719,157124288026264,157123889925612,'2019-10-17 00:50:03'),
(157124460324956,157124288026264,157123889925612,'2019-10-17 00:50:03'),
(157124460338967,157124288026264,157123889925612,'2019-10-17 00:50:03'),
(157124460378313,157124288026264,157123889925612,'2019-10-17 00:50:04'),
(157124460389161,157124288026264,157123889925612,'2019-10-17 00:50:04'),
(157124460425585,157124288026264,157123889925612,'2019-10-17 00:50:04'),
(157124460445058,157124288026264,157123889925612,'2019-10-17 00:50:04'),
(157124460465351,157124288026264,157123889925612,'2019-10-17 00:50:05'),
(157124460475301,157124288026264,157123889925612,'2019-10-17 00:50:05'),
(157124460514124,157124288026264,157123889925612,'2019-10-17 00:50:05'),
(157124460528858,157124288026264,157123889925612,'2019-10-17 00:50:05');

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL COMMENT '任务ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '任务分类ID',
  `employer_id` bigint(20) DEFAULT NULL COMMENT '任务发布雇主ID',
  `task_title` varchar(255) DEFAULT NULL COMMENT '任务标题',
  `task_profile` varchar(255) DEFAULT NULL COMMENT '任务简介',
  `task_desc` text COMMENT '任务描述',
  `fees_low` double DEFAULT NULL COMMENT '最低预算价格',
  `fees_high` double DEFAULT NULL COMMENT '最高预算价格',
  `fees_file` varchar(255) DEFAULT NULL COMMENT '任务附件地址',
  `filename` varchar(255) DEFAULT NULL COMMENT '附件文件名称',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '完成任务雇员ID',
  `task_price` double DEFAULT NULL COMMENT '任务成交价格',
  `task_status` tinyint(4) DEFAULT NULL COMMENT '任务状态',
  `close_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `bid_time` timestamp NULL DEFAULT NULL COMMENT '中标时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task` */

insert  into `task`(`id`,`category_id`,`employer_id`,`task_title`,`task_profile`,`task_desc`,`fees_low`,`fees_high`,`fees_file`,`filename`,`employee_id`,`task_price`,`task_status`,`close_time`,`bid_time`,`create_time`) values 
(157124009918832,157123941263519,157123889925612,'我需要一个微信小程序','一个商城系统','微信小程序',1000,2000,'http://recruit1.oss-cn-shenzhen.aliyuncs.com/d7077ec8-0ed5-4e7d-8e6f-d45138baf212.pdf','常用类.pdf',157124288026264,1500,3,'2019-10-17 00:37:10','2019-10-17 00:31:23','2019-10-16 23:34:59'),
(157124090699406,157123941263519,157123889925612,'我需要找个人玩游戏','LOL、王者荣耀一起来','快来玩耍呀！',10,100,'http://recruit1.oss-cn-shenzhen.aliyuncs.com/5084c0a5-71cb-495d-a929-fb6348c8211d.pdf','面向对象.pdf',157124288026264,10,3,'2019-10-17 00:39:57','2019-10-17 00:24:40','2019-10-16 23:48:27'),
(157124114265141,157123941263519,157123889925612,'急需一个Java WEB网站！！！！！！！AAAAAA','期末实训课程设计','来快活呀',100,1000,'http://recruit1.oss-cn-shenzhen.aliyuncs.com/31f0cc27-f9a6-4740-9f08-9e016c97d0df.pdf','封装继承.pdf',NULL,NULL,0,NULL,NULL,'2019-10-16 23:52:23');

/*Table structure for table `task_category` */

DROP TABLE IF EXISTS `task_category`;

CREATE TABLE `task_category` (
  `id` bigint(20) NOT NULL COMMENT '任务分类ID',
  `category_name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `category_img` varchar(255) DEFAULT NULL COMMENT '分类图片展示地址',
  `is_popular` tinyint(4) DEFAULT '0' COMMENT '是否热门 0 否 1 热门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task_category` */

insert  into `task_category`(`id`,`category_name`,`category_img`,`is_popular`) values 
(157123941263519,'网站/软件开发','http://recruit1.oss-cn-shenzhen.aliyuncs.com/f5c7a84b-89fb-4b04-8e15-4afe18068de6.jpg',1),
(157123944799651,'数据科学/分析学','http://recruit1.oss-cn-shenzhen.aliyuncs.com/853bca3f-eb79-478f-85f0-aa03e9def32e.jpg',1),
(157123947616803,'会计/咨询','http://recruit1.oss-cn-shenzhen.aliyuncs.com/ab9a164d-ea0c-4409-a214-901bea818b89.jpg',1),
(157123949774553,'写作/翻译','http://recruit1.oss-cn-shenzhen.aliyuncs.com/c588a079-e60b-46bd-b274-c3a477a06a90.jpg',1),
(157123952210737,'销售/市场营销','http://recruit1.oss-cn-shenzhen.aliyuncs.com/062b6bac-1127-4209-8987-b532180129e9.jpg',1),
(157123953698793,'图形/设计','http://recruit1.oss-cn-shenzhen.aliyuncs.com/07d93a43-d92f-44a8-8c86-cf471933eff5.jpg',1),
(157123955607669,'数字营销','http://recruit1.oss-cn-shenzhen.aliyuncs.com/875d9129-a0f8-48a8-857a-b5fc2886e215.jpg',1),
(157123956834969,'教育培训','http://recruit1.oss-cn-shenzhen.aliyuncs.com/45f15358-b3ba-4dc3-822e-ae6a233617a3.jpg',1);

/*Table structure for table `task_skill` */

DROP TABLE IF EXISTS `task_skill`;

CREATE TABLE `task_skill` (
  `id` bigint(20) NOT NULL COMMENT '任务技能ID',
  `skill_name` varchar(255) DEFAULT NULL COMMENT '技能名称',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task_skill` */

insert  into `task_skill`(`id`,`skill_name`,`task_id`) values 
(157123994236997,'Java',157123994233279),
(157123994237672,'SSM',157123994233279),
(157123994237921,'Spring Boot',157123994233279),
(157124026569962,'微信小程序',157124009918832),
(157124090700978,'LOL',157124090699406),
(157124090701279,'英雄联盟',157124090699406);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
