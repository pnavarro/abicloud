mkdir %1\tmp
cd %1\tmp

if not Exist C:\external\tomcat.zip goto :download       
copy C:\external\tomcat.zip %1\tmp\tomcat.zip
goto :nodownload

:download 
%1\wget-win\wget.exe --timeout=20 http://ftp.udc.es/apache-dist/tomcat/tomcat-6/v6.0.18/bin/apache-tomcat-6.0.18.zip -O tomcat.zip

:nodownload
%1\unzip-win\unzip.exe tomcat.zip
move apache-tomcat-* ..\tomcat

cd ..\tomcat
bin\service.bat install
