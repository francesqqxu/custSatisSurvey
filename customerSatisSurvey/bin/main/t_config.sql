/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : survey

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2019-11-18 15:14:02
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('1', '项目提交', '项目提交', 'surveytype', null);
INSERT INTO `t_config` VALUES ('2', '人力外包', '人力外包', 'surveytype', null);
INSERT INTO `t_config` VALUES ('5', '客户', '客户', 'usertype', null);
INSERT INTO `t_config` VALUES ('8', '保险证券业务线', '保险证券业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('9', '银行业务线', '银行业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('10', 'project', 'project', 'surveytype', null);
INSERT INTO `t_config` VALUES ('11', 'staff', 'staff', 'surveytype', null);
INSERT INTO `t_config` VALUES ('12', '制造与流通业务线', '制造与流通业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('13', '	交通业务线', '	交通业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('14', '	数据服务业务线', '	数据服务业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('15', '	亚太业务线', '	亚太业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('16', '	互联网+业务线', '	互联网+业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('17', '	平安业务线', '	平安业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('18', '	云&AI战略事业部', '	云&AI战略事业部', 'lob', null);
INSERT INTO `t_config` VALUES ('19', '	智慧应用业务线', '	智慧应用业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('20', '	HSBC业务线', '	HSBC业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('21', '	ODC业务线', '	ODC业务线', 'lob', null);
INSERT INTO `t_config` VALUES ('22', '	海外业务线', '	海外业务线', 'lob', null);
