-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.32-Debian_7etch8-log


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
-- Definition of table `auth_clientresource`
--

DROP TABLE IF EXISTS `auth_clientresource`;
CREATE TABLE `auth_clientresource` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `description` varchar(50) default NULL,
  `idGroup` int(11) unsigned default NULL,
  `idRole` int(11) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_clientresource`
--

/*!40000 ALTER TABLE `auth_clientresource` DISABLE KEYS */;
INSERT INTO `auth_clientresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'USER_BUTTON',NULL,1,2),
 (2,'VIRTUALAPP_BUTTON',NULL,1,3),
 (3,'VIRTUALIMAGE_BUTTON',NULL,1,2),
 (4,'INFRASTRUCTURE_BUTTON',NULL,1,2);
/*!40000 ALTER TABLE `auth_clientresource` ENABLE KEYS */;


--
-- Definition of table `auth_clientresource_exception`
--

DROP TABLE IF EXISTS `auth_clientresource_exception`;
CREATE TABLE `auth_clientresource_exception` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `idResource` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_clientresource_exception`
--

/*!40000 ALTER TABLE `auth_clientresource_exception` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_clientresource_exception` ENABLE KEYS */;


--
-- Definition of table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `description` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_group`
--

/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
INSERT INTO `auth_group` (`id`,`name`,`description`) VALUES 
 (1,'GENERIC','Generic'),
 (2,'MAIN','Flex client main menu group');
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;


--
-- Definition of table `auth_serverresource`
--

DROP TABLE IF EXISTS `auth_serverresource`;
CREATE TABLE `auth_serverresource` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `description` varchar(50) default NULL,
  `idGroup` int(11) unsigned default NULL,
  `idRole` int(11) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_serverresource`
--

/*!40000 ALTER TABLE `auth_serverresource` DISABLE KEYS */;
INSERT INTO `auth_serverresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'LOGIN','Login Service',1,1);
/*!40000 ALTER TABLE `auth_serverresource` ENABLE KEYS */;


--
-- Definition of table `auth_serverresource_exception`
--

DROP TABLE IF EXISTS `auth_serverresource_exception`;
CREATE TABLE `auth_serverresource_exception` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `idResource` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_serverresource_exception`
--

/*!40000 ALTER TABLE `auth_serverresource_exception` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_serverresource_exception` ENABLE KEYS */;


