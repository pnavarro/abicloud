-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.30-community


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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `idGroup` int(11) unsigned DEFAULT NULL,
  `idRole` int(3) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_clientresourceFK1` (`idGroup`),
  KEY `auth_clientresourceFK2` (`idRole`),
  CONSTRAINT `auth_clientresourceFK1` FOREIGN KEY (`idGroup`) REFERENCES `auth_group` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_clientresourceFK2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auth_clientresource`
--

/*!40000 ALTER TABLE `auth_clientresource` DISABLE KEYS */;
INSERT INTO `auth_clientresource` (`id`,`name`,`description`,`idGroup`,`idRole`) VALUES 
 (1,'USER_BUTTON','User access button in header',2,2),
 (2,'VIRTUALAPP_BUTTON','Virtual App access button in header',2,3),
 (3,'VIRTUALIMAGE_BUTTON','Virtual Image access button in header',2,2),
 (4,'INFRASTRUCTURE_BUTTON','Infrastructure access buttton in header',2,2),
 (5,'DASHBOARD_BUTTON','Dashboard acces button in header',2,3),
 (6,'CHARTS_BUTTON','Charts access button in header',2,2),
 (7,'CONFIG_BUTTON','Config access button in header',2,2);
/*!40000 ALTER TABLE `auth_clientresource` ENABLE KEYS */;


--
-- Definition of table `auth_clientresource_exception`
--

DROP TABLE IF EXISTS `auth_clientresource_exception`;
CREATE TABLE `auth_clientresource_exception` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idResource` int(11) unsigned NOT NULL,
  `idUser` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_clientresource_exceptionFK1` (`idResource`),
  KEY `auth_clientresource_exceptionFK2` (`idUser`),
  CONSTRAINT `auth_clientresource_exceptionFK1` FOREIGN KEY (`idResource`) REFERENCES `auth_clientresource` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_clientresource_exceptionFK2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `idGroup` int(11) unsigned DEFAULT NULL,
  `idRole` int(3) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_serverresourceFK1` (`idGroup`),
  KEY `auth_serverresourceFK2` (`idRole`),
  CONSTRAINT `auth_serverresourceFK1` FOREIGN KEY (`idGroup`) REFERENCES `auth_group` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_serverresourceFK2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idResource` int(11) unsigned NOT NULL,
  `idUser` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_serverresource_exceptionFK1` (`idResource`),
  KEY `auth_serverresource_exceptionFK2` (`idUser`),
  CONSTRAINT `auth_serverresource_exceptionFK1` FOREIGN KEY (`idResource`) REFERENCES `auth_serverresource` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auth_serverresource_exceptionFK2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `idCategory` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `isErasable` int(1) unsigned NOT NULL DEFAULT '1',
  `isDefault` int(1) unsigned NOT NULL DEFAULT '0',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idCategory`),
  KEY `Category_FK1` (`idUserCreation`),
  KEY `Category_FK2` (`idUser_lastModification`),
  CONSTRAINT `Category_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Category_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`idCategory`,`name`,`isErasable`,`isDefault`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Others',0,1,1,'2008-11-20 13:28:42',NULL,NULL),
 (2,'Database servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (4,'Applications servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL),
 (5,'Web servers',1,0,1,'2008-11-20 13:28:42',NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


--
-- Definition of table `datacenter`
--

DROP TABLE IF EXISTS `datacenter`;
CREATE TABLE `datacenter` (
  `idDataCenter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `situation` varchar(20) DEFAULT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idDataCenter`),
  KEY `DataCenter_FK1` (`idUserCreation`),
  KEY `DataCenter_FK2` (`idUser_lastModification`),
  CONSTRAINT `DataCenter_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `DataCenter_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `datacenter`
--

/*!40000 ALTER TABLE `datacenter` DISABLE KEYS */;
INSERT INTO `datacenter` (`idDataCenter`,`name`,`situation`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'myDatacenter','Barcelona',1,'2008-11-20 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `datacenter` ENABLE KEYS */;


--
-- Definition of table `dns`
--

DROP TABLE IF EXISTS `dns`;
CREATE TABLE `dns` (
  `idDns` int(10) unsigned NOT NULL DEFAULT '0',
  `idNetworkModule` int(10) unsigned NOT NULL DEFAULT '0',
  `idPhysicalMachine` int(20) unsigned NOT NULL DEFAULT '0',
  `dns` varchar(39) DEFAULT NULL,
  `idUserCreation` int(10) unsigned DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idDns`,`idNetworkModule`,`idPhysicalMachine`),
  KEY `Dns_FK1` (`idNetworkModule`,`idPhysicalMachine`),
  KEY `Dns_FK2` (`idUserCreation`),
  KEY `Dns_FK3` (`idUser_lastModification`),
  CONSTRAINT `Dns_FK1` FOREIGN KEY (`idNetworkModule`, `idPhysicalMachine`) REFERENCES `networkmodule` (`idNetworkModule`, `idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `Dns_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Dns_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dns`
--

/*!40000 ALTER TABLE `dns` DISABLE KEYS */;
/*!40000 ALTER TABLE `dns` ENABLE KEYS */;


--
-- Definition of table `enterprise`
--

DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `idEnterprise` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `deleted` int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idEnterprise`),
  KEY `enterprise_FK1` (`idUserCreation`),
  KEY `enterprise_FK2` (`idUser_lastModification`),
  CONSTRAINT `enterprise_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `enterprise_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `enterprise`
