##Adding enterprise table
DROP TABLE IF EXISTS `virtualdatacenter`;
DROP TABLE IF EXISTS `enterprise`;
DROP TABLE IF EXISTS `log`;

CREATE TABLE `enterprise` (
  `idEnterprise` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `idUserCreation` int(10) unsigned NOT NULL,
  `creationDate` datetime NOT NULL,
  `idUser_lastModification` int(10) unsigned DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `deleted` int(1) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`idEnterprise`),
  KEY `enterprise_FK1` (`idUserCreation`),
  KEY `enterprise_FK2` (`idUser_lastModification`),
  CONSTRAINT `enterprise_FK1` FOREIGN KEY (`idUserCreation`) REFERENCES `user` (`idUser`),
  CONSTRAINT `enterprise_FK2` FOREIGN KEY (`idUser_lastModification`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##Adding virtual app table
CREATE TABLE `log` (
  `idLog` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idVirtualApp` int(10) unsigned NOT NULL,
  `description` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`idLog`),
  KEY `log_FK1` (`idVirtualApp`),
  CONSTRAINT `log_FK1` FOREIGN KEY (`idVirtualApp`) REFERENCES `virtualapp` (`idVirtualApp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##Adding virtualDataCenter table
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

##Adding user changes
ALTER TABLE user ADD idEnterprise int(10) unsigned DEFAULT NULL AFTER idRole;
ALTER TABLE user ADD deleted int(1) unsigned NOT NULL DEFAULT 0 AFTER active;
ALTER TABLE user ADD CONSTRAINT FK1_user FOREIGN KEY (`idEnterprise`) REFERENCES `enterprise` (`idEnterprise`);

##Adding virtual app changes
ALTER TABLE virtualapp ADD error int(1) unsigned NOT NULL AFTER high_disponibility;
ALTER TABLE virtualapp ADD idVirtualDataCenter int(10) unsigned NOT NULL AFTER idVirtualApp;
ALTER TABLE virtualapp ADD CONSTRAINT `VirtualApp_FK4` FOREIGN KEY (`idVirtualDataCenter`) REFERENCES `virtualdatacenter` (`idVirtualDataCenter`);

commit;