/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.27 : Database - grp2021
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`grp2021` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `grp2021`;

/*Table structure for table `annotation` */

DROP TABLE IF EXISTS `annotation`;

CREATE TABLE `annotation` (
  `annotation_id` int NOT NULL AUTO_INCREMENT,
  `project_id` int DEFAULT NULL,
  `data_id` int DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `result` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`annotation_id`),
  UNIQUE KEY `data_id` (`data_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `annotation_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
  CONSTRAINT `annotation_ibfk_2` FOREIGN KEY (`data_id`) REFERENCES `data` (`data_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `data` */

DROP TABLE IF EXISTS `data`;

CREATE TABLE `data` (
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `data_id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `is_annotated` tinyint(1) DEFAULT '0',
  `is_predicted` tinyint(1) DEFAULT '0',
  `list_number` int DEFAULT NULL,
  PRIMARY KEY (`data_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `data_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `labelinterface` */

DROP TABLE IF EXISTS `labelinterface`;

CREATE TABLE `labelinterface` (
  `code` varchar(50) NOT NULL,
  `project_id` int DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `labelinterface_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `model` */

DROP TABLE IF EXISTS `model`;

CREATE TABLE `model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `model_root` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `params` mediumtext,
  `labels_path` varchar(100) DEFAULT NULL,
  `accuracy` varchar(100) DEFAULT NULL,
  `data_length` int DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `version` (`version`,`project_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `model_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `prediction` */

DROP TABLE IF EXISTS `prediction`;

CREATE TABLE `prediction` (
  `prediction_id` int NOT NULL AUTO_INCREMENT,
  `data_id` int DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `result` mediumtext,
  `update_time` date DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `model_version` varchar(100) DEFAULT NULL,
  `is_accepted` tinyint DEFAULT NULL,
  PRIMARY KEY (`prediction_id`),
  UNIQUE KEY `unique` (`project_id`,`model_version`,`data_id`),
  KEY `data_id` (`data_id`),
  CONSTRAINT `prediction_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
  CONSTRAINT `prediction_ibfk_2` FOREIGN KEY (`data_id`) REFERENCES `data` (`data_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `configs` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
