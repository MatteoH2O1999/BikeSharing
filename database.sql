CREATE DATABASE  IF NOT EXISTS `bikesharing` /*!40100 DEFAULT CHARACTER SET ascii */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bikesharing`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: bikesharing
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clamps`
--

DROP TABLE IF EXISTS `clamps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clamps` (
  `id` int NOT NULL,
  `rackid` bigint NOT NULL,
  `state` tinyint NOT NULL,
  `type` tinyint NOT NULL,
  PRIMARY KEY (`id`,`rackid`),
  KEY `rackid_idx` (`rackid`),
  CONSTRAINT `rackid` FOREIGN KEY (`rackid`) REFERENCES `racks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=ascii;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `racks`
--

DROP TABLE IF EXISTS `racks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `racks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=ascii;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rents`
--

DROP TABLE IF EXISTS `rents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rents` (
  `rentId` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `subCode` char(20) DEFAULT NULL,
  `startRent` datetime NOT NULL,
  `endRent` datetime DEFAULT NULL,
  `bike` tinyint NOT NULL COMMENT 'Enum for bike type',
  PRIMARY KEY (`rentId`),
  KEY `subCode_idx` (`subCode`),
  CONSTRAINT `subCode` FOREIGN KEY (`subCode`) REFERENCES `subscriptions` (`subCode`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=ascii;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `subCode` char(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `strikes` tinyint NOT NULL,
  `type` tinyint NOT NULL COMMENT 'Enum for subscription type',
  `payrate` tinyint NOT NULL COMMENT 'Enum for payrate type',
  `cardNumber` bigint NOT NULL,
  `cvv` smallint NOT NULL,
  `cardExpiration` date NOT NULL,
  PRIMARY KEY (`subCode`),
  UNIQUE KEY `cardNumber_UNIQUE` (`cardNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-15 23:20:32
