CREATE DATABASE  IF NOT EXISTS `plantstore` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `plantstore`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: plantstore
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `custID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `loggedin` bit(1) DEFAULT NULL,
  PRIMARY KEY (`custID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'test','test','Testy McTest','123 Fake St., ',_binary '\0'),(2,'test2','test','Testington','123 Fake St',_binary '\0'),(9,'sb','1111','Sean','sean.com',_binary '\0'),(10,'thePope','1234','Pope','pope@gmail',_binary '\0'),(15,'hey','123','','',_binary '\0'),(17,'flowers','1234','Logan','1234',_binary '\0'),(18,'sean','test','Testington','123 Fake St',_binary '\0'),(20,'WaterBoy','1234','Leo','1234',_binary '\0'),(21,'Fart Bucket','fartfartbucketbucketman','L.P. Garth Nerathian','6201 rue de Bordeaux',_binary '\0'),(22,'Coffeeboy','1234','Pete','12345',_binary '\0'),(23,'Pat','1234','pat@gmail.com','1234',_binary '\0');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `odid` int NOT NULL AUTO_INCREMENT,
  `plantid` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `orderid` int DEFAULT NULL,
  PRIMARY KEY (`odid`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,2,1,1),(2,2,1,1),(3,2,1,1),(4,2,1,1),(5,2,1,1),(6,2,1,1),(7,2,1,1),(8,2,1,1),(9,2,1,4),(10,4,1,4),(11,4,1,4),(12,2,1,4),(13,2,1,4),(14,3,1,5),(15,2,1,4),(16,4,1,6),(17,4,1,6),(18,4,1,7),(19,3,1,7),(20,5,1,7),(33,5,1,8),(37,4,1,9),(39,2,1,10),(45,1,1,11),(46,2,1,11),(52,3,1,14),(53,5,1,14),(54,3,1,15),(55,2,1,15),(58,4,1,15),(61,3,1,16),(62,5,1,16),(64,2,1,17),(65,5,1,18),(66,3,2,12),(69,2,1,19),(72,1,1,20),(73,2,1,20),(84,3,2,13),(87,2,1,22),(88,4,1,23),(95,3,2,24),(96,1,1,25),(97,2,1,25);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table` (
  `orderid` int NOT NULL AUTO_INCREMENT,
  `custid` int NOT NULL,
  `price` double DEFAULT NULL,
  `orderplaced` bit(1) DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  KEY `fk_custid_customer_idx` (`custid`),
  CONSTRAINT `fk_custid_customer` FOREIGN KEY (`custid`) REFERENCES `customer` (`custID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` VALUES (4,1,56,_binary ''),(5,2,5,_binary ''),(6,1,16,_binary ''),(7,1,21,_binary ''),(9,2,8,_binary ''),(10,2,10,_binary ''),(11,1,20,_binary ''),(12,10,10,_binary ''),(13,1,10,_binary ''),(14,17,13,_binary '\0'),(15,9,23,_binary ''),(16,20,13,_binary ''),(17,20,10,_binary ''),(18,20,8,_binary ''),(19,10,10,_binary ''),(20,10,20,_binary '\0'),(21,21,0,_binary '\0'),(22,22,10,_binary ''),(23,22,8,_binary '\0'),(24,1,10,_binary '\0'),(25,23,20,_binary '');
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant`
--

DROP TABLE IF EXISTS `plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant` (
  `plantID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `qty` int NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`plantID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant`
--

LOCK TABLES `plant` WRITE;
/*!40000 ALTER TABLE `plant` DISABLE KEYS */;
INSERT INTO `plant` VALUES (1,'Spider plant',5,10),(2,'Snake plant',2,10),(3,'Pilea',7,5),(4,'Peace Lily',6,8),(5,'African Violet',7,8);
/*!40000 ALTER TABLE `plant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-07 16:33:03
