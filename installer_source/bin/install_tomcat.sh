#!/bin/bash

mkdir $1/tmp
cd $1/tmp

if [ -f /opt/external/tomcat.tgz ]; then
	cp /opt/external/tomcat.tgz $1/tmp/tomcat.tgz
else
	wget --timeout=20 http://ftp.udc.es/apache-dist/tomcat/tomcat-6/v6.0.18/bin/apache-tomcat-6.0.18.tar.gz -O tomcat.tgz
fi

tar -xzf tomcat.tgz
mv apache-tomcat* ../tomcat

exit 0
