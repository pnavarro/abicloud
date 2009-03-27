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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
INSERT INTO `kinton`.`auth_clientresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'USER_BUTTON','User access button in header',2,2),
 (2,'VIRTUALAPP_BUTTON','Virtual App access button in header',2,3),
 (3,'VIRTUALIMAGE_BUTTON','Virtual Image access button in header',2,2),
 (4,'INFRASTRUCTURE_BUTTON','Infrastructure access buttton in header',2,2),
 (5,'DASHBOARD_BUTTON','Dashboard acces button in header',2,3),
 (6,'CHARTS_BUTTON','Charts access button in header',2,2),
 (7,'CONFIG_BUTTON','Config access button in header',2,2);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`auth_group`;
CREATE TABLE  `kinton`.`auth_group` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `description` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`category` (`idCategory`,`name`,`isErasable`,`isDefault`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Others',0,1,1,'2008-11-20 13:28:42',NULL,NULL),
 (2,'Database servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (4,'Applications servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (5,'Web servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`datacenter` (`idDataCenter`,`name`,`situation`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'myDatacenter','Barcelona',1,'2008-11-20 00:00:00',NULL,NULL);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`enterprise`;
CREATE TABLE  `kinton`.`enterprise` (
  `idEnterprise` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  `deleted` int(1) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idEnterprise`),
  KEY `enterprise_FK1` (`idUserCreation`),
  KEY `enterprise_FK2` (`idUser_lastModification`),
  CONSTRAINT `enterprise_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `enterprise_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`enterprise` (`idEnterprise`,`name`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`deleted`) VALUES 
 (1,'Abiquo',1,'2008-10-20 00:00:00',NULL,NULL,0);

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
  PRIMARY KEY  (`id`),
  KEY `Hypervisor_FK1` (`idPhysicalMachine`),
  KEY `Hypervisor_FK2` (`idType`),
  KEY `Hypervisor_FK3` (`idUserCreation`),
  KEY `Hypervisor_FK4` (`idUser_lastModification`),
  CONSTRAINT `Hypervisor_FK2` FOREIGN KEY (`idType`) REFERENCES `hypervisortype` (`id`),
  CONSTRAINT `Hypervisor_FK3` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Hypervisor_FK4` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Hypervisor_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`hypervisor` (`id`,`description`,`idType`,`idPhysicalMachine`,`ip`,`port`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'xVM Virtualbox - 127.0.0.1:18083',1,1,'127.0.0.1',18083,1,'2009-02-03 00:00:00',NULL,NULL);

DROP TABLE IF EXISTS `kinton`.`hypervisortype`;
CREATE TABLE  `kinton`.`hypervisortype` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `defaultPort` int(5) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`hypervisortype` (`id`,`name`,`defaultPort`) VALUES 
 (1,'vBox',18083);

DROP TABLE IF EXISTS `kinton`.`icon`;
CREATE TABLE  `kinton`.`icon` (
  `idIcon` int(10) unsigned NOT NULL auto_increment,
  `path` varchar(200) NOT NULL,
  `name` varchar(20) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idIcon`),
  KEY `ICON_FK1` (`idUserCreation`),
  KEY `ICON_FK2` (`idUser_lastModification`),
  CONSTRAINT `ICON_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `ICON_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`imagetype`;
