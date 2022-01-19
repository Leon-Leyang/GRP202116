/*
SQLyog Job Agent Version 10.0 Beta1 Copyright(c) Webyog Inc. All Rights Reserved.


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
  `annotation_id` int NOT NULL,
  `project_id` int DEFAULT NULL,
  `data_id` int DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `from_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `to_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`annotation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `annotation` */

/*Table structure for table `data` */

DROP TABLE IF EXISTS `data`;

CREATE TABLE `data` (
  `url` varchar(100) DEFAULT NULL,
  `data_id` int NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  PRIMARY KEY (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `data` */

/*Table structure for table `labelinterface` */

DROP TABLE IF EXISTS `labelinterface`;

CREATE TABLE `labelinterface` (
  `code` varchar(50) NOT NULL,
  `project_id` int DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `labelinterface` */

/*Table structure for table `model` */

DROP TABLE IF EXISTS `model`;

CREATE TABLE `model` (
  `url` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `model` */

/*Table structure for table `prediction` */

DROP TABLE IF EXISTS `prediction`;

CREATE TABLE `prediction` (
  `prediction_id` int NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `from_name` varchar(100) DEFAULT NULL,
  `to_name` varchar(100) DEFAULT NULL,
  `data_id` int DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  PRIMARY KEY (`prediction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `prediction` */

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project_id` int NOT NULL,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `project` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
