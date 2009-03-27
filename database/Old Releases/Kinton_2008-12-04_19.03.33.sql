-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.67


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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`auth_clientresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'USER_BUTTON','User access button in header',2,2),
 (2,'VIRTUALAPP_BUTTON','Virtual App access button in header',2,3),
 (3,'VIRTUALIMAGE_BUTTON','Virtual Image access button in header',2,2),
 (4,'INFRASTRUCTURE_BUTTON','Infrastructure access buttton in header',2,2),
 (5,'DASHBOARD_BUTTON','Dashboard acces button in header',2,3);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `kinton`.`auth_group`;
CREATE TABLE  `kinton`.`auth_group` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `description` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`auth_group` (`id`,`name`,`description`) VALUES 
 (1,'GENERIC','Generic'),
 (2,'MAIN','Flex client main menu group');

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
INSERT INTO `kinton`.`auth_serverresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'LOGIN','Login Service',1,1);

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

DROP TABLE IF EXISTS `kinton`.`category`;
CREATE TABLE  `kinton`.`category` (
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`category` (`idCategory`,`name`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Operating Systems',1,'2008-11-20 13:28:42',NULL,NULL),
 (2,'Database servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (3,'Others',1,'2008-11-20 13:28:42',NULL,NULL),
 (4,'Applications servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (5,'Web servers',1,'2008-11-20 13:28:42',NULL,NULL),
 (6,'Load Balancing servers',1,'2008-11-20 13:28:42',NULL,NULL);

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
INSERT INTO `kinton`.`datacenter` (`idDataCenter`,`name`,`situation`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (2,'ACENS MADRID','Madrid',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (3,'ACENS BARCELONA','Barcelona',1,'2008-11-20 13:32:38',1,'2008-11-20 13:28:42'),
 (6,'ACENS BILBAO','BILBAO',1,'2008-11-27 11:40:32',NULL,NULL),
 (8,'ACENS SEVILLA','Sevilla',1,'2008-11-27 13:06:58',1,'2008-11-27 15:18:03');

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

DROP TABLE IF EXISTS `kinton`.`hypervisor`;
CREATE TABLE  `kinton`.`hypervisor` (
  `id` int(2) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  `idType` int(5) NOT NULL,
  `idPhysicalMachine` int(20) unsigned NOT NULL,
  `ip` varchar(39) NOT NULL,
  `port` int(5) NOT NULL,
  PRIMARY KEY  USING BTREE (`id`),
  KEY `Hypervisor_FK1` (`idPhysicalMachine`),
  KEY `Hypervisor_FK2` (`idType`),
  CONSTRAINT `Hypervisor_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK2` FOREIGN KEY (`idType`) REFERENCES `hypervisortype` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`hypervisor` (`id`,`description`,`largeDescription`,`idType`,`idPhysicalMachine`,`ip`,`port`) VALUES 
 (1,'vBox','vBox',1,1,'192.168.102.50',18083),
 (2,'vBox','vBox',1,2,'192.168.102.50',18083),
 (3,'vBox','vBox',1,3,'192.168.102.55',18083),
 (4,'vBox','vBox',1,6,'192.168.102.57',18083),
 (5,'vBox','vBox',1,7,'192.168.102.61',18083),
 (6,'vBox','vBox',1,8,'192.168.102.62',18083);

DROP TABLE IF EXISTS `kinton`.`hypervisortype`;
CREATE TABLE  `kinton`.`hypervisortype` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`hypervisortype` (`id`,`name`) VALUES 
 (1,'vBox');

DROP TABLE IF EXISTS `kinton`.`icon`;
CREATE TABLE  `kinton`.`icon` (
  `idIcon` int(10) unsigned NOT NULL auto_increment,
  `path` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY  (`idIcon`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`icon` (`idIcon`,`path`,`name`) VALUES 
 (1,'http://swik.net/swikIcons/img-542-96x96.png','Ubuntu'),
 (2,'http://swik.net/swikIcons/img-583-96x96.png','Xubuntu icon'),
 (3,'http://swik.net/swikIcons/img-25-96x96.png','Mysql icon'),
 (4,'http://www.abiquo.com/images/logo.gif','abiquo MW'),
 (5,'http://swik.net/swikIcons/img-242-96x96.jpg','Tomcat'),
 (6,'http://swik.net/swikIcons/img-413-96x96.png','test');

DROP TABLE IF EXISTS `kinton`.`imagetype`;
CREATE TABLE  `kinton`.`imagetype` (
  `idImageType` int(3) NOT NULL auto_increment,
  `extension` varchar(20) NOT NULL,
  `idHyperType` int(5) NOT NULL,
  PRIMARY KEY  (`idImageType`),
  KEY `imageType` (`idHyperType`),
  CONSTRAINT `imageType` FOREIGN KEY (`idHyperType`) REFERENCES `hypervisortype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`imagetype` (`idImageType`,`extension`,`idHyperType`) VALUES 
 (1,'VDI',1);

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
INSERT INTO `kinton`.`networkmodule` (`idNetworkModule`,`idPhysicalMachine`,`dhcp`,`ip`,`gateway`,`bw`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModifcationDate`,`subnetMask`) VALUES 
 (1,21,1,'','','11.00',3,'2008-11-28 13:01:04',NULL,NULL,'');

DROP TABLE IF EXISTS `kinton`.`nodes`;
CREATE TABLE  `kinton`.`nodes` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode` int(3) unsigned NOT NULL auto_increment,
  `posX` int(3) NOT NULL,
  `posY` int(3) NOT NULL,
  `idVM` int(11) NOT NULL,
  `idImage` int(4) NOT NULL,
  PRIMARY KEY  USING BTREE (`idNode`),
  KEY `Nodes_FK4` (`idVirtualApp`),
  CONSTRAINT `Nodes_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`nodes` (`idVirtualApp`,`idNode`,`posX`,`posY`,`idVM`,`idImage`) VALUES 
 (1,1,-532,-243,5,1);

DROP TABLE IF EXISTS `kinton`.`physicalmachine`;
CREATE TABLE  `kinton`.`physicalmachine` (
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
  CONSTRAINT `PhysicalMachine_FK1` FOREIGN KEY (`idRack`) REFERENCES `rack` (`idRack`) ON DELETE CASCADE,
  CONSTRAINT `PhysicalMachine_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `PhysicalMachine_FK4` FOREIGN KEY (`host_SO`) REFERENCES `so` (`idSO`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`physicalmachine` (`idPhysicalMachine`,`idRack`,`name`,`description`,`ram`,`cpu`,`hd`,`host_SO`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`ramUsed`,`cpuUsed`,`hdUsed`) VALUES 
 (1,1,'Barcelona','Barcelona',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (2,1,'Mislata','Mislata',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (3,3,'L\'Hospitalet','L\'Hospitalet',1024,'1.00',100,2,1,'2008-11-20 15:29:05',1,'2008-12-01 19:28:30',0,'0.00',0),
 (6,2,'Valencia','Valencia',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (7,2,'Murcia','Murcia',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (8,3,'Madrid','Madrid',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (9,3,'Mallorca','Mallorc',1024,'1.00',100,1,1,'2008-11-20 15:29:05',1,'2008-12-04 17:41:10',0,'0.00',0),
 (10,3,'A Coruña','A Coruña',1024,'1.00',100,2,1,'2008-11-20 15:29:05',NULL,NULL,0,'0.00',0),
 (21,8,'Maquina Fisica 1','La primera',256,'1.50',300,2,3,'2008-11-28 12:39:49',3,'2008-11-28 13:01:04',0,'0.00',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`rack` (`idRack`,`idDataCenter`,`name`,`shortDescription`,`largeDescription`,`idUserCreataion`,`creatioNDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,2,'Rack 1','Rack 1','Este es el Rack 1',1,'2008-10-29 15:42:16',1,'2008-11-20 13:28:42'),
 (2,2,'Rack 3','Rack 3','Este es el Rack 3',1,'2008-11-20 15:29:05',1,'2008-11-20 15:29:05'),
 (3,2,'Rack 2','Rack 2','Este es el Rack 2',1,'2008-11-20 13:33:21',1,'2008-11-20 13:28:42'),
 (4,2,'Rack 4','Desc corta','Larga',1,'2008-11-25 12:16:51',NULL,NULL),
 (5,6,'Rack','Rack','Rack',1,'2008-11-27 12:12:54',NULL,NULL),
 (8,8,'Rack Sevilla 2','Sevilla 2','Rack Sevilla 2',1,'2008-11-27 15:26:30',NULL,NULL);

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
INSERT INTO `kinton`.`repository` (`idRepository`,`name`,`URL`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Main Repository','/opt/vm_repository',1,'2008-11-20 13:28:42',1,'2008-11-20 13:28:42');

DROP TABLE IF EXISTS `kinton`.`role`;
CREATE TABLE  `kinton`.`role` (
  `idRole` int(3) unsigned NOT NULL auto_increment,
  `shortDescription` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  `securityLevel` decimal(3,1) unsigned NOT NULL default '0.0',
  PRIMARY KEY  (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`role` (`idRole`,`shortDescription`,`largeDescription`,`securityLevel`) VALUES 
 (1,'Public','Public User (not logged)','99.9'),
 (2,'Administrator','Administrator User','1.0'),
 (3,'Registered User','Generic Registered User','2.0');

DROP TABLE IF EXISTS `kinton`.`session`;
CREATE TABLE  `kinton`.`session` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user` varchar(20) NOT NULL,
  `key` varchar(100) NOT NULL,
  `expireDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `kinton`.`so`;
CREATE TABLE  `kinton`.`so` (
  `idSO` int(3) unsigned NOT NULL auto_increment,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY  (`idSO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`so` (`idSO`,`description`) VALUES 
 (1,'Windows'),
 (2,'GNU/Linux');

DROP TABLE IF EXISTS `kinton`.`state`;
CREATE TABLE  `kinton`.`state` (
  `idState` int(1) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY  (`idState`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`state` (`idState`,`description`) VALUES 
 (1,'RUNNING'),
 (2,'PAUSED'),
 (3,'POWERED_OFF'),
 (4,'REBOOTED'),
 (5,'NOT_DEPLOYED');

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
  PRIMARY KEY  (`idUser`),
  KEY `User_FK1` (`idRole`),
  KEY `User_FK2` (`idUserCreation`),
  KEY `User_FK3` USING BTREE (`idUser_lastModification`),
  CONSTRAINT `User_FK1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`user` (`idUser`,`idRole`,`user`,`name`,`surname`,`description`,`email`,`locale`,`password`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`active`) VALUES 
 (1,2,'root','Administrador','Principal','Main administrator','root@root.com','es_ES','xabiquo',1,'2008-10-20 00:00:00',1,'2008-12-04 18:59:53',1),
 (2,3,'demo','Usuario','Standard','Demo User','demo@demo.com','es_ES','xdemo',1,'2008-10-20 00:00:00',1,'2008-12-04 18:58:56',1),
 (3,2,'root2','Root 2','Root 2','Second administrator','root2@root.com','es_ES','xabiquo',1,'2008-10-20 00:00:00',1,'2008-12-03 10:49:45',1);

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
INSERT INTO `kinton`.`virtualapp` (`idVirtualApp`,`name`,`public`,`idState`,`high_disponibility`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Test',0,5,0,1,'2008-12-02 17:25:54',1,'2008-12-02 17:26:13');

DROP TABLE IF EXISTS `kinton`.`virtualimage`;
CREATE TABLE  `kinton`.`virtualimage` (
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`virtualimage` (`idImage`,`name`,`description`,`pathName`,`idSO`,`hd_required`,`ram_required`,`cpu_required`,`idCategory`,`treaty`,`idRepository`,`idIcon`,`deleted`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`imageType`) VALUES 
 (1,'Ubuntu','Ubuntu','/opt/vm_repository/ubuntu-8.10-x86.vdi',2,2,256,'2.00',1,0,1,1,0,1,'2008-11-20 13:28:42',1,'2008-11-28 12:33:21',1),
 (2,'Xubuntu web server','Xubuntu','/opt/vm_repository/xubuntu-8.04-x86.vdi',2,2,256,'2.00',5,0,1,2,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (3,'MySql Server','Debian + Mysql server','/opt/vm_repository/Debian - MySQL database server.vdi',2,2,256,'2.00',2,0,1,3,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (4,'abiquo MW','MW in a box','/opt/vm_repository/AbiquoMW.vdi',2,2,256,'2.00',3,0,1,4,0,1,'2008-11-20 13:28:42',NULL,NULL,1),
 (5,'Tomcat','Debian + Tomcat','/opt/vm_repository/Debian - Tomcat app server.vdi',2,2,256,'2.00',4,0,1,5,0,1,'2008-11-20 13:28:42',1,'2008-11-28 12:33:15',1),
 (6,'test','test','/opt/vm_repository/WindowsXP.vdi',1,2,256,'2.00',6,0,1,6,0,1,'2008-11-20 13:28:42',NULL,NULL,1);

DROP TABLE IF EXISTS `kinton`.`virtualmachine`;
CREATE TABLE  `kinton`.`virtualmachine` (
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
  KEY `virtualMachine_FK3` (`idImage`),
  CONSTRAINT `virtualMachine_FK1` FOREIGN KEY (`idHypervisor`) REFERENCES `hypervisor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `virtualMachine_FK2` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `virtualMachine_FK3` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`virtualmachine` (`idVM`,`idHypervisor`,`idImage`,`UUID`,`name`,`description`,`ram`,`cpu`,`hd`,`vdrpPort`,`vdrpIP`,`idState`,`high_disponibility`) VALUES 
 (1,1,2,'96b1b785-2635-47ed-818e-f35c3828','XUbuntu','XUbuntu VM',1024,'2.00',2,3389,'192.168.102.50',5,0),
 (2,2,3,'8e31b7ab-2c89-496f-8abd-3b5e7d5c','MySQL server ','MySQL server VM',1024,'2.00',2,3389,'192.168.102.54',5,0),
 (3,3,4,'bc878d24-0d24-4363-8a0d-81530a11','abiquo MW','abiquo MW VM',1024,'2.00',2,3389,'192.168.102.55',5,0),
 (4,4,5,'ce164a45-5e59-43cb-bf1a-7eb4d14e','Tomcat','Tomcat VM',1024,'2.00',2,3389,'192.168.102.57',5,0),
 (5,5,1,'fb778759-a475-421d-95ed-0384521a','Ubuntu','Ubuntu VM',1024,'2.00',2,18389,'88.87.212.210',3,0),
 (6,6,6,'fb778759-a475-421d-95ed-0384521b','Test','Test VM',1024,'2.00',2,3389,'192.168.102.63',5,0);

DROP TABLE IF EXISTS `kinton`.`virtualmachinenetworkmodule`;
CREATE TABLE  `kinton`.`virtualmachinenetworkmodule` (
  `idVM` int(10) unsigned NOT NULL default '0',
  `idModule` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idVM`,`idModule`),
  CONSTRAINT `VirtualmachineNetworkModule_FK1` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
