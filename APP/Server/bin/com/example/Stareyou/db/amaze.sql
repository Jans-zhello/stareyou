
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `amaze`;
CREATE TABLE `amaze` (
  `amazeid` int(11) NOT NULL AUTO_INCREMENT,
  `helpid` int(11) NOT NULL,
  `amaze_userid` int(11) NOT NULL,
  `amaze_username` varchar(100) DEFAULT NULL,
  `amazed_userid` int(11) NOT NULL,
  `amazed_username` varchar(100) DEFAULT NULL,
  `amaze_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`amazeid`),
  KEY `helpid` (`helpid`),
  CONSTRAINT `amaze_ibfk_1` FOREIGN KEY (`helpid`) REFERENCES `help` (`helpid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `assess`;
CREATE TABLE `assess` (
  `assessid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `helpid` int(11) NOT NULL,
  `helper_number` int(11) DEFAULT '0',
  `satisfied` decimal(10,2) DEFAULT '0.00',
  `assess_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`assessid`),
  KEY `userid` (`userid`),
  KEY `helpid` (`helpid`),
  CONSTRAINT `assess_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `assess_ibfk_2` FOREIGN KEY (`helpid`) REFERENCES `help` (`helpid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `chatid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `chatted_userid` int(11) NOT NULL,
  `helpid` int(11) NOT NULL,
  `chat_content` varchar(200) DEFAULT NULL,
  `chat_type` int(20) DEFAULT NULL,
  `chat_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`chatid`),
  KEY `userid` (`userid`),
  KEY `helpid` (`helpid`),
  KEY `helped_userid` (`chatted_userid`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `chat_ibfk_3` FOREIGN KEY (`chatted_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `collectionid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `collectioned_userid` int(11) NOT NULL,
  `sendid` int(11) NOT NULL,
  `collection_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`collectionid`),
  KEY `userid` (`userid`),
  KEY `sendid` (`sendid`),
  KEY `collectioned_userid` (`collectioned_userid`),
  CONSTRAINT `collection_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `collection_ibfk_2` FOREIGN KEY (`sendid`) REFERENCES `send` (`sendid`),
  CONSTRAINT `collection_ibfk_3` FOREIGN KEY (`collectioned_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `concern`;
CREATE TABLE `concern` (
  `concernid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `concerned_userid` int(11) NOT NULL,
  `concern_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`concernid`),
  KEY `userid` (`userid`),
  KEY `concerned_userid` (`concerned_userid`),
  CONSTRAINT `concern_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `concern_ibfk_2` FOREIGN KEY (`concerned_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `help`;
CREATE TABLE `help` (
  `helpid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `help_title` varchar(15) DEFAULT NULL,
  `help_content` varchar(200) DEFAULT NULL,
  `help_type` int(1) DEFAULT NULL,
  `help_icon` varchar(100) DEFAULT NULL,
  `help_location` varchar(200) DEFAULT NULL,
  `help_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`helpid`),
  KEY `userid` (`userid`),
  CONSTRAINT `help_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `helplist`;
CREATE TABLE `helplist` (
  `helplistid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `helped_userid` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`helplistid`),
  KEY `userid` (`userid`),
  KEY `helped_userid` (`helped_userid`),
  CONSTRAINT `helplist_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `helplist_ibfk_2` FOREIGN KEY (`helped_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise` (
  `praiseid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `praised_userid` int(11) NOT NULL,
  `sendid` int(11) NOT NULL,
  `praise_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`praiseid`),
  KEY `userid` (`userid`),
  KEY `sendid` (`sendid`),
  KEY `praised_userid` (`praised_userid`),
  CONSTRAINT `praise_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `praise_ibfk_2` FOREIGN KEY (`sendid`) REFERENCES `send` (`sendid`),
  CONSTRAINT `praise_ibfk_3` FOREIGN KEY (`praised_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `userid` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`),
  CONSTRAINT `register_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `reviewid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `reviewed_userid` int(11) NOT NULL,
  `sendid` int(11) NOT NULL,
  `review_content` varchar(200) DEFAULT NULL,
  `review_type` int(1) DEFAULT NULL,
  `review_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`reviewid`),
  KEY `userid` (`userid`),
  KEY `sendid` (`sendid`),
  KEY `reviewed_userid` (`reviewed_userid`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`sendid`) REFERENCES `send` (`sendid`),
  CONSTRAINT `review_ibfk_3` FOREIGN KEY (`reviewed_userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `send`;
CREATE TABLE `send` (
  `sendid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `send_content` varchar(200) DEFAULT NULL,
  `send_type` int(1) DEFAULT NULL,
  `send_icon` varchar(100) DEFAULT NULL,
  `send_location` varchar(200) DEFAULT NULL,
  `send_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sendid`),
  KEY `userid` (`userid`),
  CONSTRAINT `send_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `stareyou_order`;
CREATE TABLE `stareyou_order` (
  `orderid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `helped_userid` int(11) NOT NULL,
  `helpid` int(11) NOT NULL,
  `order_title` varchar(100) DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderid`),
  KEY `userid` (`userid`),
  KEY `helpid` (`helpid`),
  CONSTRAINT `stareyou_order_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `stareyou_order_ibfk_2` FOREIGN KEY (`helpid`) REFERENCES `help` (`helpid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE `suggestion` (
  `suggestionid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `suggestion_content` varchar(200) DEFAULT NULL,
  `suggestion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`suggestionid`),
  KEY `userid` (`userid`),
  CONSTRAINT `suggestion_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) DEFAULT NULL,
  `send_number` int(10) NOT NULL DEFAULT '0',
  `concern_number` int(10) NOT NULL DEFAULT '0',
  `fans_number` int(10) NOT NULL DEFAULT '0',
  `collection_number` int(10) NOT NULL DEFAULT '0',
  `help_number` int(10) NOT NULL DEFAULT '0',
  `ukey` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=648042475 DEFAULT CHARSET=utf8;
