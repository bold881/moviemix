CREATE DATABASE `moviemix` /*!40100 DEFAULT CHARACTER SET latin1 */;
CREATE TABLE `videos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `posterurl` varchar(255) DEFAULT NULL,
  `pianming` varchar(200) DEFAULT NULL,
  `niandai` varchar(50) DEFAULT NULL,
  `guojia` varchar(80) DEFAULT NULL,
  `leibie` varchar(100) DEFAULT NULL,
  `yuyan` varchar(50) DEFAULT NULL,
  `zimu` varchar(50) DEFAULT NULL,
  `imdbpingfen` varchar(50) DEFAULT NULL,
  `wjgs` varchar(50) DEFAULT NULL,
  `pc` varchar(50) DEFAULT NULL,
  `daoyan` varchar(100) DEFAULT NULL,
  `zhuyan` varchar(255) DEFAULT NULL,
  `jianjie` text,
  `hjqk` text,
  `downloadurl` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `domain` varchar(80) DEFAULT NULL,
  `pageurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pageurl` (`pageurl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `videolite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `downloadurl` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `domain` varchar(80) DEFAULT NULL,
  `pageurl` varchar(255) DEFAULT NULL,
  `info` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pageurl` (`pageurl`)
) ENGINE=InnoDB AUTO_INCREMENT=7102 DEFAULT CHARSET=utf8;

CREATE TABLE `subscriber` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

