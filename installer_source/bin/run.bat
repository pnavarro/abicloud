cd tomcat\bin
start /b startup.bat
IF NOT EXIST ..\..\mysql GOTO NOMYSQL
cd ..\..\mysql\
start /b bin\mysqld --defaults-file=my.ini
:NOMYSQL
