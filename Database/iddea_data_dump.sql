-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: iddea
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `category`) VALUES (1,'A'),(2,'A1'),(3,'B'),(4,'B1'),(5,'C'),(6,'C1'),(7,'D'),(8,'D1'),(9,'CE'),(10,'C1E'),(11,'DE'),(12,'BE');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driving_licence`
--

DROP TABLE IF EXISTS `driving_licence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driving_licence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valid_from` date NOT NULL,
  `valid_until` date NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `user_id` int(11) NOT NULL,
  `serial` char(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_driving_licence_user1_idx` (`user_id`),
  CONSTRAINT `fk_driving_licence_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driving_licence`
--

LOCK TABLES `driving_licence` WRITE;
/*!40000 ALTER TABLE `driving_licence` DISABLE KEYS */;
INSERT INTO `driving_licence` (`id`, `valid_from`, `valid_until`, `status`, `user_id`, `serial`) VALUES (4,'2018-08-14','2025-08-28',0,1,'WTH40NBMD'),(5,'2018-08-14','2018-08-24',0,1,'F84WPM4YC'),(6,'2018-08-14','2018-08-31',0,1,'FFY7YXDD5'),(7,'2018-08-14','2018-08-24',0,1,'IUJPPS1GI'),(8,'2018-08-14','2028-08-14',0,1,'S6JQWZ83K'),(9,'2018-08-15','2018-08-31',0,1,'BT5RRE0RE'),(10,'2018-08-16','2018-08-31',0,1,'MNT9TBDHC'),(11,'2018-08-21','2023-08-21',1,2,'JY8IMANK7'),(12,'2018-08-21','2028-08-21',0,1,'YAWZPAVXB'),(13,'2018-08-21','2028-08-21',1,1,'F70WJ5D0U'),(14,'2018-09-03','2020-09-15',1,3,'EGDKMWFC4');
/*!40000 ALTER TABLE `driving_licence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driving_licence_has_category`
--

DROP TABLE IF EXISTS `driving_licence_has_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driving_licence_has_category` (
  `driving_licence_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `valid_from` date NOT NULL,
  `banned` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`driving_licence_id`,`category_id`),
  KEY `fk_driving_licence_has_category_category1_idx` (`category_id`),
  KEY `fk_driving_licence_has_category_driving_licence1_idx` (`driving_licence_id`),
  CONSTRAINT `fk_driving_licence_has_category_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_driving_licence_has_category_driving_licence1` FOREIGN KEY (`driving_licence_id`) REFERENCES `driving_licence` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driving_licence_has_category`
--

LOCK TABLES `driving_licence_has_category` WRITE;
/*!40000 ALTER TABLE `driving_licence_has_category` DISABLE KEYS */;
INSERT INTO `driving_licence_has_category` (`driving_licence_id`, `category_id`, `valid_from`, `banned`) VALUES (4,1,'2018-08-24',0),(5,2,'2018-08-18',0),(6,9,'2018-08-31',0),(7,1,'2018-08-17',0),(7,2,'2018-08-04',0),(7,4,'2018-08-24',0),(7,5,'2018-08-04',0),(7,10,'2018-08-17',0),(7,11,'2018-08-11',0),(7,12,'2018-08-17',0),(8,1,'2018-08-16',0),(8,6,'2018-08-25',0),(8,7,'2018-08-23',0),(8,9,'2018-08-25',0),(9,3,'2018-08-28',0),(10,7,'2018-08-31',0),(11,1,'2018-08-13',0),(11,2,'2018-08-02',0),(11,3,'2018-08-01',0),(11,5,'2018-08-13',0),(11,6,'2018-08-10',0),(11,9,'2018-08-04',0),(12,1,'2018-08-06',0),(12,5,'2018-08-09',0),(12,6,'2018-08-09',0),(12,7,'2018-08-09',0),(12,10,'2018-08-10',0),(13,1,'2018-08-09',0),(13,3,'2018-08-21',0),(14,1,'2018-09-03',0),(14,2,'2018-04-10',0),(14,9,'2018-09-02',0);
/*!40000 ALTER TABLE `driving_licence_has_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identity_card`
--

DROP TABLE IF EXISTS `identity_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identity_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valid_from` date NOT NULL,
  `valid_until` date NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `user_id` int(11) NOT NULL,
  `serial` char(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_identity_card_user2_idx` (`user_id`),
  CONSTRAINT `fk_identity_card_user2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity_card`
--

LOCK TABLES `identity_card` WRITE;
/*!40000 ALTER TABLE `identity_card` DISABLE KEYS */;
INSERT INTO `identity_card` (`id`, `valid_from`, `valid_until`, `status`, `user_id`, `serial`) VALUES (4,'2018-08-14','2027-08-28',0,1,'L7EPMZ91D'),(5,'2018-08-16','2018-08-31',0,2,'HIXBV9GDL'),(6,'2018-08-16','2018-08-31',0,2,'62908ITXZ'),(7,'2018-08-21','2028-08-21',0,2,'D0UT1LMQ6'),(8,'2018-08-21','2028-08-21',1,2,'GG6M6EVBR'),(9,'2018-08-21','2028-08-21',1,1,'YHBQIEHUF'),(10,'2018-09-03','2024-09-03',1,3,'DJNMP2V7L'),(11,'2018-09-03','2018-09-27',1,4,'WV473UXFU'),(12,'2018-09-03','2018-09-29',1,5,'SD4WIN4HV');
/*!40000 ALTER TABLE `identity_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passport`
--

DROP TABLE IF EXISTS `passport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valid_from` date NOT NULL,
  `valid_until` date NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `user_id` int(11) NOT NULL,
  `serial` char(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_passport_user1_idx` (`user_id`),
  CONSTRAINT `fk_passport_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passport`
--

LOCK TABLES `passport` WRITE;
/*!40000 ALTER TABLE `passport` DISABLE KEYS */;
INSERT INTO `passport` (`id`, `valid_from`, `valid_until`, `status`, `user_id`, `serial`) VALUES (1,'2018-08-14','2018-08-20',0,2,'QR30W6N5L'),(2,'2018-08-16','2018-08-31',0,2,'WRCNE26VI'),(3,'2018-08-20','2028-08-31',0,2,'84RX0GJG1'),(4,'2018-08-20','2018-08-31',0,2,'RVEGADA25'),(5,'2018-08-21','2018-08-21',0,2,'3FYBQ9XXN'),(6,'2018-08-21','2028-08-21',1,2,'A0MTY1LXD'),(7,'2018-08-21','2028-08-21',1,1,'SZWTTH6IX'),(8,'2018-09-03','2025-09-03',1,3,'LW5B9A5YB');
/*!40000 ALTER TABLE `passport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(30) NOT NULL,
  `valid_until` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `sso` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_token_user_idx` (`user_id`),
  CONSTRAINT `fk_token_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` (`id`, `token`, `valid_until`, `user_id`, `sso`) VALUES (1,'7UEA47F8','2018-09-03 07:11:19',1,0),(2,'DMHF2UMN','2018-09-03 06:58:21',2,0),(3,'NZG9DJN6','2018-09-03 06:59:43',3,0),(5,'2FSSPGGP','2018-09-03 07:19:06',5,0),(6,'GU8HQXUG','2018-09-03 07:20:29',4,0);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password` char(128) NOT NULL,
  `pid` char(13) NOT NULL,
  `surname` varchar(60) NOT NULL,
  `name` varchar(60) NOT NULL,
  `date_of_birth` date NOT NULL,
  `place_of_birth` varchar(60) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `residence` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `admin` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `pid`, `surname`, `name`, `date_of_birth`, `place_of_birth`, `sex`, `residence`, `email`, `admin`) VALUES (1,'jovan','af6a99e288c9612fc6d0b1de2fd826235eecb3a71c1111f1d2f0134e13749aacd1b9028fb7cf14708bcc4167ea457995ede90ed4eccec95120ef72eac0b6a82f','1307995710287','Danilovic','Jovan','1995-07-13','Beograd',1,'Laktasi','jovan.etf@gmail.com',1),(2,'test','ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff','1307995710289','Danilovic','Jovan','1995-07-13','Beograd',1,'Laktasi','jovan.etf@gmail.com',0),(3,'student','32ade5e7c36fa329ea39dbc352743db40da5aa7460ec55f95b999d6371ad20170094d88d9296643f192e9d5433b8d6d817d6777632e556e96e58f741dc5b3550','1111111111111','Student','Student','1999-09-09','Banja Luka',1,'Banja Luka','jovan.etf@gmail.com',0),(4,'petar','b2e077df1d4a917166b627ec77e35895b3859f09aa68ee8ec4a954c5e025cabf8adcb2997c86f4fc2b3cbc72fd228d4941fa9510360c194d1ceb8b3fe9ee4500','2222222222222','Petar','Petrovic','1992-05-05','Prijedor',1,'Banja Luka','jovan.etf@gmail.com',0),(5,'milica','1619e3c0b4b0f5fe92eb1fae1742590b9449f797bcae965d1097a21553fa04bbd0e77973dd33d93da2f19215421c0a14366830eddd5e6246f47b113fc01efa06','3333333333333','Milica','Micic','1995-06-05','Banja Luka',0,'Banja Luka','jovan.etf@gmail.com',0);
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

-- Dump completed on 2018-09-03  9:07:47
