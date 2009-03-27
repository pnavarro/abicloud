cd %1\mysql
start /b bin\mysqld --defaults-file=my.ini

copy /Y %1\application\abicloud_server.war %1\tomcat\webapps\
copy /Y %1\application\abicloud_WS.war %1\tomcat\webapps\
xcopy /E /Y %1\application\abicloud %1\tomcat\webapps\abicloud\

mkdir %1\tomcat\webapps\abicloud_server\
cd %1\tomcat\webapps\abicloud_server\
%1\unzip-win\unzip.exe -o %1\tomcat\webapps\abicloud_server.war

mkdir %1\tomcat\webapps\abicloud_WS
cd %1\tomcat\webapps\abicloud_WS
%1\unzip-win\unzip.exe -o %1\tomcat\webapps\abicloud_WS.war


cd %1\mysql
bin\mysql --defaults-file=my.ini -v -u root -e "create database kinton;" >> c:\log
bin\mysql --defaults-file=my.ini -v -u root -e "grant all privileges on kinton.* to cloud@localhost identified by 'cloudpass'" >> c:\log
bin\mysql --defaults-file=my.ini -v -u root kinton < %1\kinton.sql
bin\mysqladmin --defaults-file=my.ini -u root shutdown
