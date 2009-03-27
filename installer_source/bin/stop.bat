cd tomcat\bin
start /b shutdown.bat
IF NOT EXIST ..\..\mysql GOTO NOMYSQL
cd ..\..\mysql\
bin\mysqladmin --defaults-file=my.ini -u root shutdown
:NOMYSQL
