mkdir %1\tmp
cd %1\tmp

if not Exist C:\external\mysql.zip goto :download 
copy C:\external\mysql.zip %1\tmp\mysql.zip
goto :nodownload

:download
%1\wget-win\wget.exe --timeout=20 http://dev.mysql.com/get/Downloads/MySQL-5.1/mysql-noinstall-5.1.31-win32.zip/from/http://mysql.rediris.es/ -O mysql.zip

:nodownload
%1\unzip-win\unzip.exe mysql.zip

move mysql-* ..\mysql

copy %1\template\my.ini %1\mysql\

exit
