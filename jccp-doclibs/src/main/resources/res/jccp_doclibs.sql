/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : jccp_doclibs

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2016-02-19 17:40:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(50) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'c7122a1349c22cb3c009da3613d242ab', 'yang_huidi@venustech.com.cn', '0');

-- ----------------------------
-- Table structure for doc
-- ----------------------------
DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_name` varchar(200) NOT NULL,
  `doc_path` varchar(1000) NOT NULL,
  `upload_time` int(11) NOT NULL,
  `visible` int(1) DEFAULT '1',
  `menu_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `tag_ids` varchar(100) DEFAULT NULL,
  `download_count` int(11) NOT NULL DEFAULT '0',
  `html_view` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc
-- ----------------------------

-- ----------------------------
-- Table structure for doc_tag
-- ----------------------------
DROP TABLE IF EXISTS `doc_tag`;
CREATE TABLE `doc_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) NOT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  `tag_order` int(3) NOT NULL DEFAULT '0',
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc_tag
-- ----------------------------
INSERT INTO `doc_tag` VALUES ('1', 'Java', '0', '1', '1');
INSERT INTO `doc_tag` VALUES ('2', 'C', '0', '2', '1');
INSERT INTO `doc_tag` VALUES ('4', 'Javascript', '0', '3', '1');

-- ----------------------------
-- Table structure for doc_type
-- ----------------------------
DROP TABLE IF EXISTS `doc_type`;
CREATE TABLE `doc_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  `type_order` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc_type
-- ----------------------------
INSERT INTO `doc_type` VALUES ('1', '培训文档', '0', '1');
INSERT INTO `doc_type` VALUES ('2', '规范文档', '0', '2');
INSERT INTO `doc_type` VALUES ('3', '技术文档', '0', '3');
INSERT INTO `doc_type` VALUES ('4', '开源技术', '0', '4');
INSERT INTO `doc_type` VALUES ('5', '在线API', '0', '5');
INSERT INTO `doc_type` VALUES ('6', '安全知识', '0', '6');
INSERT INTO `doc_type` VALUES ('7', '常用资料', '0', '7');
INSERT INTO `doc_type` VALUES ('8', '常用网站', '0', '8');
INSERT INTO `doc_type` VALUES ('9', 'UI收集', '0', '9');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) NOT NULL,
  `menu_order` int(3) NOT NULL,
  `visible` int(1) NOT NULL DEFAULT '1',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `menu_type` int(1) NOT NULL DEFAULT '0' COMMENT '0为前台menu、1为管理员menu',
  `func_url` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '文档库', '1', '1', '0', '0', '');
INSERT INTO `menu` VALUES ('2', '知识库', '2', '1', '0', '0', '');
INSERT INTO `menu` VALUES ('3', '在线API', '3', '1', '0', '0', '');
INSERT INTO `menu` VALUES ('4', 'UI模板', '4', '1', '0', '0', '');
INSERT INTO `menu` VALUES ('5', '常用资料', '5', '1', '0', '0', '');
INSERT INTO `menu` VALUES ('6', '系统管理', '1', '1', '0', '1', '');
INSERT INTO `menu` VALUES ('7', '上传文档', '2', '1', '6', '1', '/admin/docAdd');
INSERT INTO `menu` VALUES ('8', '文档列表', '3', '1', '6', '1', '/admin/docs');
INSERT INTO `menu` VALUES ('9', '菜单管理', '4', '1', '6', '1', '/admin/menus');

-- ----------------------------
-- Table structure for menu_doc_type
-- ----------------------------
DROP TABLE IF EXISTS `menu_doc_type`;
CREATE TABLE `menu_doc_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `type_order` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_doc_type
-- ----------------------------
INSERT INTO `menu_doc_type` VALUES ('1', '1', '1', '1');
INSERT INTO `menu_doc_type` VALUES ('2', '1', '2', '2');
INSERT INTO `menu_doc_type` VALUES ('3', '1', '3', '3');
INSERT INTO `menu_doc_type` VALUES ('4', '2', '6', '1');
INSERT INTO `menu_doc_type` VALUES ('5', '2', '8', '2');
INSERT INTO `menu_doc_type` VALUES ('6', '3', '5', '1');
INSERT INTO `menu_doc_type` VALUES ('10', '4', '9', '1');
INSERT INTO `menu_doc_type` VALUES ('12', '5', '7', '1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_type` varchar(10) NOT NULL,
  `log_content` varchar(200) NOT NULL,
  `client_ip` varchar(200) NOT NULL,
  `log_time` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