--
-- Definition of table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `idCategory` int(3) unsigned NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idCategory`),
  KEY `Category_FK1` (`idUserCreation`),
  KEY `Category_FK2` (`idUser_lastModification`),
  CONSTRAINT `Category_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Category_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`idCategory`,`name`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Operating Systems',1,'2008-11-20 13:28:42',NULL,NULL),
 (2,'Database servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (3,'Others',1,'2008-11-20 13:28:42',NULL,NULL),
 (4,'Applications servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (5,'Web servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (6,'Load Balancing servers',1,'2008-11-20 13:28:42',NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


--
-- Definition of table `datacenter`
--

DROP TABLE IF EXISTS `datacenter`;
CREATE TABLE `datacenter` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `datacenter`
--

/*!40000 ALTER TABLE `datacenter` DISABLE KEYS */;
INSERT INTO `datacenter` (`idDataCenter`,`name`,`situation`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (2,'ACENS MADRID','Madrid',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (3,'ACENS BARCELONA','Barcelona',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (4,'ACENS VALENCIA','Valencia',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (5,'ACENS BILBAI','Bilbao',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42');
/*!40000 ALTER TABLE `datacenter` ENABLE KEYS */;


--
-- Definition of table `dns`
--

DROP TABLE IF EXISTS `dns`;
CREATE TABLE `dns` (
  `idDns` int(1) NOT NULL default '0',
  `idNetworkModule` int(2) unsigned NOT NULL default '0',
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
  CONSTRAINT `Dns_FK1` FOREIGN KEY (`idNetworkModule`, `idPhysicalMachine`) REFERENCES `networkmodule` (`idNetworkModule`, `idPhysicalMachine`),
  CONSTRAINT `Dns_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Dns_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dns`
--

/*!40000 ALTER TABLE `dns` DISABLE KEYS */;
/*!40000 ALTER TABLE `dns` ENABLE KEYS */;


--
-- Definition of table `hypervisor`
--

DROP TABLE IF EXISTS `hypervisor`;
CREATE TABLE `hypervisor` (
  `id` int(2) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  `idType` int(3) NOT NULL,
  `idPhysicalMachine` int(20) NOT NULL,
  `ip` varchar(39) NOT NULL,
  `port` int(5) NOT NULL,
  PRIMARY KEY  USING BTREE (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hypervisor`
--

/*!40000 ALTER TABLE `hypervisor` DISABLE KEYS */;
INSERT INTO `hypervisor` (`id`,`description`,`largeDescription`,`idType`,`idPhysicalMachine`,`ip`,`port`) VALUES 
 (1,'vBox','vBox',1,1,'192.168.102.50',18083),
 (2,'vBox','vBox',1,2,'192.168.102.50',18083),
 (3,'vBox','vBox',1,3,'192.168.102.55',18083),
 (4,'vBox','vBox',1,6,'192.168.102.57',18083),
 (5,'vBox','vBox',1,7,'192.168.102.61',18083),
 (6,'vBox','vBox',1,8,'192.168.102.62',18083);
/*!40000 ALTER TABLE `hypervisor` ENABLE KEYS */;


--
-- Definition of table `hypervisortype`
--

DROP TABLE IF EXISTS `hypervisortype`;
CREATE TABLE `hypervisortype` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hypervisortype`
--

/*!40000 ALTER TABLE `hypervisortype` DISABLE KEYS */;
INSERT INTO `hypervisortype` (`id`,`name`) VALUES 
 (1,'vBox');
/*!40000 ALTER TABLE `hypervisortype` ENABLE KEYS */;


--
-- Definition of table `icon`
--

DROP TABLE IF EXISTS `icon`;
CREATE TABLE `icon` (
  `idIcon` int(10) unsigned NOT NULL auto_increment,
  `path` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY  (`idIcon`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `icon`
--

/*!40000 ALTER TABLE `icon` DISABLE KEYS */;
INSERT INTO `icon` (`idIcon`,`path`,`name`) VALUES 
 (1,'http://swik.net/swikIcons/img-542-96x96.png','Ubuntu'),
 (2,'http://swik.net/swikIcons/img-583-96x96.png','Xubuntu icon'),
 (3,'http://swik.net/swikIcons/img-25-96x96.png','Mysql icon'),
 (4,'http://www.abiquo.com/images/logo.gif','abiquo MW'),
 (5,'http://swik.net/swikIcons/img-242-96x96.jpg','Tomcat'),
 (6,'http://swik.net/swikIcons/img-413-96x96.png','test');
/*!40000 ALTER TABLE `icon` ENABLE KEYS */;


--
-- Definition of table `imagetype`
--

DROP TABLE IF EXISTS `imagetype`;
CREATE TABLE `imagetype` (
  `idImageType` int(3) NOT NULL auto_increment,
  `extension` varchar(20) NOT NULL,
  `idHyperType` int(5) NOT NULL,
  PRIMARY KEY  (`idImageType`),
  KEY `imageType` (`idHyperType`),
  CONSTRAINT `imageType` FOREIGN KEY (`idHyperType`) REFERENCES `hypervisortype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `imagetype`
--

/*!40000 ALTER TABLE `imagetype` DISABLE KEYS */;
INSERT INTO `imagetype` (`idImageType`,`extension`,`idHyperType`) VALUES 
 (1,'VDI',1);
/*!40000 ALTER TABLE `imagetype` ENABLE KEYS */;


--
-- Definition of table `networkmodule`
--

DROP TABLE IF EXISTS `networkmodule`;
CREATE TABLE `networkmodule` (
  `idNetworkModule` int(2) unsigned NOT NULL,
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
  CONSTRAINT `NetworkModule_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`),
  CONSTRAINT `NetworkModule_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `NetworkModule_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `networkmodule`
--

/*!40000 ALTER TABLE `networkmodule` DISABLE KEYS */;
/*!40000 ALTER TABLE `networkmodule` ENABLE KEYS */;


--
-- Definition of table `nodes`
--

DROP TABLE IF EXISTS `nodes`;
CREATE TABLE `nodes` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode` int(3) unsigned NOT NULL auto_increment,
  `posX` int(3) NOT NULL,
  `posY` int(3) NOT NULL,
  `idVM` int(11) NOT NULL,
  `idImage` int(4) NOT NULL,
  PRIMARY KEY  USING BTREE (`idNode`),
  KEY `Nodes_FK4` (`idVirtualApp`),
  CONSTRAINT `Nodes_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nodes`
--

/*!40000 ALTER TABLE `nodes` DISABLE KEYS */;
INSERT INTO `nodes` (`idVirtualApp`,`idNode`,`posX`,`posY`,`idVM`,`idImage`) VALUES 
 (1,1,-422,-217,4,5),
 (5,4,-470,-220,5,1);
/*!40000 ALTER TABLE `nodes` ENABLE KEYS */;


--
-- Definition of table `physicalmachine`
--

DROP TABLE IF EXISTS `physicalmachine`;
CREATE TABLE `physicalmachine` (
  `idPhysicalMachine` int(20) unsigned NOT NULL auto_increment,
  `idRack` int(15) unsigned NOT NULL,
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
  CONSTRAINT `PhysicalMachine_FK1` FOREIGN KEY (`idRack`) REFERENCES `rack` (`idRack`),
  CONSTRAINT `PhysicalMachine_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK4` FOREIGN KEY (`host_SO`) REFERENCES `so` (`idSO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `physicalmachine`
--

/*!40000 ALTER TABLE `physicalmachine` DISABLE KEYS */;
INSERT INTO `physicalmachine` (`idPhysicalMachine`,`idRack`,`name`,`description`,`ram`,`cpu`,`hd`,`host_SO`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`ramUsed`,`cpuUsed`,`hdUsed`) VALUES 
 (1,1,'Barcelona','Barcelona',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (2,1,'Mislata','Mislata',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (3,1,'L\'Hospitalet','L\'Hospitalet',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (6,2,'Valencia','Valencia',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (7,2,'Murcia','Murcia',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (8,3,'Madrid','Madrid',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (9,1,'Mallorca','Mallorc',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (10,3,'A Coruña','A Coruña',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0);
/*!40000 ALTER TABLE `physicalmachine` ENABLE KEYS */;


--
-- Definition of table `rack`
--

DROP TABLE IF EXISTS `rack`;
CREATE TABLE `rack` (
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
  CONSTRAINT `Rack_FK1` FOREIGN KEY (`idDataCenter`) REFERENCES `datacenter` (`idDataCenter`),
  CONSTRAINT `Rack_FK2` FOREIGN KEY (`idUserCreataion`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Rack_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rack`
--

/*!40000 ALTER TABLE `rack` DISABLE KEYS */;
INSERT INTO `rack` (`idRack`,`idDataCenter`,`name`,`shortDescription`,`largeDescription`,`idUserCreataion`,`creatioNDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,2,'Rack 1','Rack 1','Este es el Rack 1',1,'2008-10-29 15:42:16',1,'2008-11-20 13:28:42'),
 (2,2,'Rack 3','Rack 3','Este es el Rack 3',1,'2008-11-20 15:29:05',1,'2008-11-20 15:29:05'),
 (3,2,'Rack 2','Rack 2','Este es el Rack 2',1,'2008-11-20 13:33:21',1,'2008-11-20 13:28:42'),
 (4,2,'Rack 4','Desc corta','Larga',1,'2008-11-25 12:16:51',NULL,NULL);
/*!40000 ALTER TABLE `rack` ENABLE KEYS */;


--
-- Definition of table `repository`
--

DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `repository`
--

/*!40000 ALTER TABLE `repository` DISABLE KEYS */;
INSERT INTO `repository` (`idRepository`,`name`,`URL`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Main Repository','/opt/vm_repository',1,'2008-11-20 13:28:42',1,'2008-11-20 13:28:42');
/*!40000 ALTER TABLE `repository` ENABLE KEYS */;


--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `idRole` int(3) unsigned NOT NULL auto_increment,
  `shortDescription` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  PRIMARY KEY  (`idRole`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`idRole`,`shortDescription`,`largeDescription`) VALUES 
 (1,'Public','Public User (not logged)'),
 (2,'Administrator','Administrator User'),
 (3,'Registered User','Generic Registered User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user` varchar(20) NOT NULL,
  `key` varchar(100) NOT NULL,
  `expireDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `session`
--

/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` (`id`,`user`,`key`,`expireDate`) VALUES 
 (187,'root','1227728840015','2008-11-26 20:47:20');
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


--
-- Definition of table `so`
--

DROP TABLE IF EXISTS `so`;
CREATE TABLE `so` (
  `idSO` int(3) unsigned NOT NULL auto_increment,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY  (`idSO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `so`
--

/*!40000 ALTER TABLE `so` DISABLE KEYS */;
INSERT INTO `so` (`idSO`,`description`) VALUES 
 (1,'Windows'),
 (2,'GNU/Linux');
/*!40000 ALTER TABLE `so` ENABLE KEYS */;


--
-- Definition of table `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `idState` int(1) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY  (`idState`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `state`
--

/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` (`idState`,`description`) VALUES 
 (1,'RUNNING'),
 (2,'PAUSED'),
 (3,'POWERED_OFF'),
 (4,'REBOOTED'),
 (5,'NOT_DEPLOYED');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
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
  PRIMARY KEY  (`idUser`),
  KEY `User_FK1` (`idRole`),
  KEY `User_FK2` (`idUserCreation`),
  KEY `User_FK3` USING BTREE (`idUser_lastModification`),
  CONSTRAINT `User_FK1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idUser`,`idRole`,`user`,`name`,`surname`,`description`,`email`,`locale`,`password`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`active`) VALUES 
 (1,2,'root','Root','Root','Main administrator','','es_ES','xabiquo',1,'2008-10-20 00:00:00',1,'2008-10-20 00:00:00',1),
 (2,3,'demo','Demo','Demo','Demo User','','es_ES','demo',1,'2008-10-20 00:00:00',1,'2008-10-20 00:00:00',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `virtualapp`
--

DROP TABLE IF EXISTS `virtualapp`;
CREATE TABLE `virtualapp` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `virtualapp`
--

/*!40000 ALTER TABLE `virtualapp` DISABLE KEYS */;
INSERT INTO `virtualapp` (`idVirtualApp`,`name`,`public`,`idState`,`high_disponibility`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'App Virtual Tomcat',0,1,0,1,'2008-11-25 10:54:35',NULL,NULL),
 (4,'demo',0,5,0,1,'2008-11-25 11:08:20',NULL,NULL),
 (5,'122',0,5,0,1,'2008-11-25 12:37:01',NULL,NULL),
 (6,'mi sitio web',0,5,0,1,'2008-11-25 17:38:15',NULL,NULL);
/*!40000 ALTER TABLE `virtualapp` ENABLE KEYS */;


--
-- Definition of table `virtualimage`
--

DROP TABLE IF EXISTS `virtualimage`;
CREATE TABLE `virtualimage` (
  `idImage` int(4) unsigned NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) default NULL,
  `pathName` varchar(100) NOT NULL,
  `idSO` int(3) unsigned default NULL,
  `hd_required` int(10) default NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `virtualimage`
--

/*!40000 ALTER TABLE `virtualimage` DISABLE KEYS */;
INSERT INTO `virtualimage` (`idImage`,`name`,`description`,`pathName`,`idSO`,`hd_required`,`ram_required`,`cpu_required`,`idCategory`,`treaty`,`idRepository`,`idIcon`,`deleted`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`imageType`) VALUES 
 (1,'Ubuntu','Ubuntu','/opt/vm_repository/ubuntu-8.10-x86.vdi',2,2,256,'2.00',1,0,1,1,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (2,'Xubuntu web server','Xubuntu','/opt/vm_repository/xubuntu-8.04-x86.vdi',2,2,256,'2.00',5,0,1,2,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (3,'MySql Server','Debian + Mysql server','/opt/vm_repository/Debian - MySQL database server.vdi',2,2,256,'2.00',2,0,1,3,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (4,'abiquo MW','MW in a box','/opt/vm_repository/AbiquoMW.vdi',2,2,256,'2.00',3,0,1,4,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (5,'Tomcat','Debian + Tomcat','/opt/vm_repository/Debian - Tomcat app server.vdi',2,2,256,'2.00',2,0,1,5,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (6,'test','test','/opt/vm_repository/WindowsXP.vdi',1,2,256,'2.00',6,0,1,6,0,1,'2008-11-20 13:28:42',NULL,NULL,1);
/*!40000 ALTER TABLE `virtualimage` ENABLE KEYS */;


--
-- Definition of table `virtualmachine`
--

DROP TABLE IF EXISTS `virtualmachine`;
CREATE TABLE `virtualmachine` (
  `idVM` int(10) unsigned NOT NULL auto_increment,
  `idHypervisor` int(2) unsigned NOT NULL,
  `idImage` int(4) unsigned NOT NULL,
  `UUID` varchar(32) NOT NULL,
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
  CONSTRAINT `virtualMachine_FK2` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `virtualmachine`
--

/*!40000 ALTER TABLE `virtualmachine` DISABLE KEYS */;
INSERT INTO `virtualmachine` (`idVM`,`idHypervisor`,`idImage`,`UUID`,`name`,`description`,`ram`,`cpu`,`hd`,`vdrpPort`,`vdrpIP`,`idState`,`high_disponibility`) VALUES 
 (1,1,2,'96b1b785-2635-47ed-818e-f35c3828','XUbuntu','XUbuntu VM',1024,'2.00',2,3389,'192.168.102.50',5,0),
 (2,2,3,'8e31b7ab-2c89-496f-8abd-3b5e7d5c','MySQL server ','MySQL server VM',1024,'2.00',2,3389,'192.168.102.54',5,0),
 (3,3,4,'bc878d24-0d24-4363-8a0d-81530a11','abiquo MW','abiquo MW VM',1024,'2.00',2,3389,'192.168.102.55',5,0),
 (4,4,5,'ce164a45-5e59-43cb-bf1a-7eb4d14e','Tomcat','Tomcat VM',1024,'2.00',2,3389,'192.168.102.57',1,0),
 (5,5,1,'fb778759-a475-421d-95ed-0384521a','Ubuntu','Ubuntu VM',1024,'2.00',2,18389,'88.87.212.210',5,0),
 (6,6,6,'fb778759-a475-421d-95ed-0384521b','Test','Test VM',1024,'2.00',2,3389,'192.168.102.63',5,0);
/*!40000 ALTER TABLE `virtualmachine` ENABLE KEYS */;


--
-- Definition of table `virtualmachinenetworkmodule`
--

DROP TABLE IF EXISTS `virtualmachinenetworkmodule`;
CREATE TABLE `virtualmachinenetworkmodule` (
  `idVM` int(10) unsigned NOT NULL default '0',
  `idModule` int(1) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idVM`,`idModule`),
  CONSTRAINT `VirtualmachineNetworkModule_FK1` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `virtualmachinenetworkmodule`
--

/*!40000 ALTER TABLE `virtualmachinenetworkmodule` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualmachinenetworkmodule` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
