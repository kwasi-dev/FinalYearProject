-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: trackfit
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Current Database: `trackfit`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `trackfit` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `trackfit`;

--
-- Table structure for table `exercises`
--

DROP TABLE IF EXISTS `exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercises` (
  `exID` int(11) NOT NULL,
  `exercise` varchar(30) NOT NULL,
  `duration` time NOT NULL,
  `hrZone` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `equipment` varchar(30) NOT NULL,
  `extype` int(11) NOT NULL,
  PRIMARY KEY (`exID`),
  KEY `extype` (`extype`),
  KEY `hrZone` (`hrZone`),
  CONSTRAINT `exercises_ibfk_1` FOREIGN KEY (`extype`) REFERENCES `extype` (`typeID`),
  CONSTRAINT `exercises_ibfk_2` FOREIGN KEY (`hrZone`) REFERENCES `heartratezones` (`hrID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercises`
--

LOCK TABLES `exercises` WRITE;
/*!40000 ALTER TABLE `exercises` DISABLE KEYS */;
INSERT INTO `exercises` VALUES (1,'Warm Up Walk','00:10:00',2,1,'Treadmill',1),(2,'Walk','00:15:00',3,1,'Treadmill',1),(3,'Jog','00:30:00',4,2,'Treadmill',1),(4,'Casual Run','00:45:00',4,3,'Treadmill',1),(5,'Fast Run','00:40:00',5,1,'Treadmill',1),(6,'Warm Up Ride','00:10:00',2,1,'Spin Bike',2),(7,'Ride','00:30:00',3,1,'Spin Bike',2),(8,'Race','00:40:00',4,1,'Spin Bike',2),(9,'Stretch','00:10:00',2,1,'No Machine Needed',4),(10,'Tri-cep','00:20:00',3,1,'Weights',2),(11,'Bi-ceps','00:40:00',3,1,'Weights',2),(12,'Core','00:40:00',3,1,'Spin Bike',2),(13,'Yoga Pose 1','00:10:00',3,1,'No Machine Needed',3),(14,'Yoga Pose 2','00:10:00',3,2,'No Machine Needed',3),(15,'Yoga Pose 3','00:10:00',3,1,'No Machine Needed',3),(16,'Yoga Pose 4','00:10:00',3,2,'No Machine Needed',3),(17,'Yoga Pose 5','00:10:00',3,3,'No Machine Needed',3),(18,'Kwasi Exercise','00:01:00',4,1,'No Machine Needed',1);
/*!40000 ALTER TABLE `exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extype`
--

DROP TABLE IF EXISTS `extype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extype` (
  `typeID` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`typeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extype`
--

LOCK TABLES `extype` WRITE;
/*!40000 ALTER TABLE `extype` DISABLE KEYS */;
INSERT INTO `extype` VALUES (1,'Endurance'),(2,'Strength'),(3,'Balance'),(4,'Flexibility');
/*!40000 ALTER TABLE `extype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `heartratezones`
--

DROP TABLE IF EXISTS `heartratezones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `heartratezones` (
  `hrID` int(11) NOT NULL,
  `zone` varchar(30) NOT NULL,
  `lowerLimit` int(11) NOT NULL,
  `upperLimit` int(11) NOT NULL,
  PRIMARY KEY (`hrID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `heartratezones`
--

LOCK TABLES `heartratezones` WRITE;
/*!40000 ALTER TABLE `heartratezones` DISABLE KEYS */;
INSERT INTO `heartratezones` VALUES (1,'Resting',30,40),(2,'Recovery Aerobic',50,60),(3,'Endurance',60,70),(4,'Stamina',70,80),(5,'Economy',80,90),(6,'Speed',90,100);
/*!40000 ALTER TABLE `heartratezones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine` (
  `machineID` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`machineID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
INSERT INTO `machine` VALUES (1,'Treadmill'),(2,'Spin Bike'),(3,'Weights'),(4,'No Machine Needed');
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  KEY `id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `notificationtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (2,'Lets pick up the pace'),(2,'Come on... You can do better'),(2,'Lets get that heart rate up'),(3,'Good job, Keep it up'),(3,'Nice, keep going'),(3,'You are doing great'),(1,'Ok, time to slow it down'),(1,'You are entering your danger zone'),(1,'Lets get your heart rate down a bit');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificationtype`
--

DROP TABLE IF EXISTS `notificationtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificationtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificationtype`
--

LOCK TABLES `notificationtype` WRITE;
/*!40000 ALTER TABLE `notificationtype` DISABLE KEYS */;
INSERT INTO `notificationtype` VALUES (1,'TooHigh'),(2,'TooLow'),(3,'Normal');
/*!40000 ALTER TABLE `notificationtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rank` (
  `rankID` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `lower point limit` int(11) NOT NULL,
  `upper point limit` int(11) NOT NULL,
  PRIMARY KEY (`rankID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
INSERT INTO `rank` VALUES (1,'Noob',0,0),(2,'Beginner - At the starting line',1,20),(3,'Beginner - Level 1',20,50),(4,'Active',50,80),(5,'Fitness Enthusiast',90,150),(6,'Athletic Hopeful',160,200);
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `emailAdd` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `gender` char(1) NOT NULL,
  `age` int(2) NOT NULL,
  `height` float NOT NULL,
  `startWeight` float NOT NULL,
  `startBMI` float NOT NULL,
  `currWeight` float DEFAULT NULL,
  `currBMI` float DEFAULT NULL,
  `exPrefID` int(11) NOT NULL,
  `datecreated` date NOT NULL,
  `points` int(11) DEFAULT '0',
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `exPrefID` (`exPrefID`),
  KEY `rank` (`rank`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`exPrefID`) REFERENCES `extype` (`typeID`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`rank`) REFERENCES `rank` (`rankID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'ffg@f.n','a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=\n','Jj','Fg','M',25,85,55,100,55,100,2,'2017-04-10',100,1),(3,'kwasi_edwards2@hotmail.com','2zOXNYbjOUMw+5UVH9cXSr8ViCxmBUqMrrX9RTOCc8A=\n','Kwasi','Edwards','M',25,180,125,100,125,17.3611,1,'2017-04-10',100,1),(4,'test@test.com','n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=\n','Test','Mc test','M',25,180,150,100,150,100,2,'2017-04-11',100,1),(5,'gj@f.n','a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=\n','Jjgh','Tg','M',1,88,55,100,55,100,2,'2017-04-11',100,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userhistory`
--

DROP TABLE IF EXISTS `userhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userhistory` (
  `userid` int(11) NOT NULL,
  `exid` int(11) NOT NULL,
  `percentOptimal` float NOT NULL,
  `points` int(11) DEFAULT NULL,
  KEY `userid_idx` (`userid`),
  KEY `exid_idx` (`exid`),
  CONSTRAINT `exid` FOREIGN KEY (`exid`) REFERENCES `exercises` (`exID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userhistory`
--

LOCK TABLES `userhistory` WRITE;
/*!40000 ALTER TABLE `userhistory` DISABLE KEYS */;
INSERT INTO `userhistory` VALUES (3,18,60,NULL);
/*!40000 ALTER TABLE `userhistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-11  9:41:29
