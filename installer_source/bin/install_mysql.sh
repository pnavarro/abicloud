#!/bin/bash
adduser mysql

mkdir $1/tmp
cd $1/tmp

if [ -f /opt/external/mysql.tgz ]; then
	cp /opt/external/mysql.tgz $1/tmp/mysql.tgz
else
	wget --timeout=20 http://dev.mysql.com/get/Downloads/MySQL-5.1/mysql-5.1.31-linux-i686-glibc23.tar.gz/from/ftp://ftp.fu-berlin.de/unix/databases/mysql/ -O mysql.tgz
fi

tar -xzf mysql.tgz
mv mysql-* ../mysql

cd $1/mysql
cp $1/template/my.cnf .
scripts/mysql_install_db --ldata=$1/mysql/data/ --datadir=$1/mysql/data/ --defaults-file=$1/mysql/my.cnf

chown -R mysql $1/mysql

exit 0