CREATE TABLE  `kinton`.`imagetype` (
  `idImageType` int(3) NOT NULL auto_increment,
  `extension` varchar(20) NOT NULL,
  `idHyperType` int(5) NOT NULL,
  PRIMARY KEY  (`idImageType`),
  KEY `imageType` (`idHyperType`),
  CONSTRAINT `imageType` FOREIGN KEY (`idHyperType`) REFERENCES `hypervisortype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`imagetype` (`idImageType`,`extension`,`idHyperType`) VALUES 
 (1,'VDI',1);

DROP TABLE IF EXISTS `kinton`.`log`;
CREATE TABLE  `kinton`.`log` (
  `idLog` int(10) unsigned NOT NULL auto_increment,
  `idVirtualApp` int(10) unsigned NOT NULL,
  `description` varchar(200) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY  (`idLog`),
  KEY `log_FK1` (`idVirtualApp`),
  CONSTRAINT `log_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`networkmodule` (`idNetworkModule`,`idPhysicalMachine`,`dhcp`,`ip`,`gateway`,`bw`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModifcationDate`,`subnetMask`) VALUES 
 (1,1,0,'127.0.0.1','127.0.0.1','100.00',1,'2009-02-18 19:33:21',NULL,NULL,'255.255.255.0');

DROP TABLE IF EXISTS `kinton`.`node`;
CREATE TABLE  `kinton`.`node` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode` int(10) unsigned NOT NULL auto_increment,
  `posX` int(3) NOT NULL,
  `posY` int(3) NOT NULL,
  `idNodeType` int(2) unsigned NOT NULL,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`idNode`),
  KEY `Nodes_FK4` (`idVirtualApp`),
  KEY `node_FK1` (`idNodeType`),
  CONSTRAINT `node_FK1` FOREIGN KEY (`idNodeType`) REFERENCES `nodetype` (`idNodeType`),
  CONSTRAINT `node_FK2` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`nodenetwork`;
CREATE TABLE  `kinton`.`nodenetwork` (
  `idNode` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idNode`),
  CONSTRAINT `nodeNetwork_FK1` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`noderelationtype`;
CREATE TABLE  `kinton`.`noderelationtype` (
  `idNodeRelationType` int(2) unsigned NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`idNodeRelationType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`nodestorage`;
CREATE TABLE  `kinton`.`nodestorage` (
  `idNode` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idNode`),
  CONSTRAINT `nodeStorage_FK1` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `kinton`.`nodetype`;
CREATE TABLE  `kinton`.`nodetype` (
  `idNodeType` int(2) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY  (`idNodeType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`nodetype` (`idNodeType`,`name`) VALUES 
 (1,'Virtual Image'),
 (2,'Storage'),
 (3,'Network');

DROP TABLE IF EXISTS `kinton`.`nodevirtualimage`;
CREATE TABLE  `kinton`.`nodevirtualimage` (
  `idNode` int(10) unsigned NOT NULL,
  `idVM` int(10) unsigned default NULL,
  `idImage` int(10) unsigned NOT NULL,
  KEY `nodevirtualImage_FK1` (`idImage`),
  KEY `nodevirtualImage_FK2` (`idVM`),
  KEY `nodevirtualimage_FK3` (`idNode`),
  CONSTRAINT `nodevirtualimage_FK3` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`) ON DELETE CASCADE,
  CONSTRAINT `nodevirtualImage_FK1` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`),
  CONSTRAINT `nodevirtualImage_FK2` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`physicalmachine` (`idPhysicalMachine`,`idRack`,`idDataCenter`,`name`,`description`,`ram`,`cpu`,`hd`,`host_SO`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`ramUsed`,`cpuUsed`,`hdUsed`) VALUES 
 (1,1,1,'myMachine','My local machine',4096,'2.00',41943040,2,1,'2009-02-03 00:00:00',1,'2009-02-18 19:33:21',0,'0.00',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`rack` (`idRack`,`idDataCenter`,`name`,`shortDescription`,`largeDescription`,`idUserCreataion`,`creatioNDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,1,'myRack','myRack','myRack',1,'2008-02-27 09:00:00',NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`repository` (`idRepository`,`name`,`URL`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Main Repository','c:\\myRepository',1,'2008-11-20 13:28:42',1,NULL);

DROP TABLE IF EXISTS `kinton`.`role`;
CREATE TABLE  `kinton`.`role` (
  `idRole` int(3) unsigned NOT NULL auto_increment,
  `shortDescription` varchar(20) NOT NULL,
  `largeDescription` varchar(100) default NULL,
  `securityLevel` decimal(3,1) unsigned NOT NULL default '0.0',
  PRIMARY KEY  (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`role` (`idRole`,`shortDescription`,`largeDescription`,`securityLevel`) VALUES 
 (1,'Public','Public User (not logged)','99.9'),
 (2,'Sys Admin','IT System administrator','1.0'),
 (3,'User','Generic Registered User','2.0');

DROP TABLE IF EXISTS `kinton`.`session`;
CREATE TABLE  `kinton`.`session` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user` varchar(20) NOT NULL,
  `key` varchar(100) NOT NULL,
  `expireDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`so`;
CREATE TABLE  `kinton`.`so` (
  `idSO` int(3) unsigned NOT NULL auto_increment,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY  (`idSO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`so` (`idSO`,`description`) VALUES 
 (1,'Windows'),
 (2,'GNU/Linux');

DROP TABLE IF EXISTS `kinton`.`state`;
CREATE TABLE  `kinton`.`state` (
  `idState` int(1) unsigned NOT NULL auto_increment,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY  (`idState`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`state` (`idState`,`description`) VALUES 
 (1,'RUNNING'),
 (2,'PAUSED'),
 (3,'POWERED_OFF'),
 (4,'REBOOTED'),
 (5,'NOT_DEPLOYED'),
 (6,'IN_PROGRESS');

DROP TABLE IF EXISTS `kinton`.`user`;
CREATE TABLE  `kinton`.`user` (
  `idUser` int(10) unsigned NOT NULL auto_increment,
  `idRole` int(3) unsigned NOT NULL,
  `idEnterprise` int(10) unsigned default NULL,
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
  `deleted` int(1) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idUser`),
  UNIQUE KEY `user_unique` (`user`),
  KEY `User_FK1` (`idRole`),
  KEY `User_FK2` (`idUserCreation`),
  KEY `User_FK3` USING BTREE (`idUser_lastModification`),
  KEY `FK1_user` (`idEnterprise`),
  CONSTRAINT `FK1_user` FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`),
  CONSTRAINT `User_FK1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `kinton`.`user` (`idUser`,`idRole`,`idEnterprise`,`user`,`name`,`surname`,`description`,`email`,`locale`,`password`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`active`,`deleted`) VALUES 
 (1,2,1,'admin','System','Administrator','Main administrator','-','en_US','xabiquo',1,'2008-10-20 00:00:00',1,NULL,1,0),
 (2,3,1,'user','Standard','User','Standard user','-','en_US','xuser',1,'2008-10-20 00:00:00',1,NULL,1,0);

DROP TABLE IF EXISTS `kinton`.`virtualapp`;
CREATE TABLE  `kinton`.`virtualapp` (
  `idVirtualApp` int(10) unsigned NOT NULL auto_increment,
  `idVirtualDataCenter` int(10) unsigned NOT NULL,
  `idEnterprise` int(10) unsigned default NULL,
  `name` varchar(30) NOT NULL,
  `public` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idState` int(1) unsigned NOT NULL,
  `high_disponibility` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `error` int(1) unsigned NOT NULL,
  `nodeconnections` text,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idVirtualApp`),
  KEY `VirtualApp_FK1` (`idState`),
  KEY `VirtualApp_FK2` (`idUserCreation`),
  KEY `VirtualApp_FK3` (`idUser_lastModification`),
  KEY `VirtualApp_FK4` (`idVirtualDataCenter`),
  KEY `VirtualApp_FK5` (`idEnterprise`),
  CONSTRAINT `VirtualApp_FK1` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `VirtualApp_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `VirtualApp_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `VirtualApp_FK4` FOREIGN KEY (`idVirtualDataCenter`) REFERENCES `virtualdatacenter` (`idVirtualDataCenter`) ON DELETE CASCADE,
  CONSTRAINT `VirtualApp_FK5` FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`virtualdatacenter`;
CREATE TABLE  `kinton`.`virtualdatacenter` (
  `idVirtualDataCenter` int(10) unsigned NOT NULL auto_increment,
  `idEnterprise` int(10) unsigned NOT NULL,
  `name` varchar(40) default NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned default NULL,
  `lastModificationDate` datetime default NULL,
  PRIMARY KEY  (`idVirtualDataCenter`),
  KEY `virtualDataCenter_FK1` (`idEnterprise`),
  KEY `virtualDataCenter_FK2` (`idUserCreation`),
  KEY `virtualDataCenter_FK3` (`idUser_lastModification`),
  CONSTRAINT `virtualDataCenter_FK1` FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`),
  CONSTRAINT `virtualDataCenter_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualDataCenter_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `imageType` int(3) NOT NULL,
  PRIMARY KEY  (`idImage`),
  KEY `virtualImage_FK1` (`idSO`),
  KEY `virtualImage_FK2` (`idCategory`),
  KEY `virtualImage_FK3` (`idRepository`),
  KEY `virtualImage_FK4` (`idIcon`),
  KEY `virtualImage_FK5` (`idUserCreation`),
  KEY `virtualImage_FK6` (`idUser_lastModification`),
  KEY `virtualImage_FK7` (`imageType`),
  CONSTRAINT `virtualImage_FK4` FOREIGN KEY (`idIcon`) REFERENCES `icon` (`idIcon`) ON DELETE SET NULL,
  CONSTRAINT `virtualImage_FK1` FOREIGN KEY (`idSO`) REFERENCES `so` (`idSO`),
  CONSTRAINT `virtualImage_FK2` FOREIGN KEY (`idCategory`) REFERENCES `category` (`idCategory`),
  CONSTRAINT `virtualImage_FK3` FOREIGN KEY (`idRepository`) REFERENCES `repository` (`idRepository`),
  CONSTRAINT `virtualImage_FK5` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualImage_FK6` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualImage_FK7` FOREIGN KEY (`imageType`) REFERENCES `imagetype` (`idImageType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`virtualmachine`;
CREATE TABLE  `kinton`.`virtualmachine` (
  `idVM` int(10) unsigned NOT NULL auto_increment,
  `idHypervisor` int(2) unsigned NOT NULL,
  `idImage` int(4) unsigned NOT NULL,
  `UUID` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
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
  CONSTRAINT `virtualMachine_FK3` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`),
  CONSTRAINT `virtualMachine_FK1` FOREIGN KEY (`idHypervisor`) REFERENCES `hypervisor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `virtualMachine_FK2` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kinton`.`virtualmachinenetworkmodule`;
CREATE TABLE  `kinton`.`virtualmachinenetworkmodule` (
  `idVM` int(10) unsigned NOT NULL default '0',
  `idModule` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idVM`,`idModule`),
  CONSTRAINT `VirtualmachineNetworkModule_FK1` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
