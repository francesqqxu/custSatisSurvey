/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : survey

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2019-11-08 16:56:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option_value` varchar(50) DEFAULT NULL,
  `option_text` varchar(50) DEFAULT NULL,
  `config_type` varchar(50) DEFAULT NULL,
  `parent` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_survey
-- ----------------------------
DROP TABLE IF EXISTS `t_survey`;
CREATE TABLE `t_survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(100) DEFAULT NULL,
  `eval_person_dep` varchar(40) DEFAULT NULL,
  `eval_person_name` varchar(40) DEFAULT NULL,
  `eval_date` varchar(20) DEFAULT NULL,
  `comprehensive_eval_1` varchar(2) DEFAULT NULL,
  `comprehensive_eval_1_reason` varchar(255) DEFAULT NULL,
  `special_eval_1_1` varchar(2) DEFAULT NULL,
  `special_eval_1_2` varchar(2) DEFAULT NULL,
  `special_eval_1_detail` varchar(255) DEFAULT NULL,
  `special_eval_2_1` varchar(2) DEFAULT NULL,
  `special_eval_2_2` varchar(2) DEFAULT NULL,
  `special_eval_2_3` varchar(2) DEFAULT NULL,
  `special_eval_2_detail` varchar(255) DEFAULT NULL,
  `special_eval_3_1` varchar(2) DEFAULT NULL,
  `special_eval_3_2` varchar(2) DEFAULT NULL,
  `special_eval_3_detail` varchar(255) DEFAULT NULL,
  `special_eval_4_1` varchar(2) DEFAULT NULL,
  `special_eval_4_2` varchar(2) DEFAULT NULL,
  `special_eval_4_detail` varchar(255) DEFAULT NULL,
  `special_eval_5_1` varchar(2) DEFAULT NULL,
  `special_eval_5_2` varchar(2) DEFAULT NULL,
  `special_eval_5_detail` varchar(255) DEFAULT NULL,
  `special_eval_6_1` varchar(2) DEFAULT NULL,
  `special_eval_6_2` varchar(2) DEFAULT NULL,
  `special_eval_6_detail` varchar(255) DEFAULT NULL,
  `special_eval_7_1` varchar(2) DEFAULT NULL,
  `special_eval_7_2` varchar(2) DEFAULT NULL,
  `special_eval_7_detail` varchar(255) DEFAULT NULL,
  `special_eval_8` varchar(255) DEFAULT NULL,
  `special_eval_9` varchar(255) DEFAULT NULL,
  `lob` varchar(100) DEFAULT NULL,
  `surveytype` varchar(50) DEFAULT NULL,
  `special_eval_5` varchar(255) DEFAULT NULL,
  `special_eval_6` varchar(255) DEFAULT NULL,
  `special_eval_2_4` varchar(2) DEFAULT NULL,
  `special_eval_3_3` varchar(2) DEFAULT NULL,
  `special_eval_3_4` varchar(2) DEFAULT NULL,
  `special_eval_3_5` varchar(2) DEFAULT NULL,
  `special_eval_4_3` varchar(2) DEFAULT NULL,
  `special_eval_4_4` varchar(2) DEFAULT NULL,
  `special_eval_3` varchar(255) DEFAULT NULL,
  `special_eval_4` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `lob` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  `surveytype` varchar(50) DEFAULT NULL,
  `custname` varchar(100) DEFAULT NULL,
  `eval_person_dep` varchar(50) DEFAULT NULL,
  `eval_person_name` varchar(50) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