--

/*!40000 ALTER TABLE `enterprise` DISABLE KEYS */;
/*!40000 ALTER TABLE `enterprise` ENABLE KEYS */;


--
-- Definition of table `hypervisor`
--

DROP TABLE IF EXISTS `hypervisor`;
CREATE TABLE `hypervisor` (
  `id` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `idType` int(5) NOT NULL,
  `idPhysicalMachine` int(20) unsigned NOT NULL,
  `ip` varchar(39) NOT NULL,
  `port` int(5) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Hypervisor_FK1` (`idPhysicalMachine`),
  KEY `Hypervisor_FK2` (`idType`),
  KEY `Hypervisor_FK3` (`idUserCreation`),
  KEY `Hypervisor_FK4` (`idUser_lastModification`),
  CONSTRAINT `Hypervisor_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK2` FOREIGN KEY (`idType`) REFERENCES `hypervisortype` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK3` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`) ON DELETE CASCADE,
  CONSTRAINT `Hypervisor_FK4` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hypervisor`
--

/*!40000 ALTER TABLE `hypervisor` DISABLE KEYS */;
INSERT INTO `hypervisor` (`id`,`description`,`idType`,`idPhysicalMachine`,`ip`,`port`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'xVM Virtualbox - 127.0.0.1:18083',1,1,'127.0.0.1',18083,1,'2009-02-03 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `hypervisor` ENABLE KEYS */;


--
-- Definition of table `hypervisortype`
--

DROP TABLE IF EXISTS `hypervisortype`;
CREATE TABLE `hypervisortype` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `defaultPort` int(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hypervisortype`
--

/*!40000 ALTER TABLE `hypervisortype` DISABLE KEYS */;
INSERT INTO `hypervisortype` (`id`,`name`,`defaultPort`) VALUES 
 (1,'vBox',18083);
/*!40000 ALTER TABLE `hypervisortype` ENABLE KEYS */;


--
-- Definition of table `icon`
--

DROP TABLE IF EXISTS `icon`;
CREATE TABLE `icon` (
  `idIcon` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `path` varchar(200) NOT NULL,
  `name` varchar(20) NOT NULL,
  `isDefault` int(1) unsigned DEFAULT '0',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idIcon`),
  KEY `ICON_FK1` (`idUserCreation`),
  KEY `ICON_FK2` (`idUser_lastModification`),
  CONSTRAINT `ICON_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`) ON DELETE CASCADE,
  CONSTRAINT `ICON_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `icon`
--

/*!40000 ALTER TABLE `icon` DISABLE KEYS */;
/*!40000 ALTER TABLE `icon` ENABLE KEYS */;


--
-- Definition of table `imagetype`
--

DROP TABLE IF EXISTS `imagetype`;
CREATE TABLE `imagetype` (
  `idImageType` int(3) NOT NULL AUTO_INCREMENT,
  `extension` varchar(20) NOT NULL,
  `idHyperType` int(5) NOT NULL,
  PRIMARY KEY (`idImageType`),
  KEY `imageType` (`idHyperType`),
  CONSTRAINT `imageType` FOREIGN KEY (`idHyperType`) REFERENCES `hypervisortype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `imagetype`
--

/*!40000 ALTER TABLE `imagetype` DISABLE KEYS */;
INSERT INTO `imagetype` (`idImageType`,`extension`,`idHyperType`) VALUES 
 (1,'VDI',1);
/*!40000 ALTER TABLE `imagetype` ENABLE KEYS */;


--
-- Definition of table `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `idLog` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idVirtualApp` int(10) unsigned NOT NULL,
  `description` varchar(40) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`idLog`),
  KEY `log_FK1` (`idVirtualApp`),
  CONSTRAINT `log_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `log`
--

/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;


--
-- Definition of table `networkmodule`
--

DROP TABLE IF EXISTS `networkmodule`;
CREATE TABLE `networkmodule` (
  `idNetworkModule` int(10) unsigned NOT NULL DEFAULT '0',
  `idPhysicalMachine` int(20) unsigned NOT NULL,
  `dhcp` int(1) unsigned NOT NULL COMMENT '0-False  1-True',
  `ip` varchar(39) DEFAULT NULL,
  `gateway` varchar(39) DEFAULT NULL,
  `bw` decimal(10,2) unsigned DEFAULT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModifcationDate` datetime DEFAULT NULL,
  `subnetMask` varchar(39) DEFAULT NULL,
  PRIMARY KEY (`idNetworkModule`,`idPhysicalMachine`),
  KEY `NetworkModule_FK1` (`idPhysicalMachine`),
  KEY `NetworkModule_FK2` (`idUserCreation`),
  KEY `NetworkModule_FK3` (`idUser_lastModification`),
  CONSTRAINT `NetworkModule_FK1` FOREIGN KEY (`idPhysicalMachine`) REFERENCES `physicalmachine` (`idPhysicalMachine`) ON DELETE CASCADE,
  CONSTRAINT `NetworkModule_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `NetworkModule_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `networkmodule`
