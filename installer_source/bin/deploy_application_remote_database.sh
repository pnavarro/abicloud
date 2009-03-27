#!/bin/bash

# $2 = username
# $3 = password
# $4 = database name
# $5 = database host
# $6 = database port

$1/bin/mysql -u $2 --password=$3 -h $5 -P $6 $3 < $1/kinton.sql

cp $1/application/*.war $1/tomcat/webapps/
cp -r $1/application/abicloud/ $1/tomcat/webapps/

$1/tomcat/bin/startup.sh
sleep 10
$1/tomcat/bin/shutdown.sh

mkdir -p $1/tomcat/webapps/icons

exit 0
