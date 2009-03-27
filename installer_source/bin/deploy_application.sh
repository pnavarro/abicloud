#!/bin/bash

cd $1/mysql
./bin/mysqld_safe --defaults-file=my.cnf &

sleep 5

./bin/mysql --defaults-file=my.cnf -u root  -e "create database kinton;" &>/tmp/mysql.log
./bin/mysql --defaults-file=my.cnf -u root  -e "grant all privileges on kinton.* to cloud@localhost identified by 'cloudpass'" &>/tmp/mysql.log
./bin/mysql --defaults-file=my.cnf -u root  kinton < $1/kinton.sql &>/tmp/mysql.log

cp $1/application/*.war $1/tomcat/webapps/
cp -r $1/application/abicloud/ $1/tomcat/webapps/

$1/tomcat/bin/startup.sh
sleep 10
$1/tomcat/bin/shutdown.sh


mkdir -p $1/tomcat/webapps/icons

kill -15  `cat $1/mysql/data/*.pid`

exit 0
