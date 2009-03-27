-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51a-3ubuntu5.4


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema kinton
--

CREATE DATABASE IF NOT EXISTS kinton;
USE kinton;

--
-- Definition of table `kinton`.`auth_clientresource`
--

DROP TABLE IF EXISTS `kinton`.`auth_clientresource`;
CREATE TABLE  `kinton`.`auth_clientresource` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `description` varchar(100) default NULL,
  `idGroup` int(11) unsigned default NULL,
  `idRole` int(3) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `auth_clientresourceFK1` (`idGroup`),
  KEY `auth_clientresourceFK2` (`idRole`),
  CONSTRAINT `auth_clientresourceFK1` FOREIGN KEY (`idGroup`) REFERENCES `auth_group` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_clientresourceFK2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`auth_clientresource`
--

/*!40000 ALTER TABLE `auth_clientresource` DISABLE KEYS */;
LOCK TABLES `auth_clientresource` WRITE;
INSERT INTO `kinton`.`auth_clientresource` VALUES  (1,'USER_BUTTON','User access button in header',2,2),
 (2,'VIRTUALAPP_BUTTON','Virtual App access button in header',2,3),
 (3,'VIRTUALIMAGE_BUTTON','Virtual Image access button in header',2,2),
 (4,'INFRASTRUCTURE_BUTTON','Infrastructure access buttton in header',2,2),
 (5,'DASHBOARD_BUTTON','Dashboard acces button in header',2,3),
 (6,'CHARTS_BUTTON','Charts access button in header',2,2),
 (7,'CONFIG_BUTTON','Config access button in header',2,2);
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_clientresource` ENABLE KEYS */;


--
-- Definition of table `kinton`.`auth_clientresource_exception`
--

DROP TABLE IF EXISTS `kinton`.`auth_clientresource_exception`;
CREATE TABLE  `kinton`.`auth_clientresource_exception` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `idResource` int(11) unsigned NOT NULL,
  `idUser` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `auth_clientresource_exceptionFK1` (`idResource`),
  KEY `auth_clientresource_exceptionFK2` (`idUser`),
  CONSTRAINT `auth_clientresource_exceptionFK1` FOREIGN KEY (`idResource`) REFERENCES `auth_clientresource` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_clientresource_exceptionFK2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`auth_clientresource_exception`
--

/*!40000 ALTER TABLE `auth_clientresource_exception` DISABLE KEYS */;
LOCK TABLES `auth_clientresource_exception` WRITE;
INSERT INTO `kinton`.`auth_clientresource_exception` VALUES  (1,4,2);
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_clientresource_exception` ENABLE KEYS */;


--
-- Definition of table `kinton`.`auth_group`
--

DROP TABLE IF EXISTS `kinton`.`auth_group`;
CREATE TABLE  `kinton`.`auth_group` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `description` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`auth_group`
--

/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
LOCK TABLES `auth_group` WRITE;
INSERT INTO `kinton`.`auth_group` VALUES  (1,'GENERIC','Generic'),
 (2,'MAIN','Flex client main menu group');
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;


--
-- Definition of table `kinton`.`auth_serverresource`
--

DROP TABLE IF EXISTS `kinton`.`auth_serverresource`;
CREATE TABLE  `kinton`.`auth_serverresource` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `description` varchar(100) default NULL,
  `idGroup` int(11) unsigned default NULL,
  `idRole` int(3) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `auth_serverresourceFK1` (`idGroup`),
  KEY `auth_serverresourceFK2` (`idRole`),
  CONSTRAINT `auth_serverresourceFK1` FOREIGN KEY (`idGroup`) REFERENCES `auth_group` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_serverresourceFK2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`auth_serverresource`
--

