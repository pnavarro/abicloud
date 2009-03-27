%1\mysql -u %2 --password=%3 -h %5 -P %6 %4 < %1\kinton.sql

copy /Y %1\application\abicloud_server.war %1\tomcat\webapps\
copy /Y %1\application\abicloud_WS.war %1\tomcat\webapps\
xcopy /E /Y %1\application\abicloud %1\tomcat\webapps\abicloud\

mkdir %1\tomcat\webapps\abicloud_server\
cd %1\tomcat\webapps\abicloud_server\
%1\unzip-win\unzip.exe -o %1\tomcat\webapps\abicloud_server.war

mkdir %1\tomcat\webapps\abicloud_WS
cd %1\tomcat\webapps\abicloud_WS
%1\unzip-win\unzip.exe -o %1\tomcat\webapps\abicloud_WS.war
