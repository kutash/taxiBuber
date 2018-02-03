CREATE DATABASE  IF NOT EXISTS `taxi_buber_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `taxi_buber_test`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: taxi_buber
-- ------------------------------------------------------
-- Server version	5.5.54

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id_address` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'address id',
  `address` varchar(255) NOT NULL COMMENT 'name of the locality',
  `id_user` int(10) unsigned NOT NULL COMMENT 'each address must have a specific user',
  `status` enum('ACTIVE','ARCHIVED','BANNED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id_address`),
  KEY `fk_address_user1_idx` (`id_user`),
  CONSTRAINT `fk_address_user1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Минск Казинца 38',5,'ACTIVE'),(2,'Минск пр. Машерова 32',5,'ACTIVE'),(3,'Минск Ленина 125',4,'ARCHIVED'),(4,'Минск Асаналиева 7',4,'ACTIVE');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `id_car` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'car id',
  `registration_number` char(8) NOT NULL COMMENT 'car state registration number, 8 characters',
  `model` varchar(45) NOT NULL COMMENT 'car model',
  `photo_path` varchar(255) DEFAULT NULL COMMENT 'path to the car''s photo  if there is one',
  `is_available` bit(1) NOT NULL COMMENT 'current status of the car, must be true or false',
  `latitude` char(10) DEFAULT NULL COMMENT 'geographical latitude of the car at time of order',
  `longitude` char(10) DEFAULT NULL COMMENT 'eographical longitude of the car at time of order',
  `id_brand` int(10) unsigned NOT NULL COMMENT 'car brand id',
  `capacity` enum('CAR','MINIBUS','MINIVAN') DEFAULT NULL,
  `id_user` int(10) unsigned NOT NULL,
  `status` enum('ACTIVE','ARCHIVED','BANNED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id_car`,`id_brand`,`id_user`),
  UNIQUE KEY `state_number_UNIQUE` (`registration_number`),
  KEY `fk_car_car_brand1_idx` (`id_brand`),
  KEY `fk_car_user1_idx` (`id_user`),
  CONSTRAINT `fk_car_car_brand1` FOREIGN KEY (`id_brand`) REFERENCES `car_brand` (`id_brand`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_car_user1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_brand`
--

DROP TABLE IF EXISTS `car_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_brand` (
  `id_brand` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'brand id',
  `name` varchar(45) NOT NULL COMMENT 'brand title',
  PRIMARY KEY (`id_brand`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_brand`
--

LOCK TABLES `car_brand` WRITE;
/*!40000 ALTER TABLE `car_brand` DISABLE KEYS */;
INSERT INTO `car_brand` VALUES (1,'ACURA'),(2,'ALFA_ROMEO'),(3,'ARO'),(4,'ASIA'),(5,'ASTON_MARTIN'),(6,'AUDI'),(7,'AUSTIN'),(8,'BAW'),(9,'BEIJING'),(10,'BENTLEY'),(11,'BMW'),(12,'BRILLIANCE'),(13,'BRISTOL'),(14,'BUGATTI'),(15,'BUICK'),(16,'BYD'),(17,'VECTOR'),(18,'VENTURI'),(19,'VOLKSWAGEN'),(20,'VOLVO'),(21,'VORTEX'),(22,'WARTBURG'),(23,'WESTFIELD'),(24,'WIESMANN'),(25,'WULING'),(26,'LADA'),(27,'GEELY'),(28,'GEO'),(29,'GMC'),(30,'GREAT_WALL'),(31,'DACIA'),(32,'DADI'),(33,'DAEWOO'),(34,'DAIHATSU'),(35,'DAIMLER'),(36,'DALLAS'),(37,'DERWAYS'),(38,'DE TOMASO'),(39,'DODGE'),(40,'DONG FENG'),(41,'DONINVEST'),(42,'JEEP'),(43,'JIANGLING'),(44,'JIANGNAN'),(45,'EAGLE'),(46,'ZASTAVA'),(47,'ZX'),(48,'INFINITI'),(49,'INNOCENTI'),(50,'INVICTA'),(51,'IRAN_KHODRO'),(52,'ISDERA'),(53,'ISUZU'),(54,'IVECO'),(55,'XIN KAI'),(56,'CADILLAC'),(57,'CALLAWAY'),(58,'CARBODIES'),(59,'CATERHAM'),(60,'CITROEN'),(61,'CIZETA'),(62,'COGGIOLA'),(63,'KIA'),(64,'KOENIGSEGG'),(65,'QVALE'),(66,'КАМАЗ'),(67,'LAMBORGHINI'),(68,'LANCIA'),(69,'LAND ROVER'),(70,'LANDWIND'),(71,'LEXUS'),(72,'LIFAN'),(73,'LINCOLN'),(74,'LOTUS'),(75,'LTI'),(76,'LUXGEN'),(77,'ЛУАЗ'),(78,'MAHINDRA'),(79,'MARCOS'),(80,'MARLIN'),(81,'MARUSSIA'),(82,'MARUTI'),(83,'MASERATI'),(84,'MAYBACH'),(85,'MAZDA'),(86,'MCLAREN'),(87,'MEGA'),(88,'MERCEDES'),(89,'MERCURY'),(90,'METROCAB'),(91,'MG'),(92,'MINELLI'),(93,'MINI'),(94,'MITSUBISHI'),(95,'MITSUOKA'),(96,'MONTE CARLO'),(97,'MORGAN'),(98,'МОСКВИЧ'),(99,'NISSAN'),(100,'NOBLE'),(101,'OLDSMOBILE'),(102,'OPEL'),(103,'PAGANI'),(104,'PANOZ'),(105,'PAYKAN'),(106,'PERODUA'),(107,'PEUGEOT'),(108,'PLYMOUTH'),(109,'PONTIAC'),(110,'PORSCHE'),(111,'PREMIER'),(112,'PROTON'),(113,'PUMA'),(114,'RELIANT'),(115,'RENAULT'),(116,'ROLLS_ROYCE'),(117,'RONART'),(118,'ROVER'),(119,'SAAB'),(120,'SALEEN'),(121,'SAMSUNG'),(122,'SATURN'),(123,'SCION'),(124,'SEAT'),(125,'SKODA'),(126,'SMA'),(127,'SMART'),(128,'SOUEAST'),(129,'SPECTRE'),(130,'SSANGYONG'),(131,'SUBARU'),(132,'SUZUKI'),(133,'TALBOT'),(134,'TATA'),(135,'TATRA'),(136,'TIANMA'),(137,'TOFAS'),(138,'TOYOTA'),(139,'TRABANT'),(140,'TVR'),(141,'FAW'),(142,'FERRARI'),(143,'FIAT'),(144,'FORD'),(145,'FSO'),(146,'FUQI'),(147,'HAFEI'),(148,'HAIMA'),(149,'HINDUSTAN'),(150,'HOLDEN'),(151,'HONDA'),(152,'HUANGHAI'),(153,'HUMMER'),(154,'HYUNDAI'),(155,'CHANA'),(156,'CHANGFENG'),(157,'CHANGHE'),(158,'CHERY'),(159,'CHEVROLET'),(160,'CHRYSLER'),(161,'SHIFENG'),(162,'SHUANGHUAN'),(163,'JAC');
/*!40000 ALTER TABLE `car_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id_comment` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `id_user` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL,
  `id_reviewer` int(10) unsigned NOT NULL,
  `mark` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id_comment`),
  UNIQUE KEY `id_comment_UNIQUE` (`id_comment`),
  KEY `fk_comment_user1_idx` (`id_user`),
  KEY `fk_comment_user2_idx` (`id_reviewer`),
  CONSTRAINT `fk_comment_user1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user2` FOREIGN KEY (`id_reviewer`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO comment (id_comment,text, id_user, date, id_reviewer,mark) VALUES
  (1,'Для меня лично важно, что удобно и быстро можно вызвать такси через смартфон, который всегда под рукой. Спасибо вам.',2,'2017-02-04',5,5),
  (2,'Пользуюсь этим такси уже год. Все супер, спасибо! Всегда на высоте и очень дешево. Пересадила всех своих знакомых на эту службу:)',3,'2017-02-04',4,4),
  (3,'Все супер!!!',2,'2017-02-04',3,5),
  (4,'Ужасный сервис, привязал карту, списали рубль и не вернули. Приложение тугое, не грузит карту.',5,'2017-02-04',2,1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id_trip` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'trip id',
  `price` decimal(8,2) unsigned NOT NULL COMMENT 'trip price',
  `date` datetime NOT NULL COMMENT 'trip date and time',
  `distance` float unsigned NOT NULL COMMENT 'total distance of the trip',
  `id_car` int(10) unsigned NOT NULL COMMENT 'id of the car that made the trip',
  `departure_address` int(10) unsigned NOT NULL COMMENT 'id departure address',
  `destination_address` int(10) unsigned NOT NULL COMMENT 'id destination address',
  `status` enum('ORDERED','COMPLETED','STARTED') NOT NULL,
  PRIMARY KEY (`id_trip`),
  KEY `fk_trip_car1_idx` (`id_car`),
  KEY `fk_trip_address1_idx` (`departure_address`),
  KEY `fk_trip_address2_idx` (`destination_address`),
  CONSTRAINT `fk_trip_address1` FOREIGN KEY (`departure_address`) REFERENCES `address` (`id_address`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_trip_address2` FOREIGN KEY (`destination_address`) REFERENCES `address` (`id_address`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_trip_car1` FOREIGN KEY (`id_car`) REFERENCES `car` (`id_car`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `role` enum('ADMIN','CLIENT','DRIVER') NOT NULL COMMENT 'user role, one of 3 values',
  `email` varchar(100) NOT NULL COMMENT 'user email, field must be unique because it is used to authenticate the user',
  `password` varchar(255) NOT NULL COMMENT 'user password',
  `rating` float NOT NULL COMMENT 'user rating from 1.0 to 5.0',
  `name` varchar(45) NOT NULL COMMENT 'user name',
  `surname` varchar(45) NOT NULL COMMENT 'user surname',
  `patronymic` varchar(45) DEFAULT NULL COMMENT 'user patronymic, optional field',
  `birthday` date DEFAULT NULL COMMENT 'user birthday, optional field',
  `photo_path` varchar(255) DEFAULT NULL COMMENT 'path to the user''s photo  if there is one',
  `phone` varchar(20) DEFAULT NULL,
  `status` enum('ACTIVE','ARCHIVED','BANNED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ADMIN','petrov123@mail.ru','222222',0,'Петр','Петров','Петрович','1989-11-05','1.jpg','8(029)2851148','ACTIVE'),(2,'DRIVER','bor123@mail.ru','3333',4.2,'Борис','Борискин','Борисович','2000-05-12','2.jpg','8(029)2851148','ACTIVE'),(3,'DRIVER','tolik123@mail.ru','555555',4,'Анатолий','Моржов','Петрович','1995-09-08','3.jpg','8(029)3351148','ACTIVE'),(4,'CLIENT','abram123@mail.ru','666666',5,'Аркадий','Абрамович','Иванович','1984-08-10','4.jpg','8(029)3366668','ACTIVE'),(5,'CLIENT','vasya123@mail.ru','777777',3.2,'Василий','Васин','Васильевич','1969-07-15','5.jpg','8(029)2356489','ACTIVE');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-03  1:04:27