/*!40000 ALTER TABLE `auth_serverresource` DISABLE KEYS */;
LOCK TABLES `auth_serverresource` WRITE;
INSERT INTO `kinton`.`auth_serverresource` VALUES  (1,'LOGIN','Login Service',1,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_serverresource` ENABLE KEYS */;


--
-- Definition of table `kinton`.`auth_serverresource_exception`
--

DROP TABLE IF EXISTS `kinton`.`auth_serverresource_exception`;
CREATE TABLE  `kinton`.`auth_serverresource_exception` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `idResource` int(11) unsigned NOT NULL,
  `idUser` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `auth_serverresource_exceptionFK1` (`idResource`),
  KEY `auth_serverresource_exceptionFK2` (`idUser`),
  CONSTRAINT `auth_serverresource_exceptionFK1` FOREIGN KEY (`idResource`) REFERENCES `auth_serverresource` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_serverresource_exceptionFK2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`auth_serverresource_exception`
--

/*!40000 ALTER TABLE `auth_serverresource_exception` DISABLE KEYS */;
LOCK TABLES `auth_serverresource_exception` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_serverresource_exception` ENABLE KEYS */;


--
-- Definition of table `kinton`.`category`
--

DROP TABLE IF EXISTS `kinton`.`category`;
CREATE TABLE  `kinton`.`category` (
  `idCategory` int(3) unsigned NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `isErasable` int(1) unsigned NOT NULL default '1',
  `isDefault` int(1) unsigned NOT NULL default '0',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idCategory`),
  KEY `Category_FK1` (`idUserCreation`),
  KEY `Category_FK2` (`idUser_lastModification`),
  CONSTRAINT `Category_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Category_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`category`
--

/*!40000 ALTER TABLE `category` DISABLE KEYS */;
LOCK TABLES `category` WRITE;
INSERT INTO `kinton`.`category` VALUES  (1,'Others',0,1,1,'2008-11-20 13:28:42',NULL,NULL),
 (2,'Database servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (4,'Applications servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (5,'Web servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


--
-- Definition of table `kinton`.`datacenter`
--

DROP TABLE IF EXISTS `kinton`.`datacenter`;
CREATE TABLE  `kinton`.`datacenter` (
  `idDataCenter` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
  `situation` varchar(20) default NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idDataCenter`),
  KEY `DataCenter_FK1` (`idUserCreation`),
  KEY `DataCenter_FK2` (`idUser_lastModification`),
  CONSTRAINT `DataCenter_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `DataCenter_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`datacenter`
--

/*!40000 ALTER TABLE `datacenter` DISABLE KEYS */;
LOCK TABLES `datacenter` WRITE;
INSERT INTO `kinton`.`datacenter` VALUES  (2,'myDatacenter','Madrid',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (3,'Datacenter 2','Barcelona',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (6,'Datacenter 3','Bilbao',1,'2008-11-27 11:40:32',1,'2008-11-20 13:28:42'),
 (8,'Datacenter 4','Sevilla',1,'2008-11-27 13:06:58',1,'2008-11-27 15:18:03');
UNLOCK TABLES;
/*!40000 ALTER TABLE `datacenter` ENABLE KEYS */;


--
-- Definition of table `kinton`.`dns`
--

DROP TABLE IF EXISTS `kinton`.`dns`;
CREATE TABLE  `kinton`.`dns` (
  `idDns` int(10) unsigned NOT NULL default '0',
  `idNetworkModule` int(10) unsigned NOT NULL default '0',
  `idPhysicalMachine` int(20) unsigned NOT NULL default '0',
  `dns` varchar(39) default NULL,
  `idUserCreation` int(10) unsigned default NULL,
  `creationDate` datetime default NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idDns`,`idNetworkModule`,`idPhysicalMachine`),
  KEY `Dns_FK1` (`idNetworkModule`,`idPhysicalMachine`),
  KEY `Dns_FK2` (`idUserCreation`),
  KEY `Dns_FK3` (`idUser_lastModification`),
  CONSTRAINT `Dns_FK1` FOREIGN KEY (`idNetworkModule`, `idPhysicalMachine`) REFERENCES `networkmodule` (`idNetworkModule`, `idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `Dns_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Dns_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`dns`
--

/*!40000 ALTER TABLE `dns` DISABLE KEYS */;
LOCK TABLES `dns` WRITE;
INSERT INTO `kinton`.`dns` VALUES  (1,1,1,'192.168.200.2',1,'2009-01-28 11:55:58',NULL,NULL),
 (1,2,1,'192.168.1.1',1,'2009-01-28 11:55:58',NULL,NULL),
 (2,1,1,'192.168.200.3',1,'2009-01-28 11:55:58',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `dns` ENABLE KEYS */;


--
-- Definition of table `kinton`.`hypervisor`
--

DROP TABLE IF EXISTS `kinton`.`hypervisor`;
CREATE TABLE  `kinton`.`hypervisor` (
  `id` int(2) unsigned NOT NULL auto_increment,
  `description` varchar(100) NOT NULL,
  `idType` int(5) NOT NULL,
  `idPhysicalMachine` int(20) unsigned NOT NULL,
  `ip` varchar(39) NOT NULL,
  `port` int(5) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  USING BTREE (`id`),
  KEY `Hypervisor_FK1` (`idPhysicalMachine`),
  KEY `Hypervisor_FK2` (`idType`),
  KEY `Hypervisor_FK3` (`idUserCreation`),
  KEY `Hypervisor_FK4` (`idUser_lastModification`),
  CONSTRAINT `Hypervisor_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK2` FOREIGN KEY (`idType`) REFERENCES `hypervisortype` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK3` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK4` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`hypervisor`
--

/*!40000 ALTER TABLE `hypervisor` DISABLE KEYS */;
LOCK TABLES `hypervisor` WRITE;
INSERT INTO `kinton`.`hypervisor` VALUES  (1,'vBox',1,1,'192.168.1.34',18083,1,'2008-11-20 15:29:05',NULL,NULL),
 (3,'vBox',1,3,'192.168.1.36',18083,1,'2008-11-20 15:29:05',NULL,NULL),
 (4,'vBox',1,6,'192.168.1.37',18083,1,'2008-11-20 15:29:05',NULL,NULL),
 (5,'vBox',1,11,'192.168.1.44',18083,1,'2008-11-20 15:29:05',NULL,NULL),
 (6,'vBox',1,12,'192.168.1.49',18083,1,'2008-11-20 15:29:05',NULL,NULL),
 (7,'vBox',1,13,'localhost',18083,1,'2008-11-20 15:29:05',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `hypervisor` ENABLE KEYS */;


--
-- Definition of table `kinton`.`hypervisortype`
--

DROP TABLE IF EXISTS `kinton`.`hypervisortype`;
CREATE TABLE  `kinton`.`hypervisortype` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `defaultPort` int(5) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`hypervisortype`
--

/*!40000 ALTER TABLE `hypervisortype` DISABLE KEYS */;
LOCK TABLES `hypervisortype` WRITE;
INSERT INTO `kinton`.`hypervisortype` VALUES  (1,'vBox',80);
UNLOCK TABLES;
/*!40000 ALTER TABLE `hypervisortype` ENABLE KEYS */;


--
-- Definition of table `kinton`.`icon`
--

DROP TABLE IF EXISTS `kinton`.`icon`;
CREATE TABLE  `kinton`.`icon` (
  `idIcon` int(10) unsigned NOT NULL auto_increment,
  `path` varchar(200) NOT NULL,
  `name` varchar(20) NOT NULL,
  `isDefault` int(1) unsigned default '0',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idIcon`),
  KEY `ICON_FK1` (`idUserCreation`),
  KEY `ICON_FK2` (`idUser_lastModification`),
  CONSTRAINT `ICON_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`) ON DELETE CASCADE,
  CONSTRAINT `ICON_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`icon`
--

/*!40000 ALTER TABLE `icon` DISABLE KEYS */;
LOCK TABLES `icon` WRITE;
INSERT INTO `kinton`.`icon` VALUES  (1,'http://swik.net/swikIcons/img-542-96x96.png','Ubuntu',0,1,'2008-11-20 15:29:05',NULL,NULL),
 (2,'http://swik.net/swikIcons/img-583-96x96.png','Xubuntu icon',1,1,'2008-11-20 15:29:05',NULL,NULL),
 (3,'http://swik.net/swikIcons/img-25-96x96.png','Mysql icon',0,1,'2008-11-20 15:29:05',NULL,NULL),
 (4,'http://www.abiquo.com/images/logo.gif','abiquo MW',0,1,'2008-11-20 15:29:05',NULL,NULL),
 (5,'http://swik.net/swikIcons/img-242-96x96.jpg','Tomcat',0,1,'2008-11-20 15:29:05',NULL,NULL),
 (6,'http://swik.net/swikIcons/img-413-96x96.png','test',0,1,'2008-11-20 15:29:05',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `icon` ENABLE KEYS */;


--
-- Definition of table `kinton`.`imagetype`
--

DROP TABLE IF EXISTS `kinton`.`imagetype`;
CREATE TABLE  `kinton`.`imagetype` (
  `idImageType` int(3) NOT NULL auto_increment,
  `extension` varchar(20) NOT NULL,
  `idHyperType` int(5) NOT NULL,
  PRIMARY KEY  (`idImageType`),
  KEY `imageType` (`idHyperType`),
  CONSTRAINT `imageType` FOREIGN KEY (`idHyperType`) REFERENCES `hypervisortype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`imagetype`
--

/*!40000 ALTER TABLE `imagetype` DISABLE KEYS */;
LOCK TABLES `imagetype` WRITE;
INSERT INTO `kinton`.`imagetype` VALUES  (1,'VDI',1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `imagetype` ENABLE KEYS */;


--
-- Definition of table `kinton`.`networkmodule`
--

DROP TABLE IF EXISTS `kinton`.`networkmodule`;
CREATE TABLE  `kinton`.`networkmodule` (
  `idNetworkModule` int(10) unsigned NOT NULL default '0',
  `idPhysicalMachine` int(20) unsigned NOT NULL,
  `dhcp` int(1) unsigned NOT NULL COMMENT '0-False  1-True',
  `ip` varchar(39) default NULL,
  `gateway` varchar(39) default NULL,
  `bw` decimal(10,2) unsigned default NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModifcationDate` datetime default NULL,
  `subnetMask` varchar(39) default NULL,
  PRIMARY KEY  (`idNetworkModule`,`idPhysicalMachine`),
  KEY `NetworkModule_FK1` (`idPhysicalMachine`),
  KEY `NetworkModule_FK2` (`idUserCreation`),
  KEY `NetworkModule_FK3` (`idUser_lastModification`),
  CONSTRAINT `NetworkModule_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `NetworkModule_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `NetworkModule_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`networkmodule`
--

/*!40000 ALTER TABLE `networkmodule` DISABLE KEYS */;
LOCK TABLES `networkmodule` WRITE;
INSERT INTO `kinton`.`networkmodule` VALUES  (1,1,0,'192.168.200.254','192.168.200.1','50.00',1,'2009-01-28 11:55:58',NULL,NULL,'255.255.255.0'),
 (2,1,1,'','','100.00',1,'2009-01-28 11:55:58',NULL,NULL,'');
UNLOCK TABLES;
/*!40000 ALTER TABLE `networkmodule` ENABLE KEYS */;


--
-- Definition of table `kinton`.`nodes`
--

DROP TABLE IF EXISTS `kinton`.`nodes`;
CREATE TABLE  `kinton`.`nodes` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode` int(3) unsigned NOT NULL auto_increment,
  `posX` int(3) NOT NULL,
  `posY` int(3) NOT NULL,
  `idVM` int(10) unsigned default NULL,
  `idImage` int(4) NOT NULL,
  PRIMARY KEY  USING BTREE (`idNode`),
  KEY `Nodes_FK4` (`idVirtualApp`),
  KEY `Nodes_FK5` (`idVM`),
  CONSTRAINT `Nodes_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE,
  CONSTRAINT `Nodes_FK2` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`nodes`
--

/*!40000 ALTER TABLE `nodes` DISABLE KEYS */;
LOCK TABLES `nodes` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `nodes` ENABLE KEYS */;


--
-- Definition of table `kinton`.`physicalmachine`
--

DROP TABLE IF EXISTS `kinton`.`physicalmachine`;
CREATE TABLE  `kinton`.`physicalmachine` (
  `idPhysicalMachine` int(20) unsigned NOT NULL auto_increment,
  `idRack` int(15) unsigned default NULL,
  `idDataCenter` int(10) unsigned NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) default NULL,
  `ram` int(7) NOT NULL,
  `cpu` decimal(6,2) unsigned NOT NULL,
  `hd` int(10) unsigned NOT NULL,
  `host_SO` int(3) unsigned NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  `ramUsed` int(7) NOT NULL,
  `cpuUsed` decimal(6,2) NOT NULL,
  `hdUsed` int(11) NOT NULL,
  PRIMARY KEY  (`idPhysicalMachine`),
  KEY `PhysicalMachine_FK1` (`idRack`),
  KEY `PhysicalMachine_FK2` (`idUserCreation`),
  KEY `PhysicalMachine_FK3` (`idUser_lastModification`),
  KEY `PhysicalMachine_FK4` (`host_SO`),
  KEY `PhysicalMachine_FK5` (`idDataCenter`),
  CONSTRAINT `PhysicalMachine_FK1` FOREIGN KEY (`idRack`) REFERENCES `rack` (`idRack`) ON DELETE CASCADE,
  CONSTRAINT `PhysicalMachine_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK4` FOREIGN KEY (`host_SO`) REFERENCES `so` (`idSO`),
  CONSTRAINT `PhysicalMachine_FK5` FOREIGN KEY (`idDataCenter`) REFERENCES `datacenter` (`idDataCenter`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`physicalmachine`
--

/*!40000 ALTER TABLE `physicalmachine` DISABLE KEYS */;
LOCK TABLES `physicalmachine` WRITE;
INSERT INTO `kinton`.`physicalmachine` VALUES  (1,3,2,'Barcelona','Barcelona',1024,'32.00',100,2,1,'2008-11-20 15:29:05',1,'2009-01-28 11:55:58',-1024,'2.00',-2),
 (3,NULL,2,'L\'Hospitalet','L\'Hospitalet',1024,'9999.99',100,2,1,'2008-11-20 15:29:05',1,'2008-12-22 10:53:52',0,'0.00',0),
 (6,4,2,'Valencia','Valencia',1024,'2.00',100,2,1,'2008-11-20 15:29:05',1,'2008-12-23 12:03:20',-1024,'-2.60',-256),
 (10,1,2,'A CoruÃ?Â±a','A CoruÃ?Â±a',1024,'2.00',100,2,1,'2008-11-20 15:29:05',1,'2008-12-18 21:01:04',0,'0.00',0),
 (11,1,2,'Cornella','Cornella',1024,'2.00',100,2,1,'2008-11-20 15:29:05',1,'2009-01-21 17:48:02',-1024,'-2.00',-2),
 (12,2,2,'Mislata','Mislata',1024,'2.00',100,2,1,'2008-11-20 15:29:05',1,'2008-11-20 15:29:05',-4096,'-8.00',-1024),
 (13,9,2,'MyMachine','MyMachine',1024,'2.00',100,2,1,'2008-11-20 15:29:05',1,'2008-11-20 15:29:05',0,'0.00',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `physicalmachine` ENABLE KEYS */;


--
-- Definition of table `kinton`.`rack`
--

DROP TABLE IF EXISTS `kinton`.`rack`;
CREATE TABLE  `kinton`.`rack` (
  `idRack` int(15) unsigned NOT NULL auto_increment,
  `idDataCenter` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `shortDescription` varchar(30) default NULL,
  `largeDescription` varchar(100) default NULL,
  `idUserCreataion` int(10) unsigned NOT NULL,
  `creatioNDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idRack`),
  KEY `Rack_FK1` (`idDataCenter`),
  KEY `Rack_FK2` (`idUserCreataion`),
  KEY `Rack_FK3` (`idUser_lastModification`),
  CONSTRAINT `Rack_FK1` FOREIGN KEY (`idDataCenter`) REFERENCES `datacenter` (`idDataCenter`) ON DELETE CASCADE,
  CONSTRAINT `Rack_FK2` FOREIGN KEY (`idUserCreataion`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Rack_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`rack`
--

/*!40000 ALTER TABLE `rack` DISABLE KEYS */;
LOCK TABLES `rack` WRITE;
INSERT INTO `kinton`.`rack` VALUES  (1,3,'Rack 1','Rack 1','Este es el Rack 1',1,'2008-10-29 15:42:16',1,'2008-11-20 13:28:42'),
 (2,3,'Rack 3','Rack 3','Este es el Rack 3',1,'2008-11-20 15:29:05',1,'2008-12-24 13:30:27'),
 (3,3,'Rack 2','Rack 2','Este es el Rack 2',1,'2008-11-20 13:33:21',1,'2008-11-20 13:28:42'),
 (4,3,'Rack 4','Desc corta','Larga',1,'2008-11-25 12:16:51',NULL,NULL),
 (5,3,'Rack','Rack','Rack',1,'2008-11-27 12:12:54',NULL,NULL),
 (8,3,'Rack Sevilla 2','Sevilla 2','Rack Sevilla 2',1,'2008-11-27 15:26:30',NULL,NULL),
 (9,2,'MyRack','MyRack','MyRack',1,'2008-11-27 15:26:30',1,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `rack` ENABLE KEYS */;


--
-- Definition of table `kinton`.`repository`
--

DROP TABLE IF EXISTS `kinton`.`repository`;
CREATE TABLE  `kinton`.`repository` (
  `idRepository` int(3) unsigned NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `URL` varchar(50) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idRepository`),
  KEY `Repository_FK1` (`idUserCreation`),
  KEY `Repository_FK2` (`idUser_lastModification`),
  CONSTRAINT `Repository_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Repository_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`repository`
--

/*!40000 ALTER TABLE `repository` DISABLE KEYS */;
LOCK TABLES `repository` WRITE;
INSERT INTO `kinton`.`repository` VALUES  (1,'Main Repository','/opt/vm_repository',1,'2008-11-20 13:28:42',1,'2009-01-27 17:06:37');
UNLOCK TABLES;
/*!40000 ALTER TABLE `repository` ENABLE KEYS */;


--
-- Definition of table `kinton`.`role`
--

DROP TABLE IF EXISTS `kinton`.`role`;
CREATE TABLE  `kinton`.`role` (
  `idRole` int(3) unsigned NOT NULL auto_increment,
  `shortDescription` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  `securityLevel` decimal(3,1) unsigned NOT NULL default '0.0',
  PRIMARY KEY  (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
LOCK TABLES `role` WRITE;
INSERT INTO `kinton`.`role` VALUES  (1,'Public','Public User (not logged)','99.9'),
 (2,'Administrator','Administrator User','1.0'),
 (3,'Registered User','Generic Registered User','2.0');
UNLOCK TABLES;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `kinton`.`session`
--

DROP TABLE IF EXISTS `kinton`.`session`;
CREATE TABLE  `kinton`.`session` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user` varchar(20) NOT NULL,
  `key` varchar(100) NOT NULL,
  `expireDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`session`
--

/*!40000 ALTER TABLE `session` DISABLE KEYS */;
LOCK TABLES `session` WRITE;
INSERT INTO `kinton`.`session` VALUES  (1,'root','1233239716255','2009-01-29 15:42:04');
UNLOCK TABLES;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


--
-- Definition of table `kinton`.`so`
--

DROP TABLE IF EXISTS `kinton`.`so`;
CREATE TABLE  `kinton`.`so` (
  `idSO` int(3) unsigned NOT NULL auto_increment,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY  (`idSO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`so`
--

/*!40000 ALTER TABLE `so` DISABLE KEYS */;
LOCK TABLES `so` WRITE;
INSERT INTO `kinton`.`so` VALUES  (1,'Windows'),
 (2,'GNU/Linux');
UNLOCK TABLES;
/*!40000 ALTER TABLE `so` ENABLE KEYS */;


--
-- Definition of table `kinton`.`state`
--

DROP TABLE IF EXISTS `kinton`.`state`;
CREATE TABLE  `kinton`.`state` (
  `idState` int(1) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY  (`idState`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`state`
--

/*!40000 ALTER TABLE `state` DISABLE KEYS */;
LOCK TABLES `state` WRITE;
INSERT INTO `kinton`.`state` VALUES  (1,'RUNNING'),
 (2,'PAUSED'),
 (3,'POWERED_OFF'),
 (4,'REBOOTED'),
 (5,'NOT_DEPLOYED'),
 (6,'IN_PROGRESS');
UNLOCK TABLES;
/*!40000 ALTER TABLE `state` ENABLE KEYS */;


--
-- Definition of table `kinton`.`user`
--

DROP TABLE IF EXISTS `kinton`.`user`;
CREATE TABLE  `kinton`.`user` (
  `idUser` int(10) unsigned NOT NULL auto_increment,
  `idRole` int(3) unsigned NOT NULL,
  `user` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(50) default NULL,
  `description` varchar(100) default NULL,
  `email` varchar(30) NOT NULL,
  `locale` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  `active` int(1) unsigned NOT NULL default '0',
  PRIMARY KEY  USING BTREE (`idUser`),
  UNIQUE KEY `user_unique` USING BTREE (`user`),
  KEY `User_FK1` (`idRole`),
  KEY `User_FK2` (`idUserCreation`),
  KEY `User_FK3` USING BTREE (`idUser_lastModification`),
  CONSTRAINT `User_FK1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
LOCK TABLES `user` WRITE;
INSERT INTO `kinton`.`user` VALUES  (1,2,'root','Administrador','Principal','Main administrator','root@root.com','es_ES','xabiquo',1,'2008-10-20 00:00:00',1,'2009-01-20 13:52:36',1),
 (2,3,'demo','Usuario','Standard','Demo User','demo@demo.com','es_ES','xdemo',1,'2008-10-20 00:00:00',1,'2008-12-16 18:49:22',1),
 (3,2,'root2','Root 2','Root 2','Second administrator','root2@root.com','es_ES','xabiquo',1,'2008-10-20 00:00:00',1,'2008-12-16 18:49:22',0),
 (4,1,'user3','Usuario 3','Usuario','DDDD','ddd@ddd.ddd','','ddd',1,'2008-12-15 18:37:06',1,'2008-12-16 18:49:22',0),
 (5,1,'dummy0','Dummy 0','Dummy Dummy0','Dummy user ','dummy@dummy,du0','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (6,1,'dummy1','Dummy 1','Dummy Dummy1','Dummy user ','dummy@dummy,du1','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (7,1,'dummy2','Dummy 2','Dummy Dummy2','Dummy user ','dummy@dummy,du2','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (8,1,'dummy3','Dummy 3','Dummy Dummy3','Dummy user ','dummy@dummy,du3','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (9,1,'dummy4','Dummy 4','Dummy Dummy4','Dummy user ','dummy@dummy,du4','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0);
INSERT INTO `kinton`.`user` VALUES  (10,1,'dummy5','Dummy 5','Dummy Dummy5','Dummy user ','dummy@dummy,du5','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (11,1,'dummy6','Dummy 6','Dummy Dummy6','Dummy user ','dummy@dummy,du6','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (12,1,'dummy7','Dummy 7','Dummy Dummy7','Dummy user ','dummy@dummy,du7','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (13,1,'dummy8','Dummy 8','Dummy Dummy8','Dummy user ','dummy@dummy,du8','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (14,1,'dummy9','Dummy 9','Dummy Dummy9','Dummy user ','dummy@dummy,du9','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0),
 (15,1,'dummy10','Dummy 10','Dummy Dummy10','Dummy user ','dummy@dummy,du10','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (16,1,'dummy11','Dummy 11','Dummy Dummy11','Dummy user ','dummy@dummy,du11','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:58:15',1),
 (17,1,'dummy12','Dummy 12','Dummy Dummy12','Dummy user ','dummy@dummy,du12','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:22',0);
INSERT INTO `kinton`.`user` VALUES  (18,1,'dummy13','Dummy 13','Dummy Dummy13','Dummy user ','dummy@dummy,du13','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:36',0),
 (19,1,'dummy14','Dummy 14','Dummy Dummy14','Dummy user ','dummy@dummy,du14','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (20,1,'dummy15','Dummy 15','Dummy Dummy15','Dummy user ','dummy@dummy,du15','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (21,1,'dummy16','Dummy 16','Dummy Dummy16','Dummy user ','dummy@dummy,du16','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (22,1,'dummy17','Dummy 17','Dummy Dummy17','Dummy user ','dummy@dummy,du17','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (23,1,'dummy18','Dummy 18','Dummy Dummy18','Dummy user ','dummy@dummy,du18','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (24,1,'dummy19','Dummy 19','Dummy Dummy19','Dummy user ','dummy@dummy,du19','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (25,1,'dummy20','Dummy 20','Dummy Dummy20','Dummy user ','dummy@dummy,du20','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (26,1,'dummy21','Dummy 21','Dummy Dummy21','Dummy user ','dummy@dummy,du21','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (27,1,'dummy22','Dummy 22','Dummy Dummy22','Dummy user ','dummy@dummy,du22','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (28,1,'dummy23','Dummy 23','Dummy Dummy23','Dummy user ','dummy@dummy,du23','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (29,1,'dummy24','Dummy 24','Dummy Dummy24','Dummy user ','dummy@dummy,du24','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (30,1,'dummy25','Dummy 25','Dummy Dummy25','Dummy user ','dummy@dummy,du25','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 18:49:30',0),
 (31,1,'dummy26','Dummy 26','Dummy Dummy26','Dummy user ','dummy@dummy,du26','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (32,1,'dummy27','Dummy 27','Dummy Dummy27','Dummy user ','dummy@dummy,du27','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (33,1,'dummy28','Dummy 28','Dummy Dummy28','Dummy user ','dummy@dummy,du28','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (34,1,'dummy29','Dummy 29','Dummy Dummy29','Dummy user ','dummy@dummy,du29','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (35,1,'dummy30','Dummy 30','Dummy Dummy30','Dummy user ','dummy@dummy,du30','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (36,1,'dummy31','Dummy 31','Dummy Dummy31','Dummy user ','dummy@dummy,du31','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (37,1,'dummy32','Dummy 32','Dummy Dummy32','Dummy user ','dummy@dummy,du32','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (38,1,'dummy33','Dummy 33','Dummy Dummy33','Dummy user ','dummy@dummy,du33','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (39,1,'dummy34','Dummy 34','Dummy Dummy34','Dummy user ','dummy@dummy,du34','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (40,1,'dummy35','Dummy 35','Dummy Dummy35','Dummy user ','dummy@dummy,du35','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (41,1,'dummy36','Dummy 36','Dummy Dummy36','Dummy user ','dummy@dummy,du36','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (42,1,'dummy37','Dummy 37','Dummy Dummy37','Dummy user ','dummy@dummy,du37','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (43,1,'dummy38','Dummy 38','Dummy Dummy38','Dummy user ','dummy@dummy,du38','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (44,1,'dummy39','Dummy 39','Dummy Dummy39','Dummy user ','dummy@dummy,du39','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (45,1,'dummy40','Dummy 40','Dummy Dummy40','Dummy user ','dummy@dummy,du40','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (46,1,'dummy41','Dummy 41','Dummy Dummy41','Dummy user ','dummy@dummy,du41','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (47,1,'dummy42','Dummy 42','Dummy Dummy42','Dummy user ','dummy@dummy,du42','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (48,1,'dummy43','Dummy 43','Dummy Dummy43','Dummy user ','dummy@dummy,du43','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (49,1,'dummy44','Dummy 44','Dummy Dummy44','Dummy user ','dummy@dummy,du44','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (50,1,'dummy45','Dummy 45','Dummy Dummy45','Dummy user ','dummy@dummy,du45','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (51,1,'dummy46','Dummy 46','Dummy Dummy46','Dummy user ','dummy@dummy,du46','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (52,1,'dummy47','Dummy 47','Dummy Dummy47','Dummy user ','dummy@dummy,du47','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (53,1,'dummy48','Dummy 48','Dummy Dummy48','Dummy user ','dummy@dummy,du48','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (54,1,'dummy49','Dummy 49','Dummy Dummy49','Dummy user ','dummy@dummy,du49','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (55,1,'dummy50','Dummy 50','Dummy Dummy50','Dummy user ','dummy@dummy,du50','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (56,1,'dummy51','Dummy 51','Dummy Dummy51','Dummy user ','dummy@dummy,du51','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (57,1,'dummy52','Dummy 52','Dummy Dummy52','Dummy user ','dummy@dummy,du52','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (58,1,'dummy53','Dummy 53','Dummy Dummy53','Dummy user ','dummy@dummy,du53','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (59,1,'dummy54','Dummy 54','Dummy Dummy54','Dummy user ','dummy@dummy,du54','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (60,1,'dummy55','Dummy 55','Dummy Dummy55','Dummy user ','dummy@dummy,du55','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (61,1,'dummy56','Dummy 56','Dummy Dummy56','Dummy user ','dummy@dummy,du56','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (62,1,'dummy57','Dummy 57','Dummy Dummy57','Dummy user ','dummy@dummy,du57','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (63,1,'dummy58','Dummy 58','Dummy Dummy58','Dummy user ','dummy@dummy,du58','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (64,1,'dummy59','Dummy 59','Dummy Dummy59','Dummy user ','dummy@dummy,du59','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (65,1,'dummy60','Dummy 60','Dummy Dummy60','Dummy user ','dummy@dummy,du60','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (66,1,'dummy61','Dummy 61','Dummy Dummy61','Dummy user ','dummy@dummy,du61','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (67,1,'dummy62','Dummy 62','Dummy Dummy62','Dummy user ','dummy@dummy,du62','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (68,1,'dummy63','Dummy 63','Dummy Dummy63','Dummy user ','dummy@dummy,du63','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (69,1,'dummy64','Dummy 64','Dummy Dummy64','Dummy user ','dummy@dummy,du64','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (70,1,'dummy65','Dummy 65','Dummy Dummy65','Dummy user ','dummy@dummy,du65','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (71,1,'dummy66','Dummy 66','Dummy Dummy66','Dummy user ','dummy@dummy,du66','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (72,1,'dummy67','Dummy 67','Dummy Dummy67','Dummy user ','dummy@dummy,du67','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (73,1,'dummy68','Dummy 68','Dummy Dummy68','Dummy user ','dummy@dummy,du68','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (74,1,'dummy69','Dummy 69','Dummy Dummy69','Dummy user ','dummy@dummy,du69','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (75,1,'dummy70','Dummy 70','Dummy Dummy70','Dummy user ','dummy@dummy,du70','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (76,1,'dummy71','Dummy 71','Dummy Dummy71','Dummy user ','dummy@dummy,du71','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (77,1,'dummy72','Dummy 72','Dummy Dummy72','Dummy user ','dummy@dummy,du72','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (78,1,'dummy73','Dummy 73','Dummy Dummy73','Dummy user ','dummy@dummy,du73','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (79,1,'dummy74','Dummy 74','Dummy Dummy74','Dummy user ','dummy@dummy,du74','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (80,1,'dummy75','Dummy 75','Dummy Dummy75','Dummy user ','dummy@dummy,du75','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (81,1,'dummy76','Dummy 76','Dummy Dummy76','Dummy user ','dummy@dummy,du76','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (82,1,'dummy77','Dummy 77','Dummy Dummy77','Dummy user ','dummy@dummy,du77','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (83,1,'dummy78','Dummy 78','Dummy Dummy78','Dummy user ','dummy@dummy,du78','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (84,1,'dummy79','Dummy 79','Dummy Dummy79','Dummy user ','dummy@dummy,du79','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (85,1,'dummy80','Dummy 80','Dummy Dummy80','Dummy user ','dummy@dummy,du80','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (86,1,'dummy81','Dummy 81','Dummy Dummy81','Dummy user ','dummy@dummy,du81','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (87,1,'dummy82','Dummy 82','Dummy Dummy82','Dummy user ','dummy@dummy,du82','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (88,1,'dummy83','Dummy 83','Dummy Dummy83','Dummy user ','dummy@dummy,du83','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (89,1,'dummy84','Dummy 84','Dummy Dummy84','Dummy user ','dummy@dummy,du84','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (90,1,'dummy85','Dummy 85','Dummy Dummy85','Dummy user ','dummy@dummy,du85','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (91,1,'dummy86','Dummy 86','Dummy Dummy86','Dummy user ','dummy@dummy,du86','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (92,1,'dummy87','Dummy 87','Dummy Dummy87','Dummy user ','dummy@dummy,du87','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (93,1,'dummy88','Dummy 88','Dummy Dummy88','Dummy user ','dummy@dummy,du88','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (94,1,'dummy89','Dummy 89','Dummy Dummy89','Dummy user ','dummy@dummy,du89','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (95,1,'dummy90','Dummy 90','Dummy Dummy90','Dummy user ','dummy@dummy,du90','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (96,1,'dummy91','Dummy 91','Dummy Dummy91','Dummy user ','dummy@dummy,du91','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (97,1,'dummy92','Dummy 92','Dummy Dummy92','Dummy user ','dummy@dummy,du92','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (98,1,'dummy93','Dummy 93','Dummy Dummy93','Dummy user ','dummy@dummy,du93','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (99,1,'dummy94','Dummy 94','Dummy Dummy94','Dummy user ','dummy@dummy,du94','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (100,1,'dummy95','Dummy 95','Dummy Dummy95','Dummy user ','dummy@dummy,du95','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (101,1,'dummy96','Dummy 96','Dummy Dummy96','Dummy user ','dummy@dummy,du96','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (102,1,'dummy97','Dummy 97','Dummy Dummy97','Dummy user ','dummy@dummy,du97','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (103,1,'dummy98','Dummy 98','Dummy Dummy98','Dummy user ','dummy@dummy,du98','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (104,1,'dummy99','Dummy 99','Dummy Dummy99','Dummy user ','dummy@dummy,du99','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (105,1,'dummy100','Dummy 100','Dummy Dummy100','Dummy user ','dummy@dummy,du100','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (106,1,'dummy101','Dummy 101','Dummy Dummy101','Dummy user ','dummy@dummy,du101','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (107,1,'dummy102','Dummy 102','Dummy Dummy102','Dummy user ','dummy@dummy,du102','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (108,1,'dummy103','Dummy 103','Dummy Dummy103','Dummy user ','dummy@dummy,du103','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (109,1,'dummy104','Dummy 104','Dummy Dummy104','Dummy user ','dummy@dummy,du104','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (110,1,'dummy105','Dummy 105','Dummy Dummy105','Dummy user ','dummy@dummy,du105','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1);
INSERT INTO `kinton`.`user` VALUES  (111,1,'dummy106','Dummy 106','Dummy Dummy106','Dummy user ','dummy@dummy,du106','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-24 11:15:53',1),
 (112,1,'dummy107','Dummy 107','Dummy Dummy107','Dummy user ','dummy@dummy,du107','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-19 19:06:33',1),
 (113,1,'dummy108','Dummy 108','Dummy Dummy108','Dummy user ','dummy@dummy,du108','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-19 19:06:33',1),
 (114,1,'dummy109','Dummy 109','Dummy Dummy109','Dummy user ','dummy@dummy,du109','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:58:15',1),
 (115,1,'dummy110','Dummy 110','Dummy Dummy110','Dummy user ','dummy@dummy,du110','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:58:15',1),
 (116,1,'dummy111','Dummy 111','Dummy Dummy111','Dummy user ','dummy@dummy,du111','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:58:15',1),
 (117,1,'dummy112','Dummy 112','Dummy Dummy112','Dummy user ','dummy@dummy,du112','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:58:15',1),
 (118,1,'dummy113','Dummy 113','Dummy Dummy113','Dummy user ','dummy@dummy,du113','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-17 14:56:36',1);
INSERT INTO `kinton`.`user` VALUES  (119,1,'dummy114','Dummy 114','Dummy Dummy114','Dummy user ','dummy@dummy,du114','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (120,1,'dummy115','Dummy 115','Dummy Dummy115','Dummy user ','dummy@dummy,du115','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (121,1,'dummy116','Dummy 116','Dummy Dummy116','Dummy user ','dummy@dummy,du116','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (122,1,'dummy117','Dummy 117','Dummy Dummy117','Dummy user ','dummy@dummy,du117','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (123,1,'dummy118','Dummy 118','Dummy Dummy118','Dummy user ','dummy@dummy,du118','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (124,1,'dummy119','Dummy 119','Dummy Dummy119','Dummy user ','dummy@dummy,du119','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (125,1,'dummy120','Dummy 120','Dummy Dummy120','Dummy user ','dummy@dummy,du120','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (126,1,'dummy121','Dummy 121','Dummy Dummy121','Dummy user ','dummy@dummy,du121','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (127,1,'dummy122','Dummy 122','Dummy Dummy122','Dummy user ','dummy@dummy,du122','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (128,1,'dummy123','Dummy 123','Dummy Dummy123','Dummy user ','dummy@dummy,du123','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (129,1,'dummy124','Dummy 124','Dummy Dummy124','Dummy user ','dummy@dummy,du124','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (130,1,'dummy125','Dummy 125','Dummy Dummy125','Dummy user ','dummy@dummy,du125','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (131,1,'dummy126','Dummy 126','Dummy Dummy126','Dummy user ','dummy@dummy,du126','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (132,1,'dummy127','Dummy 127','Dummy Dummy127','Dummy user ','dummy@dummy,du127','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (133,1,'dummy128','Dummy 128','Dummy Dummy128','Dummy user ','dummy@dummy,du128','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (134,1,'dummy129','Dummy 129','Dummy Dummy129','Dummy user ','dummy@dummy,du129','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (135,1,'dummy130','Dummy 130','Dummy Dummy130','Dummy user ','dummy@dummy,du130','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (136,1,'dummy131','Dummy 131','Dummy Dummy131','Dummy user ','dummy@dummy,du131','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (137,1,'dummy132','Dummy 132','Dummy Dummy132','Dummy user ','dummy@dummy,du132','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (138,1,'dummy133','Dummy 133','Dummy Dummy133','Dummy user ','dummy@dummy,du133','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (139,1,'dummy134','Dummy 134','Dummy Dummy134','Dummy user ','dummy@dummy,du134','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (140,1,'dummy135','Dummy 135','Dummy Dummy135','Dummy user ','dummy@dummy,du135','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (141,1,'dummy136','Dummy 136','Dummy Dummy136','Dummy user ','dummy@dummy,du136','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (142,1,'dummy137','Dummy 137','Dummy Dummy137','Dummy user ','dummy@dummy,du137','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (143,1,'dummy138','Dummy 138','Dummy Dummy138','Dummy user ','dummy@dummy,du138','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (144,1,'dummy139','Dummy 139','Dummy Dummy139','Dummy user ','dummy@dummy,du139','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (145,1,'dummy140','Dummy 140','Dummy Dummy140','Dummy user ','dummy@dummy,du140','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (146,1,'dummy141','Dummy 141','Dummy Dummy141','Dummy user ','dummy@dummy,du141','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (147,1,'dummy142','Dummy 142','Dummy Dummy142','Dummy user ','dummy@dummy,du142','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (148,1,'dummy143','Dummy 143','Dummy Dummy143','Dummy user ','dummy@dummy,du143','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (149,1,'dummy144','Dummy 144','Dummy Dummy144','Dummy user ','dummy@dummy,du144','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (150,1,'dummy145','Dummy 145','Dummy Dummy145','Dummy user ','dummy@dummy,du145','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (151,1,'dummy146','Dummy 146','Dummy Dummy146','Dummy user ','dummy@dummy,du146','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (152,1,'dummy147','Dummy 147','Dummy Dummy147','Dummy user ','dummy@dummy,du147','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (153,1,'dummy148','Dummy 148','Dummy Dummy148','Dummy user ','dummy@dummy,du148','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (154,1,'dummy149','Dummy 149','Dummy Dummy149','Dummy user ','dummy@dummy,du149','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (155,1,'dummy150','Dummy 150','Dummy Dummy150','Dummy user ','dummy@dummy,du150','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (156,1,'dummy151','Dummy 151','Dummy Dummy151','Dummy user ','dummy@dummy,du151','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (157,1,'dummy152','Dummy 152','Dummy Dummy152','Dummy user ','dummy@dummy,du152','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (158,1,'dummy153','Dummy 153','Dummy Dummy153','Dummy user ','dummy@dummy,du153','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (159,1,'dummy154','Dummy 154','Dummy Dummy154','Dummy user ','dummy@dummy,du154','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (160,1,'dummy155','Dummy 155','Dummy Dummy155','Dummy user ','dummy@dummy,du155','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (161,1,'dummy156','Dummy 156','Dummy Dummy156','Dummy user ','dummy@dummy,du156','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (162,1,'dummy157','Dummy 157','Dummy Dummy157','Dummy user ','dummy@dummy,du157','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (163,1,'dummy158','Dummy 158','Dummy Dummy158','Dummy user ','dummy@dummy,du158','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (164,1,'dummy159','Dummy 159','Dummy Dummy159','Dummy user ','dummy@dummy,du159','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (165,1,'dummy160','Dummy 160','Dummy Dummy160','Dummy user ','dummy@dummy,du160','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (166,1,'dummy161','Dummy 161','Dummy Dummy161','Dummy user ','dummy@dummy,du161','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (167,1,'dummy162','Dummy 162','Dummy Dummy162','Dummy user ','dummy@dummy,du162','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (168,1,'dummy163','Dummy 163','Dummy Dummy163','Dummy user ','dummy@dummy,du163','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (169,1,'dummy164','Dummy 164','Dummy Dummy164','Dummy user ','dummy@dummy,du164','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (170,1,'dummy165','Dummy 165','Dummy Dummy165','Dummy user ','dummy@dummy,du165','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (171,1,'dummy166','Dummy 166','Dummy Dummy166','Dummy user ','dummy@dummy,du166','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (172,1,'dummy167','Dummy 167','Dummy Dummy167','Dummy user ','dummy@dummy,du167','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (173,1,'dummy168','Dummy 168','Dummy Dummy168','Dummy user ','dummy@dummy,du168','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (174,1,'dummy169','Dummy 169','Dummy Dummy169','Dummy user ','dummy@dummy,du169','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (175,1,'dummy170','Dummy 170','Dummy Dummy170','Dummy user ','dummy@dummy,du170','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (176,1,'dummy171','Dummy 171','Dummy Dummy171','Dummy user ','dummy@dummy,du171','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (177,1,'dummy172','Dummy 172','Dummy Dummy172','Dummy user ','dummy@dummy,du172','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (178,1,'dummy173','Dummy 173','Dummy Dummy173','Dummy user ','dummy@dummy,du173','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (179,1,'dummy174','Dummy 174','Dummy Dummy174','Dummy user ','dummy@dummy,du174','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (180,1,'dummy175','Dummy 175','Dummy Dummy175','Dummy user ','dummy@dummy,du175','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (181,1,'dummy176','Dummy 176','Dummy Dummy176','Dummy user ','dummy@dummy,du176','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (182,1,'dummy177','Dummy 177','Dummy Dummy177','Dummy user ','dummy@dummy,du177','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (183,1,'dummy178','Dummy 178','Dummy Dummy178','Dummy user ','dummy@dummy,du178','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (184,1,'dummy179','Dummy 179','Dummy Dummy179','Dummy user ','dummy@dummy,du179','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (185,1,'dummy180','Dummy 180','Dummy Dummy180','Dummy user ','dummy@dummy,du180','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (186,1,'dummy181','Dummy 181','Dummy Dummy181','Dummy user ','dummy@dummy,du181','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (187,1,'dummy182','Dummy 182','Dummy Dummy182','Dummy user ','dummy@dummy,du182','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (188,1,'dummy183','Dummy 183','Dummy Dummy183','Dummy user ','dummy@dummy,du183','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (189,1,'dummy184','Dummy 184','Dummy Dummy184','Dummy user ','dummy@dummy,du184','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (190,1,'dummy185','Dummy 185','Dummy Dummy185','Dummy user ','dummy@dummy,du185','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (191,1,'dummy186','Dummy 186','Dummy Dummy186','Dummy user ','dummy@dummy,du186','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (192,1,'dummy187','Dummy 187','Dummy Dummy187','Dummy user ','dummy@dummy,du187','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (193,1,'dummy188','Dummy 188','Dummy Dummy188','Dummy user ','dummy@dummy,du188','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (194,1,'dummy189','Dummy 189','Dummy Dummy189','Dummy user ','dummy@dummy,du189','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (195,1,'dummy190','Dummy 190','Dummy Dummy190','Dummy user ','dummy@dummy,du190','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (196,1,'dummy191','Dummy 191','Dummy Dummy191','Dummy user ','dummy@dummy,du191','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (197,1,'dummy192','Dummy 192','Dummy Dummy192','Dummy user ','dummy@dummy,du192','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (198,1,'dummy193','Dummy 193','Dummy Dummy193','Dummy user ','dummy@dummy,du193','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (199,1,'dummy194','Dummy 194','Dummy Dummy194','Dummy user ','dummy@dummy,du194','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (200,1,'dummy195','Dummy 195','Dummy Dummy195','Dummy user ','dummy@dummy,du195','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (201,1,'dummy196','Dummy 196','Dummy Dummy196','Dummy user ','dummy@dummy,du196','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (202,1,'dummy197','Dummy 197','Dummy Dummy197','Dummy user ','dummy@dummy,du197','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (203,1,'dummy198','Dummy 198','Dummy Dummy198','Dummy user ','dummy@dummy,du198','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (204,1,'dummy199','Dummy 199','Dummy Dummy199','Dummy user ','dummy@dummy,du199','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (205,1,'dummy200','Dummy 200','Dummy Dummy200','Dummy user ','dummy@dummy,du200','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (206,1,'dummy201','Dummy 201','Dummy Dummy201','Dummy user ','dummy@dummy,du201','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (207,1,'dummy202','Dummy 202','Dummy Dummy202','Dummy user ','dummy@dummy,du202','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (208,1,'dummy203','Dummy 203','Dummy Dummy203','Dummy user ','dummy@dummy,du203','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (209,1,'dummy204','Dummy 204','Dummy Dummy204','Dummy user ','dummy@dummy,du204','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (210,1,'dummy205','Dummy 205','Dummy Dummy205','Dummy user ','dummy@dummy,du205','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (211,1,'dummy206','Dummy 206','Dummy Dummy206','Dummy user ','dummy@dummy,du206','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (212,1,'dummy207','Dummy 207','Dummy Dummy207','Dummy user ','dummy@dummy,du207','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (213,1,'dummy208','Dummy 208','Dummy Dummy208','Dummy user ','dummy@dummy,du208','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (214,1,'dummy209','Dummy 209','Dummy Dummy209','Dummy user ','dummy@dummy,du209','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (215,1,'dummy210','Dummy 210','Dummy Dummy210','Dummy user ','dummy@dummy,du210','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (216,1,'dummy211','Dummy 211','Dummy Dummy211','Dummy user ','dummy@dummy,du211','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (217,1,'dummy212','Dummy 212','Dummy Dummy212','Dummy user ','dummy@dummy,du212','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (218,1,'dummy213','Dummy 213','Dummy Dummy213','Dummy user ','dummy@dummy,du213','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (219,1,'dummy214','Dummy 214','Dummy Dummy214','Dummy user ','dummy@dummy,du214','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (220,1,'dummy215','Dummy 215','Dummy Dummy215','Dummy user ','dummy@dummy,du215','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (221,1,'dummy216','Dummy 216','Dummy Dummy216','Dummy user ','dummy@dummy,du216','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (222,1,'dummy217','Dummy 217','Dummy Dummy217','Dummy user ','dummy@dummy,du217','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (223,1,'dummy218','Dummy 218','Dummy Dummy218','Dummy user ','dummy@dummy,du218','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (224,1,'dummy219','Dummy 219','Dummy Dummy219','Dummy user ','dummy@dummy,du219','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (225,1,'dummy220','Dummy 220','Dummy Dummy220','Dummy user ','dummy@dummy,du220','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (226,1,'dummy221','Dummy 221','Dummy Dummy221','Dummy user ','dummy@dummy,du221','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (227,1,'dummy222','Dummy 222','Dummy Dummy222','Dummy user ','dummy@dummy,du222','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (228,1,'dummy223','Dummy 223','Dummy Dummy223','Dummy user ','dummy@dummy,du223','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (229,1,'dummy224','Dummy 224','Dummy Dummy224','Dummy user ','dummy@dummy,du224','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (230,1,'dummy225','Dummy 225','Dummy Dummy225','Dummy user ','dummy@dummy,du225','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (231,1,'dummy226','Dummy 226','Dummy Dummy226','Dummy user ','dummy@dummy,du226','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (232,1,'dummy227','Dummy 227','Dummy Dummy227','Dummy user ','dummy@dummy,du227','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (233,1,'dummy228','Dummy 228','Dummy Dummy228','Dummy user ','dummy@dummy,du228','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (234,1,'dummy229','Dummy 229','Dummy Dummy229','Dummy user ','dummy@dummy,du229','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (235,1,'dummy230','Dummy 230','Dummy Dummy230','Dummy user ','dummy@dummy,du230','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (236,1,'dummy231','Dummy 231','Dummy Dummy231','Dummy user ','dummy@dummy,du231','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (237,1,'dummy232','Dummy 232','Dummy Dummy232','Dummy user ','dummy@dummy,du232','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (238,1,'dummy233','Dummy 233','Dummy Dummy233','Dummy user ','dummy@dummy,du233','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (239,1,'dummy234','Dummy 234','Dummy Dummy234','Dummy user ','dummy@dummy,du234','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (240,1,'dummy235','Dummy 235','Dummy Dummy235','Dummy user ','dummy@dummy,du235','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (241,1,'dummy236','Dummy 236','Dummy Dummy236','Dummy user ','dummy@dummy,du236','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (242,1,'dummy237','Dummy 237','Dummy Dummy237','Dummy user ','dummy@dummy,du237','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (243,1,'dummy238','Dummy 238','Dummy Dummy238','Dummy user ','dummy@dummy,du238','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (244,1,'dummy239','Dummy 239','Dummy Dummy239','Dummy user ','dummy@dummy,du239','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (245,1,'dummy240','Dummy 240','Dummy Dummy240','Dummy user ','dummy@dummy,du240','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (246,1,'dummy241','Dummy 241','Dummy Dummy241','Dummy user ','dummy@dummy,du241','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (247,1,'dummy242','Dummy 242','Dummy Dummy242','Dummy user ','dummy@dummy,du242','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (248,1,'dummy243','Dummy 243','Dummy Dummy243','Dummy user ','dummy@dummy,du243','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (249,1,'dummy244','Dummy 244','Dummy Dummy244','Dummy user ','dummy@dummy,du244','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (250,1,'dummy245','Dummy 245','Dummy Dummy245','Dummy user ','dummy@dummy,du245','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (251,1,'dummy246','Dummy 246','Dummy Dummy246','Dummy user ','dummy@dummy,du246','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (252,1,'dummy247','Dummy 247','Dummy Dummy247','Dummy user ','dummy@dummy,du247','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (253,1,'dummy248','Dummy 248','Dummy Dummy248','Dummy user ','dummy@dummy,du248','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (254,1,'dummy249','Dummy 249','Dummy Dummy249','Dummy user ','dummy@dummy,du249','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (255,1,'dummy250','Dummy 250','Dummy Dummy250','Dummy user ','dummy@dummy,du250','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (256,1,'dummy251','Dummy 251','Dummy Dummy251','Dummy user ','dummy@dummy,du251','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (257,1,'dummy252','Dummy 252','Dummy Dummy252','Dummy user ','dummy@dummy,du252','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (258,1,'dummy253','Dummy 253','Dummy Dummy253','Dummy user ','dummy@dummy,du253','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (259,1,'dummy254','Dummy 254','Dummy Dummy254','Dummy user ','dummy@dummy,du254','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (260,1,'dummy255','Dummy 255','Dummy Dummy255','Dummy user ','dummy@dummy,du255','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (261,1,'dummy256','Dummy 256','Dummy Dummy256','Dummy user ','dummy@dummy,du256','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (262,1,'dummy257','Dummy 257','Dummy Dummy257','Dummy user ','dummy@dummy,du257','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (263,1,'dummy258','Dummy 258','Dummy Dummy258','Dummy user ','dummy@dummy,du258','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (264,1,'dummy259','Dummy 259','Dummy Dummy259','Dummy user ','dummy@dummy,du259','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (265,1,'dummy260','Dummy 260','Dummy Dummy260','Dummy user ','dummy@dummy,du260','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (266,1,'dummy261','Dummy 261','Dummy Dummy261','Dummy user ','dummy@dummy,du261','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (267,1,'dummy262','Dummy 262','Dummy Dummy262','Dummy user ','dummy@dummy,du262','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (268,1,'dummy263','Dummy 263','Dummy Dummy263','Dummy user ','dummy@dummy,du263','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (269,1,'dummy264','Dummy 264','Dummy Dummy264','Dummy user ','dummy@dummy,du264','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (270,1,'dummy265','Dummy 265','Dummy Dummy265','Dummy user ','dummy@dummy,du265','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (271,1,'dummy266','Dummy 266','Dummy Dummy266','Dummy user ','dummy@dummy,du266','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (272,1,'dummy267','Dummy 267','Dummy Dummy267','Dummy user ','dummy@dummy,du267','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (273,1,'dummy268','Dummy 268','Dummy Dummy268','Dummy user ','dummy@dummy,du268','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (274,1,'dummy269','Dummy 269','Dummy Dummy269','Dummy user ','dummy@dummy,du269','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (275,1,'dummy270','Dummy 270','Dummy Dummy270','Dummy user ','dummy@dummy,du270','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (276,1,'dummy271','Dummy 271','Dummy Dummy271','Dummy user ','dummy@dummy,du271','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (277,1,'dummy272','Dummy 272','Dummy Dummy272','Dummy user ','dummy@dummy,du272','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (278,1,'dummy273','Dummy 273','Dummy Dummy273','Dummy user ','dummy@dummy,du273','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (279,1,'dummy274','Dummy 274','Dummy Dummy274','Dummy user ','dummy@dummy,du274','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (280,1,'dummy275','Dummy 275','Dummy Dummy275','Dummy user ','dummy@dummy,du275','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (281,1,'dummy276','Dummy 276','Dummy Dummy276','Dummy user ','dummy@dummy,du276','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (282,1,'dummy277','Dummy 277','Dummy Dummy277','Dummy user ','dummy@dummy,du277','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (283,1,'dummy278','Dummy 278','Dummy Dummy278','Dummy user ','dummy@dummy,du278','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (284,1,'dummy279','Dummy 279','Dummy Dummy279','Dummy user ','dummy@dummy,du279','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (285,1,'dummy280','Dummy 280','Dummy Dummy280','Dummy user ','dummy@dummy,du280','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (286,1,'dummy281','Dummy 281','Dummy Dummy281','Dummy user ','dummy@dummy,du281','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (287,1,'dummy282','Dummy 282','Dummy Dummy282','Dummy user ','dummy@dummy,du282','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (288,1,'dummy283','Dummy 283','Dummy Dummy283','Dummy user ','dummy@dummy,du283','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (289,1,'dummy284','Dummy 284','Dummy Dummy284','Dummy user ','dummy@dummy,du284','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (290,1,'dummy285','Dummy 285','Dummy Dummy285','Dummy user ','dummy@dummy,du285','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (291,1,'dummy286','Dummy 286','Dummy Dummy286','Dummy user ','dummy@dummy,du286','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (292,1,'dummy287','Dummy 287','Dummy Dummy287','Dummy user ','dummy@dummy,du287','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (293,1,'dummy288','Dummy 288','Dummy Dummy288','Dummy user ','dummy@dummy,du288','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (294,1,'dummy289','Dummy 289','Dummy Dummy289','Dummy user ','dummy@dummy,du289','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (295,1,'dummy290','Dummy 290','Dummy Dummy290','Dummy user ','dummy@dummy,du290','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (296,1,'dummy291','Dummy 291','Dummy Dummy291','Dummy user ','dummy@dummy,du291','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (297,1,'dummy292','Dummy 292','Dummy Dummy292','Dummy user ','dummy@dummy,du292','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (298,1,'dummy293','Dummy 293','Dummy Dummy293','Dummy user ','dummy@dummy,du293','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (299,1,'dummy294','Dummy 294','Dummy Dummy294','Dummy user ','dummy@dummy,du294','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (300,1,'dummy295','Dummy 295','Dummy Dummy295','Dummy user ','dummy@dummy,du295','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (301,1,'dummy296','Dummy 296','Dummy Dummy296','Dummy user ','dummy@dummy,du296','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (302,1,'dummy297','Dummy 297','Dummy Dummy297','Dummy user ','dummy@dummy,du297','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (303,1,'dummy298','Dummy 298','Dummy Dummy298','Dummy user ','dummy@dummy,du298','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (304,1,'dummy299','Dummy 299','Dummy Dummy299','Dummy user ','dummy@dummy,du299','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (305,1,'dummy300','Dummy 300','Dummy Dummy300','Dummy user ','dummy@dummy,du300','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (306,1,'dummy301','Dummy 301','Dummy Dummy301','Dummy user ','dummy@dummy,du301','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (307,1,'dummy302','Dummy 302','Dummy Dummy302','Dummy user ','dummy@dummy,du302','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (308,1,'dummy303','Dummy 303','Dummy Dummy303','Dummy user ','dummy@dummy,du303','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (309,1,'dummy304','Dummy 304','Dummy Dummy304','Dummy user ','dummy@dummy,du304','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (310,1,'dummy305','Dummy 305','Dummy Dummy305','Dummy user ','dummy@dummy,du305','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (311,1,'dummy306','Dummy 306','Dummy Dummy306','Dummy user ','dummy@dummy,du306','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (312,1,'dummy307','Dummy 307','Dummy Dummy307','Dummy user ','dummy@dummy,du307','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (313,1,'dummy308','Dummy 308','Dummy Dummy308','Dummy user ','dummy@dummy,du308','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (314,1,'dummy309','Dummy 309','Dummy Dummy309','Dummy user ','dummy@dummy,du309','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (315,1,'dummy310','Dummy 310','Dummy Dummy310','Dummy user ','dummy@dummy,du310','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (316,1,'dummy311','Dummy 311','Dummy Dummy311','Dummy user ','dummy@dummy,du311','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (317,1,'dummy312','Dummy 312','Dummy Dummy312','Dummy user ','dummy@dummy,du312','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (318,1,'dummy313','Dummy 313','Dummy Dummy313','Dummy user ','dummy@dummy,du313','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (319,1,'dummy314','Dummy 314','Dummy Dummy314','Dummy user ','dummy@dummy,du314','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (320,1,'dummy315','Dummy 315','Dummy Dummy315','Dummy user ','dummy@dummy,du315','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (321,1,'dummy316','Dummy 316','Dummy Dummy316','Dummy user ','dummy@dummy,du316','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (322,1,'dummy317','Dummy 317','Dummy Dummy317','Dummy user ','dummy@dummy,du317','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (323,1,'dummy318','Dummy 318','Dummy Dummy318','Dummy user ','dummy@dummy,du318','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (324,1,'dummy319','Dummy 319','Dummy Dummy319','Dummy user ','dummy@dummy,du319','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (325,1,'dummy320','Dummy 320','Dummy Dummy320','Dummy user ','dummy@dummy,du320','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (326,1,'dummy321','Dummy 321','Dummy Dummy321','Dummy user ','dummy@dummy,du321','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (327,1,'dummy322','Dummy 322','Dummy Dummy322','Dummy user ','dummy@dummy,du322','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (328,1,'dummy323','Dummy 323','Dummy Dummy323','Dummy user ','dummy@dummy,du323','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (329,1,'dummy324','Dummy 324','Dummy Dummy324','Dummy user ','dummy@dummy,du324','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (330,1,'dummy325','Dummy 325','Dummy Dummy325','Dummy user ','dummy@dummy,du325','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (331,1,'dummy326','Dummy 326','Dummy Dummy326','Dummy user ','dummy@dummy,du326','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (332,1,'dummy327','Dummy 327','Dummy Dummy327','Dummy user ','dummy@dummy,du327','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (333,1,'dummy328','Dummy 328','Dummy Dummy328','Dummy user ','dummy@dummy,du328','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (334,1,'dummy329','Dummy 329','Dummy Dummy329','Dummy user ','dummy@dummy,du329','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (335,1,'dummy330','Dummy 330','Dummy Dummy330','Dummy user ','dummy@dummy,du330','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (336,1,'dummy331','Dummy 331','Dummy Dummy331','Dummy user ','dummy@dummy,du331','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (337,1,'dummy332','Dummy 332','Dummy Dummy332','Dummy user ','dummy@dummy,du332','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (338,1,'dummy333','Dummy 333','Dummy Dummy333','Dummy user ','dummy@dummy,du333','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (339,1,'dummy334','Dummy 334','Dummy Dummy334','Dummy user ','dummy@dummy,du334','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (340,1,'dummy335','Dummy 335','Dummy Dummy335','Dummy user ','dummy@dummy,du335','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (341,1,'dummy336','Dummy 336','Dummy Dummy336','Dummy user ','dummy@dummy,du336','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (342,1,'dummy337','Dummy 337','Dummy Dummy337','Dummy user ','dummy@dummy,du337','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (343,1,'dummy338','Dummy 338','Dummy Dummy338','Dummy user ','dummy@dummy,du338','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (344,1,'dummy339','Dummy 339','Dummy Dummy339','Dummy user ','dummy@dummy,du339','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (345,1,'dummy340','Dummy 340','Dummy Dummy340','Dummy user ','dummy@dummy,du340','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (346,1,'dummy341','Dummy 341','Dummy Dummy341','Dummy user ','dummy@dummy,du341','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (347,1,'dummy342','Dummy 342','Dummy Dummy342','Dummy user ','dummy@dummy,du342','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (348,1,'dummy343','Dummy 343','Dummy Dummy343','Dummy user ','dummy@dummy,du343','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (349,1,'dummy344','Dummy 344','Dummy Dummy344','Dummy user ','dummy@dummy,du344','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (350,1,'dummy345','Dummy 345','Dummy Dummy345','Dummy user ','dummy@dummy,du345','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (351,1,'dummy346','Dummy 346','Dummy Dummy346','Dummy user ','dummy@dummy,du346','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (352,1,'dummy347','Dummy 347','Dummy Dummy347','Dummy user ','dummy@dummy,du347','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (353,1,'dummy348','Dummy 348','Dummy Dummy348','Dummy user ','dummy@dummy,du348','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (354,1,'dummy349','Dummy 349','Dummy Dummy349','Dummy user ','dummy@dummy,du349','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (355,1,'dummy350','Dummy 350','Dummy Dummy350','Dummy user ','dummy@dummy,du350','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (356,1,'dummy351','Dummy 351','Dummy Dummy351','Dummy user ','dummy@dummy,du351','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (357,1,'dummy352','Dummy 352','Dummy Dummy352','Dummy user ','dummy@dummy,du352','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (358,1,'dummy353','Dummy 353','Dummy Dummy353','Dummy user ','dummy@dummy,du353','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (359,1,'dummy354','Dummy 354','Dummy Dummy354','Dummy user ','dummy@dummy,du354','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (360,1,'dummy355','Dummy 355','Dummy Dummy355','Dummy user ','dummy@dummy,du355','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (361,1,'dummy356','Dummy 356','Dummy Dummy356','Dummy user ','dummy@dummy,du356','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (362,1,'dummy357','Dummy 357','Dummy Dummy357','Dummy user ','dummy@dummy,du357','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (363,1,'dummy358','Dummy 358','Dummy Dummy358','Dummy user ','dummy@dummy,du358','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (364,1,'dummy359','Dummy 359','Dummy Dummy359','Dummy user ','dummy@dummy,du359','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (365,1,'dummy360','Dummy 360','Dummy Dummy360','Dummy user ','dummy@dummy,du360','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (366,1,'dummy361','Dummy 361','Dummy Dummy361','Dummy user ','dummy@dummy,du361','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (367,1,'dummy362','Dummy 362','Dummy Dummy362','Dummy user ','dummy@dummy,du362','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (368,1,'dummy363','Dummy 363','Dummy Dummy363','Dummy user ','dummy@dummy,du363','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (369,1,'dummy364','Dummy 364','Dummy Dummy364','Dummy user ','dummy@dummy,du364','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (370,1,'dummy365','Dummy 365','Dummy Dummy365','Dummy user ','dummy@dummy,du365','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (371,1,'dummy366','Dummy 366','Dummy Dummy366','Dummy user ','dummy@dummy,du366','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (372,1,'dummy367','Dummy 367','Dummy Dummy367','Dummy user ','dummy@dummy,du367','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (373,1,'dummy368','Dummy 368','Dummy Dummy368','Dummy user ','dummy@dummy,du368','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (374,1,'dummy369','Dummy 369','Dummy Dummy369','Dummy user ','dummy@dummy,du369','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (375,1,'dummy370','Dummy 370','Dummy Dummy370','Dummy user ','dummy@dummy,du370','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (376,1,'dummy371','Dummy 371','Dummy Dummy371','Dummy user ','dummy@dummy,du371','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (377,1,'dummy372','Dummy 372','Dummy Dummy372','Dummy user ','dummy@dummy,du372','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (378,1,'dummy373','Dummy 373','Dummy Dummy373','Dummy user ','dummy@dummy,du373','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (379,1,'dummy374','Dummy 374','Dummy Dummy374','Dummy user ','dummy@dummy,du374','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (380,1,'dummy375','Dummy 375','Dummy Dummy375','Dummy user ','dummy@dummy,du375','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (381,1,'dummy376','Dummy 376','Dummy Dummy376','Dummy user ','dummy@dummy,du376','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (382,1,'dummy377','Dummy 377','Dummy Dummy377','Dummy user ','dummy@dummy,du377','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (383,1,'dummy378','Dummy 378','Dummy Dummy378','Dummy user ','dummy@dummy,du378','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (384,1,'dummy379','Dummy 379','Dummy Dummy379','Dummy user ','dummy@dummy,du379','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (385,1,'dummy380','Dummy 380','Dummy Dummy380','Dummy user ','dummy@dummy,du380','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (386,1,'dummy381','Dummy 381','Dummy Dummy381','Dummy user ','dummy@dummy,du381','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (387,1,'dummy382','Dummy 382','Dummy Dummy382','Dummy user ','dummy@dummy,du382','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (388,1,'dummy383','Dummy 383','Dummy Dummy383','Dummy user ','dummy@dummy,du383','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (389,1,'dummy384','Dummy 384','Dummy Dummy384','Dummy user ','dummy@dummy,du384','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (390,1,'dummy385','Dummy 385','Dummy Dummy385','Dummy user ','dummy@dummy,du385','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (391,1,'dummy386','Dummy 386','Dummy Dummy386','Dummy user ','dummy@dummy,du386','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (392,1,'dummy387','Dummy 387','Dummy Dummy387','Dummy user ','dummy@dummy,du387','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (393,1,'dummy388','Dummy 388','Dummy Dummy388','Dummy user ','dummy@dummy,du388','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (394,1,'dummy389','Dummy 389','Dummy Dummy389','Dummy user ','dummy@dummy,du389','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (395,1,'dummy390','Dummy 390','Dummy Dummy390','Dummy user ','dummy@dummy,du390','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (396,1,'dummy391','Dummy 391','Dummy Dummy391','Dummy user ','dummy@dummy,du391','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (397,1,'dummy392','Dummy 392','Dummy Dummy392','Dummy user ','dummy@dummy,du392','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (398,1,'dummy393','Dummy 393','Dummy Dummy393','Dummy user ','dummy@dummy,du393','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (399,1,'dummy394','Dummy 394','Dummy Dummy394','Dummy user ','dummy@dummy,du394','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (400,1,'dummy395','Dummy 395','Dummy Dummy395','Dummy user ','dummy@dummy,du395','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (401,1,'dummy396','Dummy 396','Dummy Dummy396','Dummy user ','dummy@dummy,du396','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (402,1,'dummy397','Dummy 397','Dummy Dummy397','Dummy user ','dummy@dummy,du397','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (403,1,'dummy398','Dummy 398','Dummy Dummy398','Dummy user ','dummy@dummy,du398','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (404,1,'dummy399','Dummy 399','Dummy Dummy399','Dummy user ','dummy@dummy,du399','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (405,1,'dummy400','Dummy 400','Dummy Dummy400','Dummy user ','dummy@dummy,du400','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (406,1,'dummy401','Dummy 401','Dummy Dummy401','Dummy user ','dummy@dummy,du401','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (407,1,'dummy402','Dummy 402','Dummy Dummy402','Dummy user ','dummy@dummy,du402','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (408,1,'dummy403','Dummy 403','Dummy Dummy403','Dummy user ','dummy@dummy,du403','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (409,1,'dummy404','Dummy 404','Dummy Dummy404','Dummy user ','dummy@dummy,du404','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (410,1,'dummy405','Dummy 405','Dummy Dummy405','Dummy user ','dummy@dummy,du405','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (411,1,'dummy406','Dummy 406','Dummy Dummy406','Dummy user ','dummy@dummy,du406','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (412,1,'dummy407','Dummy 407','Dummy Dummy407','Dummy user ','dummy@dummy,du407','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (413,1,'dummy408','Dummy 408','Dummy Dummy408','Dummy user ','dummy@dummy,du408','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (414,1,'dummy409','Dummy 409','Dummy Dummy409','Dummy user ','dummy@dummy,du409','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (415,1,'dummy410','Dummy 410','Dummy Dummy410','Dummy user ','dummy@dummy,du410','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (416,1,'dummy411','Dummy 411','Dummy Dummy411','Dummy user ','dummy@dummy,du411','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (417,1,'dummy412','Dummy 412','Dummy Dummy412','Dummy user ','dummy@dummy,du412','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (418,1,'dummy413','Dummy 413','Dummy Dummy413','Dummy user ','dummy@dummy,du413','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (419,1,'dummy414','Dummy 414','Dummy Dummy414','Dummy user ','dummy@dummy,du414','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (420,1,'dummy415','Dummy 415','Dummy Dummy415','Dummy user ','dummy@dummy,du415','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (421,1,'dummy416','Dummy 416','Dummy Dummy416','Dummy user ','dummy@dummy,du416','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (422,1,'dummy417','Dummy 417','Dummy Dummy417','Dummy user ','dummy@dummy,du417','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (423,1,'dummy418','Dummy 418','Dummy Dummy418','Dummy user ','dummy@dummy,du418','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (424,1,'dummy419','Dummy 419','Dummy Dummy419','Dummy user ','dummy@dummy,du419','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (425,1,'dummy420','Dummy 420','Dummy Dummy420','Dummy user ','dummy@dummy,du420','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (426,1,'dummy421','Dummy 421','Dummy Dummy421','Dummy user ','dummy@dummy,du421','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (427,1,'dummy422','Dummy 422','Dummy Dummy422','Dummy user ','dummy@dummy,du422','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (428,1,'dummy423','Dummy 423','Dummy Dummy423','Dummy user ','dummy@dummy,du423','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (429,1,'dummy424','Dummy 424','Dummy Dummy424','Dummy user ','dummy@dummy,du424','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (430,1,'dummy425','Dummy 425','Dummy Dummy425','Dummy user ','dummy@dummy,du425','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (431,1,'dummy426','Dummy 426','Dummy Dummy426','Dummy user ','dummy@dummy,du426','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (432,1,'dummy427','Dummy 427','Dummy Dummy427','Dummy user ','dummy@dummy,du427','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (433,1,'dummy428','Dummy 428','Dummy Dummy428','Dummy user ','dummy@dummy,du428','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (434,1,'dummy429','Dummy 429','Dummy Dummy429','Dummy user ','dummy@dummy,du429','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (435,1,'dummy430','Dummy 430','Dummy Dummy430','Dummy user ','dummy@dummy,du430','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (436,1,'dummy431','Dummy 431','Dummy Dummy431','Dummy user ','dummy@dummy,du431','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (437,1,'dummy432','Dummy 432','Dummy Dummy432','Dummy user ','dummy@dummy,du432','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (438,1,'dummy433','Dummy 433','Dummy Dummy433','Dummy user ','dummy@dummy,du433','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (439,1,'dummy434','Dummy 434','Dummy Dummy434','Dummy user ','dummy@dummy,du434','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (440,1,'dummy435','Dummy 435','Dummy Dummy435','Dummy user ','dummy@dummy,du435','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (441,1,'dummy436','Dummy 436','Dummy Dummy436','Dummy user ','dummy@dummy,du436','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (442,1,'dummy437','Dummy 437','Dummy Dummy437','Dummy user ','dummy@dummy,du437','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (443,1,'dummy438','Dummy 438','Dummy Dummy438','Dummy user ','dummy@dummy,du438','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (444,1,'dummy439','Dummy 439','Dummy Dummy439','Dummy user ','dummy@dummy,du439','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (445,1,'dummy440','Dummy 440','Dummy Dummy440','Dummy user ','dummy@dummy,du440','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (446,1,'dummy441','Dummy 441','Dummy Dummy441','Dummy user ','dummy@dummy,du441','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (447,1,'dummy442','Dummy 442','Dummy Dummy442','Dummy user ','dummy@dummy,du442','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (448,1,'dummy443','Dummy 443','Dummy Dummy443','Dummy user ','dummy@dummy,du443','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (449,1,'dummy444','Dummy 444','Dummy Dummy444','Dummy user ','dummy@dummy,du444','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (450,1,'dummy445','Dummy 445','Dummy Dummy445','Dummy user ','dummy@dummy,du445','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (451,1,'dummy446','Dummy 446','Dummy Dummy446','Dummy user ','dummy@dummy,du446','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (452,1,'dummy447','Dummy 447','Dummy Dummy447','Dummy user ','dummy@dummy,du447','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (453,1,'dummy448','Dummy 448','Dummy Dummy448','Dummy user ','dummy@dummy,du448','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (454,1,'dummy449','Dummy 449','Dummy Dummy449','Dummy user ','dummy@dummy,du449','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (455,1,'dummy450','Dummy 450','Dummy Dummy450','Dummy user ','dummy@dummy,du450','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (456,1,'dummy451','Dummy 451','Dummy Dummy451','Dummy user ','dummy@dummy,du451','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (457,1,'dummy452','Dummy 452','Dummy Dummy452','Dummy user ','dummy@dummy,du452','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (458,1,'dummy453','Dummy 453','Dummy Dummy453','Dummy user ','dummy@dummy,du453','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (459,1,'dummy454','Dummy 454','Dummy Dummy454','Dummy user ','dummy@dummy,du454','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (460,1,'dummy455','Dummy 455','Dummy Dummy455','Dummy user ','dummy@dummy,du455','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (461,1,'dummy456','Dummy 456','Dummy Dummy456','Dummy user ','dummy@dummy,du456','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (462,1,'dummy457','Dummy 457','Dummy Dummy457','Dummy user ','dummy@dummy,du457','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (463,1,'dummy458','Dummy 458','Dummy Dummy458','Dummy user ','dummy@dummy,du458','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (464,1,'dummy459','Dummy 459','Dummy Dummy459','Dummy user ','dummy@dummy,du459','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (465,1,'dummy460','Dummy 460','Dummy Dummy460','Dummy user ','dummy@dummy,du460','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (466,1,'dummy461','Dummy 461','Dummy Dummy461','Dummy user ','dummy@dummy,du461','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (467,1,'dummy462','Dummy 462','Dummy Dummy462','Dummy user ','dummy@dummy,du462','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (468,1,'dummy463','Dummy 463','Dummy Dummy463','Dummy user ','dummy@dummy,du463','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (469,1,'dummy464','Dummy 464','Dummy Dummy464','Dummy user ','dummy@dummy,du464','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (470,1,'dummy465','Dummy 465','Dummy Dummy465','Dummy user ','dummy@dummy,du465','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1);
INSERT INTO `kinton`.`user` VALUES  (471,1,'dummy466','Dummy 466','Dummy Dummy466','Dummy user ','dummy@dummy,du466','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (472,1,'dummy467','Dummy 467','Dummy Dummy467','Dummy user ','dummy@dummy,du467','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (473,1,'dummy468','Dummy 468','Dummy Dummy468','Dummy user ','dummy@dummy,du468','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (474,1,'dummy469','Dummy 469','Dummy Dummy469','Dummy user ','dummy@dummy,du469','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (475,1,'dummy470','Dummy 470','Dummy Dummy470','Dummy user ','dummy@dummy,du470','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:18',1),
 (476,1,'dummy471','Dummy 471','Dummy Dummy471','Dummy user ','dummy@dummy,du471','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (477,1,'dummy472','Dummy 472','Dummy Dummy472','Dummy user ','dummy@dummy,du472','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (478,1,'dummy473','Dummy 473','Dummy Dummy473','Dummy user ','dummy@dummy,du473','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (479,1,'dummy474','Dummy 474','Dummy Dummy474','Dummy user ','dummy@dummy,du474','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (480,1,'dummy475','Dummy 475','Dummy Dummy475','Dummy user ','dummy@dummy,du475','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (481,1,'dummy476','Dummy 476','Dummy Dummy476','Dummy user ','dummy@dummy,du476','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (482,1,'dummy477','Dummy 477','Dummy Dummy477','Dummy user ','dummy@dummy,du477','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (483,1,'dummy478','Dummy 478','Dummy Dummy478','Dummy user ','dummy@dummy,du478','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (484,1,'dummy479','Dummy 479','Dummy Dummy479','Dummy user ','dummy@dummy,du479','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (485,1,'dummy480','Dummy 480','Dummy Dummy480','Dummy user ','dummy@dummy,du480','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (486,1,'dummy481','Dummy 481','Dummy Dummy481','Dummy user ','dummy@dummy,du481','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (487,1,'dummy482','Dummy 482','Dummy Dummy482','Dummy user ','dummy@dummy,du482','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (488,1,'dummy483','Dummy 483','Dummy Dummy483','Dummy user ','dummy@dummy,du483','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (489,1,'dummy484','Dummy 484','Dummy Dummy484','Dummy user ','dummy@dummy,du484','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (490,1,'dummy485','Dummy 485','Dummy Dummy485','Dummy user ','dummy@dummy,du485','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (491,1,'dummy486','Dummy 486','Dummy Dummy486','Dummy user ','dummy@dummy,du486','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (492,1,'dummy487','Dummy 487','Dummy Dummy487','Dummy user ','dummy@dummy,du487','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (493,1,'dummy488','Dummy 488','Dummy Dummy488','Dummy user ','dummy@dummy,du488','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (494,1,'dummy489','Dummy 489','Dummy Dummy489','Dummy user ','dummy@dummy,du489','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (495,1,'dummy490','Dummy 490','Dummy Dummy490','Dummy user ','dummy@dummy,du490','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (496,1,'dummy491','Dummy 491','Dummy Dummy491','Dummy user ','dummy@dummy,du491','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (497,1,'dummy492','Dummy 492','Dummy Dummy492','Dummy user ','dummy@dummy,du492','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (498,1,'dummy493','Dummy 493','Dummy Dummy493','Dummy user ','dummy@dummy,du493','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (499,1,'dummy494','Dummy 494','Dummy Dummy494','Dummy user ','dummy@dummy,du494','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (500,1,'dummy495','Dummy 495','Dummy Dummy495','Dummy user ','dummy@dummy,du495','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (501,1,'dummy496','Dummy 496','Dummy Dummy496','Dummy user ','dummy@dummy,du496','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (502,1,'dummy497','Dummy 497','Dummy Dummy497','Dummy user ','dummy@dummy,du497','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (503,1,'dummy498','Dummy 498','Dummy Dummy498','Dummy user ','dummy@dummy,du498','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (504,1,'dummy499','Dummy 499','Dummy Dummy499','Dummy user ','dummy@dummy,du499','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (505,1,'dummy500','Dummy 500','Dummy Dummy500','Dummy user ','dummy@dummy,du500','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (506,1,'dummy501','Dummy 501','Dummy Dummy501','Dummy user ','dummy@dummy,du501','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (507,1,'dummy502','Dummy 502','Dummy Dummy502','Dummy user ','dummy@dummy,du502','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (508,1,'dummy503','Dummy 503','Dummy Dummy503','Dummy user ','dummy@dummy,du503','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (509,1,'dummy504','Dummy 504','Dummy Dummy504','Dummy user ','dummy@dummy,du504','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (510,1,'dummy505','Dummy 505','Dummy Dummy505','Dummy user ','dummy@dummy,du505','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (511,1,'dummy506','Dummy 506','Dummy Dummy506','Dummy user ','dummy@dummy,du506','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (512,1,'dummy507','Dummy 507','Dummy Dummy507','Dummy user ','dummy@dummy,du507','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (513,1,'dummy508','Dummy 508','Dummy Dummy508','Dummy user ','dummy@dummy,du508','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (514,1,'dummy509','Dummy 509','Dummy Dummy509','Dummy user ','dummy@dummy,du509','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (515,1,'dummy510','Dummy 510','Dummy Dummy510','Dummy user ','dummy@dummy,du510','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (516,1,'dummy511','Dummy 511','Dummy Dummy511','Dummy user ','dummy@dummy,du511','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (517,1,'dummy512','Dummy 512','Dummy Dummy512','Dummy user ','dummy@dummy,du512','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (518,1,'dummy513','Dummy 513','Dummy Dummy513','Dummy user ','dummy@dummy,du513','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (519,1,'dummy514','Dummy 514','Dummy Dummy514','Dummy user ','dummy@dummy,du514','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (520,1,'dummy515','Dummy 515','Dummy Dummy515','Dummy user ','dummy@dummy,du515','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (521,1,'dummy516','Dummy 516','Dummy Dummy516','Dummy user ','dummy@dummy,du516','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (522,1,'dummy517','Dummy 517','Dummy Dummy517','Dummy user ','dummy@dummy,du517','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (523,1,'dummy518','Dummy 518','Dummy Dummy518','Dummy user ','dummy@dummy,du518','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (524,1,'dummy519','Dummy 519','Dummy Dummy519','Dummy user ','dummy@dummy,du519','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (525,1,'dummy520','Dummy 520','Dummy Dummy520','Dummy user ','dummy@dummy,du520','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (526,1,'dummy521','Dummy 521','Dummy Dummy521','Dummy user ','dummy@dummy,du521','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (527,1,'dummy522','Dummy 522','Dummy Dummy522','Dummy user ','dummy@dummy,du522','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (528,1,'dummy523','Dummy 523','Dummy Dummy523','Dummy user ','dummy@dummy,du523','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (529,1,'dummy524','Dummy 524','Dummy Dummy524','Dummy user ','dummy@dummy,du524','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (530,1,'dummy525','Dummy 525','Dummy Dummy525','Dummy user ','dummy@dummy,du525','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (531,1,'dummy526','Dummy 526','Dummy Dummy526','Dummy user ','dummy@dummy,du526','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (532,1,'dummy527','Dummy 527','Dummy Dummy527','Dummy user ','dummy@dummy,du527','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (533,1,'dummy528','Dummy 528','Dummy Dummy528','Dummy user ','dummy@dummy,du528','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (534,1,'dummy529','Dummy 529','Dummy Dummy529','Dummy user ','dummy@dummy,du529','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (535,1,'dummy530','Dummy 530','Dummy Dummy530','Dummy user ','dummy@dummy,du530','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (536,1,'dummy531','Dummy 531','Dummy Dummy531','Dummy user ','dummy@dummy,du531','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (537,1,'dummy532','Dummy 532','Dummy Dummy532','Dummy user ','dummy@dummy,du532','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (538,1,'dummy533','Dummy 533','Dummy Dummy533','Dummy user ','dummy@dummy,du533','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (539,1,'dummy534','Dummy 534','Dummy Dummy534','Dummy user ','dummy@dummy,du534','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (540,1,'dummy535','Dummy 535','Dummy Dummy535','Dummy user ','dummy@dummy,du535','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (541,1,'dummy536','Dummy 536','Dummy Dummy536','Dummy user ','dummy@dummy,du536','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (542,1,'dummy537','Dummy 537','Dummy Dummy537','Dummy user ','dummy@dummy,du537','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (543,1,'dummy538','Dummy 538','Dummy Dummy538','Dummy user ','dummy@dummy,du538','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (544,1,'dummy539','Dummy 539','Dummy Dummy539','Dummy user ','dummy@dummy,du539','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (545,1,'dummy540','Dummy 540','Dummy Dummy540','Dummy user ','dummy@dummy,du540','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (546,1,'dummy541','Dummy 541','Dummy Dummy541','Dummy user ','dummy@dummy,du541','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (547,1,'dummy542','Dummy 542','Dummy Dummy542','Dummy user ','dummy@dummy,du542','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (548,1,'dummy543','Dummy 543','Dummy Dummy543','Dummy user ','dummy@dummy,du543','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (549,1,'dummy544','Dummy 544','Dummy Dummy544','Dummy user ','dummy@dummy,du544','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (550,1,'dummy545','Dummy 545','Dummy Dummy545','Dummy user ','dummy@dummy,du545','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1);
INSERT INTO `kinton`.`user` VALUES  (551,1,'dummy546','Dummy 546','Dummy Dummy546','Dummy user ','dummy@dummy,du546','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (552,1,'dummy547','Dummy 547','Dummy Dummy547','Dummy user ','dummy@dummy,du547','es_ES','dummy',1,'2008-12-16 14:55:23',1,'2008-12-16 14:58:19',1),
 (553,1,'dummy548','Dummy 548','Dummy Dummy548','Dummy user ','dummy@dummy,du548','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (554,1,'dummy549','Dummy 549','Dummy Dummy549','Dummy user ','dummy@dummy,du549','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (555,1,'dummy550','Dummy 550','Dummy Dummy550','Dummy user ','dummy@dummy,du550','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (556,1,'dummy551','Dummy 551','Dummy Dummy551','Dummy user ','dummy@dummy,du551','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (557,1,'dummy552','Dummy 552','Dummy Dummy552','Dummy user ','dummy@dummy,du552','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (558,1,'dummy553','Dummy 553','Dummy Dummy553','Dummy user ','dummy@dummy,du553','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (559,1,'dummy554','Dummy 554','Dummy Dummy554','Dummy user ','dummy@dummy,du554','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (560,1,'dummy555','Dummy 555','Dummy Dummy555','Dummy user ','dummy@dummy,du555','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (561,1,'dummy556','Dummy 556','Dummy Dummy556','Dummy user ','dummy@dummy,du556','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (562,1,'dummy557','Dummy 557','Dummy Dummy557','Dummy user ','dummy@dummy,du557','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (563,1,'dummy558','Dummy 558','Dummy Dummy558','Dummy user ','dummy@dummy,du558','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (564,1,'dummy559','Dummy 559','Dummy Dummy559','Dummy user ','dummy@dummy,du559','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (565,1,'dummy560','Dummy 560','Dummy Dummy560','Dummy user ','dummy@dummy,du560','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (566,1,'dummy561','Dummy 561','Dummy Dummy561','Dummy user ','dummy@dummy,du561','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (567,1,'dummy562','Dummy 562','Dummy Dummy562','Dummy user ','dummy@dummy,du562','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (568,1,'dummy563','Dummy 563','Dummy Dummy563','Dummy user ','dummy@dummy,du563','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (569,1,'dummy564','Dummy 564','Dummy Dummy564','Dummy user ','dummy@dummy,du564','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (570,1,'dummy565','Dummy 565','Dummy Dummy565','Dummy user ','dummy@dummy,du565','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (571,1,'dummy566','Dummy 566','Dummy Dummy566','Dummy user ','dummy@dummy,du566','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (572,1,'dummy567','Dummy 567','Dummy Dummy567','Dummy user ','dummy@dummy,du567','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (573,1,'dummy568','Dummy 568','Dummy Dummy568','Dummy user ','dummy@dummy,du568','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (574,1,'dummy569','Dummy 569','Dummy Dummy569','Dummy user ','dummy@dummy,du569','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (575,1,'dummy570','Dummy 570','Dummy Dummy570','Dummy user ','dummy@dummy,du570','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (576,1,'dummy571','Dummy 571','Dummy Dummy571','Dummy user ','dummy@dummy,du571','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (577,1,'dummy572','Dummy 572','Dummy Dummy572','Dummy user ','dummy@dummy,du572','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (578,1,'dummy573','Dummy 573','Dummy Dummy573','Dummy user ','dummy@dummy,du573','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (579,1,'dummy574','Dummy 574','Dummy Dummy574','Dummy user ','dummy@dummy,du574','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (580,1,'dummy575','Dummy 575','Dummy Dummy575','Dummy user ','dummy@dummy,du575','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (581,1,'dummy576','Dummy 576','Dummy Dummy576','Dummy user ','dummy@dummy,du576','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (582,1,'dummy577','Dummy 577','Dummy Dummy577','Dummy user ','dummy@dummy,du577','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (583,1,'dummy578','Dummy 578','Dummy Dummy578','Dummy user ','dummy@dummy,du578','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (584,1,'dummy579','Dummy 579','Dummy Dummy579','Dummy user ','dummy@dummy,du579','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (585,1,'dummy580','Dummy 580','Dummy Dummy580','Dummy user ','dummy@dummy,du580','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (586,1,'dummy581','Dummy 581','Dummy Dummy581','Dummy user ','dummy@dummy,du581','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (587,1,'dummy582','Dummy 582','Dummy Dummy582','Dummy user ','dummy@dummy,du582','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (588,1,'dummy583','Dummy 583','Dummy Dummy583','Dummy user ','dummy@dummy,du583','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (589,1,'dummy584','Dummy 584','Dummy Dummy584','Dummy user ','dummy@dummy,du584','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (590,1,'dummy585','Dummy 585','Dummy Dummy585','Dummy user ','dummy@dummy,du585','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (591,1,'dummy586','Dummy 586','Dummy Dummy586','Dummy user ','dummy@dummy,du586','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (592,1,'dummy587','Dummy 587','Dummy Dummy587','Dummy user ','dummy@dummy,du587','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (593,1,'dummy588','Dummy 588','Dummy Dummy588','Dummy user ','dummy@dummy,du588','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (594,1,'dummy589','Dummy 589','Dummy Dummy589','Dummy user ','dummy@dummy,du589','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (595,1,'dummy590','Dummy 590','Dummy Dummy590','Dummy user ','dummy@dummy,du590','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (596,1,'dummy591','Dummy 591','Dummy Dummy591','Dummy user ','dummy@dummy,du591','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (597,1,'dummy592','Dummy 592','Dummy Dummy592','Dummy user ','dummy@dummy,du592','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (598,1,'dummy593','Dummy 593','Dummy Dummy593','Dummy user ','dummy@dummy,du593','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (599,1,'dummy594','Dummy 594','Dummy Dummy594','Dummy user ','dummy@dummy,du594','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (600,1,'dummy595','Dummy 595','Dummy Dummy595','Dummy user ','dummy@dummy,du595','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (601,1,'dummy596','Dummy 596','Dummy Dummy596','Dummy user ','dummy@dummy,du596','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (602,1,'dummy597','Dummy 597','Dummy Dummy597','Dummy user ','dummy@dummy,du597','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (603,1,'dummy598','Dummy 598','Dummy Dummy598','Dummy user ','dummy@dummy,du598','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (604,1,'dummy599','Dummy 599','Dummy Dummy599','Dummy user ','dummy@dummy,du599','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (605,1,'dummy600','Dummy 600','Dummy Dummy600','Dummy user ','dummy@dummy,du600','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (606,1,'dummy601','Dummy 601','Dummy Dummy601','Dummy user ','dummy@dummy,du601','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (607,1,'dummy602','Dummy 602','Dummy Dummy602','Dummy user ','dummy@dummy,du602','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (608,1,'dummy603','Dummy 603','Dummy Dummy603','Dummy user ','dummy@dummy,du603','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (609,1,'dummy604','Dummy 604','Dummy Dummy604','Dummy user ','dummy@dummy,du604','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (610,1,'dummy605','Dummy 605','Dummy Dummy605','Dummy user ','dummy@dummy,du605','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (611,1,'dummy606','Dummy 606','Dummy Dummy606','Dummy user ','dummy@dummy,du606','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (612,1,'dummy607','Dummy 607','Dummy Dummy607','Dummy user ','dummy@dummy,du607','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (613,1,'dummy608','Dummy 608','Dummy Dummy608','Dummy user ','dummy@dummy,du608','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (614,1,'dummy609','Dummy 609','Dummy Dummy609','Dummy user ','dummy@dummy,du609','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (615,1,'dummy610','Dummy 610','Dummy Dummy610','Dummy user ','dummy@dummy,du610','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (616,1,'dummy611','Dummy 611','Dummy Dummy611','Dummy user ','dummy@dummy,du611','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (617,1,'dummy612','Dummy 612','Dummy Dummy612','Dummy user ','dummy@dummy,du612','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (618,1,'dummy613','Dummy 613','Dummy Dummy613','Dummy user ','dummy@dummy,du613','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (619,1,'dummy614','Dummy 614','Dummy Dummy614','Dummy user ','dummy@dummy,du614','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (620,1,'dummy615','Dummy 615','Dummy Dummy615','Dummy user ','dummy@dummy,du615','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (621,1,'dummy616','Dummy 616','Dummy Dummy616','Dummy user ','dummy@dummy,du616','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (622,1,'dummy617','Dummy 617','Dummy Dummy617','Dummy user ','dummy@dummy,du617','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (623,1,'dummy618','Dummy 618','Dummy Dummy618','Dummy user ','dummy@dummy,du618','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (624,1,'dummy619','Dummy 619','Dummy Dummy619','Dummy user ','dummy@dummy,du619','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (625,1,'dummy620','Dummy 620','Dummy Dummy620','Dummy user ','dummy@dummy,du620','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (626,1,'dummy621','Dummy 621','Dummy Dummy621','Dummy user ','dummy@dummy,du621','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (627,1,'dummy622','Dummy 622','Dummy Dummy622','Dummy user ','dummy@dummy,du622','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (628,1,'dummy623','Dummy 623','Dummy Dummy623','Dummy user ','dummy@dummy,du623','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (629,1,'dummy624','Dummy 624','Dummy Dummy624','Dummy user ','dummy@dummy,du624','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (630,1,'dummy625','Dummy 625','Dummy Dummy625','Dummy user ','dummy@dummy,du625','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (631,1,'dummy626','Dummy 626','Dummy Dummy626','Dummy user ','dummy@dummy,du626','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (632,1,'dummy627','Dummy 627','Dummy Dummy627','Dummy user ','dummy@dummy,du627','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (633,1,'dummy628','Dummy 628','Dummy Dummy628','Dummy user ','dummy@dummy,du628','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (634,1,'dummy629','Dummy 629','Dummy Dummy629','Dummy user ','dummy@dummy,du629','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (635,1,'dummy630','Dummy 630','Dummy Dummy630','Dummy user ','dummy@dummy,du630','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (636,1,'dummy631','Dummy 631','Dummy Dummy631','Dummy user ','dummy@dummy,du631','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (637,1,'dummy632','Dummy 632','Dummy Dummy632','Dummy user ','dummy@dummy,du632','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (638,1,'dummy633','Dummy 633','Dummy Dummy633','Dummy user ','dummy@dummy,du633','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (639,1,'dummy634','Dummy 634','Dummy Dummy634','Dummy user ','dummy@dummy,du634','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (640,1,'dummy635','Dummy 635','Dummy Dummy635','Dummy user ','dummy@dummy,du635','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (641,1,'dummy636','Dummy 636','Dummy Dummy636','Dummy user ','dummy@dummy,du636','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (642,1,'dummy637','Dummy 637','Dummy Dummy637','Dummy user ','dummy@dummy,du637','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (643,1,'dummy638','Dummy 638','Dummy Dummy638','Dummy user ','dummy@dummy,du638','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (644,1,'dummy639','Dummy 639','Dummy Dummy639','Dummy user ','dummy@dummy,du639','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (645,1,'dummy640','Dummy 640','Dummy Dummy640','Dummy user ','dummy@dummy,du640','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (646,1,'dummy641','Dummy 641','Dummy Dummy641','Dummy user ','dummy@dummy,du641','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (647,1,'dummy642','Dummy 642','Dummy Dummy642','Dummy user ','dummy@dummy,du642','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (648,1,'dummy643','Dummy 643','Dummy Dummy643','Dummy user ','dummy@dummy,du643','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (649,1,'dummy644','Dummy 644','Dummy Dummy644','Dummy user ','dummy@dummy,du644','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (650,1,'dummy645','Dummy 645','Dummy Dummy645','Dummy user ','dummy@dummy,du645','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (651,1,'dummy646','Dummy 646','Dummy Dummy646','Dummy user ','dummy@dummy,du646','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (652,1,'dummy647','Dummy 647','Dummy Dummy647','Dummy user ','dummy@dummy,du647','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (653,1,'dummy648','Dummy 648','Dummy Dummy648','Dummy user ','dummy@dummy,du648','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (654,1,'dummy649','Dummy 649','Dummy Dummy649','Dummy user ','dummy@dummy,du649','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (655,1,'dummy650','Dummy 650','Dummy Dummy650','Dummy user ','dummy@dummy,du650','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (656,1,'dummy651','Dummy 651','Dummy Dummy651','Dummy user ','dummy@dummy,du651','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (657,1,'dummy652','Dummy 652','Dummy Dummy652','Dummy user ','dummy@dummy,du652','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (658,1,'dummy653','Dummy 653','Dummy Dummy653','Dummy user ','dummy@dummy,du653','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (659,1,'dummy654','Dummy 654','Dummy Dummy654','Dummy user ','dummy@dummy,du654','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (660,1,'dummy655','Dummy 655','Dummy Dummy655','Dummy user ','dummy@dummy,du655','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (661,1,'dummy656','Dummy 656','Dummy Dummy656','Dummy user ','dummy@dummy,du656','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (662,1,'dummy657','Dummy 657','Dummy Dummy657','Dummy user ','dummy@dummy,du657','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (663,1,'dummy658','Dummy 658','Dummy Dummy658','Dummy user ','dummy@dummy,du658','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (664,1,'dummy659','Dummy 659','Dummy Dummy659','Dummy user ','dummy@dummy,du659','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (665,1,'dummy660','Dummy 660','Dummy Dummy660','Dummy user ','dummy@dummy,du660','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (666,1,'dummy661','Dummy 661','Dummy Dummy661','Dummy user ','dummy@dummy,du661','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (667,1,'dummy662','Dummy 662','Dummy Dummy662','Dummy user ','dummy@dummy,du662','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (668,1,'dummy663','Dummy 663','Dummy Dummy663','Dummy user ','dummy@dummy,du663','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (669,1,'dummy664','Dummy 664','Dummy Dummy664','Dummy user ','dummy@dummy,du664','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (670,1,'dummy665','Dummy 665','Dummy Dummy665','Dummy user ','dummy@dummy,du665','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (671,1,'dummy666','Dummy 666','Dummy Dummy666','Dummy user ','dummy@dummy,du666','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (672,1,'dummy667','Dummy 667','Dummy Dummy667','Dummy user ','dummy@dummy,du667','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (673,1,'dummy668','Dummy 668','Dummy Dummy668','Dummy user ','dummy@dummy,du668','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (674,1,'dummy669','Dummy 669','Dummy Dummy669','Dummy user ','dummy@dummy,du669','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (675,1,'dummy670','Dummy 670','Dummy Dummy670','Dummy user ','dummy@dummy,du670','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (676,1,'dummy671','Dummy 671','Dummy Dummy671','Dummy user ','dummy@dummy,du671','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (677,1,'dummy672','Dummy 672','Dummy Dummy672','Dummy user ','dummy@dummy,du672','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (678,1,'dummy673','Dummy 673','Dummy Dummy673','Dummy user ','dummy@dummy,du673','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (679,1,'dummy674','Dummy 674','Dummy Dummy674','Dummy user ','dummy@dummy,du674','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (680,1,'dummy675','Dummy 675','Dummy Dummy675','Dummy user ','dummy@dummy,du675','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (681,1,'dummy676','Dummy 676','Dummy Dummy676','Dummy user ','dummy@dummy,du676','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (682,1,'dummy677','Dummy 677','Dummy Dummy677','Dummy user ','dummy@dummy,du677','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (683,1,'dummy678','Dummy 678','Dummy Dummy678','Dummy user ','dummy@dummy,du678','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (684,1,'dummy679','Dummy 679','Dummy Dummy679','Dummy user ','dummy@dummy,du679','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (685,1,'dummy680','Dummy 680','Dummy Dummy680','Dummy user ','dummy@dummy,du680','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (686,1,'dummy681','Dummy 681','Dummy Dummy681','Dummy user ','dummy@dummy,du681','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (687,1,'dummy682','Dummy 682','Dummy Dummy682','Dummy user ','dummy@dummy,du682','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (688,1,'dummy683','Dummy 683','Dummy Dummy683','Dummy user ','dummy@dummy,du683','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (689,1,'dummy684','Dummy 684','Dummy Dummy684','Dummy user ','dummy@dummy,du684','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (690,1,'dummy685','Dummy 685','Dummy Dummy685','Dummy user ','dummy@dummy,du685','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (691,1,'dummy686','Dummy 686','Dummy Dummy686','Dummy user ','dummy@dummy,du686','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (692,1,'dummy687','Dummy 687','Dummy Dummy687','Dummy user ','dummy@dummy,du687','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (693,1,'dummy688','Dummy 688','Dummy Dummy688','Dummy user ','dummy@dummy,du688','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (694,1,'dummy689','Dummy 689','Dummy Dummy689','Dummy user ','dummy@dummy,du689','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (695,1,'dummy690','Dummy 690','Dummy Dummy690','Dummy user ','dummy@dummy,du690','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (696,1,'dummy691','Dummy 691','Dummy Dummy691','Dummy user ','dummy@dummy,du691','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (697,1,'dummy692','Dummy 692','Dummy Dummy692','Dummy user ','dummy@dummy,du692','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (698,1,'dummy693','Dummy 693','Dummy Dummy693','Dummy user ','dummy@dummy,du693','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (699,1,'dummy694','Dummy 694','Dummy Dummy694','Dummy user ','dummy@dummy,du694','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (700,1,'dummy695','Dummy 695','Dummy Dummy695','Dummy user ','dummy@dummy,du695','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (701,1,'dummy696','Dummy 696','Dummy Dummy696','Dummy user ','dummy@dummy,du696','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (702,1,'dummy697','Dummy 697','Dummy Dummy697','Dummy user ','dummy@dummy,du697','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (703,1,'dummy698','Dummy 698','Dummy Dummy698','Dummy user ','dummy@dummy,du698','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (704,1,'dummy699','Dummy 699','Dummy Dummy699','Dummy user ','dummy@dummy,du699','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (705,1,'dummy700','Dummy 700','Dummy Dummy700','Dummy user ','dummy@dummy,du700','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (706,1,'dummy701','Dummy 701','Dummy Dummy701','Dummy user ','dummy@dummy,du701','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (707,1,'dummy702','Dummy 702','Dummy Dummy702','Dummy user ','dummy@dummy,du702','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (708,1,'dummy703','Dummy 703','Dummy Dummy703','Dummy user ','dummy@dummy,du703','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (709,1,'dummy704','Dummy 704','Dummy Dummy704','Dummy user ','dummy@dummy,du704','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (710,1,'dummy705','Dummy 705','Dummy Dummy705','Dummy user ','dummy@dummy,du705','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (711,1,'dummy706','Dummy 706','Dummy Dummy706','Dummy user ','dummy@dummy,du706','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (712,1,'dummy707','Dummy 707','Dummy Dummy707','Dummy user ','dummy@dummy,du707','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (713,1,'dummy708','Dummy 708','Dummy Dummy708','Dummy user ','dummy@dummy,du708','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (714,1,'dummy709','Dummy 709','Dummy Dummy709','Dummy user ','dummy@dummy,du709','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (715,1,'dummy710','Dummy 710','Dummy Dummy710','Dummy user ','dummy@dummy,du710','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (716,1,'dummy711','Dummy 711','Dummy Dummy711','Dummy user ','dummy@dummy,du711','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (717,1,'dummy712','Dummy 712','Dummy Dummy712','Dummy user ','dummy@dummy,du712','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (718,1,'dummy713','Dummy 713','Dummy Dummy713','Dummy user ','dummy@dummy,du713','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (719,1,'dummy714','Dummy 714','Dummy Dummy714','Dummy user ','dummy@dummy,du714','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (720,1,'dummy715','Dummy 715','Dummy Dummy715','Dummy user ','dummy@dummy,du715','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (721,1,'dummy716','Dummy 716','Dummy Dummy716','Dummy user ','dummy@dummy,du716','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (722,1,'dummy717','Dummy 717','Dummy Dummy717','Dummy user ','dummy@dummy,du717','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (723,1,'dummy718','Dummy 718','Dummy Dummy718','Dummy user ','dummy@dummy,du718','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (724,1,'dummy719','Dummy 719','Dummy Dummy719','Dummy user ','dummy@dummy,du719','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (725,1,'dummy720','Dummy 720','Dummy Dummy720','Dummy user ','dummy@dummy,du720','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (726,1,'dummy721','Dummy 721','Dummy Dummy721','Dummy user ','dummy@dummy,du721','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (727,1,'dummy722','Dummy 722','Dummy Dummy722','Dummy user ','dummy@dummy,du722','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (728,1,'dummy723','Dummy 723','Dummy Dummy723','Dummy user ','dummy@dummy,du723','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (729,1,'dummy724','Dummy 724','Dummy Dummy724','Dummy user ','dummy@dummy,du724','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (730,1,'dummy725','Dummy 725','Dummy Dummy725','Dummy user ','dummy@dummy,du725','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (731,1,'dummy726','Dummy 726','Dummy Dummy726','Dummy user ','dummy@dummy,du726','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (732,1,'dummy727','Dummy 727','Dummy Dummy727','Dummy user ','dummy@dummy,du727','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (733,1,'dummy728','Dummy 728','Dummy Dummy728','Dummy user ','dummy@dummy,du728','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (734,1,'dummy729','Dummy 729','Dummy Dummy729','Dummy user ','dummy@dummy,du729','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (735,1,'dummy730','Dummy 730','Dummy Dummy730','Dummy user ','dummy@dummy,du730','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (736,1,'dummy731','Dummy 731','Dummy Dummy731','Dummy user ','dummy@dummy,du731','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (737,1,'dummy732','Dummy 732','Dummy Dummy732','Dummy user ','dummy@dummy,du732','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (738,1,'dummy733','Dummy 733','Dummy Dummy733','Dummy user ','dummy@dummy,du733','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (739,1,'dummy734','Dummy 734','Dummy Dummy734','Dummy user ','dummy@dummy,du734','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (740,1,'dummy735','Dummy 735','Dummy Dummy735','Dummy user ','dummy@dummy,du735','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (741,1,'dummy736','Dummy 736','Dummy Dummy736','Dummy user ','dummy@dummy,du736','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (742,1,'dummy737','Dummy 737','Dummy Dummy737','Dummy user ','dummy@dummy,du737','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (743,1,'dummy738','Dummy 738','Dummy Dummy738','Dummy user ','dummy@dummy,du738','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (744,1,'dummy739','Dummy 739','Dummy Dummy739','Dummy user ','dummy@dummy,du739','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (745,1,'dummy740','Dummy 740','Dummy Dummy740','Dummy user ','dummy@dummy,du740','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (746,1,'dummy741','Dummy 741','Dummy Dummy741','Dummy user ','dummy@dummy,du741','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (747,1,'dummy742','Dummy 742','Dummy Dummy742','Dummy user ','dummy@dummy,du742','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (748,1,'dummy743','Dummy 743','Dummy Dummy743','Dummy user ','dummy@dummy,du743','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (749,1,'dummy744','Dummy 744','Dummy Dummy744','Dummy user ','dummy@dummy,du744','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (750,1,'dummy745','Dummy 745','Dummy Dummy745','Dummy user ','dummy@dummy,du745','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (751,1,'dummy746','Dummy 746','Dummy Dummy746','Dummy user ','dummy@dummy,du746','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (752,1,'dummy747','Dummy 747','Dummy Dummy747','Dummy user ','dummy@dummy,du747','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (753,1,'dummy748','Dummy 748','Dummy Dummy748','Dummy user ','dummy@dummy,du748','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (754,1,'dummy749','Dummy 749','Dummy Dummy749','Dummy user ','dummy@dummy,du749','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (755,1,'dummy750','Dummy 750','Dummy Dummy750','Dummy user ','dummy@dummy,du750','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (756,1,'dummy751','Dummy 751','Dummy Dummy751','Dummy user ','dummy@dummy,du751','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (757,1,'dummy752','Dummy 752','Dummy Dummy752','Dummy user ','dummy@dummy,du752','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (758,1,'dummy753','Dummy 753','Dummy Dummy753','Dummy user ','dummy@dummy,du753','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (759,1,'dummy754','Dummy 754','Dummy Dummy754','Dummy user ','dummy@dummy,du754','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (760,1,'dummy755','Dummy 755','Dummy Dummy755','Dummy user ','dummy@dummy,du755','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (761,1,'dummy756','Dummy 756','Dummy Dummy756','Dummy user ','dummy@dummy,du756','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (762,1,'dummy757','Dummy 757','Dummy Dummy757','Dummy user ','dummy@dummy,du757','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (763,1,'dummy758','Dummy 758','Dummy Dummy758','Dummy user ','dummy@dummy,du758','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (764,1,'dummy759','Dummy 759','Dummy Dummy759','Dummy user ','dummy@dummy,du759','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (765,1,'dummy760','Dummy 760','Dummy Dummy760','Dummy user ','dummy@dummy,du760','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (766,1,'dummy761','Dummy 761','Dummy Dummy761','Dummy user ','dummy@dummy,du761','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (767,1,'dummy762','Dummy 762','Dummy Dummy762','Dummy user ','dummy@dummy,du762','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (768,1,'dummy763','Dummy 763','Dummy Dummy763','Dummy user ','dummy@dummy,du763','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (769,1,'dummy764','Dummy 764','Dummy Dummy764','Dummy user ','dummy@dummy,du764','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (770,1,'dummy765','Dummy 765','Dummy Dummy765','Dummy user ','dummy@dummy,du765','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (771,1,'dummy766','Dummy 766','Dummy Dummy766','Dummy user ','dummy@dummy,du766','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (772,1,'dummy767','Dummy 767','Dummy Dummy767','Dummy user ','dummy@dummy,du767','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (773,1,'dummy768','Dummy 768','Dummy Dummy768','Dummy user ','dummy@dummy,du768','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (774,1,'dummy769','Dummy 769','Dummy Dummy769','Dummy user ','dummy@dummy,du769','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (775,1,'dummy770','Dummy 770','Dummy Dummy770','Dummy user ','dummy@dummy,du770','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (776,1,'dummy771','Dummy 771','Dummy Dummy771','Dummy user ','dummy@dummy,du771','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (777,1,'dummy772','Dummy 772','Dummy Dummy772','Dummy user ','dummy@dummy,du772','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (778,1,'dummy773','Dummy 773','Dummy Dummy773','Dummy user ','dummy@dummy,du773','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (779,1,'dummy774','Dummy 774','Dummy Dummy774','Dummy user ','dummy@dummy,du774','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (780,1,'dummy775','Dummy 775','Dummy Dummy775','Dummy user ','dummy@dummy,du775','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (781,1,'dummy776','Dummy 776','Dummy Dummy776','Dummy user ','dummy@dummy,du776','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (782,1,'dummy777','Dummy 777','Dummy Dummy777','Dummy user ','dummy@dummy,du777','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (783,1,'dummy778','Dummy 778','Dummy Dummy778','Dummy user ','dummy@dummy,du778','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (784,1,'dummy779','Dummy 779','Dummy Dummy779','Dummy user ','dummy@dummy,du779','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (785,1,'dummy780','Dummy 780','Dummy Dummy780','Dummy user ','dummy@dummy,du780','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (786,1,'dummy781','Dummy 781','Dummy Dummy781','Dummy user ','dummy@dummy,du781','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (787,1,'dummy782','Dummy 782','Dummy Dummy782','Dummy user ','dummy@dummy,du782','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (788,1,'dummy783','Dummy 783','Dummy Dummy783','Dummy user ','dummy@dummy,du783','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (789,1,'dummy784','Dummy 784','Dummy Dummy784','Dummy user ','dummy@dummy,du784','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (790,1,'dummy785','Dummy 785','Dummy Dummy785','Dummy user ','dummy@dummy,du785','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (791,1,'dummy786','Dummy 786','Dummy Dummy786','Dummy user ','dummy@dummy,du786','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (792,1,'dummy787','Dummy 787','Dummy Dummy787','Dummy user ','dummy@dummy,du787','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (793,1,'dummy788','Dummy 788','Dummy Dummy788','Dummy user ','dummy@dummy,du788','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (794,1,'dummy789','Dummy 789','Dummy Dummy789','Dummy user ','dummy@dummy,du789','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (795,1,'dummy790','Dummy 790','Dummy Dummy790','Dummy user ','dummy@dummy,du790','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (796,1,'dummy791','Dummy 791','Dummy Dummy791','Dummy user ','dummy@dummy,du791','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (797,1,'dummy792','Dummy 792','Dummy Dummy792','Dummy user ','dummy@dummy,du792','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (798,1,'dummy793','Dummy 793','Dummy Dummy793','Dummy user ','dummy@dummy,du793','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (799,1,'dummy794','Dummy 794','Dummy Dummy794','Dummy user ','dummy@dummy,du794','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (800,1,'dummy795','Dummy 795','Dummy Dummy795','Dummy user ','dummy@dummy,du795','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (801,1,'dummy796','Dummy 796','Dummy Dummy796','Dummy user ','dummy@dummy,du796','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (802,1,'dummy797','Dummy 797','Dummy Dummy797','Dummy user ','dummy@dummy,du797','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (803,1,'dummy798','Dummy 798','Dummy Dummy798','Dummy user ','dummy@dummy,du798','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (804,1,'dummy799','Dummy 799','Dummy Dummy799','Dummy user ','dummy@dummy,du799','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (805,1,'dummy800','Dummy 800','Dummy Dummy800','Dummy user ','dummy@dummy,du800','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (806,1,'dummy801','Dummy 801','Dummy Dummy801','Dummy user ','dummy@dummy,du801','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (807,1,'dummy802','Dummy 802','Dummy Dummy802','Dummy user ','dummy@dummy,du802','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (808,1,'dummy803','Dummy 803','Dummy Dummy803','Dummy user ','dummy@dummy,du803','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (809,1,'dummy804','Dummy 804','Dummy Dummy804','Dummy user ','dummy@dummy,du804','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (810,1,'dummy805','Dummy 805','Dummy Dummy805','Dummy user ','dummy@dummy,du805','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (811,1,'dummy806','Dummy 806','Dummy Dummy806','Dummy user ','dummy@dummy,du806','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (812,1,'dummy807','Dummy 807','Dummy Dummy807','Dummy user ','dummy@dummy,du807','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (813,1,'dummy808','Dummy 808','Dummy Dummy808','Dummy user ','dummy@dummy,du808','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (814,1,'dummy809','Dummy 809','Dummy Dummy809','Dummy user ','dummy@dummy,du809','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (815,1,'dummy810','Dummy 810','Dummy Dummy810','Dummy user ','dummy@dummy,du810','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (816,1,'dummy811','Dummy 811','Dummy Dummy811','Dummy user ','dummy@dummy,du811','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (817,1,'dummy812','Dummy 812','Dummy Dummy812','Dummy user ','dummy@dummy,du812','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (818,1,'dummy813','Dummy 813','Dummy Dummy813','Dummy user ','dummy@dummy,du813','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (819,1,'dummy814','Dummy 814','Dummy Dummy814','Dummy user ','dummy@dummy,du814','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (820,1,'dummy815','Dummy 815','Dummy Dummy815','Dummy user ','dummy@dummy,du815','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (821,1,'dummy816','Dummy 816','Dummy Dummy816','Dummy user ','dummy@dummy,du816','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (822,1,'dummy817','Dummy 817','Dummy Dummy817','Dummy user ','dummy@dummy,du817','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (823,1,'dummy818','Dummy 818','Dummy Dummy818','Dummy user ','dummy@dummy,du818','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (824,1,'dummy819','Dummy 819','Dummy Dummy819','Dummy user ','dummy@dummy,du819','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (825,1,'dummy820','Dummy 820','Dummy Dummy820','Dummy user ','dummy@dummy,du820','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (826,1,'dummy821','Dummy 821','Dummy Dummy821','Dummy user ','dummy@dummy,du821','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (827,1,'dummy822','Dummy 822','Dummy Dummy822','Dummy user ','dummy@dummy,du822','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (828,1,'dummy823','Dummy 823','Dummy Dummy823','Dummy user ','dummy@dummy,du823','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (829,1,'dummy824','Dummy 824','Dummy Dummy824','Dummy user ','dummy@dummy,du824','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (830,1,'dummy825','Dummy 825','Dummy Dummy825','Dummy user ','dummy@dummy,du825','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (831,1,'dummy826','Dummy 826','Dummy Dummy826','Dummy user ','dummy@dummy,du826','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (832,1,'dummy827','Dummy 827','Dummy Dummy827','Dummy user ','dummy@dummy,du827','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (833,1,'dummy828','Dummy 828','Dummy Dummy828','Dummy user ','dummy@dummy,du828','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (834,1,'dummy829','Dummy 829','Dummy Dummy829','Dummy user ','dummy@dummy,du829','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (835,1,'dummy830','Dummy 830','Dummy Dummy830','Dummy user ','dummy@dummy,du830','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (836,1,'dummy831','Dummy 831','Dummy Dummy831','Dummy user ','dummy@dummy,du831','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (837,1,'dummy832','Dummy 832','Dummy Dummy832','Dummy user ','dummy@dummy,du832','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (838,1,'dummy833','Dummy 833','Dummy Dummy833','Dummy user ','dummy@dummy,du833','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (839,1,'dummy834','Dummy 834','Dummy Dummy834','Dummy user ','dummy@dummy,du834','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (840,1,'dummy835','Dummy 835','Dummy Dummy835','Dummy user ','dummy@dummy,du835','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (841,1,'dummy836','Dummy 836','Dummy Dummy836','Dummy user ','dummy@dummy,du836','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (842,1,'dummy837','Dummy 837','Dummy Dummy837','Dummy user ','dummy@dummy,du837','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (843,1,'dummy838','Dummy 838','Dummy Dummy838','Dummy user ','dummy@dummy,du838','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (844,1,'dummy839','Dummy 839','Dummy Dummy839','Dummy user ','dummy@dummy,du839','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (845,1,'dummy840','Dummy 840','Dummy Dummy840','Dummy user ','dummy@dummy,du840','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (846,1,'dummy841','Dummy 841','Dummy Dummy841','Dummy user ','dummy@dummy,du841','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (847,1,'dummy842','Dummy 842','Dummy Dummy842','Dummy user ','dummy@dummy,du842','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (848,1,'dummy843','Dummy 843','Dummy Dummy843','Dummy user ','dummy@dummy,du843','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (849,1,'dummy844','Dummy 844','Dummy Dummy844','Dummy user ','dummy@dummy,du844','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (850,1,'dummy845','Dummy 845','Dummy Dummy845','Dummy user ','dummy@dummy,du845','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (851,1,'dummy846','Dummy 846','Dummy Dummy846','Dummy user ','dummy@dummy,du846','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (852,1,'dummy847','Dummy 847','Dummy Dummy847','Dummy user ','dummy@dummy,du847','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (853,1,'dummy848','Dummy 848','Dummy Dummy848','Dummy user ','dummy@dummy,du848','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (854,1,'dummy849','Dummy 849','Dummy Dummy849','Dummy user ','dummy@dummy,du849','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (855,1,'dummy850','Dummy 850','Dummy Dummy850','Dummy user ','dummy@dummy,du850','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (856,1,'dummy851','Dummy 851','Dummy Dummy851','Dummy user ','dummy@dummy,du851','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (857,1,'dummy852','Dummy 852','Dummy Dummy852','Dummy user ','dummy@dummy,du852','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (858,1,'dummy853','Dummy 853','Dummy Dummy853','Dummy user ','dummy@dummy,du853','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (859,1,'dummy854','Dummy 854','Dummy Dummy854','Dummy user ','dummy@dummy,du854','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (860,1,'dummy855','Dummy 855','Dummy Dummy855','Dummy user ','dummy@dummy,du855','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (861,1,'dummy856','Dummy 856','Dummy Dummy856','Dummy user ','dummy@dummy,du856','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (862,1,'dummy857','Dummy 857','Dummy Dummy857','Dummy user ','dummy@dummy,du857','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (863,1,'dummy858','Dummy 858','Dummy Dummy858','Dummy user ','dummy@dummy,du858','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (864,1,'dummy859','Dummy 859','Dummy Dummy859','Dummy user ','dummy@dummy,du859','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (865,1,'dummy860','Dummy 860','Dummy Dummy860','Dummy user ','dummy@dummy,du860','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (866,1,'dummy861','Dummy 861','Dummy Dummy861','Dummy user ','dummy@dummy,du861','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (867,1,'dummy862','Dummy 862','Dummy Dummy862','Dummy user ','dummy@dummy,du862','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (868,1,'dummy863','Dummy 863','Dummy Dummy863','Dummy user ','dummy@dummy,du863','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (869,1,'dummy864','Dummy 864','Dummy Dummy864','Dummy user ','dummy@dummy,du864','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (870,1,'dummy865','Dummy 865','Dummy Dummy865','Dummy user ','dummy@dummy,du865','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (871,1,'dummy866','Dummy 866','Dummy Dummy866','Dummy user ','dummy@dummy,du866','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (872,1,'dummy867','Dummy 867','Dummy Dummy867','Dummy user ','dummy@dummy,du867','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (873,1,'dummy868','Dummy 868','Dummy Dummy868','Dummy user ','dummy@dummy,du868','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (874,1,'dummy869','Dummy 869','Dummy Dummy869','Dummy user ','dummy@dummy,du869','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (875,1,'dummy870','Dummy 870','Dummy Dummy870','Dummy user ','dummy@dummy,du870','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (876,1,'dummy871','Dummy 871','Dummy Dummy871','Dummy user ','dummy@dummy,du871','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (877,1,'dummy872','Dummy 872','Dummy Dummy872','Dummy user ','dummy@dummy,du872','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (878,1,'dummy873','Dummy 873','Dummy Dummy873','Dummy user ','dummy@dummy,du873','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (879,1,'dummy874','Dummy 874','Dummy Dummy874','Dummy user ','dummy@dummy,du874','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (880,1,'dummy875','Dummy 875','Dummy Dummy875','Dummy user ','dummy@dummy,du875','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (881,1,'dummy876','Dummy 876','Dummy Dummy876','Dummy user ','dummy@dummy,du876','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (882,1,'dummy877','Dummy 877','Dummy Dummy877','Dummy user ','dummy@dummy,du877','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (883,1,'dummy878','Dummy 878','Dummy Dummy878','Dummy user ','dummy@dummy,du878','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (884,1,'dummy879','Dummy 879','Dummy Dummy879','Dummy user ','dummy@dummy,du879','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (885,1,'dummy880','Dummy 880','Dummy Dummy880','Dummy user ','dummy@dummy,du880','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (886,1,'dummy881','Dummy 881','Dummy Dummy881','Dummy user ','dummy@dummy,du881','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (887,1,'dummy882','Dummy 882','Dummy Dummy882','Dummy user ','dummy@dummy,du882','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (888,1,'dummy883','Dummy 883','Dummy Dummy883','Dummy user ','dummy@dummy,du883','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (889,1,'dummy884','Dummy 884','Dummy Dummy884','Dummy user ','dummy@dummy,du884','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (890,1,'dummy885','Dummy 885','Dummy Dummy885','Dummy user ','dummy@dummy,du885','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (891,1,'dummy886','Dummy 886','Dummy Dummy886','Dummy user ','dummy@dummy,du886','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (892,1,'dummy887','Dummy 887','Dummy Dummy887','Dummy user ','dummy@dummy,du887','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (893,1,'dummy888','Dummy 888','Dummy Dummy888','Dummy user ','dummy@dummy,du888','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (894,1,'dummy889','Dummy 889','Dummy Dummy889','Dummy user ','dummy@dummy,du889','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (895,1,'dummy890','Dummy 890','Dummy Dummy890','Dummy user ','dummy@dummy,du890','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (896,1,'dummy891','Dummy 891','Dummy Dummy891','Dummy user ','dummy@dummy,du891','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (897,1,'dummy892','Dummy 892','Dummy Dummy892','Dummy user ','dummy@dummy,du892','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (898,1,'dummy893','Dummy 893','Dummy Dummy893','Dummy user ','dummy@dummy,du893','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (899,1,'dummy894','Dummy 894','Dummy Dummy894','Dummy user ','dummy@dummy,du894','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (900,1,'dummy895','Dummy 895','Dummy Dummy895','Dummy user ','dummy@dummy,du895','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (901,1,'dummy896','Dummy 896','Dummy Dummy896','Dummy user ','dummy@dummy,du896','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (902,1,'dummy897','Dummy 897','Dummy Dummy897','Dummy user ','dummy@dummy,du897','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (903,1,'dummy898','Dummy 898','Dummy Dummy898','Dummy user ','dummy@dummy,du898','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (904,1,'dummy899','Dummy 899','Dummy Dummy899','Dummy user ','dummy@dummy,du899','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (905,1,'dummy900','Dummy 900','Dummy Dummy900','Dummy user ','dummy@dummy,du900','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (906,1,'dummy901','Dummy 901','Dummy Dummy901','Dummy user ','dummy@dummy,du901','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (907,1,'dummy902','Dummy 902','Dummy Dummy902','Dummy user ','dummy@dummy,du902','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (908,1,'dummy903','Dummy 903','Dummy Dummy903','Dummy user ','dummy@dummy,du903','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (909,1,'dummy904','Dummy 904','Dummy Dummy904','Dummy user ','dummy@dummy,du904','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (910,1,'dummy905','Dummy 905','Dummy Dummy905','Dummy user ','dummy@dummy,du905','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (911,1,'dummy906','Dummy 906','Dummy Dummy906','Dummy user ','dummy@dummy,du906','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (912,1,'dummy907','Dummy 907','Dummy Dummy907','Dummy user ','dummy@dummy,du907','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (913,1,'dummy908','Dummy 908','Dummy Dummy908','Dummy user ','dummy@dummy,du908','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (914,1,'dummy909','Dummy 909','Dummy Dummy909','Dummy user ','dummy@dummy,du909','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (915,1,'dummy910','Dummy 910','Dummy Dummy910','Dummy user ','dummy@dummy,du910','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (916,1,'dummy911','Dummy 911','Dummy Dummy911','Dummy user ','dummy@dummy,du911','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (917,1,'dummy912','Dummy 912','Dummy Dummy912','Dummy user ','dummy@dummy,du912','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (918,1,'dummy913','Dummy 913','Dummy Dummy913','Dummy user ','dummy@dummy,du913','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (919,1,'dummy914','Dummy 914','Dummy Dummy914','Dummy user ','dummy@dummy,du914','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (920,1,'dummy915','Dummy 915','Dummy Dummy915','Dummy user ','dummy@dummy,du915','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (921,1,'dummy916','Dummy 916','Dummy Dummy916','Dummy user ','dummy@dummy,du916','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (922,1,'dummy917','Dummy 917','Dummy Dummy917','Dummy user ','dummy@dummy,du917','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (923,1,'dummy918','Dummy 918','Dummy Dummy918','Dummy user ','dummy@dummy,du918','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (924,1,'dummy919','Dummy 919','Dummy Dummy919','Dummy user ','dummy@dummy,du919','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (925,1,'dummy920','Dummy 920','Dummy Dummy920','Dummy user ','dummy@dummy,du920','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (926,1,'dummy921','Dummy 921','Dummy Dummy921','Dummy user ','dummy@dummy,du921','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (927,1,'dummy922','Dummy 922','Dummy Dummy922','Dummy user ','dummy@dummy,du922','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (928,1,'dummy923','Dummy 923','Dummy Dummy923','Dummy user ','dummy@dummy,du923','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (929,1,'dummy924','Dummy 924','Dummy Dummy924','Dummy user ','dummy@dummy,du924','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (930,1,'dummy925','Dummy 925','Dummy Dummy925','Dummy user ','dummy@dummy,du925','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (931,1,'dummy926','Dummy 926','Dummy Dummy926','Dummy user ','dummy@dummy,du926','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (932,1,'dummy927','Dummy 927','Dummy Dummy927','Dummy user ','dummy@dummy,du927','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (933,1,'dummy928','Dummy 928','Dummy Dummy928','Dummy user ','dummy@dummy,du928','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (934,1,'dummy929','Dummy 929','Dummy Dummy929','Dummy user ','dummy@dummy,du929','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (935,1,'dummy930','Dummy 930','Dummy Dummy930','Dummy user ','dummy@dummy,du930','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (936,1,'dummy931','Dummy 931','Dummy Dummy931','Dummy user ','dummy@dummy,du931','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (937,1,'dummy932','Dummy 932','Dummy Dummy932','Dummy user ','dummy@dummy,du932','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (938,1,'dummy933','Dummy 933','Dummy Dummy933','Dummy user ','dummy@dummy,du933','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (939,1,'dummy934','Dummy 934','Dummy Dummy934','Dummy user ','dummy@dummy,du934','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (940,1,'dummy935','Dummy 935','Dummy Dummy935','Dummy user ','dummy@dummy,du935','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (941,1,'dummy936','Dummy 936','Dummy Dummy936','Dummy user ','dummy@dummy,du936','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (942,1,'dummy937','Dummy 937','Dummy Dummy937','Dummy user ','dummy@dummy,du937','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (943,1,'dummy938','Dummy 938','Dummy Dummy938','Dummy user ','dummy@dummy,du938','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (944,1,'dummy939','Dummy 939','Dummy Dummy939','Dummy user ','dummy@dummy,du939','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (945,1,'dummy940','Dummy 940','Dummy Dummy940','Dummy user ','dummy@dummy,du940','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (946,1,'dummy941','Dummy 941','Dummy Dummy941','Dummy user ','dummy@dummy,du941','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (947,1,'dummy942','Dummy 942','Dummy Dummy942','Dummy user ','dummy@dummy,du942','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (948,1,'dummy943','Dummy 943','Dummy Dummy943','Dummy user ','dummy@dummy,du943','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (949,1,'dummy944','Dummy 944','Dummy Dummy944','Dummy user ','dummy@dummy,du944','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (950,1,'dummy945','Dummy 945','Dummy Dummy945','Dummy user ','dummy@dummy,du945','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (951,1,'dummy946','Dummy 946','Dummy Dummy946','Dummy user ','dummy@dummy,du946','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (952,1,'dummy947','Dummy 947','Dummy Dummy947','Dummy user ','dummy@dummy,du947','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (953,1,'dummy948','Dummy 948','Dummy Dummy948','Dummy user ','dummy@dummy,du948','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (954,1,'dummy949','Dummy 949','Dummy Dummy949','Dummy user ','dummy@dummy,du949','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (955,1,'dummy950','Dummy 950','Dummy Dummy950','Dummy user ','dummy@dummy,du950','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (956,1,'dummy951','Dummy 951','Dummy Dummy951','Dummy user ','dummy@dummy,du951','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (957,1,'dummy952','Dummy 952','Dummy Dummy952','Dummy user ','dummy@dummy,du952','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (958,1,'dummy953','Dummy 953','Dummy Dummy953','Dummy user ','dummy@dummy,du953','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (959,1,'dummy954','Dummy 954','Dummy Dummy954','Dummy user ','dummy@dummy,du954','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (960,1,'dummy955','Dummy 955','Dummy Dummy955','Dummy user ','dummy@dummy,du955','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (961,1,'dummy956','Dummy 956','Dummy Dummy956','Dummy user ','dummy@dummy,du956','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (962,1,'dummy957','Dummy 957','Dummy Dummy957','Dummy user ','dummy@dummy,du957','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (963,1,'dummy958','Dummy 958','Dummy Dummy958','Dummy user ','dummy@dummy,du958','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (964,1,'dummy959','Dummy 959','Dummy Dummy959','Dummy user ','dummy@dummy,du959','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (965,1,'dummy960','Dummy 960','Dummy Dummy960','Dummy user ','dummy@dummy,du960','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (966,1,'dummy961','Dummy 961','Dummy Dummy961','Dummy user ','dummy@dummy,du961','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (967,1,'dummy962','Dummy 962','Dummy Dummy962','Dummy user ','dummy@dummy,du962','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (968,1,'dummy963','Dummy 963','Dummy Dummy963','Dummy user ','dummy@dummy,du963','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (969,1,'dummy964','Dummy 964','Dummy Dummy964','Dummy user ','dummy@dummy,du964','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (970,1,'dummy965','Dummy 965','Dummy Dummy965','Dummy user ','dummy@dummy,du965','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (971,1,'dummy966','Dummy 966','Dummy Dummy966','Dummy user ','dummy@dummy,du966','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (972,1,'dummy967','Dummy 967','Dummy Dummy967','Dummy user ','dummy@dummy,du967','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (973,1,'dummy968','Dummy 968','Dummy Dummy968','Dummy user ','dummy@dummy,du968','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (974,1,'dummy969','Dummy 969','Dummy Dummy969','Dummy user ','dummy@dummy,du969','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (975,1,'dummy970','Dummy 970','Dummy Dummy970','Dummy user ','dummy@dummy,du970','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (976,1,'dummy971','Dummy 971','Dummy Dummy971','Dummy user ','dummy@dummy,du971','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (977,1,'dummy972','Dummy 972','Dummy Dummy972','Dummy user ','dummy@dummy,du972','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (978,1,'dummy973','Dummy 973','Dummy Dummy973','Dummy user ','dummy@dummy,du973','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (979,1,'dummy974','Dummy 974','Dummy Dummy974','Dummy user ','dummy@dummy,du974','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (980,1,'dummy975','Dummy 975','Dummy Dummy975','Dummy user ','dummy@dummy,du975','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (981,1,'dummy976','Dummy 976','Dummy Dummy976','Dummy user ','dummy@dummy,du976','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (982,1,'dummy977','Dummy 977','Dummy Dummy977','Dummy user ','dummy@dummy,du977','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (983,1,'dummy978','Dummy 978','Dummy Dummy978','Dummy user ','dummy@dummy,du978','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (984,1,'dummy979','Dummy 979','Dummy Dummy979','Dummy user ','dummy@dummy,du979','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (985,1,'dummy980','Dummy 980','Dummy Dummy980','Dummy user ','dummy@dummy,du980','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (986,1,'dummy981','Dummy 981','Dummy Dummy981','Dummy user ','dummy@dummy,du981','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (987,1,'dummy982','Dummy 982','Dummy Dummy982','Dummy user ','dummy@dummy,du982','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (988,1,'dummy983','Dummy 983','Dummy Dummy983','Dummy user ','dummy@dummy,du983','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (989,1,'dummy984','Dummy 984','Dummy Dummy984','Dummy user ','dummy@dummy,du984','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (990,1,'dummy985','Dummy 985','Dummy Dummy985','Dummy user ','dummy@dummy,du985','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (991,1,'dummy986','Dummy 986','Dummy Dummy986','Dummy user ','dummy@dummy,du986','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (992,1,'dummy987','Dummy 987','Dummy Dummy987','Dummy user ','dummy@dummy,du987','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (993,1,'dummy988','Dummy 988','Dummy Dummy988','Dummy user ','dummy@dummy,du988','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (994,1,'dummy989','Dummy 989','Dummy Dummy989','Dummy user ','dummy@dummy,du989','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (995,1,'dummy990','Dummy 990','Dummy Dummy990','Dummy user ','dummy@dummy,du990','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (996,1,'dummy991','Dummy 991','Dummy Dummy991','Dummy user ','dummy@dummy,du991','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (997,1,'dummy992','Dummy 992','Dummy Dummy992','Dummy user ','dummy@dummy,du992','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (998,1,'dummy993','Dummy 993','Dummy Dummy993','Dummy user ','dummy@dummy,du993','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (999,1,'dummy994','Dummy 994','Dummy Dummy994','Dummy user ','dummy@dummy,du994','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
INSERT INTO `kinton`.`user` VALUES  (1000,1,'dummy995','Dummy 995','Dummy Dummy995','Dummy user ','dummy@dummy,du995','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (1001,1,'dummy996','Dummy 996','Dummy Dummy996','Dummy user ','dummy@dummy,du996','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (1002,1,'dummy997','Dummy 997','Dummy Dummy997','Dummy user ','dummy@dummy,du997','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (1003,1,'dummy998','Dummy 998','Dummy Dummy998','Dummy user ','dummy@dummy,du998','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1),
 (1004,1,'dummy999','Dummy 999','Dummy Dummy999','Dummy user ','dummy@dummy,du999','es_ES','dummy',1,'2008-12-16 14:55:23',NULL,NULL,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `kinton`.`virtualapp`
--

DROP TABLE IF EXISTS `kinton`.`virtualapp`;
CREATE TABLE  `kinton`.`virtualapp` (
  `idVirtualApp` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `public` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idState` int(1) unsigned NOT NULL,
  `high_disponibility` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idVirtualApp`),
  KEY `VirtualApp_FK1` (`idState`),
  KEY `VirtualApp_FK2` (`idUserCreation`),
  KEY `VirtualApp_FK3` (`idUser_lastModification`),
  CONSTRAINT `VirtualApp_FK1` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `VirtualApp_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `VirtualApp_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`virtualapp`
--

/*!40000 ALTER TABLE `virtualapp` DISABLE KEYS */;
LOCK TABLES `virtualapp` WRITE;
INSERT INTO `kinton`.`virtualapp` VALUES  (1,'1',0,1,0,1,'2009-01-29 15:35:21',1,'2009-01-29 15:35:26');
UNLOCK TABLES;
/*!40000 ALTER TABLE `virtualapp` ENABLE KEYS */;


--
-- Definition of table `kinton`.`virtualimage`
--

DROP TABLE IF EXISTS `kinton`.`virtualimage`;
CREATE TABLE  `kinton`.`virtualimage` (
  `idImage` int(4) unsigned NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) default NULL,
  `pathName` varchar(100) NOT NULL,
  `idSO` int(3) unsigned default NULL,
  `hd_required` int(11) default NULL,
  `ram_required` int(7) unsigned default NULL,
  `cpu_required` decimal(6,2) default NULL,
  `idCategory` int(3) unsigned NOT NULL,
  `treaty` int(1) NOT NULL COMMENT '0-No 1-Yes',
  `idRepository` int(3) unsigned NOT NULL,
  `idIcon` int(4) unsigned default NULL,
  `deleted` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  `imageType` int(3) default NULL,
  PRIMARY KEY  (`idImage`),
  KEY `virtualImage_FK1` (`idSO`),
  KEY `virtualImage_FK2` (`idCategory`),
  KEY `virtualImage_FK3` (`idRepository`),
  KEY `virtualImage_FK4` (`idIcon`),
  KEY `virtualImage_FK5` (`idUserCreation`),
  KEY `virtualImage_FK6` (`idUser_lastModification`),
  KEY `virtualImage_FK7` (`imageType`),
  CONSTRAINT `virtualImage_FK1` FOREIGN KEY (`idSO`) REFERENCES `so` (`idSO`),
  CONSTRAINT `virtualImage_FK2` FOREIGN KEY (`idCategory`) REFERENCES `category` (`idCategory`),
  CONSTRAINT `virtualImage_FK3` FOREIGN KEY (`idRepository`) REFERENCES `repository` (`idRepository`),
  CONSTRAINT `virtualImage_FK4` FOREIGN KEY (`idIcon`) REFERENCES `icon` (`idIcon`),
  CONSTRAINT `virtualImage_FK5` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualImage_FK6` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualImage_FK7` FOREIGN KEY (`imageType`) REFERENCES `imagetype` (`idImageType`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`virtualimage`
--

/*!40000 ALTER TABLE `virtualimage` DISABLE KEYS */;
LOCK TABLES `virtualimage` WRITE;
INSERT INTO `kinton`.`virtualimage` VALUES  (1,'Ubuntu','Ubuntu','/opt/vm_repository/ubuntu-8.10-x86.vdi',2,2,256,'2.00',5,0,1,1,0,1,'2008-11-20 13:28:42',1,'2009-01-23 13:45:42',1),
 (2,'Xubuntu web server','Xubuntu','/opt/vm_repository/ubuntu-8.04-x86.vdi',2,0,256,'32.00',1,0,1,2,0,1,'2008-11-20 13:28:42',1,'2009-01-28 10:51:26',1),
 (3,'MySql Server','Debian + Mysql server','/opt/vm_repository/Debian - MySQL database server.vdi',2,2,256,'2.00',1,0,1,3,0,1,'2008-11-20 13:28:42',1,'2009-01-27 19:25:39',1),
 (4,'abiquo MW','MW in a box','/opt/vm_repository/AbiquoMW.vdi',2,0,256,'2.00',1,0,1,4,0,1,'2008-11-20 13:28:42',1,'2009-01-28 11:00:25',1),
 (5,'Tomcat','Debian + Tomcat','/opt/vm_repository/Debian - Tomcat app server.vdi',2,10,256,'2.00',5,0,1,5,0,1,'2008-11-20 13:28:42',1,'2009-01-28 10:58:53',1),
 (6,'test','test','/opt/vm_repository/WindowsXP.vdi',1,2,256,'2.00',4,0,1,6,0,1,'2008-11-20 13:28:42',1,'2009-01-27 19:27:31',1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `virtualimage` ENABLE KEYS */;


--
-- Definition of table `kinton`.`virtualmachine`
--

DROP TABLE IF EXISTS `kinton`.`virtualmachine`;
CREATE TABLE  `kinton`.`virtualmachine` (
  `idVM` int(10) unsigned NOT NULL auto_increment,
  `idHypervisor` int(2) unsigned NOT NULL,
  `idImage` int(4) unsigned NOT NULL,
  `UUID` varchar(36) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) default NULL,
  `ram` int(7) unsigned default NULL,
  `cpu` decimal(6,2) unsigned default NULL,
  `hd` int(10) unsigned default NULL,
  `vdrpPort` int(5) unsigned default NULL,
  `vdrpIP` varchar(39) default NULL,
  `idState` int(1) unsigned NOT NULL,
  `high_disponibility` int(1) unsigned NOT NULL,
  PRIMARY KEY  (`idVM`),
  KEY `VirtualMachine_FK1` (`idHypervisor`),
  KEY `virtualMachine_FK2` (`idState`),
  KEY `virtualMachine_FK3` (`idImage`),
  CONSTRAINT `virtualMachine_FK1` FOREIGN KEY (`idHypervisor`) REFERENCES `hypervisor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `virtualMachine_FK2` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `virtualMachine_FK3` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`virtualmachine`
--

/*!40000 ALTER TABLE `virtualmachine` DISABLE KEYS */;
LOCK TABLES `virtualmachine` WRITE;
INSERT INTO `kinton`.`virtualmachine` VALUES  (1,1,2,'96b1b785-2635-47ed-818e-f35c3828','XUbuntu','XUbuntu VM',1024,'9999.99',0,3389,'192.168.1.44',5,0),
 (3,3,4,'bc878d24-0d24-4363-8a0d-81530a11','abiquo MW','abiquo MW VM',2048,'2.00',300,3389,'192.168.1.34',5,0),
 (4,1,5,'ce164a45-5e59-43cb-bf1a-7eb4d14e','Tomcat','Tomcat VM',1024,'9999.99',256,3389,'192.168.1.37',5,0),
 (5,1,1,'ce164a45-5e59-43cb-bf1a-7eb4d14e','ubuntu','ubuntu',1024,'9999.99',256,3389,'abiquo.homelinux.net',5,0),
 (7,1,3,'8e31b7ab-2c89-496f-8abd-3b5e7d5c','Mysql server','MySql server',1024,'9999.99',1,3389,'192.168.1.35',5,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `virtualmachine` ENABLE KEYS */;


--
-- Definition of table `kinton`.`virtualmachinenetworkmodule`
--

DROP TABLE IF EXISTS `kinton`.`virtualmachinenetworkmodule`;
CREATE TABLE  `kinton`.`virtualmachinenetworkmodule` (
  `idVM` int(10) unsigned NOT NULL default '0',
  `idModule` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idVM`,`idModule`),
  CONSTRAINT `VirtualmachineNetworkModule_FK1` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinton`.`virtualmachinenetworkmodule`
--

/*!40000 ALTER TABLE `virtualmachinenetworkmodule` DISABLE KEYS */;
LOCK TABLES `virtualmachinenetworkmodule` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `virtualmachinenetworkmodule` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
