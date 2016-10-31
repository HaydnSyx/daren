/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50630
Source Host           : localhost:3306
Source Database       : taobao

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2016-10-13 20:29:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_func
-- ----------------------------
DROP TABLE IF EXISTS `tb_func`;
CREATE TABLE `tb_func` (
  `ID` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `MODULE_ID` int(6) unsigned NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `URL` varchar(256) NOT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FUND_MODULEID` (`MODULE_ID`) USING BTREE,
  CONSTRAINT `tb_func_ibfk_1` FOREIGN KEY (`MODULE_ID`) REFERENCES `tb_module` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_func
-- ----------------------------
INSERT INTO `tb_func` VALUES ('1', '1', '查看', '/index.htm', '2016-06-03 16:34:18');
INSERT INTO `tb_func` VALUES ('2', '2', '修改密码', '/muser/editpwd.htm', '2016-06-03 17:07:46');
INSERT INTO `tb_func` VALUES ('3', '2', '退出登录', '/signout.htm', '2016-06-03 17:08:19');
INSERT INTO `tb_func` VALUES ('5', '4', '查看', '/module/list.htm', '2015-11-17 16:39:23');
INSERT INTO `tb_func` VALUES ('6', '4', '添加', '/module/add.htm', '2015-11-17 16:39:45');
INSERT INTO `tb_func` VALUES ('7', '4', '更新', '/module/update.htm', '2015-11-17 16:39:57');
INSERT INTO `tb_func` VALUES ('8', '4', '删除', '/module/delete.htm', '2015-11-17 16:40:09');
INSERT INTO `tb_func` VALUES ('9', '5', '查看', '/func/list.htm', '2015-11-17 16:41:29');
INSERT INTO `tb_func` VALUES ('10', '5', '添加', '/func/add.htm', '2015-11-17 16:41:41');
INSERT INTO `tb_func` VALUES ('11', '5', '更新', '/func/update.htm', '2015-11-17 16:41:54');
INSERT INTO `tb_func` VALUES ('12', '5', '删除', '/func/delete.htm', '2015-11-17 16:42:04');
INSERT INTO `tb_func` VALUES ('13', '6', '查看', '/role/list.htm', '2015-11-17 16:42:29');
INSERT INTO `tb_func` VALUES ('14', '6', '添加', '/role/add.htm', '2015-11-17 16:42:41');
INSERT INTO `tb_func` VALUES ('15', '6', '更新', '/role/update.htm', '2015-11-17 16:42:51');
INSERT INTO `tb_func` VALUES ('16', '6', '删除', '/role/delete.htm', '2015-11-17 16:43:02');
INSERT INTO `tb_func` VALUES ('17', '6', '授权', '/role/addPerm.htm', '2015-11-17 17:30:53');
INSERT INTO `tb_func` VALUES ('18', '7', '查看', '/muser/list.htm', '2016-06-06 18:17:42');
INSERT INTO `tb_func` VALUES ('19', '7', '添加', '/muser/add.htm', '2016-06-06 18:17:53');
INSERT INTO `tb_func` VALUES ('20', '7', '更新', '/muser/update.htm', '2016-06-06 18:18:04');
INSERT INTO `tb_func` VALUES ('21', '7', '冻结', '/muser/updateStatus.htm', '2016-06-06 18:25:46');
INSERT INTO `tb_func` VALUES ('22', '9', '查看', '/product/list.htm', '2016-10-13 11:41:23');
INSERT INTO `tb_func` VALUES ('23', '9', '添加', '/product/add.htm', '2016-10-13 11:41:52');
INSERT INTO `tb_func` VALUES ('24', '9', '更新', '/product/update.htm', '2016-10-13 11:42:06');
INSERT INTO `tb_func` VALUES ('25', '9', '删除', '/product/delete.htm', '2016-10-13 11:42:19');
INSERT INTO `tb_func` VALUES ('26', '11', '查看', '/work/record/list.htm', '2016-10-13 20:07:44');
INSERT INTO `tb_func` VALUES ('27', '11', '添加', '/work/record/add.htm', '2016-10-13 20:07:53');
INSERT INTO `tb_func` VALUES ('28', '11', '更新', '/work/record/update.htm', '2016-10-13 20:08:10');
INSERT INTO `tb_func` VALUES ('29', '11', '删除', '/work/record/delete.htm', '2016-10-13 20:08:23');

-- ----------------------------
-- Table structure for tb_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_module`;
CREATE TABLE `tb_module` (
  `ID` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `PID` int(6) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  `URL` varchar(256) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_module
-- ----------------------------
INSERT INTO `tb_module` VALUES ('1', null, '首页', '/index.htm', '2016-10-11 16:32:57');
INSERT INTO `tb_module` VALUES ('2', null, '系统相关', '/system.htm', '2016-10-11 17:06:22');
INSERT INTO `tb_module` VALUES ('3', null, '权限', '/authority,htm', '2016-10-11 18:00:45');
INSERT INTO `tb_module` VALUES ('4', '3', '模块列表', '', '2016-10-11 18:01:27');
INSERT INTO `tb_module` VALUES ('5', '3', '功能', '', '2016-10-11 16:32:17');
INSERT INTO `tb_module` VALUES ('6', '3', '角色', '', '2016-10-11 16:32:34');
INSERT INTO `tb_module` VALUES ('7', null, '管理员', '/muser.htm', '2016-10-11 18:16:09');
INSERT INTO `tb_module` VALUES ('8', null, '工作', '/worker.htm', '2016-10-13 11:39:33');
INSERT INTO `tb_module` VALUES ('9', '8', '项目', '', '2016-10-13 11:41:03');
INSERT INTO `tb_module` VALUES ('11', '8', '工作记录', '', '2016-10-13 20:02:24');

-- ----------------------------
-- Table structure for tb_muser
-- ----------------------------
DROP TABLE IF EXISTS `tb_muser`;
CREATE TABLE `tb_muser` (
  `M_ID` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `MUSER_NAME` varchar(64) NOT NULL,
  `MUSER_PWD` varchar(64) NOT NULL,
  `ROLE_ID` int(6) DEFAULT NULL,
  `NICK_NAME` varchar(32) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(500) DEFAULT NULL,
  `LEVEL` int(2) DEFAULT NULL,
  `REC_DATE` datetime DEFAULT NULL,
  `REC_STATUS` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`M_ID`),
  UNIQUE KEY `INDEX_NAME` (`MUSER_NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_muser
-- ----------------------------
INSERT INTO `tb_muser` VALUES ('1', 'HaydnSyx', '14e1b600b1fd579f47433b88e8d85291', '1', '海迪', null, null, null, null, '2016-06-11 15:34:19', 'T');
INSERT INTO `tb_muser` VALUES ('2', 'staff1', '14e1b600b1fd579f47433b88e8d85291', '3', '员工1', '', '', '', null, '2016-10-13 20:22:38', 'T');

-- ----------------------------
-- Table structure for tb_perm
-- ----------------------------
DROP TABLE IF EXISTS `tb_perm`;
CREATE TABLE `tb_perm` (
  `ID` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `FUNC_ID` int(12) unsigned NOT NULL,
  `ROLE_ID` int(6) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PERM_FUNCID` (`FUNC_ID`) USING BTREE,
  KEY `FK_PERM_ROLEID` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `tb_perm_ibfk_1` FOREIGN KEY (`FUNC_ID`) REFERENCES `tb_func` (`ID`),
  CONSTRAINT `tb_perm_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `tb_role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_perm
-- ----------------------------
INSERT INTO `tb_perm` VALUES ('1', '1', '1');
INSERT INTO `tb_perm` VALUES ('2', '2', '1');
INSERT INTO `tb_perm` VALUES ('3', '3', '1');
INSERT INTO `tb_perm` VALUES ('5', '5', '1');
INSERT INTO `tb_perm` VALUES ('6', '6', '1');
INSERT INTO `tb_perm` VALUES ('7', '7', '1');
INSERT INTO `tb_perm` VALUES ('8', '8', '1');
INSERT INTO `tb_perm` VALUES ('9', '9', '1');
INSERT INTO `tb_perm` VALUES ('10', '10', '1');
INSERT INTO `tb_perm` VALUES ('11', '11', '1');
INSERT INTO `tb_perm` VALUES ('12', '12', '1');
INSERT INTO `tb_perm` VALUES ('13', '13', '1');
INSERT INTO `tb_perm` VALUES ('14', '14', '1');
INSERT INTO `tb_perm` VALUES ('15', '15', '1');
INSERT INTO `tb_perm` VALUES ('16', '16', '1');
INSERT INTO `tb_perm` VALUES ('17', '17', '1');
INSERT INTO `tb_perm` VALUES ('18', '18', '1');
INSERT INTO `tb_perm` VALUES ('19', '19', '1');
INSERT INTO `tb_perm` VALUES ('20', '20', '1');
INSERT INTO `tb_perm` VALUES ('21', '21', '1');
INSERT INTO `tb_perm` VALUES ('22', '22', '1');
INSERT INTO `tb_perm` VALUES ('23', '23', '1');
INSERT INTO `tb_perm` VALUES ('24', '24', '1');
INSERT INTO `tb_perm` VALUES ('25', '25', '1');
INSERT INTO `tb_perm` VALUES ('26', '26', '1');
INSERT INTO `tb_perm` VALUES ('27', '27', '1');
INSERT INTO `tb_perm` VALUES ('28', '28', '1');
INSERT INTO `tb_perm` VALUES ('29', '29', '1');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `ID` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `PRODUCT_TYPE` int(2) unsigned DEFAULT NULL,
  `PRODUCT_PRICE` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES ('1', '1', '1.00');
INSERT INTO `tb_product` VALUES ('2', '2', '10.00');
INSERT INTO `tb_product` VALUES ('3', '3', '10.00');
INSERT INTO `tb_product` VALUES ('4', '4', '1.00');
INSERT INTO `tb_product` VALUES ('5', '5', '10.00');
INSERT INTO `tb_product` VALUES ('7', '6', '10.00');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `ID` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) NOT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CONTACT_PHONE` varchar(128) DEFAULT NULL,
  `INDEX_URL` varchar(255) DEFAULT NULL COMMENT '主页地址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', '超级管理员', '2016-10-11 15:48:58', null, null);
INSERT INTO `tb_role` VALUES ('2', '达人', '2016-10-13 11:55:54', null, '/index.htm');
INSERT INTO `tb_role` VALUES ('3', '兼职', '2016-10-13 11:56:01', null, '/index.htm');

-- ----------------------------
-- Table structure for tb_work_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_work_record`;
CREATE TABLE `tb_work_record` (
  `ID` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `M_ID` int(8) unsigned NOT NULL,
  `S_DATE` date NOT NULL,
  `TB_SINGLEPRODUCT_NUM` int(4) unsigned DEFAULT NULL,
  `TB_SINGLEPRODUCT_AMOUNT` decimal(8,2) DEFAULT NULL,
  `TB_DETAILLIST_NUM` int(4) unsigned DEFAULT NULL,
  `TB_DETAILLIST_AMOUNT` decimal(8,2) DEFAULT NULL,
  `TB_CARD_NUM` int(4) unsigned DEFAULT NULL,
  `TB_CARD_AMOUNT` decimal(8,2) DEFAULT NULL,
  `TM_SINGLEPRODUCT_NUM` int(4) unsigned DEFAULT NULL,
  `TM_SINGLEPRODUCT_AMOUNT` decimal(8,2) DEFAULT NULL,
  `TM_DETAILLIST_NUM` int(4) unsigned DEFAULT NULL,
  `TM_DETAILLIST_AMOUNT` decimal(8,2) DEFAULT NULL,
  `TM_CARD_NUM` int(4) unsigned DEFAULT NULL,
  `TM_CARD_AMOUNT` decimal(8,2) DEFAULT NULL,
  `LEVEL` int(2) DEFAULT NULL,
  `REMARK` text,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNQ_FPAL_MID_DATE` (`M_ID`,`S_DATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_work_record
-- ----------------------------