--

/*!40000 ALTER TABLE `networkmodule` DISABLE KEYS */;
INSERT INTO `networkmodule` (`idNetworkModule`,`idPhysicalMachine`,`dhcp`,`ip`,`gateway`,`bw`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModifcationDate`,`subnetMask`) VALUES 
 (1,1,0,'127.0.0.1','127.0.0.1','100.00',1,'2009-02-03 17:56:01',NULL,NULL,'255.255.255.0');
/*!40000 ALTER TABLE `networkmodule` ENABLE KEYS */;


--
-- Definition of table `node`
--

DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `posX` int(3) NOT NULL,
  `posY` int(3) NOT NULL,
  `idNodeType` int(2) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idNode`) USING BTREE,
  KEY `Nodes_FK4` (`idVirtualApp`),
  KEY `node_FK1` (`idNodeType`),
  CONSTRAINT `node_FK1` FOREIGN KEY (`idNodeType`) REFERENCES `nodetype` (`idNodeType`),
  CONSTRAINT `node_FK2` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `node`
--

/*!40000 ALTER TABLE `node` DISABLE KEYS */;
/*!40000 ALTER TABLE `node` ENABLE KEYS */;


--
-- Definition of table `nodenetwork`
--

DROP TABLE IF EXISTS `nodenetwork`;
CREATE TABLE `nodenetwork` (
  `idNode` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idNode`),
  CONSTRAINT `nodeNetwork_FK1` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nodenetwork`
--

/*!40000 ALTER TABLE `nodenetwork` DISABLE KEYS */;
/*!40000 ALTER TABLE `nodenetwork` ENABLE KEYS */;


--
-- Definition of table `noderelation`
--

DROP TABLE IF EXISTS `noderelation`;
CREATE TABLE `noderelation` (
  `idVirtualApp` int(10) unsigned NOT NULL,
  `idNode1` int(10) unsigned NOT NULL,
  `idNode2` int(10) unsigned NOT NULL,
  `idRelationType` int(2) unsigned NOT NULL,
  PRIMARY KEY (`idVirtualApp`,`idNode1`,`idNode2`,`idRelationType`),
  KEY `noderelation_FK4` (`idRelationType`),
  KEY `noderelation_FK2` (`idNode1`),
  KEY `noderelation_FK3` (`idNode2`),
  CONSTRAINT `noderelation_FK4` FOREIGN KEY (`idRelationType`) REFERENCES `noderelationtype` (`idNodeRelationType`),
  CONSTRAINT `noderelation_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`) ON DELETE CASCADE,
  CONSTRAINT `noderelation_FK2` FOREIGN KEY (`idNode1`) REFERENCES `node` (`idNode`) ON UPDATE CASCADE,
  CONSTRAINT `noderelation_FK3` FOREIGN KEY (`idNode2`) REFERENCES `node` (`idNode`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `noderelation`
--

/*!40000 ALTER TABLE `noderelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `noderelation` ENABLE KEYS */;


--
-- Definition of table `noderelationtype`
--

DROP TABLE IF EXISTS `noderelationtype`;
CREATE TABLE `noderelationtype` (
  `idNodeRelationType` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idNodeRelationType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `noderelationtype`
--

/*!40000 ALTER TABLE `noderelationtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `noderelationtype` ENABLE KEYS */;


--
-- Definition of table `nodestorage`
--

DROP TABLE IF EXISTS `nodestorage`;
CREATE TABLE `nodestorage` (
  `idNode` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idNode`),
  CONSTRAINT `nodeStorage_FK1` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nodestorage`
--

/*!40000 ALTER TABLE `nodestorage` DISABLE KEYS */;
/*!40000 ALTER TABLE `nodestorage` ENABLE KEYS */;


--
-- Definition of table `nodetype`
--

DROP TABLE IF EXISTS `nodetype`;
CREATE TABLE `nodetype` (
  `idNodeType` int(2) unsigned NOT NULL,
  `idName` varchar(20) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`idNodeType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nodetype`
--

/*!40000 ALTER TABLE `nodetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `nodetype` ENABLE KEYS */;


--
-- Definition of table `nodevirtualimage`
--

DROP TABLE IF EXISTS `nodevirtualimage`;
CREATE TABLE `nodevirtualimage` (
  `idNode` int(10) unsigned NOT NULL,
  `idVM` int(10) unsigned DEFAULT NULL,
  `idImage` int(10) unsigned NOT NULL,
  KEY `nodevirtualImage_FK1` (`idImage`),
  KEY `nodevirtualImage_FK2` (`idVM`),
  CONSTRAINT `nodevirtualImage_FK1` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`) ON DELETE CASCADE,
  CONSTRAINT `nodevirtualImage_FK2` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nodevirtualimage`
--

/*!40000 ALTER TABLE `nodevirtualimage` DISABLE KEYS */;
/*!40000 ALTER TABLE `nodevirtualimage` ENABLE KEYS */;


--
-- Definition of table `physicalmachine`
--

DROP TABLE IF EXISTS `physicalmachine`;
CREATE TABLE `physicalmachine` (
  `idPhysicalMachine` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `idRack` int(15) unsigned DEFAULT NULL,
  `idDataCenter` int(10) unsigned NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `ram` int(7) NOT NULL,
  `cpu` decimal(6,2) unsigned NOT NULL,
  `hd` int(10) unsigned NOT NULL,
  `host_SO` int(3) unsigned NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `ramUsed` int(7) NOT NULL,
  `cpuUsed` decimal(6,2) NOT NULL,
  `hdUsed` int(11) NOT NULL,
  PRIMARY KEY (`idPhysicalMachine`),
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

--
-- Dumping data for table `physicalmachine`
--

/*!40000 ALTER TABLE `physicalmachine` DISABLE KEYS */;
INSERT INTO `physicalmachine` (`idPhysicalMachine`,`idRack`,`idDataCenter`,`name`,`description`,`ram`,`cpu`,`hd`,`host_SO`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`ramUsed`,`cpuUsed`,`hdUsed`) VALUES 
 (1,1,1,'myMachine','My local machine',4096,'2.00',200,2,1,'2009-02-03 00:00:00',NULL,NULL,0,'0.00',0);
/*!40000 ALTER TABLE `physicalmachine` ENABLE KEYS */;


--
-- Definition of table `rack`
--

DROP TABLE IF EXISTS `rack`;
CREATE TABLE `rack` (
  `idRack` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `idDataCenter` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `shortDescription` varchar(30) DEFAULT NULL,
  `largeDescription` varchar(100) DEFAULT NULL,
  `idUserCreataion` int(10) unsigned NOT NULL,
  `creatioNDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idRack`),
  KEY `Rack_FK1` (`idDataCenter`),
  KEY `Rack_FK2` (`idUserCreataion`),
  KEY `Rack_FK3` (`idUser_lastModification`),
  CONSTRAINT `Rack_FK1` FOREIGN KEY (`idDataCenter`) REFERENCES `datacenter` (`idDataCenter`) ON DELETE CASCADE,
  CONSTRAINT `Rack_FK2` FOREIGN KEY (`idUserCreataion`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Rack_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rack`
--

/*!40000 ALTER TABLE `rack` DISABLE KEYS */;
INSERT INTO `rack` (`idRack`,`idDataCenter`,`name`,`shortDescription`,`largeDescription`,`idUserCreataion`,`creatioNDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,1,'myRack','myRack','myRack',1,'2008-02-27 09:00:00',NULL,NULL);
/*!40000 ALTER TABLE `rack` ENABLE KEYS */;


--
-- Definition of table `repository`
--

DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository` (
  `idRepository` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `URL` varchar(50) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idRepository`),
  KEY `Repository_FK1` (`idUserCreation`),
  KEY `Repository_FK2` (`idUser_lastModification`),
  CONSTRAINT `Repository_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `Repository_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `repository`
--

/*!40000 ALTER TABLE `repository` DISABLE KEYS */;
INSERT INTO `repository` (`idRepository`,`name`,`URL`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`) VALUES 
 (1,'Main Repository','c:\\myRepository',1,'2008-11-20 13:28:42',1,NULL);
/*!40000 ALTER TABLE `repository` ENABLE KEYS */;


--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `idRole` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `shortDescription` varchar(20) NOT NULL,
  `largeDescription` varchar(100) DEFAULT NULL,
  `securityLevel` decimal(3,1) unsigned NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`idRole`,`shortDescription`,`largeDescription`,`securityLevel`) VALUES 
 (1,'Public','Public User (not logged)','99.9'),
 (2,'Sys Admin','IT System administrator','1.0'),
 (3,'User','Generic Registered User','2.0');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(20) NOT NULL,
  `key` varchar(100) NOT NULL,
  `expireDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `session`
--

/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


--
-- Definition of table `so`
--

DROP TABLE IF EXISTS `so`;
CREATE TABLE `so` (
  `idSO` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY (`idSO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
  `idState` int(1) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY (`idState`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `state`
--

/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` (`idState`,`description`) VALUES 
 (1,'RUNNING'),
 (2,'PAUSED'),
 (3,'POWERED_OFF'),
 (4,'REBOOTED'),
 (5,'NOT_DEPLOYED'),
 (6,'IN_PROGRESS');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `idUser` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idRole` int(3) unsigned NOT NULL,
  `idEnterprise` int(10) unsigned DEFAULT NULL,
  `user` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `locale` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `active` int(1) unsigned NOT NULL DEFAULT '0',
  `deleted` int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUser`) USING BTREE,
  UNIQUE KEY `user_unique` (`user`) USING BTREE,
  KEY `User_FK1` (`idRole`),
  KEY `User_FK2` (`idUserCreation`),
  KEY `User_FK3` (`idUser_lastModification`) USING BTREE,
  KEY `FK1_user` (`idEnterprise`),
  CONSTRAINT `FK1_user` FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`),
  CONSTRAINT `User_FK1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idUser`,`idRole`,`idEnterprise`,`user`,`name`,`surname`,`description`,`email`,`locale`,`password`,`idUserCreation`,`creationDate`,`idUser_lastModification`,`lastModificationDate`,`active`,`deleted`) VALUES 
 (1,2,NULL,'admin','System','Administrator','Main administrator','-','en_US','xabiquo',1,'2008-10-20 00:00:00',1,NULL,1,0),
 (2,3,NULL,'user','Standard','User','Standard user','-','en_US','xuser',1,'2008-10-20 00:00:00',1,NULL,1,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `virtualapp`
--

DROP TABLE IF EXISTS `virtualapp`;
CREATE TABLE `virtualapp` (
  `idVirtualApp` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idVirtualDataCenter` int(10) unsigned NOT NULL,
  `name` varchar(30) NOT NULL,
  `public` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idState` int(1) unsigned NOT NULL,
  `high_disponibility` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `error` int(1) unsigned NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idVirtualApp`),
  KEY `VirtualApp_FK1` (`idState`),
  KEY `VirtualApp_FK2` (`idUserCreation`),
  KEY `VirtualApp_FK3` (`idUser_lastModification`),
  KEY `VirtualApp_FK4` (`idVirtualDataCenter`),
  CONSTRAINT `VirtualApp_FK1` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `VirtualApp_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `VirtualApp_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`),
  CONSTRAINT `VirtualApp_FK4` FOREIGN KEY (`idVirtualDataCenter`) REFERENCES `virtualdatacenter` (`idVirtualDataCenter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `virtualapp`
--

/*!40000 ALTER TABLE `virtualapp` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualapp` ENABLE KEYS */;


--
-- Definition of table `virtualdatacenter`
--

DROP TABLE IF EXISTS `virtualdatacenter`;
CREATE TABLE `virtualdatacenter` (
  `idVirtualDataCenter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idEnterprise` int(10) unsigned NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`idVirtualDataCenter`),
  KEY `virtualDataCenter_FK1` (`idEnterprise`),
  KEY `virtualDataCenter_FK2` (`idUserCreation`),
  KEY `virtualDataCenter_FK3` (`idUser_lastModification`),
  CONSTRAINT `virtualDataCenter_FK1` FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`),
  CONSTRAINT `virtualDataCenter_FK2` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `virtualDataCenter_FK3` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `virtualdatacenter`
--

/*!40000 ALTER TABLE `virtualdatacenter` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualdatacenter` ENABLE KEYS */;


--
-- Definition of table `virtualimage`
--

DROP TABLE IF EXISTS `virtualimage`;
CREATE TABLE `virtualimage` (
  `idImage` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `pathName` varchar(100) NOT NULL,
  `idSO` int(3) unsigned DEFAULT NULL,
  `hd_required` int(11) DEFAULT NULL,
  `ram_required` int(7) unsigned DEFAULT NULL,
  `cpu_required` decimal(6,2) DEFAULT NULL,
  `idCategory` int(3) unsigned NOT NULL,
  `treaty` int(1) NOT NULL COMMENT '0-No 1-Yes',
  `idRepository` int(3) unsigned NOT NULL,
  `idIcon` int(4) unsigned NOT NULL,
  `deleted` int(1) unsigned NOT NULL COMMENT '0-No 1-Yes',
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `imageType` int(3) NOT NULL,
  PRIMARY KEY (`idImage`),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `virtualimage`
--

/*!40000 ALTER TABLE `virtualimage` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualimage` ENABLE KEYS */;


--
-- Definition of table `virtualmachine`
--

DROP TABLE IF EXISTS `virtualmachine`;
CREATE TABLE `virtualmachine` (
  `idVM` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idHypervisor` int(2) unsigned NOT NULL,
  `idImage` int(4) unsigned NOT NULL,
  `UUID` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `ram` int(7) unsigned DEFAULT NULL,
  `cpu` decimal(6,2) unsigned DEFAULT NULL,
  `hd` int(10) unsigned DEFAULT NULL,
  `vdrpPort` int(5) unsigned DEFAULT NULL,
  `vdrpIP` varchar(39) DEFAULT NULL,
  `idState` int(1) unsigned NOT NULL,
  `high_disponibility` int(1) unsigned NOT NULL,
  PRIMARY KEY (`idVM`),
  KEY `VirtualMachine_FK1` (`idHypervisor`),
  KEY `virtualMachine_FK2` (`idState`),
  KEY `virtualMachine_FK3` (`idImage`),
  CONSTRAINT `virtualMachine_FK1` FOREIGN KEY (`idHypervisor`) REFERENCES `hypervisor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `virtualMachine_FK2` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`),
  CONSTRAINT `virtualMachine_FK3` FOREIGN KEY (`idImage`) REFERENCES `virtualimage` (`idImage`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `virtualmachine`
--

/*!40000 ALTER TABLE `virtualmachine` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualmachine` ENABLE KEYS */;


--
-- Definition of table `virtualmachinenetworkmodule`
--

DROP TABLE IF EXISTS `virtualmachinenetworkmodule`;
CREATE TABLE `virtualmachinenetworkmodule` (
  `idVM` int(10) unsigned NOT NULL DEFAULT '0',
  `idModule` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idVM`,`idModule`),
  CONSTRAINT `VirtualmachineNetworkModule_FK1` FOREIGN KEY (`idVM`) REFERENCES `virtualmachine` (`idVM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
