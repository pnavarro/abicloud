/**
 *  This sql script adds a few entries in some of the tables in the kinton database so that they can be used during development
 */


-- Table: physicalmachine
INSERT INTO physicalmachine 
	VALUES(2,1,1,'VMWare ESXi','',1024,1,42949672960,2,1,'2009-02-17 15:39:16',NULL,NULL,256,0,2147483648);


-- Table: hypervisortype
INSERT INTO hypervisortype VALUES(2,'vmx-04',443);


-- Table: hypervisor
INSERT INTO hypervisor VALUES (2,'vmx-04 - 192.168.1.34:443',2,2,'192.168.1.34',443,1,'2009-02-17 15:39:16',NULL,NULL);


-- Table: icon
INSERT INTO icon 
	VALUES(1,'http://bestwindowssoftware.org/icon/ubuntu_icon.png','ubuntu',1,'2009-02-17 16:00:06',NULL,NULL),
          (2,'http://www.pixeljoint.com/files/icons/mipreview1.gif','Guybrush',1,'2009-02-17 16:01:47',NULL,NULL);
          

-- Table: imagetype
INSERT INTO imagetype VALUES(2,'VMDK',2);


-- Table: networkmodule
INSERT INTO networkmodule VALUES(2,2,0,'192.168.1.34','','0.00',1,'2009-02-17 15:39:16',NULL,NULL,'');


-- Table: virtualimage
INSERT INTO virtualimage 
	VALUES(1,'ubuntu vmdk','ubuntu','ubuntu810desktop/ubuntu810desktop.vmdk',2,8589934592,512,1,1,0,1,1,0,1,'2009-02-17 16:00:09',NULL,NULL,2), 	
    	  (2,'Nostalgia','Nostalgia','/Nostalgia/Nostalgia.vmdk',2,107374080,64,1,1,0,1,2,0,1,'2009-02-17 16:01:49',NULL,NULL,2),   	
    	  (3,'Load Balander','Load Balander','/debian-lb/debian-lb.vmdk',2,1073741824,128,1,1,0,1,1,0,1,'2009-02-17 16:01:49',NULL,NULL,2),    	
    	  (4,'Wordpress','Wordpress','/debian-apache/debian-apache.vmdk',2,1073741824,128,1,1,0,1,1,0,1,'2009-02-17 16:01:49',NULL,NULL,2);   		
