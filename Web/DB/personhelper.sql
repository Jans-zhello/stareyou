/*
Navicat MySQL Data Transfer

Source Server         : prh
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : personhelper

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-04-02 00:15:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `collectionid` int(8) NOT NULL AUTO_INCREMENT,
  `userid` int(8) NOT NULL,
  `collect_userid` int(8) NOT NULL,
  `collect_textid` int(8) NOT NULL,
  `collection_date` datetime NOT NULL,
  PRIMARY KEY (`collectionid`),
  KEY `userid` (`userid`),
  KEY `collect_textid` (`collect_textid`),
  CONSTRAINT `collection_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `collection_ibfk_2` FOREIGN KEY (`collect_textid`) REFERENCES `send_text` (`textid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for `concern`
-- ----------------------------
DROP TABLE IF EXISTS `concern`;
CREATE TABLE `concern` (
  `concernid` int(8) NOT NULL AUTO_INCREMENT,
  `userid` int(8) NOT NULL,
  `attention_id` int(8) NOT NULL,
  `concern_date` datetime NOT NULL,
  PRIMARY KEY (`concernid`),
  KEY `userid` (`userid`),
  CONSTRAINT `concern_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of concern
-- ----------------------------

-- ----------------------------
-- Table structure for `new`
-- ----------------------------
DROP TABLE IF EXISTS `new`;
CREATE TABLE `new` (
  `newid` int(8) NOT NULL AUTO_INCREMENT,
  `new_content` varchar(20) NOT NULL,
  `userid` int(8) NOT NULL,
  `new_userid` int(8) NOT NULL,
  `new_userid_number` int(1) NOT NULL,
  `textid` int(8) NOT NULL,
  `new_date` datetime NOT NULL,
  `new_status` varchar(5) DEFAULT '未查看',
  PRIMARY KEY (`newid`),
  KEY `userid` (`userid`),
  KEY `textid` (`textid`),
  CONSTRAINT `new_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `new_ibfk_2` FOREIGN KEY (`textid`) REFERENCES `send_text` (`textid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of new
-- ----------------------------

-- ----------------------------
-- Table structure for `praise`
-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise` (
  `praiseid` int(8) NOT NULL AUTO_INCREMENT,
  `userid` int(8) NOT NULL,
  `praise_userid` int(8) NOT NULL,
  `textid` int(8) NOT NULL,
  `praise_date` datetime NOT NULL,
  PRIMARY KEY (`praiseid`),
  KEY `userid` (`userid`),
  KEY `textid` (`textid`),
  CONSTRAINT `praise_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `praise_ibfk_2` FOREIGN KEY (`textid`) REFERENCES `send_text` (`textid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of praise
-- ----------------------------

-- ----------------------------
-- Table structure for `register`
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `registerid` int(10) NOT NULL AUTO_INCREMENT,
  `sex` varchar(2) NOT NULL,
  `mail` varchar(25) NOT NULL,
  `phone` varchar(16) NOT NULL,
  `address` varchar(50) NOT NULL,
  `userid` int(8) NOT NULL,
  PRIMARY KEY (`registerid`),
  KEY `userid` (`userid`),
  CONSTRAINT `register_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------

-- ----------------------------
-- Table structure for `review`
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `reviewid` int(8) NOT NULL AUTO_INCREMENT,
  `review_content` varchar(100) DEFAULT NULL,
  `userid` int(8) NOT NULL,
  `review_userid` int(8) NOT NULL,
  `textid` int(8) NOT NULL,
  `review_date` datetime NOT NULL,
  PRIMARY KEY (`reviewid`),
  KEY `userid` (`userid`),
  KEY `textid` (`textid`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`textid`) REFERENCES `send_text` (`textid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review
-- ----------------------------

-- ----------------------------
-- Table structure for `send_picture`
-- ----------------------------
DROP TABLE IF EXISTS `send_picture`;
CREATE TABLE `send_picture` (
  `pictureid` int(8) NOT NULL AUTO_INCREMENT,
  `picture_content` varchar(100) DEFAULT NULL,
  `picture_date` datetime NOT NULL,
  `userid` int(8) NOT NULL,
  PRIMARY KEY (`pictureid`),
  KEY `userid` (`userid`),
  CONSTRAINT `send_picture_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_picture
-- ----------------------------

-- ----------------------------
-- Table structure for `send_text`
-- ----------------------------
DROP TABLE IF EXISTS `send_text`;
CREATE TABLE `send_text` (
  `textid` int(8) NOT NULL AUTO_INCREMENT,
  `text_content` varchar(400) DEFAULT NULL,
  `text_date` datetime NOT NULL,
  `userid` int(8) NOT NULL,
  PRIMARY KEY (`textid`),
  KEY `userid` (`userid`),
  CONSTRAINT `send_text_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_text
-- ----------------------------

-- ----------------------------
-- Table structure for `send_video`
-- ----------------------------
DROP TABLE IF EXISTS `send_video`;
CREATE TABLE `send_video` (
  `videoid` int(8) NOT NULL AUTO_INCREMENT,
  `video_content` varchar(100) DEFAULT NULL,
  `video_date` datetime NOT NULL,
  `userid` int(8) NOT NULL,
  PRIMARY KEY (`videoid`),
  KEY `userid` (`userid`),
  CONSTRAINT `send_video_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_video
-- ----------------------------

-- ----------------------------
-- Table structure for `suggestion`
-- ----------------------------
DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE `suggestion` (
  `suggestionid` int(8) NOT NULL AUTO_INCREMENT,
  `suggestion_title` varchar(20) NOT NULL,
  `suggestion_content` varchar(200) NOT NULL,
  `suggestion_user` varchar(20) NOT NULL,
  `suggestion_date` datetime NOT NULL,
  PRIMARY KEY (`suggestionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of suggestion
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `collection_number` int(10) NOT NULL DEFAULT '0',
  `attention_number` int(10) NOT NULL DEFAULT '0',
  `attentioned_number` int(10) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
